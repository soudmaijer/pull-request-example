package nl.sourcelabs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BoatTripApp {
    private final BoatTripService boatTripService;
    private final List<BoatTrip> boatTrips = new ArrayList<>();
    private boolean running;

    public BoatTripApp() {
        this.boatTripService = new BoatTripService(new BoatTripRepository());
        Thread tripRunner = new Thread(() -> {
            running = true;

            while (running) {
                for (BoatTrip boatTrip : boatTrips) {
                    System.out.println("BoatTrip-" + boatTrip.getBoatTripId() + " active for: " + boatTrip.getDuration().getSeconds() + " seconds.");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tripRunner.start();
    }

    public void printLogo() {
        String logo = "\n\n     .  o ..                  \n" +
                "     o . o o.o                \n" +
                "          ...oo               \n" +
                "            __[]__            \n" +
                "         __|_o_o_o\\__         \n" +
                "         \\\"\"\"\"\"\"\"\"\"\"/         \n" +
                "          \\. ..  . /          \n" +
                "     ^^^^^^^^^^^^^^^^^^^^   \n\n";
        System.out.println(logo);
    }

    public String startBoatTrip() {
        BoatTrip boatTrip = boatTripService.startBoatTrip();
        boatTrips.add(boatTrip);

        System.out.println("BoatTrip-" + boatTrip.getBoatTripId() + " started!");
        return boatTrip.getBoatTripId();
    }

    public void stopBoatTrip(String boatTripId) {
        boatTripService.stopBoatTrip(boatTripId);
        boatTrips.removeIf(it -> it.getBoatTripId().equals(boatTripId));

        System.out.println("BoatTrip-" + boatTripId + " stopped!");
        System.out.println("BoatTrip-" + boatTripId + " was active for: " + boatTripService.getDurationInSeconds(boatTripId) + " second(s).");
    }

    public void printSummaryOfToday() {
        System.out.println("\n----------------------");
        System.out.println("Summary for " + LocalDate.now().toString());
        System.out.println("----------------------\n");
        boatTripService.getTripsFromToday(LocalDate.now()).forEach(it ->
                System.out.println("BoatTrip-" + it.getBoatTripId() + " duration: " + it.getDuration().getSeconds() + " seconds.")
        );
    }

    private void shutdown() {
        running = false;
    }

    public static void main(String[] args) throws InterruptedException {
        BoatTripApp boatTripApp = new BoatTripApp();
        boatTripApp.printLogo();

        String boatTripId1 = boatTripApp.startBoatTrip();

        // Wait 2 seconds.
        Thread.sleep(2 * 1000);

        String boatTripId2 = boatTripApp.startBoatTrip();

        // Wait 2 seconds.
        Thread.sleep(2 * 1000);

        boatTripApp.stopBoatTrip(boatTripId1);

        // Wait 2 seconds.
        Thread.sleep(2 * 1000);

        boatTripApp.stopBoatTrip(boatTripId2);

        boatTripApp.printSummaryOfToday();

        boatTripApp.shutdown();
    }
}

package nl.sourcelabs;

import java.util.ArrayList;
import java.util.List;

public class BoatTripApp {
    private final BoatTripService boatTripService;
    private final List<BoatTrip> boatTrips = new ArrayList<>();
    private boolean running;

    public BoatTripApp() {
        this.boatTripService = new BoatTripService(new JdbcBoatTripRepository());
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

    private void shutdown() {
        running = false;
    }

    public static void main(String[] args) throws InterruptedException {
        BoatTripApp boatTripApp = new BoatTripApp();

        String boatTripId1 = boatTripApp.startBoatTrip();

        // Wait 5 seconds.
        Thread.sleep(5 * 1000);

        String boatTripId2 = boatTripApp.startBoatTrip();

        // Wait 5 seconds.
        Thread.sleep(5 * 1000);

        boatTripApp.stopBoatTrip(boatTripId1);

        // Wait 5 seconds.
        Thread.sleep(5 * 1000);

        boatTripApp.stopBoatTrip(boatTripId2);

        boatTripApp.shutdown();
    }
}

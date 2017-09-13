package nl.sourcelabs;

public class BoatTripApp {

    public static void main(String[] args) throws InterruptedException {
        BoatTripService boatTripService = new BoatTripService(new BoatTripRepository());
        String boatTripId1 = boatTripService.startBoatTrip();
        System.out.println("BoatTrip started with boatTripId: " + boatTripId1);
        String boatTripId2 = boatTripService.startBoatTrip();
        System.out.println("BoatTrip started with boatTripId: " + boatTripId1);

        // Wait 5 seconds.
        Thread.sleep(5 * 1000);

        boatTripService.stopBoatTrip(boatTripId1);
        System.out.println("BoatTrip stopped with boatTripId: " + boatTripId1);
        System.out.println("Duration of BoatTrip with boatTripId: " + boatTripId1 + ", seconds: " + boatTripService.getDurationInSeconds(boatTripId1));

        // Wait 5 seconds.
        Thread.sleep(5 * 1000);

        boatTripService.stopBoatTrip(boatTripId2);
        System.out.println("BoatTrip stopped with boatTripId: " + boatTripId1);
        System.out.println("Duration of BoatTrip with boatTripId: " + boatTripId2 + ", seconds: " + boatTripService.getDurationInSeconds(boatTripId2));
    }
}

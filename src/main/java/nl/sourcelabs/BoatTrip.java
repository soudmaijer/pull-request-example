package nl.sourcelabs;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a bookable boat trip.
 */
public class BoatTrip {

    private LocalDateTime startTime, endTime;
    private String boatTripId;
    private boolean active = false;
    private static final AtomicInteger boatIdGenerator = new AtomicInteger();
    private Thread tripRunner = new Thread(() -> {
        active = true;
        while (active) {
            System.out.println("BoatTrip with boatTripId: " + boatTripId + " active for: " + getDuration().getSeconds() + " seconds.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public BoatTrip() {
        boatTripId = String.valueOf(boatIdGenerator.incrementAndGet());
    }

    public String start() {
        if (startTime != null) {
            throw new BoatTripException("Cannot start BoatTrip, already started!");
        }
        if (endTime != null) {
            throw new BoatTripException("Cannot start BoatTrip, already completed!");
        }
        startTime = LocalDateTime.now();
        tripRunner.start();
        return boatTripId;
    }

    public void stop() {
        if (startTime == null) {
            throw new BoatTripException("Cannot stop BoatTrip, not started yet!");
        }
        if (startTime == null) {
            throw new BoatTripException("Cannot stop BoatTrip, already completed!");
        }
        active = false;
        endTime = LocalDateTime.now();
    }

    public Duration getDuration() {
        if (endTime == null) {
            return Duration.between(startTime, LocalDateTime.now());
        }
        return Duration.between(startTime, endTime);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getBoatTripId() {
        return boatTripId;
    }
}

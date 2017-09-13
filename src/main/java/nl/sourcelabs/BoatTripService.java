package nl.sourcelabs;

import java.time.LocalDate;
import java.util.List;

public class BoatTripService {

    private BoatTripRepository repository;

    public BoatTripService(BoatTripRepository repository) {
        this.repository = repository;
    }

    public BoatTrip startBoatTrip() {
        BoatTrip bt = new BoatTrip();
        bt.start();
        repository.add(bt);
        return bt;
    }

    public void stopBoatTrip(String boatTripId) {
        BoatTrip bt = repository.get(boatTripId);
        if (bt == null) {
            throw new BoatTripException("BoatTrip not found for boatTripId: " + boatTripId);
        }
        bt.stop();
    }

    public Long getDurationInSeconds(String boatTripId) {
        BoatTrip bt = repository.get(boatTripId);
        if (bt == null) {
            throw new BoatTripException("BoatTrip not found for boatTripId: " + boatTripId);
        }
        return bt.getDuration().getSeconds();
    }


    public List<BoatTrip> getTripsFromToday(LocalDate now) {
        return repository.getTripsOnDate(LocalDate.now());
    }
}

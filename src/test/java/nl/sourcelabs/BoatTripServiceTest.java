package nl.sourcelabs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoatTripServiceTest {

    private BoatTripService service;

    @Before
    public void before() {
        service = new BoatTripService(new BoatTripRepository());
    }

    @Test
    public void startBoatTrip() throws Exception {
        BoatTrip boatTrip = service.startBoatTrip();
        Assert.assertNotNull(boatTrip);
        Assert.assertTrue(boatTrip.isActive());
    }

    @Test
    public void stopBoatTrip() throws Exception {
        BoatTrip boatTrip = service.startBoatTrip();
        Assert.assertNotNull(boatTrip);
        boatTrip.stop();
        Assert.assertFalse(boatTrip.isActive());
    }

    @Test
    public void getDurationInSeconds() throws Exception {
        BoatTrip boatTrip = service.startBoatTrip();
        Assert.assertTrue(service.getDurationInSeconds(boatTrip.getBoatTripId()) >= 0);
    }

    @Test
    public void getTripsFromToday() throws Exception {
        service.startBoatTrip();
        service.startBoatTrip();
        Assert.assertEquals(2, service.getTripsFromToday().size());
    }

}
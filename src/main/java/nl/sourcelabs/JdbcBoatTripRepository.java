package nl.sourcelabs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcBoatTripRepository extends BoatTripRepository {

    private Map<String, BoatTrip> map = new ConcurrentHashMap<>();

    public void add(BoatTrip bt) {
        map.put(bt.getBoatTripId(), bt);
    }

    public BoatTrip get(String id) {
        return map.get(id);
    }
}

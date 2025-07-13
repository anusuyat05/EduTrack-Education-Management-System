package dao;

import java.util.List;
import java.util.Map;

public interface BatchVenueDAO {
    boolean isVenueExists(int venueId, String venueDetail);

    boolean add(String venueDetail);

    boolean delete(int venueId);

    boolean updateVenue(int venueId, String venueDetail);

    List<Map<String, Object>> getAllBatchVenues();
}

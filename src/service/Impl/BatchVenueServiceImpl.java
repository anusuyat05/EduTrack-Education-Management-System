package service.Impl;

import dao.BatchVenueDAO;
import dao.Impl.BatchVenueDAOImpl;
import service.BatchVenueService;

import java.util.List;
import java.util.Map;

public class BatchVenueServiceImpl implements BatchVenueService {
    BatchVenueDAO batchVenueDAO = new BatchVenueDAOImpl();
    //Adds a new venue detail to the system.
    @Override
    public String add(Map<String, Object> addValues) {
        StringBuilder errors = new StringBuilder();
        String venueDetail = (String) addValues.get("venueDetail");

        if (venueDetail == null || venueDetail.trim().isEmpty()) {
            errors.append("Venue detail cannot be empty.\n");
        }

        if (batchVenueDAO.isVenueExists(0,venueDetail)) {
            errors.append("Venue already exists.\n");
        }
        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }
        boolean success = batchVenueDAO.add(venueDetail);
        return success ? "Venue added successfully!" : "Failed to add venue.";
    }

    //Deletes an existing venue by ID.
    @Override
    public boolean delete(Map<String, Object> deleteValues) {
        Integer venueId = (Integer) deleteValues.get("venueId");

        if (venueId == null) {
            return false;
        }
        if (!batchVenueDAO.isVenueExists(venueId,null)) {
                return false;
        }
        return batchVenueDAO.delete(venueId);
    }

    //Updates an existing venue’s detail.
    @Override
    public String update(Map<String, Object> updateValues) {
        StringBuilder errors = new StringBuilder();

        Integer venueId = (Integer) updateValues.get("id");
        if (venueId == null) {
            errors.append("Venue ID cannot be null.\n");
        }

        String venueDetail = (String) updateValues.get("venueDetail");
        if (venueDetail == null || venueDetail.trim().isEmpty()) {
            errors.append("Venue detail cannot be empty.\n");
        }

        if (venueId != null && !batchVenueDAO.isVenueExists(venueId,null)) {
            errors.append("Venue with the provided ID does not exist.\n");
        }

        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }

        boolean success = batchVenueDAO.updateVenue(venueId, venueDetail);
        return success ? "Venue updated successfully!" : "Failed to update venue.";
    }

    //Retrieves all venue records from the database.
    @Override
    public List<Map<String, Object>> getAllBatchVenues() {
        return batchVenueDAO.getAllBatchVenues();
    }
}

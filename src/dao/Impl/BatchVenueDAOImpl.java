package dao.Impl;

import dao.BatchVenueDAO;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;

public class BatchVenueDAOImpl implements BatchVenueDAO {
    /**
     * Checks whether a venue with the given ID or venue detail already exists in the database.
     * @param venueId     the ID of the venue to check
     * @param venueDetail the detail or description of the venue
     * @return true if the venue exists, false otherwise
     */
    @Override
    public boolean isVenueExists(int venueId, String venueDetail) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM batch_venue WHERE id = " + venueId + " OR venue_detail = '" + venueDetail + "'";
            resultSet = statement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return false;
    }

    /**
     * Adds a new venue to the batch_venue table.
     * @param venueDetail the detail or description of the venue to be added
     * @return true if the venue was added successfully, false otherwise
     */
    @Override
    public boolean add(String venueDetail) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "INSERT INTO batch_venue(venue_detail) VALUES ('" + venueDetail + "')";
            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Deletes a venue from the database based on its ID.
     * @param venueId the ID of the venue to delete
     * @return true if the venue was deleted successfully, false otherwise
     */
    @Override
    public boolean delete(int venueId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM batch_venue WHERE id = " + venueId;
            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Updates the venue detail for a specific venue ID in the database.
     * @param venueId     the ID of the venue to update
     * @param venueDetail the new detail or description to set for the venue
     * @return true if the venue was updated successfully, false otherwise
     */
    @Override
    public boolean updateVenue(int venueId, String venueDetail) {
        Connection connection = null;
        Statement statement = null;
        String query = "UPDATE batch_venue SET venue_detail = '" + venueDetail + "' WHERE id = " + venueId;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            return statement.executeUpdate(query) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Retrieves all venues from the batch_venue table.
     * @return a list of maps, where each map represents a venue with its ID and details
     */
    @Override
    public List<Map<String, Object>> getAllBatchVenues() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, venue_detail FROM batch_venue";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<Map<String, Object>> venues = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> venue = new HashMap<>();
                venue.put("id", resultSet.getInt("id"));
                venue.put("venueDetail", resultSet.getString("venue_detail"));

                venues.add(venue);
            }
            return venues;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }
}

package dao.Impl;

import dao.BatchDAO;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class BatchDAOImpl implements BatchDAO {
    /**
     * Checks whether a batch exists in the database by ID or name.
     * @param batchId   the ID of the batch to check
     * @param batchName the name of the batch to check
     * @return true if a batch exists with the given ID or name, false otherwise
     */
    @Override
    public boolean isBatchExists(int batchId, String batchName) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM batch WHERE id = " + batchId + " OR name = '" + batchName + "'";
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
     * Deletes a batch from the database by its ID.
     * @param batchId the ID of the batch to delete
     * @return true if the batch was deleted successfully, false otherwise
     */
    @Override
    public boolean delete(int batchId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM batch WHERE id = " + batchId;
            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Updates specific fields of a batch in the database.
     * @param updateValues a map containing the batch ID and the fields to update
     * @return true if the batch was updated successfully, false otherwise
     */
    @Override
    public boolean update(Map<String, Object> updateValues) {
        Connection connection = null;
        Statement statement = null;

        int batchId = (Integer) updateValues.get("id");
        String query = "UPDATE batch SET ";

        if (updateValues.containsKey("courseId"))
            query += "course_id = '" + updateValues.get("courseId") + "', ";
        if (updateValues.containsKey("trainerId"))
            query += "trainer_id = '" + updateValues.get("trainerId") + "', ";
        if (updateValues.containsKey("name"))
            query += "name = '" + updateValues.get("name") + "', ";
        if (updateValues.containsKey("startDate"))
            query += "start_date = '" + updateValues.get("startDate") + "', ";
        if (updateValues.containsKey("endDate"))
            query += "end_date = '" + updateValues.get("endDate") + "', ";
        if (updateValues.containsKey("classMode"))
            query += "class_mode = '" + updateValues.get("classMode") + "', ";
        if (updateValues.containsKey("venueId"))
            query += "venue_id = '" + updateValues.get("venueId") + "', ";
        if (updateValues.containsKey("isActive"))
            query += "is_active = '" + updateValues.get("isActive") + "', ";

        query = query.substring(0, query.length() - 2);
        query += " WHERE id = " + batchId + ";";

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Retrieves all batches from the database.
     * @return a list of maps, where each map contains the details of a batch
     */
    @Override
    public List<Map<String, Object>> getAllBatches() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, course_id, trainer_id, name, start_date, end_date, class_mode, venue_id, is_active FROM batch";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            List<Map<String, Object>> batches = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> batch = new HashMap<>();
                batch.put("id", resultSet.getInt("id"));
                batch.put("courseId", resultSet.getInt("course_id"));
                batch.put("trainerId", resultSet.getInt("trainer_id"));
                batch.put("batchName", resultSet.getString("name"));
                batch.put("startDate", resultSet.getDate("start_date"));
                batch.put("endDate", resultSet.getDate("end_date"));
                batch.put("classMode", resultSet.getString("class_mode"));
                batch.put("venueId", resultSet.getInt("venue_id"));
                batch.put("isActive", resultSet.getBoolean("is_active"));

                batches.add(batch);
            }
            return batches;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }

    /**
     * Adds a new batch to the database.
     * @param courseId   the ID of the course
     * @param trainerId  the ID of the trainer
     * @param batchName  the name of the batch
     * @param startDate  the start date of the batch
     * @param endDate    the end date of the batch
     * @param classMode  the class mode (e.g., "Online", "Classroom")
     * @param venueId    the ID of the venue
     * @param isActive   whether the batch is active
     * @return true if the batch was added successfully, false otherwise
     */
    @Override
    public boolean add(int courseId, int trainerId, String batchName, Date startDate, Date endDate, String classMode, int venueId, Boolean isActive) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String startDateString = "'" + startDate.toString() + "'";
            String endDateString = "'" + endDate.toString() + "'";
            String query = "INSERT INTO batch (course_id, trainer_id, name, start_date, end_date, class_mode, venue_id, is_active) " +
                    "VALUES (" + courseId + ", " + trainerId + ", '" + batchName + "', " + startDateString + ", " + endDateString +
                    ", '" + classMode + "', " + venueId + ", " + isActive + ")";
            return statement.executeUpdate(query) > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }
}

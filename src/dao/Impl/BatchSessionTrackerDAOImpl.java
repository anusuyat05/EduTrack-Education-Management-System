package dao.Impl;

import dao.BatchSessionTrackerDAO;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BatchSessionTrackerDAOImpl implements BatchSessionTrackerDAO {
    /**
     * Checks if a session with the given ID exists in the database.
     * @param sessionId the ID of the session to check
     * @return true if the session exists, false otherwise
     */
    @Override
    public boolean isSessionExists(int sessionId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM batch_session_tracker WHERE id = " + sessionId;
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
     * Updates the fields of a session record in the database.
     * @param updateValues a map containing the session ID and fields to update
     * @return true if the session was updated successfully, false otherwise
     */
    @Override
    public boolean update(Map<String, Object> updateValues) {
        Connection connection = null;
        Statement statement = null;

        int sessionId = (Integer) updateValues.get("id");
        String query = "UPDATE batch_session_tracker SET ";

        if (updateValues.containsKey("batchId"))
            query += "batch_id = '" + updateValues.get("batchId") + "', ";
        if (updateValues.containsKey("topicId"))
            query += "topic_id = '" + updateValues.get("topicId") + "', ";
        if (updateValues.containsKey("sessionDate"))
            query += "session_date = '" + updateValues.get("sessionDate") + "', ";
        if (updateValues.containsKey("status"))
            query += "status = '" + updateValues.get("status") + "', ";

        query = query.substring(0, query.length() - 2);
        query += " WHERE id = " + sessionId + ";";

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
     * Adds a new session to the batch_session_tracker table.
     * @param batchId     the batch ID the session belongs to
     * @param topicId     the topic ID covered in the session
     * @param sessionDate the date on which the session occurs
     * @param status      the status of the session (Pending, Ongoing, Completed)
     * @return true if the session was added successfully, false otherwise
     */
    @Override
    public boolean add(int batchId, int topicId, Date sessionDate, String status) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String sessionDateString = sessionDate.toString();
            String query = "INSERT INTO batch_session_tracker (batch_id, topic_id, session_date, status) " +
                    "VALUES (" + batchId + ", " + topicId + ", '" + sessionDateString + "', '" + status + "')";
            return statement.executeUpdate(query) > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Retrieves all session records from the database.
     * @return a list of maps, where each map represents a session with its details
     */
    @Override
    public List<Map<String, Object>> getAllSessions() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, batch_id, topic_id, session_date, status FROM batch_session_tracker";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            List<Map<String, Object>> sessions = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> session = new HashMap<>();
                session.put("id", resultSet.getInt("id"));
                session.put("batchId", resultSet.getInt("batch_id"));
                session.put("topicId", resultSet.getInt("topic_id"));
                session.put("sessionDate", resultSet.getDate("session_date"));
                session.put("status", resultSet.getString("status"));

                sessions.add(session);
            }
            return sessions;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }

    /**
     * Deletes a session from the database based on its ID.
     * @param batchSessionTrackerId the ID of the session to delete
     * @return true if the session was deleted successfully, false otherwise
     */
    @Override
    public boolean delete(int batchSessionTrackerId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM batch_session_tracker WHERE id = " + batchSessionTrackerId;
            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }
}
package dao.Impl;

import dao.TopicDAO;
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

public class TopicDAOImpl implements TopicDAO {
    /**
     * Adds a new topic to the database.
     * @param topicName the name of the topic
     * @param courseId the ID of the course the topic belongs to
     * @return true if the topic was added successfully, false otherwise
     */
    @Override
    public boolean add(String topicName, int courseId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO topic(name, course_id) VALUES ('" + topicName + "', " + courseId + ")";

            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Retrieves all topics from the database.
     * @return a list of maps, each containing topicId, topicName, and courseId
     */
    @Override
    public List<Map<String, Object>> getAllTopics() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, name, course_id FROM topic";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<Map<String, Object>> topics = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> topic = new HashMap<>();
                topic.put("topicId", resultSet.getInt("id"));
                topic.put("topicName", resultSet.getString("name"));
                topic.put("courseId", resultSet.getInt("course_id"));

                topics.add(topic);
            }
            return topics;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }

    /**
     * Deletes a topic from the database.
     * @param topicId the ID of the topic to delete
     * @return true if the topic was deleted successfully, false otherwise
     */
    @Override
    public boolean delete(int topicId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            String query = "DELETE FROM topic WHERE id = " + topicId;

            return statement.executeUpdate(query) > 0;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Updates topic details in the database.
     * @param updateTopic a map containing updated topic values
     * @return true if the topic was updated successfully, false otherwise
     */
    @Override
    public boolean update(Map<String, Object> updateTopic) {
        Connection connection = null;
        Statement statement = null;

        int id = (Integer) updateTopic.get("id");
        String query = "UPDATE topic SET ";

        if (updateTopic.containsKey("name"))
            query += "name = '" + updateTopic.get("name") + "', ";
        if (updateTopic.containsKey("courseId"))
            query += "course_id = " + updateTopic.get("courseId") + ", ";

        query = query.substring(0, query.length() - 2);
        query += " WHERE id = " + id + ";";

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            return statement.executeUpdate(query.toString()) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Checks if a topic exists in the database.
     * @param topicId the ID of the topic to check
     * @return true if the topic exists, false otherwise
     */
    @Override
    public boolean isTopicExists(int topicId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT 1 FROM topic WHERE id = " + topicId;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }  finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return false;
    }
}
package dao.Impl;

import dao.FreeCourseOfferDAO;
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

public class FreeCourseOfferDAOImpl implements FreeCourseOfferDAO {

    /**
     * Adds a new free course offer by linking a paid course to a free course.
     * @param paidCourseId the ID of the paid course
     * @param freeCourseId the ID of the free course
     * @return true if the offer was successfully added, otherwise false
     */
    @Override
    public boolean addOffer(int paidCourseId, int freeCourseId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO free_course_offer(paid_course_id, free_course_id) VALUES ('" + paidCourseId + "', " + freeCourseId + ")";

            return statement.executeUpdate(query) > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Checks whether an offer already exists between a paid course and a free course.
     * @param paidCourseId the ID of the paid course
     * @param freeCourseId the ID of the free course
     * @return true if the offer exists, otherwise false
     */
    @Override
    public boolean isOfferExists(int paidCourseId, int freeCourseId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM free_course_offer WHERE paid_course_id = '" + paidCourseId + "' AND free_course_id = '" + freeCourseId + "'LIMIT 1";
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
     * Deletes an existing offer from the database based on its ID.
     * @param offerId the ID of the offer to delete
     * @return true if the offer was deleted successfully, otherwise false
     */
    @Override
    public boolean delete(int offerId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM free_course_offer WHERE id = " + offerId;
            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Checks whether a specific offer ID exists in the database.
     * @param offerId the ID of the offer
     * @return true if the offer ID exists, otherwise false
     */
    @Override
    public boolean isOfferIdExists(int offerId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM free_course_offer WHERE id = " + offerId;
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
     * Updates an existing offer with new paid or free course IDs.
     * @param updateValues a map containing values to update (e.g., paidCourseId, freeCourseId)
     * @return true if the offer was updated successfully, otherwise false
     */
    @Override
    public boolean updateOffer(Map<String, Object> updateValues) {
        Connection connection = null;
        Statement statement = null;

        int id = (Integer) updateValues.get("id");

        String query = "UPDATE free_course_offer SET ";

        if (updateValues.containsKey("paidCourseId"))
            query += "paid_course_id = '" + updateValues.get("paidCourseId") + "', ";
        if (updateValues.containsKey("freeCourseId"))
            query += "free_course_id = '" + updateValues.get("freeCourseId") + "', ";

        query = query.substring(0, query.length() - 2);
        query += " WHERE id = " + id + ";";

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
     * Retrieves all course offers from the database.
     * @return a list of all offers, where each offer is represented as a map
     */
    @Override
    public List<Map<String, Object>> getAllOffers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, paid_course_id, free_course_id FROM free_course_offer";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<Map<String, Object>> offers = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> offer = new HashMap<>();
                offer.put("id", resultSet.getInt("id"));
                offer.put("paidCourseId", resultSet.getString("paid_course_id"));
                offer.put("freeCourseId", resultSet.getInt("free_course_id"));

                offers.add(offer);
            }
            return offers;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }
}

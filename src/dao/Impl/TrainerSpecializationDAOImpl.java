package dao.Impl;

import dao.TrainerSpecializationDAO;
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

public class TrainerSpecializationDAOImpl implements TrainerSpecializationDAO {
    /**
     * Checks if a specialization already exists for a trainer.
     * @param trainerId      ID of the trainer
     * @param specialization specialization name
     * @return true if specialization exists, false otherwise
     */
    @Override
    public boolean isSpecializationExists(int trainerId, String specialization) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM trainer_specialization WHERE trainer_id = " + trainerId + " AND specialization = '" + specialization + "' LIMIT 1";
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
     * Adds a specialization for a trainer.
     * @param trainerId      ID of the trainer
     * @param specialization specialization name
     * @return true if insertion was successful, false otherwise
     */
    @Override
    public boolean add(int trainerId, String specialization) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO trainer_specialization (trainer_id, specialization) VALUES (" + trainerId + ", '" + specialization + "')";

            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Updates specialization record by ID.
     * @param updateValues a map with keys like "id", "trainerId", "specialization"
     * @return true if update was successful, false otherwise
     */
    @Override
    public boolean update(Map<String, Object> updateValues) {
        Connection connection = null;
        Statement statement = null;

        int id = (Integer) updateValues.get("id");

        String query = "UPDATE trainer_specialization SET ";

        if (updateValues.containsKey("trainerId"))
            query += "trainer_id = " + updateValues.get("trainerId") + ", ";
        if (updateValues.containsKey("specialization"))
            query += "specialization = '" + updateValues.get("specialization") + "', ";

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
     * Retrieves all trainer specializations.
     * @return list of maps, each representing a specialization record
     */
    @Override
    public List<Map<String, Object>> getAllTrainerSpecialization() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, trainer_id, specialization FROM trainer_specialization";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<Map<String, Object>> specializations = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> specialization = new HashMap<>();
                specialization.put("id", resultSet.getInt("id"));
                specialization.put("trainerId", resultSet.getString("trainer_id"));
                specialization.put("specialization", resultSet.getString("specialization"));

                specializations.add(specialization);
            }
            return specializations;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }

    /**
     * Deletes a specialization by its ID.
     * @param trainerSpecializationId ID of the specialization to delete
     * @return true if deleted, false otherwise
     */
    @Override
    public boolean delete(int trainerSpecializationId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM trainer_specialization WHERE id = " + trainerSpecializationId;
            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Checks if a specialization record exists by ID.
     * @param trainerSpecializationId specialization record ID
     * @return true if exists, false otherwise
     */
    @Override
    public boolean isSpecializationIdExists(int trainerSpecializationId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM trainer_specialization WHERE id = " + trainerSpecializationId;
            resultSet = statement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return false;
    }
}

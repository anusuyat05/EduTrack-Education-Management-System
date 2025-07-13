package dao.Impl;

import dao.TrainerDAO;
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

public class TrainerDAOImpl implements TrainerDAO {
    /**
     * Adds a new trainer to the database.
     * @param username  the username of the trainer
     * @param password  the password for login
     * @param name      the full name of the trainer
     * @param experience number of years of experience
     * @param email     trainer's email
     * @param isAdmin   true if admin, false if regular trainer
     * @return true if insertion was successful, false otherwise
     */
    @Override
    public boolean addTrainer(String username, String password, String name, int experience, String email, Boolean isAdmin) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO trainer (username, password, name, experience, email, is_admin) VALUES ('" + username + "', '" + password + "', '" + name + "', " + experience + ", '" + email + "', " + isAdmin + ")";

            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Checks if a trainer with the given email already exists.
     * @param email the trainer's email to check
     * @return true if email exists, false otherwise
     */
    @Override
    public boolean isEmailExists(String email) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM trainer WHERE email = '" + email + "' LIMIT 1";
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
     * Deletes a trainer from the database.
     * @param trainerId the ID of the trainer to delete
     * @return true if deleted successfully, false otherwise
     */
    @Override
    public boolean delete(int trainerId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM trainer WHERE id = " + trainerId;
            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Checks if a trainer with the given ID exists.
     * @param trainerId the trainer ID to check
     * @return true if the trainer exists, false otherwise
     */
    @Override
    public boolean isTrainerExists(int trainerId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM trainer WHERE id = " + trainerId + " LIMIT 1";
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
     * Updates trainer details based on provided fields.
     * @param updateValues a map containing fields to update with their new values
     * @return true if update was successful, false otherwise
     */
    @Override
    public boolean update(Map<String, Object> updateValues) {
        Connection connection = null;
        Statement statement = null;

        int id = (Integer) updateValues.get("id");
        String query = "UPDATE trainer SET ";
        if (updateValues.containsKey("name"))
            query += "name = '" + updateValues.get("name") + "', ";
        if (updateValues.containsKey("username"))
            query += "username = '" + updateValues.get("username") + "', ";
        if (updateValues.containsKey("password"))
            query += "password = '" + updateValues.get("password") + "', ";
        if (updateValues.containsKey("experience"))
            query += "experience = " + updateValues.get("experience") + ", ";
        if (updateValues.containsKey("isAdmin"))
            query += "is_admin = " + updateValues.get("isAdmin") + ", ";
        if (updateValues.containsKey("email"))
            query += "email = '" + updateValues.get("email") + "', ";

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
     * Retrieves all trainers from the database.
     * @return a list of maps, each containing trainer details
     */
    @Override
    public List<Map<String, Object>> getAllTrainers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, username, name, experience, email, is_admin FROM trainer";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            List<Map<String, Object>> trainers = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> trainer = new HashMap<>();
                trainer.put("id", resultSet.getInt("id"));
                trainer.put("username", resultSet.getString("username"));
                trainer.put("name", resultSet.getString("name"));
                trainer.put("experience", resultSet.getInt("experience"));
                trainer.put("email", resultSet.getString("email"));
                trainer.put("isAdmin", resultSet.getBoolean("is_admin"));

                trainers.add(trainer);
            }
            return trainers;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }

        return Collections.emptyList();

    }

    /**
     * Authenticates a user and returns their role.
     * @param username the trainer's username
     * @param password the trainer's password
     * @return "Admin", "Trainer", or null if invalid credentials
     */
    @Override
    public String getUserRole(String username, String password) {
        String role = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT password, is_admin FROM trainer WHERE username = '" + username + "'";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                if (password.equals(resultSet.getString("password"))) {
                    boolean isAdmin = resultSet.getBoolean("is_admin");
                    role = isAdmin ? "Admin" : "Trainer";
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return role.isEmpty() ? null : role;
    }
}

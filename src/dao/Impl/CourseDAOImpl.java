package dao.Impl;

import dao.CourseDAO;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CourseDAOImpl implements CourseDAO {
    /**
     * Adds a new course to the database.
     * @param courseName        the name of the course
     * @param courseFee         the fee for the course
     * @param isPaid            whether the course is paid (true) or free (false)
     * @param courseDescription a brief description of the course
     * @return true if the course was successfully added, otherwise false
     */
    @Override
    public boolean addCourse (String courseName,int courseFee,boolean isPaid, String courseDescription) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO course(name, fee, is_paid, description) VALUES ('" + courseName + "', " + courseFee + ", " + isPaid + ", '" + courseDescription + "')";

            return statement.executeUpdate(query) > 0;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Updates one or more fields of a course based on the given values.
     * @param updateCourse a map containing course fields to update (e.g., name, fee, description)
     * @return true if the course was updated successfully, otherwise false
     */
    @Override
    public boolean updateCourse(Map<String, Object> updateCourse) {
        Connection connection = null;
        Statement statement = null;

        int id = (Integer) updateCourse.get("id");
        String query = "UPDATE course SET ";

        if (updateCourse.containsKey("name"))
            query += "name = '" + updateCourse.get("name") + "', ";
        if (updateCourse.containsKey("fee"))
            query += "fee = '" + updateCourse.get("fee") + "', ";
        if (updateCourse.containsKey("isPaid"))
            query += "is_paid = '" + updateCourse.get("isPaid") + "', ";
        if (updateCourse.containsKey("description"))
            query += "description = '" + updateCourse.get("description") + "', ";

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
     * Checks if a given course is marked as paid in the database.
     * @param courseId the ID of the course
     * @return true if the course is paid, otherwise false
     */
    @Override
    public boolean isCoursePaid(int courseId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM course WHERE id = '" + courseId + "'AND is_paid = true LIMIT 1";
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
     * Checks if a course with the given ID or name already exists in the database.
     * @param courseId   the ID of the course
     * @param courseName the name of the course
     * @return true if the course exists, otherwise false
     */
    @Override
    public boolean isCourseExists(int courseId, String courseName) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM course WHERE id = " + courseId + " OR name = '" + courseName + "'";
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
     * Deletes a course from the database.
     * @param courseId the ID of the course to delete
     * @return true if the course was deleted successfully, otherwise false
     */
    @Override
    public boolean deleteCourse(int courseId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM course WHERE id = " + courseId;
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }
}
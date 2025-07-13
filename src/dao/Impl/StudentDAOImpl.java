package dao.Impl;

import dao.StudentDAO;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Map;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {
    /**
     * Adds a new student to the database.
     * @param studentName the name of the student
     * @param gender the gender of the student
     * @param dob the date of birth of the student
     * @param email the email address of the student
     * @param phone the phone number of the student
     * @return true if the student was successfully added, otherwise false
     */
    @Override
    public boolean add(String studentName, String gender, Date dob, String email, String phone) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String dobString = "'" + dob.toString() + "'";
            String query = "INSERT INTO student(name, gender, date_of_birth, email, phone) VALUES ('" + studentName + "', '" + gender + "', " + dobString + ", '" + email + "', " + phone + ")";

            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Retrieves all students from the database.
     * @return a list of maps, where each map contains details of one student
     */
    @Override
    public List<Map<String, Object>> getAllStudents() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, name, gender, date_of_birth, email, phone  FROM student";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<Map<String, Object>> students = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> student = new HashMap<>();
                student.put("studentId", resultSet.getInt("id"));
                student.put("studentName", resultSet.getString("name"));
                student.put("gender", resultSet.getString("gender"));
                student.put("dob", resultSet.getString("date_of_birth"));
                student.put("email", resultSet.getString("email"));
                student.put("phone", resultSet.getString("phone"));

                students.add(student);
            }
            return students;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }

    /**
     * Deletes a student from the database based on the student ID.
     * @param studentId the ID of the student to be deleted
     * @return true if the student was deleted successfully, otherwise false
     */
    @Override
    public boolean delete(int studentId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM student WHERE id = " + studentId;
            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Updates student information in the database.
     * @param updateStudent a map containing the updated student fields with values
     * @return true if the student was updated successfully, otherwise false
     */
    @Override
    public boolean update(Map<String, Object> updateStudent) {
        Connection connection = null;
        Statement statement = null;

        int id = (Integer) updateStudent.get("id");

        String query = "UPDATE student SET ";

        if (updateStudent.containsKey("name"))
            query += "name = '" + updateStudent.get("name") + "', ";
        if (updateStudent.containsKey("gender"))
            query += "gender = '" + updateStudent.get("gender") + "', ";
        if (updateStudent.containsKey("dateOfBirth"))
            query += "date_of_birth = '" + updateStudent.get("dateOfBirth") + "', ";
        if (updateStudent.containsKey("email"))
            query += "email = '" + updateStudent.get("email") + "', ";
        if (updateStudent.containsKey("phone"))
            query += "phone = '" + updateStudent.get("phone") + "', ";

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
     * Checks whether a student with the given email already exists in the database.
     * @param email the email address to check
     * @return true if the email already exists, otherwise false
     */
    @Override
    public boolean isEmailExists(String email) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM student WHERE email = '" + email + "' LIMIT 1";
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
     * Checks whether a student with the given phone number already exists in the database.
     * @param phone the phone number to check
     * @return true if the phone number already exists, otherwise false
     */
    @Override
    public boolean isPhoneExists(String phone) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM student WHERE phone = '" + phone + "' LIMIT 1";
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
     * Checks whether a student exists in the database based on their ID.
     * @param studentId the ID of the student
     * @return true if the student exists, otherwise false
     */
    @Override
    public boolean isStudentExists(Integer studentId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM student WHERE id = '" + studentId + "' LIMIT 1";
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

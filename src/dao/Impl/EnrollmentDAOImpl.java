package dao.Impl;

import dao.EnrollmentDAO;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class EnrollmentDAOImpl implements EnrollmentDAO {
    /**
     * Adds a new enrollment to the database.
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @param batchId   the ID of the batch
     * @return true if the enrollment was successfully added, otherwise false
     */
    @Override
    public boolean enrollStudent(int studentId, int courseId, int batchId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO enrollment (student_id, course_id, batch_id) " + "VALUES (" + studentId + ", " + courseId + ", " + batchId + ")";

            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }
    /**
     * Retrieves all paid courses from the database.
     * @return a list of paid courses, where each course is represented as a map
     */
    @Override
    public List<Map<String, Object>> getAllPaidCourses() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, name, fee, is_paid, description FROM course WHERE is_paid = true ORDER BY fee DESC";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<Map<String, Object>> courses = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("courseId", resultSet.getInt("id"));
                course.put("courseName", resultSet.getString("name"));
                course.put("courseFee", resultSet.getInt("fee"));
                course.put("isPaid", resultSet.getBoolean("is_paid"));
                course.put("description", resultSet.getString("description"));

                courses.add(course);
            }
            return courses;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }
    /**
     * Checks whether a student is already enrolled in a specific course.
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @return true if the student is already enrolled in the course, otherwise false
     */
    @Override
    public boolean isStudentAlreadyEnrolledInCourse(int studentId, int courseId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();

            String query = "SELECT 1 FROM enrollment WHERE student_id = " + studentId + " AND course_id = " + courseId;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            return resultSet.next();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return false;
    }
    /**
     * Retrieves all active batches for a given course.
     * @param courseId the ID of the course
     * @return a list of active batches, where each batch is represented as a map
     */
    @Override
    public List<Map<String, Object>> getBatchesByCourse(int courseId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, name, start_date, end_date, class_mode FROM batch WHERE course_id = " + courseId + " AND is_active = true ORDER BY start_date ASC";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<Map<String, Object>> batches = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> batch = new HashMap<>();
                batch.put("batchId", resultSet.getInt("id"));
                batch.put("batchName", resultSet.getString("name"));
                batch.put("startDate", resultSet.getDate("start_date"));
                batch.put("endDate", resultSet.getDate("end_date"));
                batch.put("classMode", resultSet.getString("class_mode"));

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
     * Checks whether a student is already enrolled in a specific batch.
     * @param studentId the ID of the student
     * @param batchId   the ID of the batch
     * @return true if the student is already enrolled in the batch, otherwise false
     */
    @Override
    public boolean isStudentAlreadyEnrolledInBatch(int studentId, int batchId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM enrollment WHERE student_id = " + studentId + " AND batch_id = " + batchId;
            resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return false;
    }
    /**
     * Retrieves all free courses associated with a specific paid course.
     * @param courseId the ID of the paid course
     * @return a list of free courses, where each course is represented as a map
     */
    @Override
    public List<Map<String, Object>> getFreeCoursesForPaidCourse(int courseId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT c.id, c.name, c.description FROM course c JOIN free_course_offer fc ON c.id = fc.free_course_id WHERE fc.paid_course_id = " + courseId;
            resultSet = statement.executeQuery(query);

            List<Map<String, Object>> freeCourses = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("courseId", resultSet.getInt("id"));
                course.put("courseName", resultSet.getString("name"));
                course.put("description", resultSet.getString("description"));

                freeCourses.add(course);
            }
            return freeCourses;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }
    /**
     * Retrieves the paid course ID linked to a given free course.
     * @param courseId the ID of the free course
     * @return the ID of the linked paid course, or 0 if not found
     */
    @Override
    public int getLinkedPaidCourseId(int courseId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();

            String query = "SELECT paid_course_id FROM free_course_offer WHERE free_course_id = " + courseId;
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return resultSet.getInt("paid_course_id");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return 0;
    }

    /**
     * Checks whether an enrollment exists in the database.
     * @param enrollmentId the ID of the enrollment
     * @return true if the enrollment exists, otherwise false
     */
    @Override
    public boolean isEnrollmentExists(Integer enrollmentId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "SELECT 1 FROM enrollment WHERE id = '" + enrollmentId + "' LIMIT 1";
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
     * Deletes an enrollment from the database.
     * @param enrollmentId the ID of the enrollment to delete
     * @return true if the enrollment was successfully deleted, otherwise false
     */
    @Override
    public boolean delete(Integer enrollmentId) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnection.provideConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM enrollment WHERE id = " + enrollmentId;
            return statement.executeUpdate(query) > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, null);
        }
        return false;
    }

    /**
     * Updates an existing enrollment record based on provided values.
     * @param updateValues a map containing fields to update (e.g., studentId, courseId, batchId)
     * @return true if the enrollment was updated successfully, otherwise false
     */
    @Override
    public boolean update(Map<String, Object> updateValues) {
        Connection connection = null;
        Statement statement = null;

        int id = (Integer) updateValues.get("id");
        String query = "UPDATE enrollment SET ";

        if (updateValues.containsKey("studentId"))
            query += "student_id = '" + updateValues.get("studentId") + "', ";
        if (updateValues.containsKey("courseId"))
            query += "course_id = '" + updateValues.get("courseId") + "', ";
        if (updateValues.containsKey("batchId"))
            query += "batch_id = '" + updateValues.get("batchId") + "', ";

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
     * Retrieves all enrollment records from the database.
     * @return a list of enrollments, where each enrollment is represented as a map
     */
    @Override
    public List<Map<String, Object>> getAllEnrollments() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.provideConnection();
            String query = "SELECT id, student_id, course_id, batch_id FROM enrollment";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<Map<String, Object>> enrollments = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> enrollment = new HashMap<>();
                enrollment.put("id", resultSet.getInt("id"));
                enrollment.put("studentId", resultSet.getInt("student_id"));
                enrollment.put("courseId", resultSet.getInt("course_id"));
                enrollment.put("batchId", resultSet.getInt("batch_id"));
                enrollments.add(enrollment);
            }
            return enrollments;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DBConnection.closeResources(connection, statement, resultSet);
        }
        return Collections.emptyList();
    }
}
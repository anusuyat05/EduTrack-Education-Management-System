package service.Impl;

import dao.BatchDAO;
import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.Impl.BatchDAOImpl;
import dao.Impl.CourseDAOImpl;
import dao.Impl.EnrollmentDAOImpl;
import dao.Impl.StudentDAOImpl;
import dao.StudentDAO;
import service.EnrollmentService;

import java.util.List;
import java.util.Map;

public class EnrollmentServiceImpl implements EnrollmentService {
    EnrollmentDAO enrollmentDAO = new EnrollmentDAOImpl();
    BatchDAO batchDAO = new BatchDAOImpl();
    CourseDAO courseDAO = new CourseDAOImpl();
    StudentDAO studentDAO = new StudentDAOImpl();
    /**
     * Adds a new enrollment to the database.
     * parameter enrollmentDetails A map contains enrollment details
     * return String Returns message about the status.
     */
    @Override
    public String enrollStudent(Map<String, Object> enrollmentDetails) {
        StringBuilder errors = new StringBuilder();
        Integer studentId = (Integer) enrollmentDetails.get("studentId");
        Integer courseId = (Integer) enrollmentDetails.get("courseId");
        Integer batchId = (Integer) enrollmentDetails.get("batchId");

        if (studentId == null) errors.append("Student ID cannot be null.\n");
        if (courseId == null) errors.append("Course ID cannot be null.\n");

        if (!errors.isEmpty()) return errors.toString().trim();

        if (!studentDAO.isStudentExists(studentId)) errors.append("Student does not exist.\n");
        if (!courseDAO.isCourseExists(courseId,null)) errors.append("Course does not exist.\n");
        if (!batchDAO.isBatchExists(batchId,null)) errors.append("Selected batch does not exist.\n");

        if (!errors.isEmpty()) return errors.toString().trim();
        boolean isPaidCourse = courseDAO.isCoursePaid(courseId);

        if (isPaidCourse) {
            // Check for duplicate enrollments
            if (enrollmentDAO.isStudentAlreadyEnrolledInCourse(studentId, courseId)) {
                errors.append("Student is already enrolled in this course.\n");
            }

            if (enrollmentDAO.isStudentAlreadyEnrolledInBatch(studentId, batchId)) {
                errors.append("Student is already enrolled in another batch for this course.\n");
            }

            if (!errors.isEmpty()) return errors.toString().trim();
        } else {
            // Free course restriction logic
            int linkedPaidCourseId = enrollmentDAO.getLinkedPaidCourseId(courseId);
            if (linkedPaidCourseId == 0 || enrollmentDAO.isStudentAlreadyEnrolledInCourse(studentId, linkedPaidCourseId)) {
                return "Enrollment failed: Free courses can only be enrolled if you have an associated paid course.";
            }
        }
        boolean success = enrollmentDAO.enrollStudent(studentId, courseId, batchId);
        return success ? "Student successfully enrolled in the course!" : "Sorry, the enrollment failed. Please retry.";
    }
    /**
     * Retrieves all the paid courses from the database.
     * return a list of courses, where each course is represented as map.
     */
    @Override
    public List<Map<String, Object>> getAllPaidCourses() {
        List<Map<String, Object>> courses = enrollmentDAO.getAllPaidCourses();
        if (courses.isEmpty()) {
            System.out.println("No paid courses available.");
        }
        return courses;
    }
    /**
     * Retrieves all the batches which are active in that particular course.
     * return a list of batches, where each batch is represented as map.
     */
    @Override
    public List<Map<String, Object>> getBatchesByCourse(int courseId) {
        return enrollmentDAO.getBatchesByCourse(courseId);
    }

    //Retrieves free courses linked to a given paid course.
    @Override
    public List<Map<String, Object>> getFreeCoursesForPaidCourse(int courseId) {
        return enrollmentDAO.getFreeCoursesForPaidCourse(courseId);
    }

    //Deletes a student's enrollment by ID.
    @Override
    public boolean delete(Map<String, Object> deleteValues) {
        Integer enrollmentId = (Integer) deleteValues.get("enrollmentId");
        if(enrollmentId == null) {
            return false;
        }
        if (!enrollmentDAO.isEnrollmentExists(enrollmentId)) {
            return false;
        }
        return enrollmentDAO.delete(enrollmentId);
    }

    //Updates an existing enrollment's details.
    @Override
    public String update(Map<String, Object> updateValues) {
        StringBuilder errors = new StringBuilder();

        if (!updateValues.containsKey("id") || updateValues.get("id") == null) {
            return "Enrollment ID is required for updating enrollment.";
        }

        if (updateValues.containsKey("studentId")) {
            Integer studentId = (Integer) updateValues.get("studentId");
            if (studentId == null) {
                errors.append("New student ID cannot be null.\n");
            } else if (!studentDAO.isStudentExists(studentId)) {
                errors.append("Student with ID ").append(studentId).append(" does not exist.\n");
            }
        }

        if (updateValues.containsKey("courseId")) {
            Integer courseId = (Integer) updateValues.get("courseId");
            if (courseId == null) {
                errors.append("New course ID cannot be null.\n");
            } else if (!courseDAO.isCourseExists(courseId,null)) {
                errors.append("Course with ID ").append(courseId).append(" does not exist.\n");
            }
        }

        if (updateValues.containsKey("batchId")) {
            Integer batchId = (Integer) updateValues.get("batchId");
            if (batchId == null) {
                errors.append("New batch ID cannot be null.\n");
            } else if (!batchDAO.isBatchExists(batchId,null)) {
                errors.append("Batch with ID ").append(batchId).append(" does not exist.\n");
            }
        }

        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }
        boolean success = enrollmentDAO.update(updateValues);
        return success ? "Enrollment updated successfully!" : "Enrollment update failed. Please retry.";
    }

    //Fetches all enrollment records from the system.
    @Override
    public List<Map<String, Object>> getAllEnrollments() {
        return enrollmentDAO.getAllEnrollments();
    }
}

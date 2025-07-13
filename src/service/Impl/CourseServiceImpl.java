package service.Impl;

import dao.CourseDAO;
import dao.Impl.CourseDAOImpl;
import service.CourseService;

import java.util.List;
import java.util.Map;

/**
 * Implements the CourseService Interface
 * Provides business logic for course related things
 */
public class CourseServiceImpl implements CourseService {
    CourseDAO courseDAO = new CourseDAOImpl();
    /**
     * Adds a new course to the database.
     * parameter addCourse A map contains course details
     * return boolean Returns true if the course was successfully added, otherwise false.
     */
    @Override
    public String addCourse(Map<String, Object>courseDetails) {
        String courseName = (String) courseDetails.get("courseName");
        Integer courseFee = (Integer) courseDetails.get("courseFee");
        Boolean isPaid = (Boolean) courseDetails.get("isPaid");
        String courseDescription = (String) courseDetails.get("courseDescription");

        StringBuilder errors = new StringBuilder();

        if (courseName == null || courseName.trim().isEmpty()) {
            errors.append("Course name cannot be null or empty.\n");
        }
        if (courseFee == null) {
            errors.append("Course fee cannot be null.\n");
        }
        if (isPaid == null) {
            errors.append("Course payment type (isPaid) cannot be null.\n");
        }
        if (!errors.isEmpty()) return errors.toString().trim();

        if (courseDAO.isCourseExists(0,courseName)) {
            errors.append("A course with this name already exists.\n");
        }
        if (!errors.isEmpty()) return errors.toString().trim();

        if (courseName.length() > 50) {
            errors.append("Course name must not exceed 50 characters.\n");
        }
        if (courseFee < 0) {
            errors.append("Course fee cannot be negative.\n");
        }
        if (!isPaid && courseFee > 0) {
            errors.append("Free courses must have a fee of 0.\n");
        }
        if (isPaid && courseFee == 0) {
            errors.append("Paid courses must have a fee greater than 0.\n");
        }

        if (!errors.isEmpty()) return errors.toString().trim();

        boolean success = courseDAO.addCourse(courseName, courseFee, isPaid, courseDescription);
        return success ? "New Course Added Successfully..." : "Failed to add the course. Please retry.";
    }

    /**
     * Updates a specific column of a course in the database.
     * parameter updateCourse a map contains courses which need to be updated
     * return boolean Returns true if the course was updated successfully, otherwise false.
     */
    @Override
    public  String updateCourse(Map<String, Object> updateCourse) {
        StringBuilder errors = new StringBuilder();
        if (!updateCourse.containsKey("id") || updateCourse.get("id") == null) {
            return "Course ID is required for updating.";
        }
        int courseId = (int) updateCourse.get("id");
        if (!courseDAO.isCourseExists(courseId,null)) {
            return "Course with ID " + courseId + " does not exist.";
        }
        if (updateCourse.containsKey("name")) {
            String courseName = (String) updateCourse.get("name");
            if (courseName == null || courseName.trim().isEmpty()) {
                errors.append("Course name cannot be null or empty.\n");
            } else if (courseName.length() > 50) {
                errors.append("Course name must not exceed 50 characters.\n");
            } else if (courseDAO.isCourseExists(0,courseName)) {
                errors.append("Another course with the same name already exists.\n");
            }
        }
        if (updateCourse.containsKey("isPaid") || updateCourse.containsKey("fee")) {
            Boolean isPaid = updateCourse.containsKey("isPaid") ? (Boolean) updateCourse.get("isPaid") : null;
            Integer courseFee = updateCourse.containsKey("fee") ? (Integer) updateCourse.get("fee") : null;

            if (courseFee != null && courseFee < 0) {
                errors.append("Course fee cannot be negative.\n");
            }
            if (isPaid != null && courseFee != null) {
                if (!isPaid && courseFee > 0) {
                    errors.append("Free courses must have a fee of 0.\n");
                }
                if (isPaid && courseFee == 0) {
                    errors.append("Paid courses must have a fee greater than 0.\n");
                }
            }
        }

        if (!errors.isEmpty()) return errors.toString().trim();
        boolean updated = courseDAO.updateCourse(updateCourse);
        return updated ? "Course updated successfully." : "Failed to update course. Please try again.";
    }

    /**
     * Deletes a course from the database.
     * parameter deleteCourse a map contains courses which need to be deleted
     * return boolean Returns true if the course was deleted successfully, otherwise false.
     */
    @Override
    public boolean deleteCourse(Map<String, Object> deleteCourse) {
        Integer courseId = (Integer) deleteCourse.get("courseId");
        if(courseId == null) {
            return false;
        }
        if (!courseDAO.isCourseExists(courseId,null)) {
            return false;
        }
        return courseDAO.deleteCourse(courseId);
    }
}
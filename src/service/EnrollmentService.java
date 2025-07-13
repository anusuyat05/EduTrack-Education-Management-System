package service;

import java.util.List;
import java.util.Map;

public interface EnrollmentService {
    String enrollStudent(Map<String, Object> addCourse);

    List<Map<String, Object>> getAllPaidCourses();

    List<Map<String, Object>> getBatchesByCourse(int courseId);

    List<Map<String, Object>> getFreeCoursesForPaidCourse(int courseId);

    boolean delete(Map<String, Object> deleteValues);

    String update(Map<String, Object> updateValues);

    List<Map<String, Object>> getAllEnrollments();
}

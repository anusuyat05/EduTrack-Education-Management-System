package dao;

import service.EnrollmentService;

import java.util.List;
import java.util.Map;

public interface EnrollmentDAO {
    boolean enrollStudent(int studentId,int courseId,int batchId);

    List<Map<String, Object>> getAllPaidCourses();

    boolean isStudentAlreadyEnrolledInCourse(int studentId, int courseId);

    List<Map<String, Object>> getBatchesByCourse(int courseId);

    boolean isStudentAlreadyEnrolledInBatch(int studentId, int batchId);

    List<Map<String, Object>> getFreeCoursesForPaidCourse(int courseId);

    int getLinkedPaidCourseId(int courseId);

    boolean isEnrollmentExists(Integer enrollmentId);

    boolean delete(Integer enrollmentId);

    boolean update(Map<String, Object> updateValues);

    List<Map<String, Object>> getAllEnrollments();
}

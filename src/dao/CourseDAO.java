package dao;

import java.util.List;
import java.util.Map;

public interface CourseDAO {
    boolean addCourse(String courseName, int courseFee, boolean isPaid, String courseDescription);
    boolean deleteCourse(int courseId);
    boolean updateCourse(Map<String, Object> updateCourse);

    boolean isCoursePaid(int paidCourseId);

    boolean isCourseExists(int courseId, String courseName);
}

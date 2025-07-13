package service;
import java.util.List;
import java.util.Map;

public interface CourseService {
    String addCourse(Map<String, Object> courseDetails);
    String updateCourse(Map<String, Object> updateCourse);
    boolean deleteCourse(Map<String, Object> deleteCourse);
}

package service;

import java.util.List;
import java.util.Map;

public interface StudentService {
    String add(Map<String, Object> addStudent);
    List<Map<String, Object>> getAllStudent();
    String update(Map<String, Object> updateStudent);
    boolean delete(Map<String, Object> deleteStudent);
}

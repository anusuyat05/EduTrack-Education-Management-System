package dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface StudentDAO {
    boolean add(String studentName, String gender, Date dob, String email, String phone);
    List<Map<String, Object>> getAllStudents();
    boolean delete(int studentId);
    boolean update(Map<String, Object> updateStudent);

    boolean isEmailExists(String email);

    boolean isPhoneExists(String phone);

    boolean isStudentExists(Integer studentId);
}

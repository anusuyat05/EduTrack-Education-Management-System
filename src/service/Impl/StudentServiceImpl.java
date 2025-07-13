package service.Impl;

import dao.Impl.StudentDAOImpl;
import dao.StudentDAO;
import service.StudentService;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class StudentServiceImpl implements StudentService {
    StudentDAO studentDAO = new StudentDAOImpl();
    //Adds a new student to the system after validation.
    @Override
    public String add(Map<String, Object> addStudent) {
        String studentName = (String)addStudent.get("studentName");
        String gender = (String)addStudent.get("gender");
        String dobString = (String) addStudent.get("dob");
        Date dob = Date.valueOf(dobString);
        String email = (String) addStudent.get("email");
        String phone = (String) addStudent.get("phone");

        StringBuilder errors = new StringBuilder();

        if (studentName == null || studentName.trim().isEmpty()) {
            errors.append("Student name cannot be null or empty.\n");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.append("Email cannot be null or empty.\n");
        }
        if (phone == null || phone.trim().isEmpty()) {
            errors.append("Phone cannot be null or empty.\n");
        }

        if (!errors.isEmpty()) return errors.toString().trim();

        if (isValidEmail(email)) {
            errors.append("Invalid email format.\n");
        }
        if (isValidPhone(phone)) {
            errors.append("Invalid phone number format. Must be 10 digits.\n");
        }

        if (!errors.isEmpty()) return errors.toString().trim();
        if (studentDAO.isEmailExists(email)) {
            errors.append("This email is already registered.\n");
        }
        if (studentDAO.isPhoneExists(phone)) {
            errors.append("This phone number is already registered.\n");
        }

        if (!errors.isEmpty()) return errors.toString().trim();

        boolean success = studentDAO.add(studentName, gender, dob, email, phone);
        return success ? "New Student Added Successfully..." : "Failed to add student. Please try again.";
    }

    //Retrieves all students from the system
    @Override
    public List<Map<String, Object>> getAllStudent() {
        return studentDAO.getAllStudents();
    }

    //Updates student details based on the provided map.
    @Override
    public String update(Map<String, Object> updateStudent) {
        StringBuilder errors = new StringBuilder();
        if (!updateStudent.containsKey("id")) {
            errors.append("Student ID is required for updating.\n");
        }
        if (updateStudent.containsKey("email")) {
            String email = (String) updateStudent.get("email");
            if (isValidEmail(email)) {
                errors.append("Invalid email format.\n");
            }
        }

        if (updateStudent.containsKey("phone")) {
            String phone = (String) updateStudent.get("phone");
            if (isValidPhone(phone)) {
                errors.append("Invalid phone number.\n");
            }
        }

        if (updateStudent.containsKey("dateOfBirth")) {
            String dob = (String) updateStudent.get("dob");
            if (isValidDateOfBirth(dob)) {
                errors.append("Invalid date of birth.\n");
            }
        }
        if (!errors.isEmpty()) return errors.toString().trim();
        boolean updated = studentDAO.update(updateStudent);
        return updated ? "Student updated successfully." : "Failed to update student. Please try again.";
    }

    //Deletes a student based on their ID.
    @Override
    public boolean delete(Map<String, Object> deleteStudent) {
        Integer studentId = (Integer) deleteStudent.get("studentId");
        if(studentId == null) {
            return false;
        }
        if (!studentDAO.isStudentExists(studentId)) {
            return false;
        }
        return studentDAO.delete(studentId);
    }
    //Validates the format of the email.
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email == null || !email.matches(emailRegex);
    }

    //Validates the phone number (must be exactly 10 digits).
    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$";
        return phone == null || !phone.matches(phoneRegex);
    }

    //Validates if the date of birth is in the correct format and not in the future.
    private boolean isValidDateOfBirth(String dob) {
        try {
            LocalDate birthDate = LocalDate.parse(dob.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return !birthDate.isAfter(LocalDate.now());
        } catch (Exception exception) {
            return false;
        }
    }
}

package service.Impl;

import dao.Impl.TrainerDAOImpl;
import dao.TrainerDAO;
import service.TrainerService;

import java.util.List;
import java.util.Map;

public class TrainerServiceImpl implements TrainerService {
    TrainerDAO trainerDAO = new TrainerDAOImpl();
    //Adds a new trainer to the system after validating input fields.
    @Override
    public String addTrainer(Map<String, Object> addValues) {
        String username = (String) addValues.get("username");
        String password = (String) addValues.get("password");
        String name = (String) addValues.get("name");
        Integer experience = (Integer) addValues.get("experience");
        String email = (String) addValues.get("email");
        Boolean isAdmin = (Boolean) addValues.get("isAdmin");

        StringBuilder errors = new StringBuilder();

        if (username == null || username.trim().isEmpty()) {
            errors.append("Username cannot be null or empty.\n");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.append("Password cannot be null or empty.\n");
        }
        if (name == null || name.trim().isEmpty()) {
            errors.append("Name cannot be null or empty.\n");
        }
        if (experience == null || experience < 0) {
            errors.append("Experience must be a non-negative value.\n");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.append("Email cannot be null or empty.\n");
        }
        if (isAdmin == null) {
            errors.append("Admin status must be specified.\n");
        }

        if (!errors.isEmpty()) return errors.toString().trim();

        if (isValidEmail(email)) {
            errors.append("Invalid email format.\n");
        }

        if (!errors.isEmpty()) return errors.toString().trim();

        if (trainerDAO.isEmailExists(email)) {
            errors.append("This email is already registered.\n");
        }

        if (!errors.isEmpty()) return errors.toString().trim();

        boolean success = trainerDAO.addTrainer(username, password, name, experience, email, isAdmin);
        return success ? "New Trainer Added Successfully..." : "Failed to add trainer. Please try again.";
    }

    //Validates the email format using a regular expression.
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email == null || !email.matches(emailRegex);
    }

    //Deletes a trainer by ID if the trainer exists.
    @Override
    public boolean delete(Map<String, Object> deleteValues) {
        Integer trainerId = (Integer) deleteValues.get("trainerId");
        if(trainerId == null) {
            return false;
        }
        if (!trainerDAO.isTrainerExists(trainerId)) {
            return false;
        }
        return trainerDAO.delete(trainerId);
    }

    //Retrieves all trainers from the database.
    @Override
    public List<Map<String, Object>> getAllTrainers() {
        return trainerDAO.getAllTrainers();
    }

    //Updates trainer details based on provided fields.
    @Override
    public String update(Map<String, Object> updateValues) {
        StringBuilder errors = new StringBuilder();

        if (!updateValues.containsKey("id")) {
            errors.append("Trainer ID is required for updating.\n");
        }

        if (updateValues.containsKey("name")) {
            String name = (String) updateValues.get("name");
            if (name == null || name.trim().isEmpty()) {
                errors.append("Name cannot be null or empty.\n");
            }
        }

        if (updateValues.containsKey("username")) {
            String username = (String) updateValues.get("username");
            if (username == null || username.trim().isEmpty()) {
                errors.append("Username cannot be null or empty.\n");
            }
        }

        if (updateValues.containsKey("password")) {
            String password = (String) updateValues.get("password");
            if (password == null || password.trim().isEmpty()) {
                errors.append("Password cannot be null or empty.\n");
            }
        }

        if (updateValues.containsKey("experience")) {
            Integer experience = (Integer) updateValues.get("experience");
            if (experience == null || experience < 0) {
                errors.append("Experience cannot be negative.\n");
            }
        }

        if (updateValues.containsKey("isAdmin")) {
            Boolean isAdmin = (Boolean) updateValues.get("isAdmin");
            if (isAdmin == null) {
                errors.append("Admin status cannot be null.\n");
            }
        }

        if (!errors.isEmpty()) return errors.toString().trim();

        boolean updated = trainerDAO.update(updateValues);
        return updated ? "Trainer updated successfully." : "Failed to update trainer. Please try again.";
    }

    /**
     * Authenticates a trainer using username and password and returns the user role.
     * @param username Trainer's username
     * @param password Trainer's password
     * @return Role string ("admin", "trainer", etc.) or an error message
     */
    @Override
    public String login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty.";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty.";
        }
        return trainerDAO.getUserRole(username, password);
    }
}

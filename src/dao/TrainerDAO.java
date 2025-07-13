package dao;

import java.util.List;
import java.util.Map;

public interface TrainerDAO {
    boolean addTrainer(String username, String password, String name, int experience, String email, Boolean isAdmin);

    boolean isEmailExists(String email);

    boolean delete(int trainerId);

    boolean isTrainerExists(int trainerId);

    boolean update(Map<String, Object> updateValues);

    List<Map<String, Object>> getAllTrainers();

    String getUserRole(String username, String password);
}

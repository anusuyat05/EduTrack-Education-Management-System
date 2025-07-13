package service;

import java.util.List;
import java.util.Map;

public interface TrainerService {

    String addTrainer(Map<String, Object> addValues);

    boolean delete(Map<String, Object> deleteValues);

    List<Map<String, Object>> getAllTrainers();

    String update(Map<String, Object> updateValues);

    String login(String username, String password);
}

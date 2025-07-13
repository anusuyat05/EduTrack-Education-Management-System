package dao;

import java.util.List;
import java.util.Map;

public interface TrainerSpecializationDAO {
    boolean isSpecializationExists(int trainerId, String specialization);

    boolean add(int trainerId, String specialization);

    boolean update(Map<String, Object> updateValues);

    List<Map<String, Object>> getAllTrainerSpecialization();

    boolean delete(int trainerSpecializationId);

    boolean isSpecializationIdExists(int trainerSpecializationId);
}

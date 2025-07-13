package service.Impl;

import dao.Impl.TrainerDAOImpl;
import dao.Impl.TrainerSpecializationDAOImpl;
import dao.TrainerDAO;
import dao.TrainerSpecializationDAO;
import service.TrainerSpecializationService;

import java.util.List;
import java.util.Map;

public class TrainerSpecializationServiceImpl implements TrainerSpecializationService {
    TrainerSpecializationDAO trainerSpecializationDAO = new TrainerSpecializationDAOImpl();
    TrainerDAO trainerDAO = new TrainerDAOImpl();
    //Adds a new specialization for a trainer after performing validations.
    @Override
    public String add(Map<String, Object> addValues) {
        StringBuilder errors = new StringBuilder();

        Integer trainerId = (Integer) addValues.get("trainerId");
        String specialization = (String) addValues.get("specialization");

        if (trainerId == null) {
            errors.append("Trainer ID cannot be null.\n");
        }
        if (specialization == null || specialization.trim().isEmpty()) {
            errors.append("Specialization cannot be empty.\n");
        }

        if (trainerId != null && !trainerDAO.isTrainerExists(trainerId)) {
            errors.append("Trainer does not exist.\n");
        }

        if (trainerId != null && specialization != null
                && trainerSpecializationDAO.isSpecializationExists(trainerId, specialization)) {
            errors.append("This trainer already has the specified specialization.\n");
        }

        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }

        boolean success = trainerSpecializationDAO.add(trainerId, specialization);
        return success ? "Trainer specialization added successfully!" : "Failed to add trainer specialization.";

    }

    //Deletes a specialization record using its ID.
    @Override
    public boolean delete(Map<String, Object> deleteValues) {
        Integer trainerSpecializationId = (Integer) deleteValues.get("trainerSpecializationId");
        if(trainerSpecializationId == null) {
            return false;
        }
        if (!trainerSpecializationDAO.isSpecializationIdExists(trainerSpecializationId)) {
            return false;
        }
        return trainerSpecializationDAO.delete(trainerSpecializationId);
    }

    //Updates a trainer's specialization after validating data.
    @Override
    public String update(Map<String, Object> updateValues) {
        StringBuilder errors = new StringBuilder();

        Integer trainerId = null;
        String specialization = null;

        if (updateValues.containsKey("trainerId")) {
            trainerId = (Integer) updateValues.get("trainerId");

            if (trainerId == null) {
                errors.append("Trainer ID cannot be null.\n");
            } else if (!trainerDAO.isTrainerExists(trainerId)) {
                errors.append("Trainer does not exist.\n");
            }
        }

        // Validate specialization if present
        if (updateValues.containsKey("specialization")) {
            specialization = (String) updateValues.get("specialization");

            if (specialization == null || specialization.trim().isEmpty()) {
                errors.append("Specialization cannot be empty.\n");
            }
        }

        // Ensure both trainerId and specialization exist for further validation
        if (trainerId != null && specialization != null) {
            if (!trainerSpecializationDAO.isSpecializationExists(trainerId, specialization)) {
                errors.append("The trainer does not have this specialization to update.\n");
            }
            if (trainerSpecializationDAO.isSpecializationExists(trainerId, specialization)) {
                errors.append("The specialization already exists and cannot be updated.\n");
            }
        }

        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }

        boolean success = trainerSpecializationDAO.update(updateValues);
        return success ? "Trainer specialization updated successfully!" : "Failed to update trainer specialization.";
    }

    //Retrieves all trainer specializations.
    @Override
    public List<Map<String, Object>> getAllTrainerSpecialization() {
        return trainerSpecializationDAO.getAllTrainerSpecialization();
    }
}

package service.Impl;

import dao.BatchDAO;
import dao.BatchVenueDAO;
import dao.CourseDAO;
import dao.Impl.BatchDAOImpl;
import dao.Impl.BatchVenueDAOImpl;
import dao.Impl.CourseDAOImpl;
import dao.Impl.TrainerDAOImpl;
import dao.TrainerDAO;
import service.BatchService;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class BatchServiceImpl implements BatchService {
    CourseDAO courseDAO = new CourseDAOImpl();
    TrainerDAO trainerDAO = new TrainerDAOImpl();
    BatchVenueDAO batchVenueDAO = new BatchVenueDAOImpl();
    BatchDAO batchDAO = new BatchDAOImpl();
    //Adds a new batch after validating input values.
    @Override
    public String add(Map<String, Object> addValues) {
        StringBuilder errors = new StringBuilder();
        // Extracting input values
        Integer courseId = (Integer) addValues.get("courseId");
        String batchName = (String) addValues.get("name");
        String startDateString = (String) addValues.get("startDate");
        Date startDate = Date.valueOf(startDateString);
        String endDateString = (String) addValues.get("endDate");
        Date endDate = Date.valueOf(endDateString);
        String classMode = (String) addValues.get("classMode");
        Integer trainerId = (Integer) addValues.get("trainerId");
        Integer venueId = (Integer) addValues.get("venueId");
        Boolean isActive = (Boolean) addValues.get("isActive");

        // Validation
        if (courseId == null || !courseDAO.isCourseExists(courseId,null))
            errors.append("Invalid course ID.\n");

        if (batchName == null || batchName.trim().isEmpty())
            errors.append("Batch name cannot be empty.\n");
        else if (batchDAO.isBatchExists(0,batchName))
            errors.append("Batch name already exists.\n");

        if (startDate == null || endDate == null)
            errors.append("Start and end dates are required.\n");
        else if (!startDate.before(endDate))
            errors.append("End date must be after start date.\n");

        if (classMode == null)
            errors.append("Class mode is required.\n");
        else if (!classMode.equalsIgnoreCase("Classroom") && !classMode.equalsIgnoreCase("Online"))
            errors.append("Invalid class mode. Must be 'Classroom' or 'Online'.\n");

        if (trainerId != null) {
            if (!trainerDAO.isTrainerExists(trainerId))
                errors.append("Trainer does not exist.\n");
        }

        if (venueId != null && !batchVenueDAO.isVenueExists(venueId,null))
            errors.append("Invalid venue ID.\n");

        if (isActive == null)
            errors.append("Batch active status must be specified.\n");

        // Return error if any validations fail
        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }
        return batchDAO.add(courseId, trainerId, batchName, startDate, endDate, classMode, venueId, isActive) ? "Batch added successfully!" : "Failed to add batch.";
    }

    //Deletes a batch based on the provided batch ID.
    @Override
    public boolean delete(Map<String, Object> deleteValues) {
        Integer batchId = (Integer) deleteValues.get("batchId");

        if (batchId == null) {
            return false;
        }
        if (!batchDAO.isBatchExists(batchId,null)) {
            return false;
        }
        return batchDAO.delete(batchId);
    }

    //Updates batch details after validation.
    @Override
    public String updateBatch(Map<String, Object> updateValues) {
        StringBuilder errors = new StringBuilder();

        if (!updateValues.containsKey("id")) {
            return "Batch ID is required for update.";
        }

        Integer batchId = (Integer) updateValues.get("id");
        if (!batchDAO.isBatchExists(batchId,null)) {
            return "Invalid batch ID.";
        }

        if (updateValues.containsKey("name")) {
            String batchName = (String) updateValues.get("name");
            if (batchName.trim().isEmpty()) {
                errors.append("Batch name cannot be empty.\n");
            } else if (batchDAO.isBatchExists(0,batchName)) {
                errors.append("Batch name already exists.\n");
            }
        }

        if (updateValues.containsKey("start_date") && updateValues.containsKey("end_date")) {
            Date startDate = (Date) updateValues.get("start_date");
            Date endDate = (Date) updateValues.get("end_date");
            if (!startDate.before(endDate)) {
                errors.append("End date must be after start date.\n");
            }
        }

        if (updateValues.containsKey("class_mode")) {
            String classMode = (String) updateValues.get("class_mode");
            if (!classMode.equalsIgnoreCase("Classroom") && !classMode.equalsIgnoreCase("Online")) {
                errors.append("Invalid class mode. Must be 'Classroom' or 'Online'.\n");
            }
        }

        if (updateValues.containsKey("trainer_id")) {
            Integer trainerId = (Integer) updateValues.get("trainer_id");
            if (!trainerDAO.isTrainerExists(trainerId)) {
                errors.append("Trainer does not exist.\n");
            }
        }

        if (updateValues.containsKey("venue_id")) {
            Integer venueId = (Integer) updateValues.get("venue_id");
            if (!batchVenueDAO.isVenueExists(venueId,null)) {
                errors.append("Invalid venue ID.\n");
            }
        }

        if (updateValues.containsKey("is_active")) {
            Boolean isActive = (Boolean) updateValues.get("is_active");
            if (isActive == null) {
                errors.append("Batch active status must be specified.\n");
            }
        }
        // Return errors if any
        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }

        return batchDAO.update(updateValues) ? "Batch updated successfully!" : "Failed to update batch.";
    }

    //Retrieves all batches from the database.
    @Override
    public List<Map<String, Object>> getAllBatches() {
        return batchDAO.getAllBatches();
    }
}

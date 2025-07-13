package service.Impl;

import dao.BatchDAO;
import dao.BatchSessionTrackerDAO;
import dao.Impl.BatchDAOImpl;
import dao.Impl.BatchSessionTrackerDAOImpl;
import dao.Impl.TopicDAOImpl;
import dao.TopicDAO;
import service.BatchSessionTrackerService;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class BatchSessionTrackerServiceImpl implements BatchSessionTrackerService {
    BatchSessionTrackerDAO batchSessionTrackerDAO = new BatchSessionTrackerDAOImpl();
    BatchDAO batchDAO = new BatchDAOImpl();
    TopicDAO topicDAO = new TopicDAOImpl();
    //Adds a new batch session tracker entry after validation.
    @Override
    public String add(Map<String, Object> addValues) {
        StringBuilder errors = new StringBuilder();

        Integer batchId = (Integer) addValues.get("batchId");
        Integer topicId = (Integer) addValues.get("topicId");
        String sessionDateString = (String) addValues.get("sessionDate");
        Date sessionDate = Date.valueOf(sessionDateString);
        String status = (String) addValues.get("status");

        if (batchId == null || !batchDAO.isBatchExists(batchId,null)) {
            errors.append("Invalid or missing batch ID.\n");
        }

        if (topicId == null || !topicDAO.isTopicExists(topicId)) {
            errors.append("Invalid or missing topic ID.\n");
        }

        if (status == null || !(status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Ongoing") || status.equalsIgnoreCase("Completed"))) {
            errors.append("Invalid or missing status. Must be 'Pending', 'Ongoing', or 'Completed'.\n");
        }

        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }

        boolean success = batchSessionTrackerDAO.add(batchId, topicId, sessionDate, status);
        return success ? "Batch session tracker added successfully!" : "Failed to add batch session tracker.";
    }

    //Deletes a session tracker entry by ID.
    @Override
    public boolean delete(Map<String, Object> deleteValues) {
        Integer sessionId = (Integer) deleteValues.get("sessionId");

        if (sessionId == null) {
            return false;
        }
        if (!batchSessionTrackerDAO.isSessionExists(sessionId)) {
            return false;
        }
        return batchSessionTrackerDAO.delete(sessionId);
    }

    //Updates a session tracker entry after validation.
    @Override
    public String update(Map<String, Object> updateValues) {
        StringBuilder errors = new StringBuilder();

        if (!updateValues.containsKey("id")) {
            return "Batch session tracker ID is required.";
        }

        Integer sessionId = (Integer) updateValues.get("id");
        if (sessionId == null || !batchSessionTrackerDAO.isSessionExists(sessionId)) {
            return "Invalid batch session tracker ID.";
        }

        if (updateValues.containsKey("batch_id")) {
            Integer batchId = (Integer) updateValues.get("batch_id");
            if (batchId == null || !batchDAO.isBatchExists(batchId,null)) {
                errors.append("Invalid batch ID.\n");
            }
        }

        if (updateValues.containsKey("topic_id")) {
            Integer topicId = (Integer) updateValues.get("topic_id");
            if (topicId == null || !topicDAO.isTopicExists(topicId)) {
                errors.append("Invalid topic ID.\n");
            }
        }

        if (updateValues.containsKey("session_date")) {
            Date sessionDate = (Date) updateValues.get("session_date");
        }

        if (updateValues.containsKey("status")) {
            String status = (String) updateValues.get("status");
            if (status == null || !(status.equalsIgnoreCase("Pending") ||
                    status.equalsIgnoreCase("Ongoing") ||
                    status.equalsIgnoreCase("Completed"))) {
                errors.append("Invalid status. Must be 'Pending', 'Ongoing', or 'Completed'.\n");
            }
        }

        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }

        boolean success = batchSessionTrackerDAO.update(updateValues);
        return success ? "Batch session tracker updated successfully!" : "Failed to update batch session tracker.";
    }

    //Fetches all session tracker records.
    @Override
    public List<Map<String, Object>> getAllSessions() {
        return batchSessionTrackerDAO.getAllSessions();
    }
}

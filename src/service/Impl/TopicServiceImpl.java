package service.Impl;

import dao.Impl.TopicDAOImpl;
import dao.TopicDAO;
import service.TopicService;

import java.util.List;
import java.util.Map;

public class TopicServiceImpl implements TopicService {
    TopicDAO topicDAO = new TopicDAOImpl();

    //Adds a new topic to a specific course.
    @Override
    public boolean add(Map<String, Object> addTopic) {
        String topicName = (String) addTopic.get("topicName");
        Integer courseId = (Integer) addTopic.get("courseId");

        if (topicName == null || courseId == null || topicName.trim().isEmpty() || courseId <= 0) {
            return false;
        }
        return topicDAO.add(topicName, courseId);
    }

    //Retrieves all topics across all courses.
    @Override
    public List<Map<String, Object>> getAllTopics() {
        return topicDAO.getAllTopics();
    }

    //Updates topic details based on the provided fields.
    @Override
    public String update(Map<String, Object> updateTopic) {
        StringBuilder errors = new StringBuilder();
        if (!updateTopic.containsKey("id")) {
            return "Topic ID is required for updating.";
        }

        Integer topicId = (Integer) updateTopic.get("id");

        if (topicId == null || topicId <= 0) {
            errors.append("Invalid Topic ID.\n");
        }

        if (updateTopic.containsKey("name")) {
            String topicName = (String) updateTopic.get("name");
            if (topicName == null || topicName.trim().isEmpty()) {
                errors.append("Topic name cannot be empty.\n");
            }
        }

        if (updateTopic.containsKey("courseId")) {
            Integer courseId = (Integer) updateTopic.get("courseId");
            if (courseId == null || courseId <= 0) {
                errors.append("Invalid Course ID.\n");
            }
        }

        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }

        boolean success = topicDAO.update(updateTopic);

        return success ? "Topic updated successfully." : "Failed to update topic. Please try again.";
    }

    //Deletes a topic based on its ID.
    @Override
    public boolean delete(Map<String, Object> deleteTopic) {
        Integer topicId = (Integer) deleteTopic.get("topicId");
        if (topicId == null) {
            return false;
        }
        if (!topicDAO.isTopicExists(topicId)) {
            return false;
        }
        return topicDAO.delete(topicId);
    }
}

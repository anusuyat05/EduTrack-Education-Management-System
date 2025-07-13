package dao;

import java.util.List;
import java.util.Map;

public interface TopicDAO {
    boolean add(String topicName, int courseId);
    List<Map<String, Object>> getAllTopics();
    boolean delete(int topicId);
    boolean update(Map<String, Object> updateTopic);

    boolean isTopicExists(int topicId);
}

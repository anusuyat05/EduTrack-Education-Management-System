package service;

import java.util.List;
import java.util.Map;

public interface TopicService {
    boolean add(Map<String, Object> addTopic);
    List<Map<String, Object>> getAllTopics();
    String update(Map<String, Object> updateTopic);
    boolean delete(Map<String, Object> deleteTopic);
}

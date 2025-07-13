package dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface BatchSessionTrackerDAO {
    boolean isSessionExists(int sessionId);

    boolean update(Map<String, Object> updateValues);

    boolean add(int batchId, int topicId, Date sessionDate, String status);

    List<Map<String, Object>> getAllSessions();


    boolean delete(int batchSessionTrackerId);
}

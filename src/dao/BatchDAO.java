package dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface BatchDAO {
    boolean isBatchExists(int batchId, String batchName);

    boolean delete(int batchId);

    boolean update(Map<String, Object> updateValues);

    List<Map<String, Object>> getAllBatches();

    boolean add(int courseId, int trainerId, String batchName, Date startDate, Date endDate, String classMode, int venueId, Boolean isActive);
}

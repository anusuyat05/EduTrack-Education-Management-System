package service;

import java.util.List;
import java.util.Map;

public interface BatchService {
    String add(Map<String, Object> addValues);

    boolean delete(Map<String, Object> deleteValues);

    String updateBatch(Map<String, Object> updateValues);

    List<Map<String, Object>> getAllBatches();
}

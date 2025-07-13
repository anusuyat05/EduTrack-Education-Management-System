package service;

import java.util.List;
import java.util.Map;

public interface FreeCourseOfferService {
    String add(Map<String, Object> addValues);

    boolean delete(Map<String, Object> deleteValues);

    String update(Map<String, Object> updateValues);

    List<Map<String, Object>> getAllOffers();
}

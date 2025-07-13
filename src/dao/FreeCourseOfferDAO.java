package dao;

import java.util.List;
import java.util.Map;

public interface FreeCourseOfferDAO {
    boolean addOffer(int paidCourseId, int freeCourseId);

    boolean isOfferExists(int paidCourseId, int freeCourseId);

    boolean delete(int offerId);

    boolean isOfferIdExists(int offerId);

    boolean updateOffer(Map<String, Object> updateValues);

    List<Map<String, Object>> getAllOffers();
}

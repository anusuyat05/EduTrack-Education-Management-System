package service.Impl;

import dao.CourseDAO;
import dao.FreeCourseOfferDAO;
import dao.Impl.CourseDAOImpl;
import dao.Impl.FreeCourseOfferDAOImpl;
import service.FreeCourseOfferService;

import java.util.List;
import java.util.Map;

public class FreeCourseOfferServiceImpl implements FreeCourseOfferService {
    FreeCourseOfferDAO freeCourseOfferDAO = new FreeCourseOfferDAOImpl();
    CourseDAO courseDAO = new CourseDAOImpl();
    //Adds a new free course offer linked to a paid course.
    @Override
    public String add(Map<String, Object> addValues) {
        StringBuilder errors = new StringBuilder();

        Integer paidCourseId = (Integer) addValues.get("paidCourseId");
        Integer freeCourseId = (Integer) addValues.get("freeCourseId");

        if (paidCourseId == null) {
            errors.append("Paid course ID cannot be null.\n");
        }
        if (freeCourseId == null) {
            errors.append("Free course ID cannot be null.\n");
        }

        if (paidCourseId != null && paidCourseId.equals(freeCourseId)) {
            errors.append("A course cannot be offered as free when it is itself a paid course.\n");
        }

        if (paidCourseId != null && !courseDAO.isCourseExists(paidCourseId,null)) {
            errors.append("Paid course does not exist.\n");
        }

        if (freeCourseId != null && !courseDAO.isCourseExists(freeCourseId,null)) {
            errors.append("Free course does not exist.\n");
        }

        if (paidCourseId != null && !courseDAO.isCoursePaid(paidCourseId)) {
            errors.append("The selected paid course is not a paid course.\n");
        }

        if (freeCourseId != null && courseDAO.isCoursePaid(freeCourseId)) {
            errors.append("The selected free course is not a free course.\n");
        }

        if (paidCourseId != null && freeCourseId != null && freeCourseOfferDAO.isOfferExists(paidCourseId, freeCourseId)) {
            errors.append("This paid and free course combination already exists.\n");
        }

        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }

        boolean success = freeCourseOfferDAO.addOffer(paidCourseId, freeCourseId);
        return success ? "Free course offer added successfully!" : "Failed to add free course offer.";
    }

    //Deletes a free course offer by its ID.
    @Override
    public boolean delete(Map<String, Object> deleteValues) {
        Integer offerId = (Integer) deleteValues.get("offerId");
        if(offerId == null) {
            return false;
        }
        if (!freeCourseOfferDAO.isOfferIdExists(offerId)) {
            return false;
        }
        return freeCourseOfferDAO.delete(offerId);
    }

    //Updates an existing free course offer's details.
    @Override
    public String update(Map<String, Object> updateValues) {
        StringBuilder errors = new StringBuilder();

        if (updateValues.containsKey("paidCourseId")) {
            Integer paidCourseId = (Integer) updateValues.get("paidCourseId");

            if (paidCourseId == null) {
                errors.append("Paid course ID cannot be null.\n");
            } else {
                if (!courseDAO.isCourseExists(paidCourseId,null)) {
                    errors.append("Paid course does not exist.\n");
                }

                if (!courseDAO.isCoursePaid(paidCourseId)) {
                    errors.append("The selected paid course is not a paid course.\n");
                }
            }
        }

        if (updateValues.containsKey("freeCourseId")) {
            Integer freeCourseId = (Integer) updateValues.get("freeCourseId");

            if (freeCourseId == null) {
                errors.append("Free course ID cannot be null.\n");
            } else {
                if (!courseDAO.isCourseExists(freeCourseId,null)) {
                    errors.append("Free course does not exist.\n");
                }

                if (courseDAO.isCoursePaid(freeCourseId)) {
                    errors.append("The selected free course is not a free course.\n");
                }
            }
        }

        Integer paidCourseId = (Integer) updateValues.get("paidCourseId");
        Integer freeCourseId = (Integer) updateValues.get("freeCourseId");

        if (paidCourseId != null && paidCourseId.equals(freeCourseId)) {
            errors.append("A course cannot be offered as free when it is itself a paid course.\n");
        }

        if (paidCourseId != null && freeCourseId != null && freeCourseOfferDAO.isOfferExists(paidCourseId, freeCourseId)) {
            errors.append("The paid and free course combination already exists and cannot be updated.\n");
        }

        if (!errors.isEmpty()) {
            return errors.toString().trim();
        }

        boolean success = freeCourseOfferDAO.updateOffer(updateValues);
        return success ? "Free course offer updated successfully!" : "Failed to update free course offer.";

    }

    //Retrieves all free course offers in the system.
    @Override
    public List<Map<String, Object>> getAllOffers() {
        return freeCourseOfferDAO.getAllOffers();
    }
}

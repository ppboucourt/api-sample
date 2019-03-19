package co.tmunited.bluebook.domain.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A DashBoardVO.
 */
public class DashBoardVO implements Serializable {

    private Map<String, Object> bedAvailability;
    private Map<String, Object> typeLevelCare;
    private Map<String, Object> typePaymentMethod;
    private Map<String, Integer> concurrentReviews;

    public Map<String, Object> getBedAvailability() {
        return bedAvailability;
    }

    public void setBedAvailability(Map<String, Object> bedAvailability) {
        this.bedAvailability = bedAvailability;
    }

    public Map<String, Object> getTypeLevelCare() {
        return typeLevelCare;
    }

    public void setTypeLevelCare(Map<String, Object> typeLevelCare) {
        this.typeLevelCare = typeLevelCare;
    }

    public Map<String, Object> getTypePaymentMethod() {
        return typePaymentMethod;
    }

    public void setTypePaymentMethod(Map<String, Object> typePaymentMethod) {
        this.typePaymentMethod = typePaymentMethod;
    }

    public Map<String, Integer> getConcurrentReviews() {
        return concurrentReviews;
    }

    public void setConcurrentReviews(Map<String, Integer> concurrentReviews) {
        this.concurrentReviews = concurrentReviews;
    }
}

package pl.air_quality.app.exception;

public class SubscriptionExistException extends RuntimeException {

    public SubscriptionExistException(String email, int cityId) {
        super("Subscription for " + email + " and cityId = " + cityId + " already exists");
    }
}

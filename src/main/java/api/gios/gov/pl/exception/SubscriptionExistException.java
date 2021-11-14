package api.gios.gov.pl.exception;

public class SubscriptionExistException extends RuntimeException {

    public SubscriptionExistException(String email, int cityId) {
        super("Subscription for " + email + " and cityId = " + cityId + " already exists");
    }
}

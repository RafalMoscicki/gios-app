package pl.air_quality.app.exception;

public class SubscriptionDoesNotExistException extends RuntimeException {

    public SubscriptionDoesNotExistException(long subscriptionId) {
        super("Subscription " + subscriptionId + " does not exist");
    }
}

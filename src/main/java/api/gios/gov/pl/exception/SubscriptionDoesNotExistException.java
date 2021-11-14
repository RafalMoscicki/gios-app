package api.gios.gov.pl.exception;

public class SubscriptionDoesNotExistException extends RuntimeException {

    public SubscriptionDoesNotExistException(long subscriptionId) {
        super("Subscription " + subscriptionId + " does not exist");
    }
}

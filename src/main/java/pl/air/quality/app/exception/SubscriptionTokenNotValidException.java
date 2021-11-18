package pl.air.quality.app.exception;

public class SubscriptionTokenNotValidException extends RuntimeException {

    public SubscriptionTokenNotValidException(String token) {
        super("Token " + token + " not valid");
    }
}

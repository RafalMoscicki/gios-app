package api.gios.gov.pl.exception;

public class SubscriptionTokenNotValidException extends RuntimeException {

    public SubscriptionTokenNotValidException(String token) {
        super("Token " + token + " not valid");
    }
}

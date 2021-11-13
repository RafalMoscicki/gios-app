package api.gios.gov.pl.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(int cityId) {
        super("City with id=" + cityId + " not found.");
    }
}

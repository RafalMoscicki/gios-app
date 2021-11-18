package pl.air.quality.app.exception;

public class CityNotFoundException extends Exception {

    public CityNotFoundException(int cityId) {
        super("City with id=" + cityId + " not found.");
    }
}

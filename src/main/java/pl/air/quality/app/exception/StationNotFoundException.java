package pl.air.quality.app.exception;

public class StationNotFoundException extends Exception {

    public StationNotFoundException(int stationId) {
        super("Station with id=" + stationId + " not found");
    }
}

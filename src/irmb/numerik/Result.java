package irmb.numerik;

/**
 * Created by Sven on 09.11.2016.
 */
public class Result {

    private double coordinate;
    private double simulatedResult;
    private double analyticResult;
    private double error;

    public double getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(double coordinate) {
        this.coordinate = coordinate;
    }

    public double getSimulatedResult() {
        return simulatedResult;
    }

    public void setSimulatedResult(double simulatedResult) {
        this.simulatedResult = simulatedResult;
    }

    public double getAnalyticResult() {
        return analyticResult;
    }

    public void setAnalyticResult(double analyticResult) {
        this.analyticResult = analyticResult;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}

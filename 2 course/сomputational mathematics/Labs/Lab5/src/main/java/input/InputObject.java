package input;

import comulation.Coordinate;

public class InputObject {
    private int numberOfEquation;
    private Coordinate initialPoint;
    private double[] bounds;
    private double accuracy;

    public InputObject() {
    }

    public InputObject(int numberOfEquation, Coordinate initialPoint, double[] bounds, double accuracy) {
        this.numberOfEquation = numberOfEquation;
        this.initialPoint = initialPoint;
        this.bounds = bounds;
        this.accuracy = accuracy;
    }

    public int getNumberOfEquation() {
        return numberOfEquation;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public Coordinate getInitialPoint() {
        return initialPoint;
    }

    public double[] getBounds() {
        return bounds;
    }
}

package drawUtils;

import comulation.Coordinate;

import java.util.ArrayList;

public class DrawData {
    private ArrayList<Coordinate> approximationCoordinates;
    private ArrayList<Coordinate> analyticCoordinates;
    private Coordinate initialPoint;
    private String graphHeader;

    public DrawData(ArrayList<Coordinate> approximationCoordinates, ArrayList<Coordinate> analyticCoordinates, Coordinate initialPoint, String graphHeader) {
        this.approximationCoordinates = approximationCoordinates;
        this.analyticCoordinates = analyticCoordinates;
        this.initialPoint = initialPoint;
        this.graphHeader = graphHeader;
    }

    public Coordinate getInitialPoint() {
        return initialPoint;
    }

    public ArrayList<Coordinate> getAnalyticCoordinates() {
        return analyticCoordinates;
    }

    public ArrayList<Coordinate> getApproximationCoordinates() {
        return approximationCoordinates;
    }

    public String getGraphHeader() {
        return graphHeader;
    }

    public void setAnalyticCoordinates(ArrayList<Coordinate> analyticCoordinates) {
        this.analyticCoordinates = analyticCoordinates;
    }

    public void setApproximationCoordinates(ArrayList<Coordinate> approximationCoordinates) {
        this.approximationCoordinates = approximationCoordinates;
    }

    public void setGraphHeader(String graphHeader) {
        this.graphHeader = graphHeader;
    }

    public void setInitialPoint(Coordinate initialPoint) {
        this.initialPoint = initialPoint;
    }
}

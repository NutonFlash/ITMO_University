package comulation;

import equation.Equation;
import input.InputObject;

import java.util.ArrayList;
import java.util.function.Function;

public class ComputationalMethods {
    private InputObject inputObject;
    private Equation equation;
    final private double h;
    private Function<Coordinate, Double> function;

    public ComputationalMethods(InputObject inputObject, Equation equation) {
        this.inputObject = inputObject;
        this.equation = equation;
        this.h = Math.pow(inputObject.getAccuracy(), 0.25);
        this.function = equation.getFunction();
    }

    public ArrayList<Coordinate> findApproximationPoints() {
        double[] bounds  = inputObject.getBounds();
        long n = Math.round(Math.abs(bounds[1]-bounds[0]) / h);
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(inputObject.getInitialPoint());
        for (int i=1; i<n; ++i) {
            coordinates.add(calcNextPoint(coordinates.get(coordinates.size()-1)));
        }
        if (coordinates.get(coordinates.size()-1).getX() < bounds[1])
            coordinates.add(calcNextPoint(coordinates.get(coordinates.size()-1)));
        else if (coordinates.get(coordinates.size()-1).getX() > bounds[1])
            coordinates.remove(coordinates.size()-1);
        return coordinates;
    }

    public ArrayList<Coordinate> findAnalyticPoints() {
        double[] bounds  = inputObject.getBounds();
        final double C = equation.getExpressedC().apply(inputObject.getInitialPoint());
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        double x0 = bounds[0];
        double h = Math.abs(bounds[1]-bounds[0])/500;
        Coordinate point = new Coordinate(x0, C);
        for (double x=x0; x<=bounds[1]; x+=h) {
            point.setX(x);
            coordinates.add(new Coordinate(x, equation.getCommonDecision().apply(point)));
        }
        return coordinates;
    }

    private Coordinate calcNextPoint(Coordinate point) {
        return new Coordinate(point.getX()+h, point.getY()+calcDeltaY(point));
    }

    private double calcDeltaY(Coordinate point) {
        double k1 = h*function.apply(point);
        double k2 = h*function.apply(new Coordinate(point.getX() + h/2, point.getY() + k1/2));
        double k3 = h*function.apply(new Coordinate(point.getX() + h/2, point.getY() + k2/2));
        double k4 = h*function.apply(new Coordinate(point.getX() + h, point.getY() + k3));
        return 1d/6d*(k1+2*k2+2*k3+k4);
    }
}

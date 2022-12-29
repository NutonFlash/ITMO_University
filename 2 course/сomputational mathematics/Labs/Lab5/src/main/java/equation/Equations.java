package equation;

import comulation.Coordinate;

import java.util.ArrayList;
import java.util.function.Function;

public class Equations {
    private final ArrayList<Equation> equations = new ArrayList<>();

    public Equations() {
        equations.add(initFirstEquation());
        equations.add(initSecondEquation());
        equations.add(initThirdEquation());
    }

    public ArrayList<Equation> getEquations() {
        return equations;
    }

    public int getSize() {
        return equations.size();
    }

    private Equation initFirstEquation() {
        final String exp = "y' = x^2 - 2y";
        final Function<Coordinate, Double> function = (point) -> Math.pow(point.getX(), 2) - 2*point.getY();
        final Function<Coordinate, Double> expressedC = (point) -> (point.getY() - Math.pow(point.getX(), 2)/2 + point.getX()/2 - 0.25)*Math.pow(Math.E, 2* point.getX());
        final Function<Coordinate, Double> commonDecision = (point) -> point.getY()/Math.pow(Math.E, 2* point.getX()) + Math.pow(point.getX(), 2)/2 - point.getX()/2 + 0.25;
        return new Equation(function, expressedC, commonDecision, exp);
    }

    private Equation initSecondEquation() {
        final String exp = "y' = 5xy";
        final Function<Coordinate, Double> function = (point) -> 5* point.getX()* point.getY();
        final Function<Coordinate, Double> expressedC = (point) -> point.getY()/Math.pow(Math.E, 2.5*Math.pow(point.getX(), 2));
        final Function<Coordinate, Double> commonDecision = (point) -> point.getY()*Math.pow(Math.E, 2.5*Math.pow(point.getX(), 2));
        return new Equation(function, expressedC, commonDecision, exp);
    }

    private Equation initThirdEquation() {
        final String exp = "y' = 3x";
        final Function<Coordinate, Double> function = (point) -> 3* point.getX();
        final Function<Coordinate, Double> expressedC = (point) -> point.getY() - 3*Math.pow(point.getX(), 2)/2;
        final Function<Coordinate, Double> commonDecision = (point) -> 3*Math.pow(point.getX(), 2)/2 + point.getY();
        return new Equation(function, expressedC, commonDecision, exp);
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < equations.size(); i++)
            output += " " + (i + 1) + ") " + equations.get(i) + "\n";
        return output;
    }
}

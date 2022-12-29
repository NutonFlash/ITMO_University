import java.util.HashMap;
import java.util.function.Function;

public class Equation {
    private String equationInString;
    private Function<Double, Double> function;
    private HashMap<Double, Boolean> punchedPoints;

    public Equation() {}

    public Equation(HashMap<Double, Boolean> punchedPoints, String equationInString, Function<Double, Double> function) {
        this.punchedPoints = punchedPoints;
        this.equationInString = equationInString;
        this.function = function;
    }

    public void setEquationInString(String equationInString) {
        this.equationInString = equationInString;
    }

    public void setFunction(Function<Double, Double> function) {
        this.function = function;
    }

    public void setPunchedPoints(HashMap<Double, Boolean> punchedPoints) {
        this.punchedPoints = punchedPoints;
    }

    public HashMap<Double, Boolean> getPunchedPoints() {
        return punchedPoints;
    }

    public Function<Double, Double> getFunction() {
        return function;
    }

    @Override
    public String toString() {
        return this.equationInString;
    }
}

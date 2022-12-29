package computation;

import java.util.ArrayList;
import java.util.function.Function;

public class Equations {
    private final ArrayList<String> expressionList = new ArrayList<>();
    private final ArrayList<Function<Double, Double>> functions = new ArrayList<>();
    private final int numberOfEquations;

    public Equations() {
        functions.add(x -> Math.sin(x) + Math.cos(x));
        functions.add(x -> 2 * Math.pow(x, 2) + 0.2*x- 4);
        functions.add(x -> Math.pow(x, 4) - 3 * Math.pow(x, 2) + x);
        functions.add(x -> 0.3 * Math.pow(x, 2) + x - 0.3);
        functions.add(x -> 0.2 * Math.pow(x, 2) + x - 0.1 * Math.pow(x, 3) - 0.75);

        expressionList.add("sin(x) + cos(x) = 0");
        expressionList.add("2x^2 + 0.2x - 4 = 0");
        expressionList.add("x^4 - 3x^2 + x = 0");
        expressionList.add("0.3x^2 + x - 0.3 = 0");
        expressionList.add("0.2x^2 + x - 0.1x^3 - 0.75 = 0");
        this.numberOfEquations = expressionList.size();
    }

    public int getNumberOfEquations() {
        return numberOfEquations;
    }

    public ArrayList<Function<Double, Double>> getFunctions() {
        return functions;
    }

    public ArrayList<String> getEquationsForOneVar() {
        return expressionList;
    }

    @Override
    public String toString() {
        String equations = "";
        for (int i=0; i<numberOfEquations; ++i)
            equations += (i+1)+") "+expressionList.get(i) + "\n";
        return equations;
    }
}

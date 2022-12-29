package equation;

import comulation.Coordinate;

import java.util.function.Function;

public class Equation {
    private final Function<Coordinate, Double> function;
    private final Function<Coordinate, Double> expressedC;
    private final Function<Coordinate, Double> commonDecision;
    private final String stringExpression;

    public Equation(Function<Coordinate, Double> function, Function<Coordinate, Double> expressedC, Function<Coordinate, Double> commonDecision, String expression) {
        this.function = function;
        this.expressedC = expressedC;
        this.commonDecision = commonDecision;
        this.stringExpression = expression;
    }

    public Function<Coordinate, Double> getFunction() {
        return function;
    }

    public Function<Coordinate, Double> getCommonDecision() {
        return commonDecision;
    }

    public Function<Coordinate, Double> getExpressedC() {
        return expressedC;
    }

    public String getStringExpression() {
        return stringExpression;
    }

    @Override
    public String toString() {
        return stringExpression;
    }
}

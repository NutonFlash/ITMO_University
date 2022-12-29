package computation;

import computation.exceptions.AbsException;
import computation.exceptions.MethodDivergesException;
import computation.exceptions.TooMuchIterationException;
import computation.system.SystemInterface;
import inputManager.InputObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class ComputationMethods {

    private final int LIMIT = 20000;
    private final InputObject inputObject;
    private final ArrayList<Function<Double, Double>> functions;
    private SystemInterface system;
    private int itrCounter1;
    private int itrCounter2;
    private int itrCounter3;

    public ComputationMethods(InputObject inputObject, ArrayList<SystemInterface> systems) {
        this.inputObject = inputObject;
        this.functions = new Equations().getFunctions();
        if (inputObject.getEquationType() == InputObject.typeOfEquation.SYSTEM_OF_EQUATIONS) {
            system = systems.get(inputObject.getNumberOfSystem() - 1);
        }
    }

    public double solveBySecantMethod(int numberOfEquation) throws AbsException {
        double[] bounds = inputObject.getBounds();
        double error = Double.MAX_VALUE;
        double root = 0;
        Function<Double, Double> function = functions.get(numberOfEquation - 1);
        while (error >= inputObject.getError() && itrCounter1 < LIMIT) {
            root = findAbscissaIntersectionBySecant(bounds, function);
            if (function.apply(root) * function.apply(bounds[0]) < 0)
                bounds[1] = root;
            else bounds[0] = root;
            itrCounter1++;
            error = Math.abs(function.apply(root));
        }
        if (itrCounter1 >= LIMIT)
            throw new TooMuchIterationException("При расчете корня уравнения методом хорд был превышен лимит итераций (limit=" + LIMIT + ")");
        return root;
    }

    public double solveByNewtonMethod(int numberOfEquation) throws AbsException {
        double[] bounds = inputObject.getBounds();
        double a = bounds[0];
        double b = bounds[1];
        double x0;
        double root;
        Function<Double, Double> function = functions.get(numberOfEquation - 1);
        if (function.apply(a) * derivative(function, 2, a) <= 0) {
            x0 = b;
        } else x0 = a;
        root = findAbscissaIntersectionByTangent(numberOfEquation, x0);
        while (Math.abs(root - x0) >= inputObject.getError() && itrCounter2 < LIMIT) {
            x0 = root;
            root = findAbscissaIntersectionByTangent(numberOfEquation, x0);
            itrCounter2++;
        }
        if (itrCounter2 >= LIMIT)
            throw new TooMuchIterationException("При расчете корня уравнения методом касательных был превышен лимит итераций (limit=" + LIMIT + ")");
        return root;
    }

    public Vector findRootsBySimpleIterationMethod(double[] point) throws AbsException {
        Matrix lambdas = findLambdasMatrix(point);
        Vector pointVector = new Vector(point);
        ArrayList<Function<Vector, Double>> functions = system.getSystem();
        double delta;
        do {
            delta = 0;
            double[] funcValues = new double[system.size()];
            for (int i = 0; i < point.length; ++i) {
                funcValues[i] = functions.get(i).apply(pointVector);
            }
            Vector newPointVector = pointVector.subtract(lambdas.multiply(new Vector(funcValues)));
            for (int i = 0; i < point.length; ++i) {
                if (Double.isNaN(newPointVector.get(i))) {
                    throw new MethodDivergesException("Нельзя решить СНАУ для этого начального приближения "
                            + Arrays.toString(point) +
                            "\nВы можете попробовать указать другое приближение, "
                            + "которое ближе к реальному ответу");
                }
                if (Math.abs(newPointVector.get(i) - pointVector.get(i)) > delta) {
                    delta = Math.abs(newPointVector.get(i) - pointVector.get(i));
                }
            }
            pointVector = new Vector(newPointVector.getVector().clone());
            itrCounter3++;
        } while (delta > inputObject.getError() && itrCounter3 < LIMIT);
        if (itrCounter3 >= LIMIT)
            throw new TooMuchIterationException("При расчете корней СНАУ методом простых итераций был превышен лимит итераций (limit=" + LIMIT + ")");
        return pointVector;
    }

    public int getItrCounter1() {
        return itrCounter1;
    }

    public int getItrCounter2() {
        return itrCounter2;
    }

    public int getItrCounter3() {
        return itrCounter3;
    }

    public boolean verifyOneRoot(double[] bounds, int numberOfEquation) {
        double a = bounds[0];
        double b = bounds[1];
        return functions.get(numberOfEquation - 1).apply(a) * functions.get(numberOfEquation - 1).apply(b) < 0;
    }

    public boolean verifyBoundsForNewtonMethod(double[] bounds, int numberOfEquation) {
        double a = bounds[0];
        double b = bounds[1];
        if (!verifyOneRoot(bounds, numberOfEquation)) return false;
        return (!(functions.get(numberOfEquation - 1).apply(a) * derivative(functions.get(numberOfEquation - 1), 2, a) <= 0))
                || (!(functions.get(numberOfEquation - 1).apply(b) * derivative(functions.get(numberOfEquation - 1), 2, b) <= 0));
    }

    private double[][] findJacobian(double[] point) {
        ArrayList<Function<Vector, Double>> functions = system.getSystem();
        double[][] jacobian = new double[point.length][point.length];
        for (int i = 0; i < point.length; ++i) {
            for (int j = 0; j < point.length; ++j) {
                jacobian[i][j] = partialDerivative(j, point, functions.get(i));
            }
        }
        return jacobian;
    }

    private Matrix findLambdasMatrix(double[] point) throws MethodDivergesException {
        double[][] jacobian = findJacobian(point);
        Matrix lambdas = new Matrix(jacobian).getReverseMatrix();
        if (lambdas == null) {
            throw new MethodDivergesException("Якобиан для точки " +
                    Arrays.toString(point) + " равен нулю");
        }
        for (int i = 0; i < lambdas.getMatrix().length; ++i) {
            for (int j = 0; j < lambdas.getMatrix().length; ++j) {
                if (Double.isNaN(lambdas.getElement(i, j))) {
                    throw new MethodDivergesException("Нельзя решить СНАУ для этого начального приближения "
                            + Arrays.toString(point) +
                            "\nВы можете попробовать указать другое приближение, "
                            + "которое ближе к реальному ответу");
                }
            }

        }
        return lambdas;
    }

    private double derivative(Function<Double, Double> function, int numberOfDerivative, double x) {
        double functionValue;
        double step = 0.0001;
        if (numberOfDerivative == 1)
            functionValue = (function.apply(x + step) - function.apply(x - step)) / (2 * step);
        else
            functionValue = (function.apply(x + step) - 2 * function.apply(x) + function.apply(x - step)) / Math.pow(step, 2);
        return functionValue;
    }

    private double partialDerivative(int index, double[] vars, Function<Vector, Double> function) {
        double oldF = function.apply(new Vector(vars));
        double step = 0.0001;
        vars[index] += step;
        double newF = function.apply(new Vector(vars));
        vars[index] -= step;
        return (newF - oldF) / step;
    }

    private double findAbscissaIntersectionBySecant(double[] bounds, Function<Double, Double> function) {
        double a = bounds[0];
        double b = bounds[1];
        return a - function.apply(a) / (function.apply(b) - function.apply(a)) * (b - a);
    }

    private double findAbscissaIntersectionByTangent(int numberOfEquation, double x) {
        return x - functions.get(numberOfEquation - 1).apply(x) / derivative(functions.get(numberOfEquation - 1), 1, x);
    }
}

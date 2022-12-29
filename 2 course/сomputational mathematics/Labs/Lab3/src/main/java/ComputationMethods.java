import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

public class ComputationMethods {
    private final Equations equations = new Equations();
    private double[] bounds;
    private Function<Double, Double> function;
    private double step;
    private int n;

    public ComputationMethods(InputObject inputObject) throws InterruptedException {
        int numberOfEquation = inputObject.getNumberOfEquation();
        Equation equation = equations.getEquations().get(numberOfEquation-1);
        this.bounds = inputObject.getBounds();
        this.function = equation.getFunction();
        this.step = inputObject.getStep();
        this.n = (int) Math.round(Math.abs(bounds[1] - bounds[0]) / step);
        Stack<Double> stack1 = new Stack<>();
        Stack<Double> stack2 = new Stack<>();
        Stack<Double> stack3 = new Stack<>();
        if (equation.getPunchedPoints().size() > 0) {
            int fixedPoints = (int) equation.getPunchedPoints().entrySet().stream().filter(Map.Entry::getValue).count();
            int unfixedPoints = (int) equation.getPunchedPoints().entrySet().stream().filter(key -> !key.getValue()).count();
            if (unfixedPoints > 0) {
                if (unfixedPoints == 1)
                    System.out.println("В функции " + unfixedPoints + " неустранимый разрыв, поэтому не получится посчитать интеграл");
                else
                    System.out.println("В функции " + unfixedPoints + " неустранимых разрыва, поэтому не получится посчитать интеграл");
                System.exit(0);
            }
            else {
                equation.getPunchedPoints().entrySet().stream().filter(Map.Entry::getValue).forEach(obj -> {
                    stack1.push(obj.getKey()); stack2.push(obj.getKey()); stack3.push(obj.getKey());
                });
                if (fixedPoints == 1)
                    System.out.println("В функции "+fixedPoints+" устранимый разрыв, поэтому при решении интеграла значение в этой точке будет заменено средним от суммы двух соседних точек");
                else
                    System.out.println("В функции "+fixedPoints+" устранимых разрыва, поэтому при решении интеграла значение в этих точках будет заменено средним от суммы двух соседних точек");
            }
        }
        double maxValue = findTheGreatestValue(function, bounds);
        double middleRectanglesErr = getErrorForMiddleRectangles(maxValue);
        double sideRectanglesErr = getErrorForLeftRectangles(maxValue);
        System.out.println("Значения интеграла от функции "+numberOfEquation+" на участке ["+bounds[0]+", "+bounds[1]+"]:");
        Runnable runnable1 = () -> System.out.println("  методом средних прямоугольников: "+solveByMiddleRectangles(stack1) + " (погрешность - "+middleRectanglesErr+")");
        Runnable runnable2 = () -> System.out.println("  методом левых прямоугольников: "+solveByLeftRectangles(stack2) + " (погрешность - "+sideRectanglesErr+")");
        Runnable runnable3 = () -> System.out.println("  методом правых прямоугольников: "+solveByRightRectangles(stack3) + " (погрешность - "+sideRectanglesErr+")");
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("Количество итераций: "+ Math.round(Math.abs(bounds[1]-bounds[0]) / step));
    }

    public double solveByMiddleRectangles(Stack<Double> fixedPointStack) {
        double x = bounds[0];
        double result = 0;
        while (x < bounds[1]) {
            if (!fixedPointStack.isEmpty()) {
                if (fixedPointStack.peek() != x + step / 2)
                    result += function.apply(x + step / 2) * step;
                else {
                    result += (function.apply(x + step / 4) + function.apply(x + 3 * step / 4)) / 2 * step;
                    fixedPointStack.pop();
                }
            } else result += function.apply(x + step / 2) * step;
            x += step;
        }
        return result;
    }

    public double solveByLeftRectangles(Stack<Double> fixedPointStack) {
        double x = bounds[0];
        double result = 0;
        while (x < bounds[1]) {
            if (!fixedPointStack.isEmpty()) {
                if (fixedPointStack.peek() != x)
                    result += function.apply(x) * step;
                else {
                    result += (function.apply(x - step / 2) + function.apply(x + step / 2)) / 2 * step;
                    fixedPointStack.pop();
                }
            } else result += function.apply(x) * step;
            x += step;
        }
        return result;
    }

    public double solveByRightRectangles(Stack<Double> fixedPointStack) {
        double x = bounds[0] + step;
        double result = 0;
        while (x <= bounds[1]) {
            if (!fixedPointStack.isEmpty()) {
                if (fixedPointStack.peek() != x)
                    result += function.apply(x) * step;
                else {
                    result += (function.apply(x - step / 2) + function.apply(x + step / 2)) / 2 * step;
                    fixedPointStack.pop();
                }
            } else result += function.apply(x) * step;
            x += step;
        }
        return result;
    }

    private double getErrorForMiddleRectangles(double maxValue) {
        return maxValue * n * Math.pow(step, 3) / 24;
    }

    private double getErrorForLeftRectangles(double maxValue) {
        return maxValue * n * Math.pow(step , 2) / 2;
    }

    private double findTheGreatestValue(Function<Double, Double> function, double[] bounds) {
        double maxValue = Math.abs(derivative(function,2, bounds[0]));
        for (double i=bounds[0]; i<bounds[1]; i+=step) {
            if (Math.abs(derivative(function,2, i)) > maxValue)
                maxValue = derivative(function,2, i);
        }
        return maxValue;
    }
    private double derivative(Function<Double, Double> function, int numberOfDerivative, double x) {
        double functionValue;
        double step = 0.00001;
        if (numberOfDerivative == 1)
            functionValue = (function.apply(x + step) - function.apply(x - step)) / (2 * step);
        else
            functionValue = (function.apply(x + step) - 2 * function.apply(x) + function.apply(x - step)) / Math.pow(step, 2);
        return functionValue;
    }
}

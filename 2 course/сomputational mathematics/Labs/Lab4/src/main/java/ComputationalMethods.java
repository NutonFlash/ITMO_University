import java.util.ArrayList;

public class ComputationalMethods {
    private final double x;
    private final GraphicObject graphicObject;
    private double step = 0;

    public ComputationalMethods(Equation equation, double x) {
        this.x = x;
        graphicObject = new GraphicObject(calcCoefficientsOfPolynomial(equation.getInterpolationData()), equation.getInterpolationData());
        ArrayList<Coordinate> coordinates = graphicObject.getCoordinates();
        if (coordinates.size() > 1) {
            this.step = coordinates.get(1).getX() - coordinates.get(0).getX();
        }
        System.out.println("Начальный набор точек:\n" + printPoints(coordinates));
        System.out.println("\nПолином Ньютона, рассчитанный по начальным точкам:");
        System.out.println(printNewtonPolynomial(graphicObject));
        System.out.println("\nЗначение функции, рассчитанной в точке x=" + x + ": " + calcByNewtonPolynomial(x, graphicObject));
    }

    public double calcByNewtonPolynomial(double x, GraphicObject object) {
        double result = object.getCoordinates().get(0).getY();
        double x0 = object.getCoordinates().get(0).getX();
        double q = (x - x0) / step;
        for (int i = 1; i < object.getCoordinates().size(); i++) {
            double numerator = 1;
            for (int j = 0; j < i; j++)
                numerator *= q - j;
            result += numerator * object.getCoefficientsOfPolynomial()[i - 1];
        }
        return result;
    }

    private double[] calcCoefficientsOfPolynomial(ArrayList<Coordinate> coordinates) {
        double[] coefficients = new double[coordinates.size() - 1];
        for (int i = 1; i < coordinates.size(); i++) {
            coefficients[i - 1] = calcFiniteDifference(coordinates, 0, i) / factorial(i);
        }
        return coefficients;
    }

    private double calcFiniteDifference(ArrayList<Coordinate> coordinates, int k, int degreeOfPolynomial) {
        if (degreeOfPolynomial == 1)
            return coordinates.get(k + 1).getY() - coordinates.get(k).getY();
        else
            return calcFiniteDifference(coordinates, k + 1, degreeOfPolynomial - 1) - calcFiniteDifference(coordinates, k, degreeOfPolynomial - 1);
    }

    private long factorial(int number) {
        long result = 1;
        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }
        return result;
    }

    private String printPoints(ArrayList<Coordinate> coordinates) {
        String output = "";
        for (int i = 0; i < coordinates.size(); ++i)
            output += (i + 1) + "." + coordinates.get(i) + " ";
        return output;
    }

    public String printNewtonPolynomial(GraphicObject object) {
        String output = "P(x) = " + object.getCoordinates().get(0).getY();
        for (int i = 0; i < object.getCoefficientsOfPolynomial().length; ++i)
            output += String.format((" + %.1f*" + printQ(i)), object.getCoefficientsOfPolynomial()[i]);
        return output;
    }

    private String printQ(int index) {
        if (index == 0) return "q";
        else return printQ(index - 1) + "(q-" + index + ")";
    }

    public double getStep() {
        return step;
    }

    public double getX() {
        return x;
    }

    public GraphicObject getGraphicObject() {
        return graphicObject;
    }

    public class GraphicObject {
        private final double[] coefficientsOfPolynomial;
        private final ArrayList<Coordinate> coordinates;

        public GraphicObject(double[] coefficientsOfPolynomial, ArrayList<Coordinate> coordinates) {
            this.coefficientsOfPolynomial = coefficientsOfPolynomial;
            this.coordinates = coordinates;
        }

        public double[] getCoefficientsOfPolynomial() {
            return coefficientsOfPolynomial;
        }

        public ArrayList<Coordinate> getCoordinates() {
            return coordinates;
        }
    }
}

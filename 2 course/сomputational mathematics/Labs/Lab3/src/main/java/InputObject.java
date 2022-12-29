public class InputObject {
    private int numberOfEquation;
    private double[] bounds;
    private double step;

    public InputObject() {}

    public InputObject(int numberOfEquation, double[] bounds, double step) {
        this.numberOfEquation = numberOfEquation;
        this.bounds = bounds;
        this.step = step;
    }

    public int getNumberOfEquation() {
        return numberOfEquation;
    }

    public double getStep() {
        return step;
    }

    public double[] getBounds() {
        return bounds;
    }
}

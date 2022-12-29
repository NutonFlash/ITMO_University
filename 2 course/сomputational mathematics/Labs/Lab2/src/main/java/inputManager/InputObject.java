package inputManager;

public class InputObject {

    private typeOfEquation equationType;
    private Integer numberOfEquation;
    private double[] bounds;
    private int numberOfSystem;
    private double[] approximation;
    private double error;
    private boolean boundsForSecantCorrect;
    private boolean boundsForNewtonCorrect;

    public InputObject() {
    }

    public Integer getNumberOfEquation() {
        return numberOfEquation;
    }

    public void setNumberOfEquation(Integer numberOfEquation) {
        this.numberOfEquation = numberOfEquation;
    }

    public typeOfEquation getEquationType() {
        return equationType;
    }

    public void setEquationType(typeOfEquation equationType) {
        this.equationType = equationType;
    }

    public double[] getBounds() {
        return bounds;
    }

    public void setBounds(double[] bounds) {
        this.bounds = bounds;
    }

    public int getNumberOfSystem() {
        return numberOfSystem;
    }

    public void setNumberOfSystem(int numberOfSystem) {
        this.numberOfSystem = numberOfSystem;
    }

    public double[] getApproximation() {
        return approximation;
    }

    public void setApproximation(double[] approximation) {
        this.approximation = approximation;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public boolean isBoundsForSecantCorrect() {
        return boundsForSecantCorrect;
    }

    public void setBoundsForSecantCorrect(boolean boundsForSecantCorrect) {
        this.boundsForSecantCorrect = boundsForSecantCorrect;
    }

    public boolean isBoundsForNewtonCorrect() {
        return boundsForNewtonCorrect;
    }

    public void setBoundsForNewtonCorrect(boolean boundsForNewtonCorrect) {
        this.boundsForNewtonCorrect = boundsForNewtonCorrect;
    }

    public enum typeOfEquation {
        EQUATION,
        SYSTEM_OF_EQUATIONS
    }
}

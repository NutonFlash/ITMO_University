public class Matrix {

    public Matrix(double[][] coefficientsOfUnknowns, double[] freeCoefficients, int numberOfEquations, int numberOfUnknowns) {
        this.coefficientsOfUnknowns = coefficientsOfUnknowns;
        this.freeCoefficients = freeCoefficients;
        this.numberOfEquations = numberOfEquations;
        this.numberOfUnknowns = numberOfUnknowns;
    }

    private double[][] coefficientsOfUnknowns;
    private double[] freeCoefficients;
    private int numberOfEquations;
    private int numberOfUnknowns;


    public double[][] getCoefficientsOfUnknowns() {
        return this.coefficientsOfUnknowns;
    }

    public double[] getFreeCoefficients() {
        return this.freeCoefficients;
    }

    public int getNumberOfEquations() { return numberOfEquations; }
    public int getNumberOfUnknowns() { return numberOfUnknowns; }

    public Matrix getCopyOfSquareMatrix() {
        double[][] coefficientsOfUnknowns = new double[this.getNumberOfUnknowns()][this.getNumberOfUnknowns()];
        double[] freeCoefficients = new double[this.getNumberOfUnknowns()];
        for (int i = 0; i < this.getNumberOfUnknowns(); ++i) {
            for (int j = 0; j < this.getNumberOfUnknowns(); ++j)
                coefficientsOfUnknowns[i][j] = this.getCoefficientsOfUnknowns()[i][j];
            freeCoefficients[i] = this.getFreeCoefficients()[i];
        }
        return new Matrix(coefficientsOfUnknowns,freeCoefficients,this.getNumberOfUnknowns(),this.getNumberOfUnknowns());
    }

    public Matrix getCopy() {
        double[][] coefficientsOfUnknowns = new double[this.getNumberOfEquations()][this.getNumberOfUnknowns()];
        double[] freeCoefficients = new double[this.getNumberOfEquations()];
        for (int i = 0; i < this.getNumberOfEquations(); ++i) {
            for (int j = 0; j < this.getNumberOfUnknowns(); ++j)
                coefficientsOfUnknowns[i][j] = this.getCoefficientsOfUnknowns()[i][j];
            freeCoefficients[i] = this.getFreeCoefficients()[i];
        }
        return new Matrix(coefficientsOfUnknowns,freeCoefficients,this.getNumberOfEquations(),this.getNumberOfUnknowns());
    }

    @Override
    public String toString(){
        String matrix = "";
        for (int i = 0; i<numberOfEquations ; ++i) {
            for (int j = 0; j < numberOfUnknowns; ++j) {
                matrix+=String.format("%.2f",this.coefficientsOfUnknowns[i][j])+"\t";
            }
            matrix+=" |"+String.format("%.2f",freeCoefficients[i])+"\n";
        }
        return matrix;
    }
}

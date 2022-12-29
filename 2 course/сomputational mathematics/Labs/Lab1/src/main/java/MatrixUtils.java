public class MatrixUtils {

    public MatrixUtils(){}

    public boolean validateMatrixForMethodGauss(Matrix matrix) {
        for (int i=0; i<matrix.getNumberOfEquations() ; ++i) {
            if (matrix.getCoefficientsOfUnknowns()[i][i] == 0) {
                if(!replaceMatrixLine(matrix,i) && !replaceMatrixColumn(matrix,i)) {
                    System.out.println("Ведущий элемент "+(i+1)+" строки равняется нулю.\nНе удалось найти корректную перестановку, поэтому СЛАУ не сможет быть решено методом Гаусса.");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean replaceMatrixLine(Matrix matrix, int elementIndex) {
        boolean isReplacingSuccessful = false;
        for (int i=0; i<matrix.getNumberOfEquations() ; ++i) {
            if (matrix.getCoefficientsOfUnknowns()[elementIndex][i]!=0 && matrix.getCoefficientsOfUnknowns()[i][elementIndex]!=0) {
                double[] problemLine = new double[matrix.getNumberOfUnknowns()];
                System.arraycopy(matrix.getCoefficientsOfUnknowns()[elementIndex],0,problemLine,0,matrix.getNumberOfUnknowns());
                matrix.getCoefficientsOfUnknowns()[elementIndex] = matrix.getCoefficientsOfUnknowns()[i];
                matrix.getCoefficientsOfUnknowns()[i] = problemLine;
                double freeCoefficientFromProblemLine = matrix.getFreeCoefficients()[elementIndex];
                matrix.getFreeCoefficients()[elementIndex] = matrix.getFreeCoefficients()[i];
                matrix.getFreeCoefficients()[i] = freeCoefficientFromProblemLine;
                isReplacingSuccessful = true;
                break;
            }
        }
        return isReplacingSuccessful;
    }

    private boolean replaceMatrixColumn(Matrix matrix, int elementIndex) {
        boolean isReplacingSuccessful = false;
        for (int i=0; i<matrix.getNumberOfUnknowns() ; ++i) {
            if (matrix.getCoefficientsOfUnknowns()[elementIndex][i]!=0 && matrix.getCoefficientsOfUnknowns()[i][elementIndex]!=0) {
                for (int j=0; i<matrix.getNumberOfEquations() ; ++j) {
                    double elementFromProblemColumn = matrix.getCoefficientsOfUnknowns()[j][elementIndex];
                    matrix.getCoefficientsOfUnknowns()[j][elementIndex] = matrix.getCoefficientsOfUnknowns()[j][i];
                    matrix.getCoefficientsOfUnknowns()[j][i] = elementFromProblemColumn;
                }
                isReplacingSuccessful = true;
                break;
            }
        }
        return isReplacingSuccessful;
    }
}

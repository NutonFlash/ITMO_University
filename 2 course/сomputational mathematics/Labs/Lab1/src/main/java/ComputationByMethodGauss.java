public class ComputationByMethodGauss {

    public ComputationByMethodGauss(){}

    public double calculateDeterminant(Matrix matrix) {
        Matrix convertedMatrix = convertMatrixForDetermination(matrix);
        double determinant = 0;
        if (convertedMatrix!=null) {
            determinant = convertedMatrix.getCoefficientsOfUnknowns()[0][0];
            for (int i = 1; i<convertedMatrix.getNumberOfUnknowns() ; ++i) {
                determinant *= convertedMatrix.getCoefficientsOfUnknowns()[i][i];
            }
        }
        return determinant;
    }

    private Matrix convertMatrixForDetermination(final Matrix matrix) {
        Matrix copiedMatrix = matrix.getCopyOfSquareMatrix();
        for (int i=0; i<matrix.getNumberOfUnknowns()-1 ; ++i) {
            double maxElement = 0;
            int lineIndex = i;
            for (int j=i; j<matrix.getNumberOfUnknowns() ; ++j) {
                if (copiedMatrix.getCoefficientsOfUnknowns()[j][i]!=0 && Math.abs(copiedMatrix.getCoefficientsOfUnknowns()[j][i])>Math.abs(maxElement)) {
                    maxElement = copiedMatrix.getCoefficientsOfUnknowns()[j][i];
                    lineIndex = j;
                }
            }
            if (maxElement == 0 && i==0)
                return null;
            else if (maxElement!=0) {
                double[] firstLine = new double[matrix.getNumberOfUnknowns()];
                double freeCofFirstLine = copiedMatrix.getFreeCoefficients()[i];
                for (int z = 0; z < matrix.getNumberOfUnknowns(); ++z)
                    firstLine[z] = copiedMatrix.getCoefficientsOfUnknowns()[i][z];
                copiedMatrix.getCoefficientsOfUnknowns()[i] = copiedMatrix.getCoefficientsOfUnknowns()[lineIndex];
                copiedMatrix.getCoefficientsOfUnknowns()[lineIndex] = firstLine;
                copiedMatrix.getFreeCoefficients()[i] = copiedMatrix.getFreeCoefficients()[lineIndex];
                copiedMatrix.getFreeCoefficients()[lineIndex] = freeCofFirstLine;
                double[] divideCoefficients = new double[matrix.getNumberOfUnknowns() - 1 - i];
                int arrIndex = 0;
                for (int s = i + 1; s < matrix.getNumberOfUnknowns(); ++s) {
                    divideCoefficients[arrIndex] = -copiedMatrix.getCoefficientsOfUnknowns()[s][i] / maxElement;
                    arrIndex++;
                }
                arrIndex = 0;
                for (int k = i + 1; k < matrix.getNumberOfUnknowns(); ++k) {
                    for (int l = 0; l < matrix.getNumberOfUnknowns(); ++l) {
                        copiedMatrix.getCoefficientsOfUnknowns()[k][l] += divideCoefficients[arrIndex] * copiedMatrix.getCoefficientsOfUnknowns()[i][l];
                    }
                    copiedMatrix.getFreeCoefficients()[k] += divideCoefficients[arrIndex] * copiedMatrix.getFreeCoefficients()[i];
                    arrIndex++;
                }
            }
        }
        return copiedMatrix;
    }

    public Matrix convertToTriangularMatrix(final Matrix matrix) {
        Matrix copiedMatrix = matrix.getCopyOfSquareMatrix();
        for (int i = 0; i < matrix.getNumberOfUnknowns()-1; ++i) {
            double leadingElement = copiedMatrix.getCoefficientsOfUnknowns()[i][i];
            for (int j = i; j < matrix.getNumberOfUnknowns(); ++j) {
                copiedMatrix.getCoefficientsOfUnknowns()[i][j] /= leadingElement;
            }
            copiedMatrix.getFreeCoefficients()[i] /= leadingElement;
            for (int k = i+1; k < matrix.getNumberOfUnknowns(); ++k) {
                double leadingElementOfCurrentEquation = copiedMatrix.getCoefficientsOfUnknowns()[k][i];
                for (int j = i; j < matrix.getNumberOfUnknowns(); ++j)
                    copiedMatrix.getCoefficientsOfUnknowns()[k][j] -= leadingElementOfCurrentEquation*copiedMatrix.getCoefficientsOfUnknowns()[i][j];
                copiedMatrix.getFreeCoefficients()[k] -= leadingElementOfCurrentEquation*copiedMatrix.getFreeCoefficients()[i];
            }
        }
        return copiedMatrix;
    }

    public double[] findUnknownsOfEquations(Matrix triangularMatrix) {
        double[] definitionOfUnknowns = new double[triangularMatrix.getNumberOfUnknowns()];
        final double[][] coefficientsOfUnknowns = triangularMatrix.getCoefficientsOfUnknowns();
        final double[] freeCoefficients = triangularMatrix.getFreeCoefficients();
        for (int i = triangularMatrix.getNumberOfUnknowns()-1; i>=0 ; --i) {
            double subtrahend = 0;
            for (int j = i+1; j<triangularMatrix.getNumberOfUnknowns(); ++j) {
                subtrahend += coefficientsOfUnknowns[i][j]*definitionOfUnknowns[j];
            }
            definitionOfUnknowns[i] = (freeCoefficients[i]-subtrahend) / coefficientsOfUnknowns[i][i];
        }
        return definitionOfUnknowns;
    }

    public double[] calculateFaults(Matrix matrix, double[] definitionOfUnknowns) {
        double[] faults = new double[matrix.getNumberOfUnknowns()];
        for (int i = 0 ; i<matrix.getNumberOfUnknowns() ; ++i) {
            double leftSum = 0;
            for (int j = 0 ; j<matrix.getNumberOfUnknowns() ; ++j)
                leftSum += matrix.getCoefficientsOfUnknowns()[i][j]*definitionOfUnknowns[j];
            faults[i] = Math.abs(leftSum-matrix.getFreeCoefficients()[i]);
        }
        return faults;
    }

    public double calculateMatrixDeterminant(Matrix matrix) {
        return calculateMatrixMinor(matrix.getNumberOfUnknowns(),matrix.getCoefficientsOfUnknowns());
    }

    private double calculateMatrixMinor(int matrixOrder, double[][] coefficientsOfUnknowns) {
        double result = 0;
        if (matrixOrder == 2) {
            result = calculateMatrixMinorForOrderTwo(coefficientsOfUnknowns);
        }
        else
        {
            for (int i = 0; i < matrixOrder ; ++i) {
                double[][] lowerMatrix = new double[matrixOrder-1][matrixOrder-1];
                for (int j = 1; j<matrixOrder ; ++j) {
                    int indexOfArray = 0;
                    for (int z = 0; z < matrixOrder; ++z)
                        if (i != z) {
                            lowerMatrix[j-1][indexOfArray] = coefficientsOfUnknowns[j][z];
                            indexOfArray++;
                        }
                }
                if (i % 2 != 0)
                    result -= coefficientsOfUnknowns[0][i] * calculateMatrixMinor(matrixOrder - 1, lowerMatrix);
                else
                    result += coefficientsOfUnknowns[0][i] * calculateMatrixMinor(matrixOrder - 1, lowerMatrix);
            }
        }
        return result;
    }

    private double calculateMatrixMinorForOrderTwo(double[][] coefficientsOfUnknowns) {
        return (coefficientsOfUnknowns[0][0]*coefficientsOfUnknowns[1][1] - coefficientsOfUnknowns[1][0]*coefficientsOfUnknowns[0][1]);
    }
}

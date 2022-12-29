package computation;

import computation.exceptions.DimensionMismatchException;

public class Matrix {
    private final double[][] matrix;

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix getMinor(int i, int j) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];
        for (int k = 0; k < i; ++k) {
            if (j >= 0) {
                System.arraycopy(matrix[k], 0, minor[k], 0, j);
            }
        }
        for (int k = i + 1; k < n; ++k) {
            for (int p = 0; p < j; ++p) {
                minor[k - 1][p] = matrix[k][p];
            }
        }
        for (int k = i + 1; k < n; ++k) {
            for (int p = j + 1; p < n; ++p) {
                minor[k - 1][p - 1] = matrix[k][p];
            }
        }
        for (int k = 0; k < i; ++k) {
            for (int p = j + 1; p < n; ++p) {
                minor[k][p - 1] = matrix[k][p];
            }
        }
        return new Matrix(minor);
    }

    public double getDeterminant() {
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        double deter = 0;
        for (int i = 0; i < matrix.length; ++i) {
            int sign = i % 2 == 0 ? 1 : -1;
            deter += sign * matrix[0][i] * getMinor(0, i).getDeterminant();
        }
        return deter;
    }

    public Matrix transpose() {
        double[][] transposed = new double[matrix.length][matrix.length];
        for (int i = 0; i < transposed.length; ++i) {
            for (int j = 0; j < transposed.length; ++j) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return new Matrix(transposed);
    }

    public Matrix getReverseMatrix() {
        double det = getDeterminant();
        if (Math.abs(det) < 1e-5) {
            return null;
        }
        double[][] reversed = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                int sign = (i + j) % 2 == 0 ? 1 : -1;
                reversed[j][i] = sign * getMinor(i, j).getDeterminant() / det;
            }
        }
        return new Matrix(reversed);
    }

    public Vector multiply(Vector vec) throws DimensionMismatchException {
        double[] vector = vec.getVector();
        if (matrix[0].length != vector.length) {
            throw new DimensionMismatchException("Shapes of matrix and vector don't match ("
                    + matrix.length + " x " + matrix[0].length + ") and ("
                    + vector.length + " x 1)");
        }
        double[] res = new double[matrix.length];
        for (int i = 0; i < matrix.length; ++i) {
            double sum = 0;
            for (int j = 0; j < vector.length; ++j) {
                sum += matrix[i][j] * vector[j];
            }
            res[i] = sum;
        }
        return new Vector(res);
    }

    public Matrix multiply(Matrix matrix2) throws DimensionMismatchException {
        double[][] other = matrix2.getMatrix();
        if (matrix[0].length != other.length) {
            throw new DimensionMismatchException("Размер матрицы и вектора не совпадают ("
                    + matrix.length + " x " + matrix[0].length + ") и ("
                    + other.length + " x " + other[0].length + ")");
        }
        double[][] result = new double[matrix.length][other[0].length];
        for (int i = 0; i < result.length; ++i) {
            for (int j = 0; j < result[0].length; ++j) {
                double num = 0;
                for (int p = 0; p < matrix[0].length; ++p) {
                    num += matrix[i][p] * other[p][j];
                }
                result[i][j] = num;
            }
        }
        return new Matrix(result);
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double getElement(int i, int j) {
        return matrix[i][j];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : matrix) {
            sb.append("| ");
            for (double element : row) {
                sb.append(String.format("%.4f", element)).append(" ");
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}

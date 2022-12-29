package computation;

public class Vector {
    private final double[] vector;

    public Vector(double[] vector) {
        this.vector = vector;
    }

    public Vector multiply(double num) {
        double[] res = new double[vector.length];
        for (int i = 0; i < vector.length; ++i) {
            res[i] *= num;
        }
        return new Vector(res);
    }

    public Vector subtract(Vector o) {
        double[] res = new double[vector.length];
        for (int i = 0; i < vector.length; ++i) {
            res[i] = vector[i] - o.get(i);
        }
        return new Vector(res);
    }

    public double get(int i) {
        return vector[i];
    }

    public double[] getVector() {
        return vector;
    }
}


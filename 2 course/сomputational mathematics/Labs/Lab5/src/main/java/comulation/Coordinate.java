package comulation;

public class Coordinate implements Comparable<Coordinate> {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", x, y);
    }

    @Override
    public int compareTo(Coordinate o) {
        return Double.compare(this.getX(), o.getX());
    }
}

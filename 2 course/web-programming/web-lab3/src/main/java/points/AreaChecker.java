package points;

public class AreaChecker {

    private long startTime;

    public Result handleNumbers(float x, float y, float r) {
        startTime = System.nanoTime();
        boolean hitValue = checkGetInto(x, y, r);
        float time = (float) (System.nanoTime() - startTime);
        return new Result(x, y, r, time, hitValue);
    }

    public boolean checkGetInto(float x, float y, float r) {
        return checkIntoTriangle(x, y, r) || checkIntoRectangle(x, y, r) || checkIntoCircle(x, y, r);
    }

    public boolean checkIntoTriangle(float x, float y, float r) {
        return x >= 0 && y >= 0 && (y + x - r) <= 0;
    }

    public boolean checkIntoRectangle(float x, float y, float r) {
        return (x <= 0 && x >= -r) && (y <= r / 2 && y >= 0);
    }

    public boolean checkIntoCircle(float x, float y, float r) {
        if (((x >= -r / 2 && x <= 0) && (y <= 0 && y >= -r / 2))) {
            return (x * x + y * y) <= r / 2 * r / 2;
        }
        return false;
    }
}

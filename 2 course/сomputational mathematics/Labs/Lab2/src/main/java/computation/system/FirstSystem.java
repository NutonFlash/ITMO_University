package computation.system;

import computation.Vector;

import java.util.ArrayList;
import java.util.function.Function;

public class FirstSystem implements SystemInterface {
    private final ArrayList<Function<Vector, Double>> system = new ArrayList<>();

    public FirstSystem() {
        system.add(vector -> 0.1 * Math.pow(vector.get(0), 2) + vector.get(0) + 0.2 * Math.pow(vector.get(1), 2) - 0.3);
        system.add(vector -> 0.2 * Math.pow(vector.get(0), 2) + vector.get(1) - 0.1 * Math.pow(vector.get(1), 2) - 0.7);
    }

    @Override
    public ArrayList<Function<Vector, Double>> getSystem() {
        return system;
    }

    @Override
    public int size() {
        return system.size();
    }

    @Override
    public String toString() {
        return "  1) 0.1x^2 + x + 0.2y^2 - 0.3 = 0" + "\n" +
                "  2) 0.2x^2 + y - 0.1y^2 - 0.7 = 0";
    }
}

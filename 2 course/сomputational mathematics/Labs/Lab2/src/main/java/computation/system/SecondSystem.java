package computation.system;

import computation.Vector;

import java.util.ArrayList;
import java.util.function.Function;

public class SecondSystem implements SystemInterface {
    private final ArrayList<Function<Vector, Double>> system = new ArrayList<>();

    public SecondSystem() {
        system.add(vector -> 2 * Math.pow(vector.get(0), 2) - vector.get(0) * vector.get(1) - 5 * vector.get(0) + 1);
        system.add(vector -> vector.get(0) + 3 * Math.log(vector.get(0)) - Math.pow(vector.get(1), 2));
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
        return "  1) 2x^2 - xy - 5x + 1 = 0" + "\n" +
                "  2) x + 3ln(x) - y^2 = 0";
    }
}

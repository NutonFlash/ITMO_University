package computation.system;

import computation.Vector;

import java.util.ArrayList;
import java.util.function.Function;

public class ThirdSystem implements SystemInterface {
    private final ArrayList<Function<Vector, Double>> system = new ArrayList<>();

    public ThirdSystem() {
        system.add(vector -> Math.pow(vector.get(0), 2) + Math.pow(vector.get(1), 2) - 1);
        system.add(vector -> Math.pow(vector.get(0), 3) - vector.get(1));
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
        return "  1) x^2 + y^2 - 1 = 0" + "\n" +
                "  2) x^3 - y = 0";
    }
}

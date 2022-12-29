package computation.system;

import computation.Vector;

import java.util.ArrayList;
import java.util.function.Function;

public interface SystemInterface {
    ArrayList<Function<Vector, Double>> getSystem();
    int size();
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class Equations {
    private final ArrayList<Equation> equations = new ArrayList<>();

    public Equations() {
        //первое уравнение
        Function<Double, Double> func1 = x -> Math.pow(x, 3) - 3*Math.pow(x, 2) + 4;
        String funcInString1 = "x^3 - 3x^2 + 4";
        HashMap<Double, Boolean> punchedPoints1 = new HashMap<>();
        Equation equation1 = new Equation(punchedPoints1, funcInString1, func1);

        //второе уравнение
        Function<Double, Double> func2 = x -> Math.pow(Math.sin(x), 2) + Math.pow(Math.cos(x), 2) + Math.pow(x, 2) + 3*x;
        String funcInString2 = "(sinx)^2 + (cosx)^2 + x^2 + 3x";
        HashMap<Double, Boolean> punchedPoints2 = new HashMap<>();
        Equation equation2 = new Equation(punchedPoints2, funcInString2, func2);

        //третье уравнение
        Function<Double, Double> func3 = x -> Math.sin(x)/x + 2;
        String funcInString3 = "sinx/x + 2";
        HashMap<Double, Boolean> punchedPoints3 = new HashMap<>();
        punchedPoints3.put(0d, true);
        Equation equation3 = new Equation(punchedPoints3, funcInString3, func3);

        equations.add(equation1);
        equations.add(equation2);
        equations.add(equation3);
    }

    public int getSize() {
        return equations.size();
    }

    public ArrayList<Equation> getEquations() {
        return equations;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i=0; i<getSize(); i++)
            str += (i+1) + " - " + equations.get(i) + "\n";
        return str;
    }
}

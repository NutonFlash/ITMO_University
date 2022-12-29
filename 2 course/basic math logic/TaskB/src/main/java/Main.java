import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main {
    static int falseCount = 0;
    static int trueCount = 0;

    public static void main(String[] args) {
        ExpressionsParser expressionsParser = new ExpressionsParser();
        Expression expression = expressionsParser.parse();
//        long time = System.currentTimeMillis();
        int variablesValue = 0;
        ArrayList<Variable> variables = ExpressionsParser.variables;
        if (expression != null) {
            expression.normalize();
            for (int z = 0; z < Math.pow(2, variables.size()-1); ++z) {
                if (z == Math.pow(2, variables.size()-1) - 1) {
                    int a = 123;
                }
                int varValue1 = variablesValue;
                variablesValue++;
                int varValue2 = variablesValue;
                variablesValue++;
                boolean[] results = expression.calculate(varValue1, varValue2);
                for (int j = 0; j < results.length; ++j) {
                    if (results[j])
                        trueCount++;
                    else falseCount++;
                }
            }
        }
        if (trueCount == 0)
            System.out.println("Unsatisfiable");
        else if (falseCount == 0)
            System.out.println("Valid");
        else
            System.out.println("Satisfiable and invalid, " + trueCount + " true and " + falseCount + " false cases");
//        System.out.println(System.currentTimeMillis() - time);
    }
}
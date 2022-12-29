import java.util.Objects;

public class Negation implements Expression {

    private Expression negation;

    public Negation(Expression negation) {
        this.negation = negation;
    }

    public Expression getNegation() {
        return negation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(negation);
    }

    @Override
    public String toTree() {
        return "(!" + negation.toTree() + ")";
    }

    @Override
    public String toString() {
        return "!" + negation.toString();
    }

    @Override
    public boolean[] calculate(int varValue1, int varValue2) {
        boolean[] results = negation.calculate(varValue1, varValue2);
        results[0] = !results[0];
        results[1] = !results[1];
        return results;
    }

    @Override
    public void normalize() {
        if (negation instanceof Variable) {
            if (ExpressionsParser.variables.stream().anyMatch(var -> var.getName().equals(((Variable) negation).getName())))
                negation = ExpressionsParser.variables.stream().filter(var -> var.getName().equals(((Variable) negation).getName())).findFirst().get();
        } else negation.normalize();
    }
}
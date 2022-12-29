import java.util.Objects;

public class Negation implements Expression {

    private Expression negated;

    public Negation(Expression negated) {
        this.negated = negated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Negation negation = (Negation) o;
        return Objects.equals(negated, negation.negated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(negated);
    }

    @Override
    public String toTree() {
        return "(!" + negated.toTree() + ")";
    }

    @Override
    public String toString() {
        return "!" + negated.toString();
    }

    @Override
    public boolean[] calculate(int varValue1, int varValue2) {
        boolean[] results = negated.calculate(varValue1, varValue2);
        results[0] = !results[0];
        results[1] = !results[1];
        return results;
    }

    @Override
    public void normalize() {
        if (negated instanceof Variable) {
            if (ExpressionsParser.variables.stream().anyMatch(var -> var.getName().equals(((Variable) negated).getName())))
                negated = ExpressionsParser.variables.stream().filter(var -> var.getName().equals(((Variable) negated).getName())).findFirst().get();
        } else negated.normalize();
    }
}
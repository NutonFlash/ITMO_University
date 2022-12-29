import java.util.Objects;

public class Disjunction implements Expression {

    private Expression left; //Disjunction
    private Expression right; //Conjunction

    public Disjunction(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disjunction that = (Disjunction) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "|" + right.toString() + ")";
    }

    @Override
    public String toTree() {
        return "(|," + left.toTree() + "," + right.toTree() + ")";
    }

    @Override
    public boolean[] calculate(int varValue1, int varValue2) {
        boolean[] results = new boolean[2];
        boolean[] leftResult = left.calculate(varValue1, varValue2);
        boolean[] rightResult = right.calculate(varValue1, varValue2);
        results[0] = leftResult[0] || rightResult[0];
        results[1] = leftResult[1] || rightResult[1];
        return results;
    }

    @Override
    public void normalize() {
        if (left instanceof Variable) {
            if (ExpressionsParser.variables.stream().anyMatch(var -> var.getName().equals(((Variable) left).getName())))
                left = ExpressionsParser.variables.stream().filter(var -> var.getName().equals(((Variable) left).getName())).findFirst().get();
        } else left.normalize();
        if (right instanceof Variable) {
            if (ExpressionsParser.variables.stream().anyMatch(var -> var.getName().equals(((Variable) right).getName())))
                right = ExpressionsParser.variables.stream().filter(var -> var.getName().equals(((Variable) right).getName())).findFirst().get();
        } else right.normalize();
    }
}
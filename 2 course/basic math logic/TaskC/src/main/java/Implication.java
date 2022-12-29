import java.util.Objects;

public class Implication implements Expression {

    private Expression left; //Disjunction
    private Expression right; //Expression

    public Implication(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "->" + right.toString() + ")";
    }

    @Override
    public String toTree() {
        return "(->," + left.toTree() + "," + right.toTree() + ")";
    }

    @Override
    public boolean[] calculate(int varValue1, int varValue2) {
        boolean[] results = new boolean[2];
        boolean[] leftResult = left.calculate(varValue1, varValue2);
        boolean[] rightResult = right.calculate(varValue1, varValue2);
        if (!(leftResult[0] && !rightResult[0]))
            results[0] = true;
        if (!(leftResult[1] && !rightResult[1]))
            results[1] = true;
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
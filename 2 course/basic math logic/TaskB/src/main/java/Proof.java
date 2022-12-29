public class Proof implements Expression {

    private Expression expression;
    private int line;
    private boolean isLastInProof;

    public Proof(Expression expression, int line) {
        this.expression  = expression;
        this.line = line;
        this.isLastInProof = false;
    }

    public Proof(Expression expression, int line, boolean isLastInProof) {
        this.expression  = expression;
        this.line = line;
        this.isLastInProof = isLastInProof;
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean isLastInProof() {
        return isLastInProof;
    }

    public int getLine() {
        return line;
    }


    @Override
    public String toTree() {
        return expression.toTree();
    }

    @Override
    public boolean[] calculate(int varValue1, int varValue2) {
        return new boolean[0];
    }

    @Override
    public void normalize() {

    }
}

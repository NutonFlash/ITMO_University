public interface Expression {
    String toTree();

    boolean[] calculate(int varValue1, int varValue2);

    void normalize();

    default boolean equals(Expression expression) {
        if (expression.hashCode() == this.hashCode())
            return expression.toString().equals(this.toString());
        return false;
    }
}
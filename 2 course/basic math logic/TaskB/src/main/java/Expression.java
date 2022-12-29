public interface Expression {
    String toTree();

    boolean[] calculate(int varValue1, int varValue2);

    void normalize();
}
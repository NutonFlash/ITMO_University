import java.util.Objects;

public class Variable implements Expression {

    private String name;
    private boolean varValue;
    private int index;
    public Variable(String name) {
        this.name = name;
    }

    public boolean getVarValue() {
        return this.varValue;
    }

    public void setVarValue(boolean value) {
        this.varValue = value;
    }

    public void setIndex(int index) {this.index = index;}

    public int getIndex() {return this.index;}

    @Override
    public String toTree() {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean[] calculate(int varValue1, int varValue2) {
        boolean[] values = new boolean[2];
        if ((varValue1 >> index) % 2 == 1)
                values[0] = true;
        if ((varValue2 >> index) % 2 == 1)
                values[1] = true;
        return values;
    }

    @Override
    public void normalize() {
    }
}
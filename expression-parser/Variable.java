package expression;

import java.util.List;
import java.util.Objects;

public class Variable implements AllInterfaceExpression {
    private final String variable;
    private int variableInt = 0;


    public Variable(String variable) {
        this.variable = variable;
    }

    public Variable(int variableIndex) {
        this.variable = "$" + variableIndex;
        this.variableInt = variableIndex;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if ("x".equals(variable)) {
            return x;
        } else if ("y".equals(variable)) {
            return y;
        } else if ("z".equals(variable)) {
            return z;
        } else {
            throw new RuntimeException("Unexpected value!");
        }
    }

    @Override
    public int evaluate(List<Integer> list) {
        return list.get(variableInt);
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable1 = (Variable) o;
        return Objects.equals(variable, variable1.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(variable);
    }
}

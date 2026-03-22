package expression.exceptions;

import expression.AllInterfaceExpression;

import java.util.List;
import java.util.Objects;

public class CheckedVariable implements AllInterfaceExpression {
    private final String variable;

    public CheckedVariable(String variable) {
        this.variable = variable;
    }

    public CheckedVariable(int variableIndex) {
        this.variable = "" + variableIndex;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (variable) {
            case ("x") -> x;
            case ("y") -> y;
            case ("z") -> z;
            default -> 0;
        };
    }

    @Override
    public int evaluate(List<Integer> list) {
        return list.get(Integer.parseInt(variable));
    }

    @Override
    public String toString() {
        try {
            Integer.parseInt(variable);
            return "$" + variable;
        } catch (NumberFormatException e) {
            return variable;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CheckedVariable variable1 = (CheckedVariable) o;
        return Objects.equals(variable, variable1.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(variable);
    }
}

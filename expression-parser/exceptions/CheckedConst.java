package expression.exceptions;

import expression.AllInterfaceExpression;

import java.util.List;
import java.util.Objects;

public class CheckedConst implements AllInterfaceExpression {
    private final Integer constant;

    public CheckedConst(int constant) {
        this.constant = constant;
    }

    @Override
    public int evaluate(int x) {
        return constant;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return constant;
    }

    @Override
    public int evaluate(List<Integer> list) {
        return constant;
    }

    @Override
    public String toString() {
        return constant.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CheckedConst aConst = (CheckedConst) o;
        return Objects.equals(constant, aConst.constant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(constant);
    }
}
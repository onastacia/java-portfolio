package expression;

import java.util.List;
import java.util.Objects;

public class Const implements AllInterfaceExpression {
    private final int constant;

    public Const(int constant) {
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
        return Integer.toString(constant);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return constant == aConst.constant;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(constant);
    }
}
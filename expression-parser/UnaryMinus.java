package expression;

import java.util.List;
import java.util.Objects;

public class UnaryMinus implements AllInterfaceExpression {
    private final AllInterfaceExpression unaryMinusValue;

    public UnaryMinus(AllInterfaceExpression unaryMinusValue) {
        this.unaryMinusValue = unaryMinusValue;
    }

    @Override
    public int evaluate(int x) {
        return (-1) * unaryMinusValue.evaluate(x);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return (-1) * unaryMinusValue.evaluate(variables);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (-1) * unaryMinusValue.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-(" + unaryMinusValue + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UnaryMinus aConst = (UnaryMinus) o;
        return Objects.equals(unaryMinusValue, aConst.unaryMinusValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(unaryMinusValue);
    }


}

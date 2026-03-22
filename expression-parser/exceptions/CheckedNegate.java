package expression.exceptions;

import expression.AllInterfaceExpression;

import java.util.List;
import java.util.Objects;

public class CheckedNegate implements AllInterfaceExpression {
    private final AllInterfaceExpression unaryMinusValue;

    public CheckedNegate(AllInterfaceExpression unaryMinusValue) {
        this.unaryMinusValue = unaryMinusValue;
    }

    private void checkedError(int unaryMinusValue) {
        if (unaryMinusValue == Integer.MIN_VALUE) {
            throw new OverFlow("Overflow");
        }
    }
    
    @Override
    public int evaluate(int x) {
        checkedError(unaryMinusValue.evaluate(x));
        return (-1) * unaryMinusValue.evaluate(x);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        checkedError(unaryMinusValue.evaluate(variables));
        return (-1) * unaryMinusValue.evaluate(variables);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        checkedError(unaryMinusValue.evaluate(x, y, z));
        return (-1) * unaryMinusValue.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-(" + unaryMinusValue + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CheckedNegate aConst = (CheckedNegate) o;
        return Objects.equals(unaryMinusValue, aConst.unaryMinusValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(unaryMinusValue);
    }


}

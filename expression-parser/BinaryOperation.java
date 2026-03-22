package expression;


import java.util.List;
import java.util.Objects;

public abstract class BinaryOperation implements AllInterfaceExpression {
    protected final AllInterfaceExpression leftPart;
    protected final AllInterfaceExpression rightPart;

    public abstract String getSign();

    protected BinaryOperation(AllInterfaceExpression leftPart, AllInterfaceExpression rightPart) {
        this.leftPart = leftPart;
        this.rightPart = rightPart;
    }

    protected abstract int operate(int leftPart, int rightPart);

    @Override
    public String toString() {
        return "(" + leftPart.toString() + " " + getSign() + " " + rightPart.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return operate(leftPart.evaluate(x), rightPart.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operate(leftPart.evaluate(x, y, z), rightPart.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> list) {
        return operate(leftPart.evaluate(list), rightPart.evaluate(list));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperation that = (BinaryOperation) o;
        return Objects.equals(leftPart, that.leftPart) && Objects.equals(rightPart, that.rightPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftPart, rightPart, getClass());
    }
}

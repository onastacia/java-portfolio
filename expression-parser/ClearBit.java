package expression;

public class ClearBit extends BinaryOperation {
    public ClearBit(AllInterfaceExpression leftPart, AllInterfaceExpression rightPart) {
        super(leftPart, rightPart);
    }

    @Override
    public String getSign() {
        return "clear";
    }

    @Override
    protected int operate(int leftPart, int rightPart) {
        return leftPart & ~(1 << rightPart);
    }
}

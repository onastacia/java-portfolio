package expression;

public class SetBit extends BinaryOperation {
    public SetBit(AllInterfaceExpression leftPart, AllInterfaceExpression rightPart) {
        super(leftPart, rightPart);
    }

    @Override
    public String getSign() {
        return "set";
    }

    @Override
    protected int operate(int leftPart, int rightPart) {
        return leftPart | (1 << rightPart);
    }

}

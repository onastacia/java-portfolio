package expression;

public class Subtract extends BinaryOperation {

    public Subtract(AllInterfaceExpression leftPart, AllInterfaceExpression rightPart) {
        super(leftPart, rightPart);
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    protected int operate(int resLeftPart, int resRightPart) {
        return resLeftPart - resRightPart;
    }

}

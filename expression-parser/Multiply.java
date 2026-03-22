package expression;

public class Multiply extends BinaryOperation {

    public Multiply(AllInterfaceExpression leftPart, AllInterfaceExpression rightPart) {
        super(leftPart, rightPart);
    }

    @Override
    public String getSign() {
        return "*";
    }

    @Override
    protected int operate(int resLeftPart, int resRightPart) {
        return resLeftPart * resRightPart;
    }

}

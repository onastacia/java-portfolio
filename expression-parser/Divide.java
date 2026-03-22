package expression;

public class Divide extends BinaryOperation {

    public Divide(AllInterfaceExpression leftPart, AllInterfaceExpression rightPart) {
        super(leftPart, rightPart);
    }

    @Override
    public String getSign() {
        return "/";
    }

    @Override
    protected int operate(int resLeftPart, int resRightPart) {
        return resLeftPart / resRightPart;
    }

}

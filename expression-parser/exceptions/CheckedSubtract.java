package expression.exceptions;

import expression.AllInterfaceExpression;
import expression.BinaryOperation;

public class CheckedSubtract extends BinaryOperation {

    public CheckedSubtract(AllInterfaceExpression leftPart, AllInterfaceExpression rightPart) {
        super(leftPart, rightPart);
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    protected int operate(int resLeftPart, int resRightPart) {
        if (resRightPart < 0 &&
                resLeftPart > Integer.MAX_VALUE + resRightPart) {
            throw new OverFlow("Overflow");
        } else if (resRightPart > 0 &&
                resLeftPart < Integer.MIN_VALUE + resRightPart) {
            throw new OverFlow("Overflow");
        }
        return resLeftPart - resRightPart;
    }

}

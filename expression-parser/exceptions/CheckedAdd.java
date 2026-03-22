package expression.exceptions;

import expression.AllInterfaceExpression;
import expression.BinaryOperation;

public class CheckedAdd extends BinaryOperation {

    public CheckedAdd(AllInterfaceExpression leftPart, AllInterfaceExpression rightPart) {
        super(leftPart, rightPart);
    }

    @Override
    public String getSign() {
        return "+";
    }

    @Override
    protected int operate(int resLeftPart, int resRightPart) {
        if (resLeftPart > 0 && resRightPart > 0) {
            if (resLeftPart > Integer.MAX_VALUE - resRightPart) {
                throw new OverFlow("Overflow");
            }
        } else if (resLeftPart < 0 && resRightPart < 0) {
            if (resLeftPart < Integer.MIN_VALUE - resRightPart) {
                throw new OverFlow("Underflow");
            }
        }
        return resLeftPart + resRightPart;
    }

}

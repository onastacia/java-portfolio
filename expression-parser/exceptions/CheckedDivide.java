package expression.exceptions;

import expression.AllInterfaceExpression;
import expression.BinaryOperation;

public class CheckedDivide extends BinaryOperation {

    public CheckedDivide(AllInterfaceExpression leftPart, AllInterfaceExpression rightPart) {
        super(leftPart, rightPart);
    }

    @Override
    public String getSign() {
        return "/";
    }

    @Override
    protected int operate(int resLeftPart, int resRightPart) {
        if( resRightPart == 0){
            throw new DivisionByZero("Division by zero");
        }
        if (resLeftPart == Integer.MIN_VALUE && resRightPart == -1 ){
            throw new OverFlow("Overflow");
        }
        return resLeftPart / resRightPart;
    }

}

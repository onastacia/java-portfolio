package expression;

import java.util.List;

public class Pow implements AllInterfaceExpression {
    private final AllInterfaceExpression pow;

    public Pow(AllInterfaceExpression pow) {
        this.pow = pow;
    }

    private void checkedError(int argument) {
        if (argument < 0) {
            throw new RuntimeException("Invalid value of log argument");
        }
    }
    private int exponentiation(int pow) {
        checkedError(pow);
        int number = 2;
        for (int i = 1; i < pow; i++) {
            if(number > Integer.MAX_VALUE / 2){
                throw new RuntimeException("Overflow");
            }
            number *= 2;
        }
        if(pow == 0){
            return 1;
        }
        return number;
    }

    @Override
    public int evaluate(int x) {
        int res = pow.evaluate(x);
        return exponentiation(res);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        int res = pow.evaluate(variables);
        return exponentiation(res);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = pow.evaluate(x, y, z);
        return exponentiation(res);
    }

    @Override
    public String toString() {
        return "pow₂(" + pow + ")";
    }
}

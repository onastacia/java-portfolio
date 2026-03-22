package expression;

import java.util.List;

public class Ceiling implements AllInterfaceExpression {

    private final AllInterfaceExpression number;

    public Ceiling(AllInterfaceExpression number) {
        this.number = number;
    }
    private int ceilDiv(int x, int y) {
        final int q = x / y;
        if ((x ^ y) >= 0 && (q * y != x)) {
            return q + 1;
        }
        return q;
    }
    @Override
    public int evaluate(int x) {
        int res = number.evaluate(x);
        return ceilDiv(res, 1000) * 1000;

    }

    @Override
    public int evaluate(List<Integer> variables) {
        int res = number.evaluate(variables);
        return ceilDiv(res, 1000) * 1000;

    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = number.evaluate(x, y, z);
        return ceilDiv(res, 1000) * 1000;
    }

    @Override
    public String toString() {
        return "ceiling(" + number + ")";
    }
}

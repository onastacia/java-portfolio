package expression;

import java.util.List;

public class Floor implements AllInterfaceExpression {

    private final AllInterfaceExpression number;

    public Floor(AllInterfaceExpression number) {
        this.number = number;
    }

    private int floorDiv(int x, int y) {
        final int q = x / y;
        if ((x ^ y) < 0 && (q * y != x)) {
            return q - 1;
        }
        return q;
    }

    @Override
    public int evaluate(int x) {
        int res = number.evaluate(x);
        return floorDiv(res, 1000) * 1000;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        int res = number.evaluate(variables);
        return floorDiv(res, 1000) * 1000;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = number.evaluate(x, y, z);
        return floorDiv(res, 1000) * 1000;
    }

    @Override
    public String toString() {
        return "floor(" + number + ")";
    }
}

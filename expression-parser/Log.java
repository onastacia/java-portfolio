package expression;

import java.util.List;

public class Log implements AllInterfaceExpression {
    private final AllInterfaceExpression argument;

    public Log(AllInterfaceExpression argument) {
        this.argument = argument;
    }

    private void checkedError(int argument) {
        if (argument <= 0) {
            throw new RuntimeException("Invalid value of log argument");
        }
    }

    private int nearestValue(int argument) {
        checkedError(argument);
        int count = 0, number = argument;
        while (number >= 2) {
            number /= 2;
            count++;
        }
        return count;
    }

    @Override
    public int evaluate(int x) {
        int res = argument.evaluate(x);
        return nearestValue(res);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        int res = argument.evaluate(variables);
        return nearestValue(res);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = argument.evaluate(x, y, z);
        return nearestValue(res);
    }

    @Override
    public String toString() {
        return "log₂(" + argument + ")";
    }
}

package expression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        int value = Integer.parseInt(args[0]);
        System.out.println(new Subtract(
                new Multiply(
                        new Const(2),
                        new Variable("x")
                ),
                new Const(3)
        ).evaluate(value));

        System.out.println(new Subtract(
                new Multiply(
                        new Const(2),
                        new Variable("x")
                ),
                new Const(3)
        ).toString());
        
    }

}

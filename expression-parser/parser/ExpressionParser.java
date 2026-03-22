package expression.parser;

import expression.*;

import java.util.List;

public class ExpressionParser implements ListParser {

    @Override
    public ListExpression parse(String expression, List<String> variables) {
        Parser parser = new Parser(new StringSource(expression), variables);
        return parser.parse();
    }

    private static class Parser extends BaseParser {

        private boolean flagIsFirstMinus = false;

        public Parser(CharSource sourse, List<String> variables) {
            super(sourse);
        }

        private ListExpression parse() {
            AllInterfaceExpression o = parseExpression();
            skipWhitespace();
            expectEOF();
            return o;
        }

        private AllInterfaceExpression parseExpression() {
            return parseSetCeiling();
        }

        private AllInterfaceExpression parseSetCeiling() {
            AllInterfaceExpression firstOperant = parseAddSub();
            while (true) {
                skipWhitespace();
                if (!between('a', 'z')) {
                    break;
                }
                String operator = readPotentialBinOperator();
                switch (operator) {
                    case ("clear") -> firstOperant = new ClearBit(firstOperant, parseAddSub());
                    case ("set") -> firstOperant = new SetBit(firstOperant, parseAddSub());
                }
            }
            return firstOperant;
        }

        private AllInterfaceExpression parseAddSub() {
            AllInterfaceExpression firstOperant = parseMultiplyDivide();
            while (true) {
                skipWhitespace();
                if (!test('+') && !test('-')) {
                    break;
                }
                String operator = String.valueOf(take());
                AllInterfaceExpression secondOperant = parseMultiplyDivide();
                firstOperant = caseExpression(operator, firstOperant, secondOperant);
            }
            return firstOperant;
        }

        private AllInterfaceExpression parseMultiplyDivide() {
            AllInterfaceExpression firstOperant = parseBottom();
            while (true) {
                skipWhitespace();
                if (!test('*') && !test('/')) {
                    break;
                }
                String operator = String.valueOf(take());
                AllInterfaceExpression secondOperant = parseBottom();
                firstOperant = caseExpression(operator, firstOperant, secondOperant);
            }
            return firstOperant;
        }

        private AllInterfaceExpression parseBottom() {
            skipWhitespace();
            if (test('-')) {
                return parseMeetMinus();
            } else if (test('(')) {
                return parseMeetBracket();
            } else if (test('$')) {
                return parseMeetDollar();
            } else if (between('0', '9')) {
                return new Const(numberBuilder());
            } else if (test('f')) {
                expect("floor");
                return new Floor(parseBottom());
            } else if (test('c')) {
                expect("ceiling");
                return new Ceiling(parseBottom());
            } else {
                throw error("Unexpected value");
            }
        }

        private Variable parseMeetDollar() {
            take();
            if (between('0', '9')) {
                int index = numberBuilder();
                return new Variable(index);
            } else {
                throw error("Invalid value of variable");
            }
        }

        private AllInterfaceExpression parseMeetBracket() {
            take();
            AllInterfaceExpression obj = parseSetCeiling();
            skipWhitespace();
            expect(')');
            return obj;
        }

        private AllInterfaceExpression parseMeetMinus() {
            take();
            skipWhitespace();
            if (between('0', '9')) {
                flagIsFirstMinus = true;
                return new Const(numberBuilder());
            }
            return new UnaryMinus(parseBottom());
        }

        private AllInterfaceExpression caseExpression(String operator, AllInterfaceExpression
                firstOperant, AllInterfaceExpression secondOperant) {
            return switch (operator) {
                case "+" -> new Add(firstOperant, secondOperant);
                case "-" -> new Subtract(firstOperant, secondOperant);
                case "*" -> new Multiply(firstOperant, secondOperant);
                case "/" -> new Divide(firstOperant, secondOperant);
                case "set" -> new SetBit(firstOperant, secondOperant);
                case "clear" -> new ClearBit(firstOperant, secondOperant);
                default -> throw error("Invalid");
            };
        }

        private String readPotentialBinOperator() {
            skipWhitespace();
            StringBuilder sb = new StringBuilder();
            while (between('a', 'z')) {
                sb.append(take());
            }
            return sb.toString();
        }

        private int numberBuilder() {
            StringBuilder sb = new StringBuilder();
            while (between('0', '9')) {
                sb.append(take());
            }
            if (sb.charAt(0) == '0' && sb.length() > 1) {
                throw error("The first digit in a number = '0'. Invalid value.");
            }
            try {
                if (flagIsFirstMinus) {
                    flagIsFirstMinus = false;
                    return Integer.parseInt("-" + sb);
                }
                return Integer.parseInt(sb.toString());
            } catch (NumberFormatException e) {
                throw error("Too big value for int");
            }
        }

    }
}

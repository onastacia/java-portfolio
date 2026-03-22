package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.ListParser;
import expression.parser.StringSource;

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
            checkedErrorFirstOperator();
            AllInterfaceExpression firstOperant = parseAddSub();
            while (true) {
                skipWhitespace();
                if (!between('a', 'z')) {
                    break;
                }
                String operator = readPotentialBinOperator();
                checkedErrorTwoSignInRow(operator);
                switch (operator) {
                    case ("clear") -> firstOperant = new ClearBit(firstOperant, parseAddSub());
                    case ("set") -> firstOperant = new SetBit(firstOperant, parseAddSub());
                }
            }
            return firstOperant;
        }

        private AllInterfaceExpression parseAddSub() {
            checkedErrorFirstOperator();
            AllInterfaceExpression firstOperant = parseMultiplyDivide();
            while (true) {
                skipWhitespace();
                if (!test('+') && !test('-')) {
                    break;
                }
                String operator = String.valueOf(take());
                checkedErrorTwoSignInRow(operator);
                AllInterfaceExpression secondOperant = parseMultiplyDivide();
                firstOperant = caseExpression(operator, firstOperant, secondOperant);
            }
            return firstOperant;
        }

        private AllInterfaceExpression parseMultiplyDivide() {
            checkedErrorFirstOperator();
            AllInterfaceExpression firstOperant = parseBottom();
            while (true) {
                skipWhitespace();
                if (!test('*') && !test('/')) {
                    break;
                }
                String operator = String.valueOf(take());
                checkedErrorTwoSignInRow(operator);
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
                return new CheckedConst(numberBuilder());
            } else if (test('f')) {
                expect("floor");
                return new Floor(parseBottom());
            } else if (test('c')) {
                expect("ceiling");
                return new Ceiling(parseBottom());
            } else if (test('p')) {
                expect("pow₂");
                return new Pow(parseBottom());
            } else if (test('l')) {
                expect("log₂");
                return new Log(parseBottom());
            } else if (test('+') || test('*') || test('/')) {
                throw new ParsingException("Incorrected argument of unary operator!");
            } else if (test('[') || test(']')) {
                throw new IncorrectedBracket("Incorrected type of bracket: '" + take() + "'!");
            } else if (between('a', 'z')) {
                throw new InvalidArgument("Invalid argument!");
            } else if (test(EOF)) {
                throw new ParsingException("An operand or argument is expected, but it is missing!");
            } else {
                throw new ParsingException("Unexpected value!");
            }
        }

        private CheckedVariable parseMeetDollar() {
            take();
            if (between('0', '9')) {
                int index = numberBuilder();
                return new CheckedVariable(index);
            } else {
                throw new ParsingException("Invalid value of variable");
            }
        }

        private AllInterfaceExpression parseMeetBracket() {
            take();
            skipWhitespace();
            if (test(')')) {
                throw new MissingValueInBracket("Missing value in bracket!");
            }
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
                return new CheckedConst(numberBuilder());
            }
            return new CheckedNegate(parseBottom());
        }

        private AllInterfaceExpression caseExpression(String operator, AllInterfaceExpression
                firstOperant, AllInterfaceExpression secondOperant) {
            return switch (operator) {
                case "+" -> new CheckedAdd(firstOperant, secondOperant);
                case "-" -> new CheckedSubtract(firstOperant, secondOperant);
                case "*" -> new CheckedMultiply(firstOperant, secondOperant);
                case "/" -> new CheckedDivide(firstOperant, secondOperant);
                case "set" -> new SetBit(firstOperant, secondOperant);
                case "clear" -> new ClearBit(firstOperant, secondOperant);
                default -> throw new ParsingException("Invalid");
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
            if (test(' ') && between('0', '9')) {
                throw new WhitespaceInNumber("Unexpected whitespace in number!");
            }
            if (sb.charAt(0) == '0' && sb.length() > 1) {
                throw new ParsingException("The first digit in a number = '0'. Invalid value!");
            }
            try {
                if (flagIsFirstMinus) {
                    flagIsFirstMinus = false;
                    return Integer.parseInt("-" + sb);
                }
                return Integer.parseInt(sb.toString());
            } catch (NumberFormatException e) {
                throw new OverFlow("Overflow. Can't parse to int '" + sb + "'!");
            }
        }

        private void checkedErrorTwoSignInRow(String operator) {
            skipWhitespace();
            if (test('+') || test('*') || test('/')) {
                throw new TwoSigns("Incorrect input. Two signs without an operand between: '" + operator + "' and '" + take() + "'!");
            }
        }

        private void checkedErrorFirstOperator() {
            skipWhitespace();
            if (test('+') || test('*') || test('/')) {
                throw new ParsingException("No first argument!");
            }
        }
    }
}

package FaruksAdvancedCalculator;

import java.util.Stack;

public class CalculatorService {

    public double calculate(String expression) {
        expression = expression.replaceAll("\\s", "");

        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (Character.isDigit(currentChar)) {
                StringBuilder operand = new StringBuilder();
                while (i < expression.length()
                        && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operand.append(expression.charAt(i));
                    i++;
                }
                String[] seperateOperand = operand.toString().split("");
                double multiplyResult = 1;
                double result = Double.parseDouble(operand.toString());
                if (seperateOperand.length > 1) {
                    for (String number : seperateOperand) {
                        multiplyResult = Double.parseDouble(number) * multiplyResult;
                    }
                    result = multiplyResult;
                }

                operandStack.push(result);
                i--;
            } else if (isOperator(currentChar)) {
                while (!operatorStack.isEmpty() && hasPrecedence(currentChar, operatorStack.peek())) {
                    applyOperator(operandStack, operatorStack);
                }
                operatorStack.push(currentChar);
            } else if (currentChar == '(') {
                operatorStack.push(currentChar);
            } else if (currentChar == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    applyOperator(operandStack, operatorStack);
                }
                if (operatorStack.isEmpty()) {
                    throw new IllegalArgumentException("Mismatched parentheses: missing '('");
                }
                operatorStack.pop(); // remove '('
            } else if (Character.isLetter(currentChar)) {
                StringBuilder function = new StringBuilder();
                String functionName = "";
                StringBuilder newExpression = new StringBuilder();

                while (i < expression.length()) {
                    char c = expression.charAt(i);
                    if (!Character.isLetter(c) && !Character.isDigit(c) && c != '(' && c != ')') {
                        break;
                    }
                    function.append(c);
                    if (isFunction(function.toString())) {
                        functionName = function.toString();
                        if (functionName.toLowerCase().equals("pi")) {
                            break;
                        }
                        // Skip the opening parenthesis
                        i++;
                        // Read until closing parenthesis with bounds check
                        while (i < expression.length() && expression.charAt(i) != ')') {
                            newExpression.append(expression.charAt(i));
                            i++;
                        }
                        if (i >= expression.length()) {
                            throw new IllegalArgumentException("Missing closing parenthesis");
                        }
                        operandStack.push(calculate(newExpression.toString()));
                        break;
                    }
                    i++;
                }

                applyFunction(operandStack, functionName);
            } else {
                throw new IllegalArgumentException("Not a valid character: " + currentChar);
            }
        }

        while (!operatorStack.isEmpty()) {
            applyOperator(operandStack, operatorStack);
        }

        if (operandStack.size() == 1 && operatorStack.isEmpty()) {
            return operandStack.pop();
        } else if (operandStack.size() == 2 && operatorStack.isEmpty()) {
            return (operandStack.pop() + operandStack.peek());
        } else {
            throw new IllegalArgumentException("Not a valid expression format.");
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '!';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return getPrecedence(op1) > getPrecedence(op2);
    }

    private int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
            case '!':
                return 3;
            default:
                return 0;
        }
    }

    private void applyOperator(Stack<Double> operandStack, Stack<Character> operator) {
        if (operandStack.size() < 1) {
            throw new IllegalArgumentException("Not a valid expression format.");
        }
        double operand2 = operandStack.pop();
        double operand1 = 0;
        if (operandStack.isEmpty()) {
            throw new IllegalArgumentException("Not a valid expression format.");
        }
        operand1 = operandStack.pop();
        if (operator.isEmpty()) {
            throw new IllegalArgumentException("Not a valid expression format.");
        }
        char operatorElement = operator.pop();

        double result;
        switch (operatorElement) {
            case '+':
                result = operand1 + operand2;
                operandStack.push(result);
                break;
            case '-':
                result = operand1 - operand2;
                operandStack.push(result);
                break;
            case '*':
                result = operand1 * operand2;
                operandStack.push(result);
                break;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                result = operand1 / operand2;
                operandStack.push(result);
                break;
            case '%':
                result = operand1 % operand2;
                operandStack.push(result);
                break;
            case '^':
                result = Math.pow(operand1, operand2);
                operandStack.push(result);
                break;
            case '!':
                result = factorial(operand2);
                operandStack.push(result);
                break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operatorElement);
        }
    }

    private boolean isFunction(String name) {
        String[] functionNames = { "sin", "cos", "tan", "cot", "asin", "acos", "atan", "acot", "ln", "log", "sgn", "pi",
                "exp" };
        for (String functionName : functionNames) {
            if (functionName.equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void applyFunction(Stack<Double> operandStack, String function) {
        try {
            function = function.toLowerCase();
            double operand = 0;
            double result;

            if (!operandStack.isEmpty() || !function.equals("pi")) {
                operand = operandStack.pop();
            }

            switch (function) {
                case "sin":
                    result = Math.sin(operand);
                    operandStack.push(result);
                    break;
                case "cos":
                    result = Math.cos(operand);
                    operandStack.push(result);
                    break;
                case "tan":
                    result = Math.tan(operand);
                    operandStack.push(result);
                    break;
                case "cot":
                    result = 1 / Math.tan(operand);
                    operandStack.push(result);
                    break;
                case "asin":
                    result = Math.asin(operand);
                    operandStack.push(result);
                    break;
                case "acos":
                    result = Math.acos(operand);
                    operandStack.push(result);
                    break;
                case "atan":
                    result = Math.atan(operand);
                    operandStack.push(result);
                    break;
                case "acot":
                    result = Math.atan(1 / operand);
                    operandStack.push(result);
                    break;
                case "ln":
                    result = Math.log(operand);
                    operandStack.push(result);
                    break;
                case "log":
                    result = Math.log10(operand);
                    operandStack.push(result);
                    break;
                case "sgn":
                    result = Math.signum(operand);
                    operandStack.push(result);
                    break;
                case "pi":
                    operandStack.push(Math.PI);
                    break;
                case "exp":
                    operandStack.push(Math.exp(1));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown function: " + function);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Not a valid expression format.");
        }
    }

    private double factorial(double n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is undefined for negative numbers.");
        }
        if (n != Math.floor(n)) {
            throw new IllegalArgumentException("Factorial requires an integer value.");
        }
        int num = (int) n;
        if (num == 0 || num == 1) {
            return 1;
        }
        double result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
        }
        return result;
    }
}

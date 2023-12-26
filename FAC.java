package FaruksAdvancedCalculator;

import java.util.Scanner;
import java.util.Stack;

class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Faruk's Advanced Calculator ");
        System.out.println("Math Expression: ");
        String expression = scanner.nextLine();

        try {
            double result = calculate(expression);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        scanner.close();
    }

    private static double calculate(String expression) {
        expression = expression.replaceAll("\\s", "");

        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            System.out.println(currentChar);

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
                while (operatorStack.peek() != '(') {
                    applyOperator(operandStack, operatorStack);
                }
                operatorStack.pop();
            } else if (Character.isLetter(currentChar)) {
                StringBuilder function = new StringBuilder();
                String functionName = "";
                StringBuilder newExpression = new StringBuilder();

                while (i < expression.length()
                        && (Character.isLetter(expression.charAt(i)) || Character.isDigit(expression.charAt(i))
                                || expression.charAt(i) == '(')
                        || expression.charAt(i) == ')') {
                    function.append(expression.charAt(i));
                    if (isFunction(function.toString())) {
                        functionName = function.toString();
                        if (functionName.toLowerCase().equals("pi")) {
                            break;
                        }
                        i = i + 2;
                        while (expression.charAt(i) != ')') {
                            newExpression.append(expression.charAt(i));
                            i++;
                        }
                        operandStack.push(calculate(newExpression.toString()));
                        break;
                    }
                    ;
                    i++;
                }

                System.out.println(function);

                applyFunction(operandStack, functionName);
            } else {
                throw new IllegalArgumentException("Not a valid character." + currentChar);
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

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '!';
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return getPrecedence(op1) > getPrecedence(op2);
    }

    private static int getPrecedence(char operator) {
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

    private static void applyOperator(Stack<Double> operandStack, Stack<Character> operator) {
        try {
            double operand2 = operandStack.pop();
            double operand1 = 0;
            char operatorElement = operator.pop();
            double result;
            char nextOperator;

            if (!operandStack.isEmpty()) {
                operand1 = operandStack.pop();
            }

            if (!operator.isEmpty()) {
                nextOperator = operator.pop();

                if (nextOperator == '-') {
                    operand1 *= -1;
                }

            }

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
                        throw new ArithmeticException("Error: Cannot divide by zero");
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
            }
        } catch (Exception e) {

            throw new IllegalArgumentException("Not a valid expression format.");
        }
    }

    private static boolean isFunction(String name) {

        String[] functionNames = { "sin", "cos", "tan", "cot", "asin", "acos", "atan", "acot", "ln", "log", "sgn", "pi",
                "exp", "and" };
        for (String functionName : functionNames) {
            if (functionName.equals(name.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    private static void applyFunction(Stack<Double> operandStack, String function) {
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

    private static double factorial(double n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is undefined for negative numbers.");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}

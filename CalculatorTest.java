import FaruksAdvancedCalculator.CalculatorService;

public class CalculatorTest {
    public static void main(String[] args) {
        CalculatorService calc = new CalculatorService();
        int passed = 0;
        int failed = 0;

        // Test cases
        test(calc, "2 + 3", 5.0);
        test(calc, "2 + 3 * 4", 14.0);
        test(calc, "(2 + 3) * 4", 20.0);
        test(calc, "5!", 120.0);
        test(calc, "2^3", 8.0);
        test(calc, "10 / 2", 5.0);
        test(calc, "10 % 3", 1.0);
        test(calc, "sin(0)", 0.0);
        test(calc, "cos(0)", 1.0);
        test(calc, "pi", Math.PI);
        test(calc, "log(100)", 2.0);
        test(calc, "ln(exp)", 1.0);

        System.out.println("\n=== Results ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }

    static void test(CalculatorService calc, String expr, double expected) {
        try {
            double result = calc.calculate(expr);
            if (Math.abs(result - expected) < 0.0001) {
                System.out.println("PASS: " + expr + " = " + result);
            } else {
                System.out.println("FAIL: " + expr + " = " + result + " (expected " + expected + ")");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + expr + " -> " + e.getMessage());
        }
    }
}

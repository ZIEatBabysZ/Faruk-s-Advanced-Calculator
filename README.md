# Faruk's Advanced Calculator

A Java-based command-line calculator that supports advanced mathematical operations including basic arithmetic, trigonometric functions, logarithmic functions, and more.

## Features

### Basic Operations
- Addition (+)
- Subtraction (-)
- Multiplication (*)
- Division (/)
- Modulus (%)
- Exponentiation (^)
- Factorial (!)

### Advanced Functions
- **Trigonometric Functions**: sin, cos, tan, cot
- **Inverse Trigonometric Functions**: asin, acos, atan, acot
- **Logarithmic Functions**: ln (natural log), log (base 10)
- **Other Functions**: sgn (signum), exp (e), pi (π constant)

### Additional Features
- Parentheses support for complex expressions
- Decimal number support
- Expression evaluation using stack-based algorithm
- Error handling for invalid expressions and operations

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line interface

### Installation
1. Clone or download the repository
2. Navigate to the project directory
3. Compile the Java file:
   ```bash
   javac FAC.java
   ```
4. Run the calculator:
   ```bash
   java FaruksAdvancedCalculator.Calculator
   ```

## Usage

1. Run the program
2. Enter a mathematical expression when prompted
3. The calculator will evaluate and display the result

### Example Expressions
```
2 + 3 * 4          // Result: 14
sin(pi/2)          // Result: 1.0
5!                 // Result: 120
2^3                // Result: 8.0
log(100)           // Result: 2.0
(2 + 3) * 4        // Result: 20
```

## Supported Functions
| Function | Description | Example |
|----------|-------------|---------|
| sin(x) | Sine of x | sin(pi/2) |
| cos(x) | Cosine of x | cos(0) |
| tan(x) | Tangent of x | tan(pi/4) |
| cot(x) | Cotangent of x | cot(pi/4) |
| asin(x) | Arcsine of x | asin(1) |
| acos(x) | Arccosine of x | acos(0) |
| atan(x) | Arctangent of x | atan(1) |
| acot(x) | Arccotangent of x | acot(1) |
| ln(x) | Natural logarithm | ln(2.718) |
| log(x) | Base-10 logarithm | log(100) |
| sgn(x) | Sign function | sgn(-5) |
| pi | Pi constant (3.14159...) | pi |
| exp | Euler's number (2.718...) | exp |
| x! | Factorial of x | 5! |

## Error Handling
The calculator handles various error cases:
- Division by zero
- Invalid characters in expressions
- Malformed expressions
- Factorial of negative numbers
- Invalid function names

## Technical Details
- Uses stack-based evaluation algorithm
- Implements operator precedence
- Supports nested parentheses
- Written in Java with standard libraries only

## Contributing
Feel free to submit issues, fork the repository, and create pull requests for any improvements.

## Author
Faruk

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
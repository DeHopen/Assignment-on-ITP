import java.util.Scanner;

enum CalculatorType {
    INTEGER,
    DOUBLE,
    STRING,
    INCORRECT
}

enum OperationType {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    INCORRECT
}

abstract class Calculator {
    public abstract String add(String a, String b);

    public abstract String subtract(String a, String b);
    public abstract String multiply(String a, String b);
    public abstract String divide(String a, String  b);
}

class IntegerCalculator extends Calculator {
    @Override
    public String add(String a, String b) {
        return String.valueOf(Integer.parseInt(a) + Integer.parseInt(b));
    }

    @Override
    public String subtract(String a, String b) {
        return String.valueOf(Integer.parseInt(a) - Integer.parseInt(b));
    }

    @Override
    public String multiply(String a, String b) {
        return String.valueOf(Integer.parseInt(a) * Integer.parseInt(b));
    }

    @Override
    public String divide(String a, String b) {
        return String.valueOf(Integer.parseInt(a) / Integer.parseInt(b));
    }
}

class DoubleCalculator extends Calculator {
    @Override
    public String add(String a, String b) {
        return String.valueOf(Double.parseDouble(a) + Double.parseDouble(b));
    }

    @Override
    public String subtract(String a, String b) {
        return String.valueOf(Double.parseDouble(a) - Double.parseDouble(b));
    }

    @Override
    public String multiply(String a, String b) {
        return String.valueOf(Double.parseDouble(a) * Double.parseDouble(b));
    }

    @Override
    public String divide(String a, String b) {
        return String.valueOf(Double.parseDouble(a) / Double.parseDouble(b));
    }
}

class StringCalculator extends Calculator {
    @Override
    public String add(String a, String b) {
        return a + b;
    }

    @Override
    public String subtract(String a, String b) {
        return a.replace(b, "");
    }

    @Override
    public String multiply(String a, String b) {
        return String.valueOf(a).repeat(Math.max(0, Integer.parseInt(b)));
    }

    @Override
    public String divide(String a, String b) {
        return a.substring(0, a.length() / Integer.parseInt(b));
    }
}

public class Main {
    private Scanner scanner = new Scanner(System.in);
    public CalculatorType readCalculator() {
        System.out.println("Enter the type of calculator: ");
        String type = scanner.nextLine();
        return switch (type) {
            case "INTEGER" -> CalculatorType.INTEGER;
            case "DOUBLE" -> CalculatorType.DOUBLE;
            case "STRING" -> CalculatorType.STRING;
            default -> CalculatorType.INCORRECT;
        };
    }



    public int readCommandNumber(){
        int N = scanner.nextInt();
        if (N < 1 || N > 50) {
            System.out.println("Incorrect command number");
        }
        return N;
    }
    public void reportFatalError(){
        System.out.println("Wrong calculator type");
    }
    public OperationType parseOperation(String operation){
        String[] operationParts = operation.split(" ");
        String operationT = operationParts[0];
        return switch (operationT) {
            case "+" -> OperationType.ADDITION;
            case "-" -> OperationType.SUBTRACTION;
            case "*" -> OperationType.MULTIPLICATION;
            case "/" -> OperationType.DIVISION;
            default -> OperationType.INCORRECT;
        };
    }
    public static void main(String[] args) {
        Main main = new Main();
        CalculatorType calculatorType = main.readCalculator();
        if (calculatorType == CalculatorType.INCORRECT) {
            main.reportFatalError();
            return;
        }
        int comm = main.readCommandNumber();
        String operation = main.scanner.nextLine();
        OperationType operationType = main.parseOperation(operation);
        if (operationType == OperationType.INCORRECT) {
            System.out.println("Wrong operation type");
            return;
        }
        Calculator calculator = switch (calculatorType) {
            case INTEGER -> new IntegerCalculator();
            case DOUBLE -> new DoubleCalculator();
            case STRING -> new StringCalculator();
            default -> null;
        };
        String[] operationParts = operation.split(" ");
        String a = operationParts[1];
        String b = operationParts[2];
        String result = switch (operationType) {
            case ADDITION -> calculator.add(a, b);
            case SUBTRACTION -> calculator.subtract(a, b);
            case MULTIPLICATION -> calculator.multiply(a, b);
            case DIVISION -> calculator.divide(a, b);
            default -> null;
        };
        System.out.println(result);
    }
}

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
    public void readCalculator(){
        System.out.println("Enter the type of calculator: ");
        String type = scanner.nextLine();
        if (type.equals("INTEGER")) {
            CalculatorType calculatorType = CalculatorType.INTEGER;
        }
        else if (type.equals("DOUBLE")) {
            CalculatorType calculatorType = CalculatorType.DOUBLE;
        }
        else if (type.equals("STRING")) {
            CalculatorType calculatorType = CalculatorType.STRING;
        }
        try {
            CalculatorType calculatorType = CalculatorType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect type of calculator");
        }
    }


    public void readCommandNumber(){
        int N;
        while (true) {
            System.out.println("Enter the number of commands: ");
            String n = scanner.nextLine();
            try {
                N = Integer.parseInt(n);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect number of commands");
            }
        }
    }
    public void reportFatalError(){
        System.out.println("Fatal error");
    }
    public void parseOperation(String operation){
        String[] operationParts = operation.split(" ");
        String operationType = operationParts[0];
        String a = operationParts[1];
        String b = operationParts[2];
        switch (operationType) {
            case "+" -> {
                OperationType operationType1 = OperationType.ADDITION;
                break;
            }
            case "-" -> {
                OperationType operationType1 = OperationType.SUBTRACTION;
                break;
            }
            case "*" -> {
                OperationType operationType1 = OperationType.MULTIPLICATION;
                break;
            }
            case "/" -> {
                OperationType operationType1 = OperationType.DIVISION;
                break;
            }
        }
        try {
            OperationType operationType1 = OperationType.valueOf(operationType.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect operation type");
        }

    }
    public static void main(String[] args) {
        Main main = new Main();
        main.readCalculator();
        main.readCommandNumber();
        main.parseOperation("+ 1 2");
    }
}

import java.util.Objects;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);

    public CalculatorType readCalculator() {
        String type = scanner.nextLine();
        switch (type) {
            case "INTEGER":
                return CalculatorType.INTEGER;
            case "DOUBLE":
                return CalculatorType.DOUBLE;
            case "STRING":
                return CalculatorType.STRING;
            default:
                return CalculatorType.INCORRECT;
        }
    }


    public int readCommandNumber() {
        String command = scanner.nextLine();
        try {
            return Integer.parseInt(command);
        } catch (NumberFormatException e) {
            System.out.println("Amount of commands is Not a Number");
            System.exit(0);
        }
        return -1;
    }

    public void reportFatalError() {
        System.out.println("Wrong calculator type");
    }

    public OperationType parseOperation(String operation) {
        for (OperationType operationType : OperationType.values()) {
            String[] operationParts = operation.split(" ");
            String operationT = operationParts[0];
            switch (operationT) {
                case "+":
                    return OperationType.ADDITION;
                case "-":
                    return OperationType.SUBTRACTION;
                case "*":
                    return OperationType.MULTIPLICATION;
                case "/":
                    return OperationType.DIVISION;
                default:
                    return OperationType.INCORRECT;
            }
        }
        return OperationType.INCORRECT;
    }

    public static void main(String[] args) {
        Main main = new Main();
        CalculatorType calculatorType = main.readCalculator();
        if (calculatorType == CalculatorType.INCORRECT) {
            main.reportFatalError();
            return;
        }
        int comm = main.readCommandNumber();
        Calculator calculator = null;
        switch (calculatorType) {
            case INTEGER:
                calculator = new IntegerCalculator();
                break;
            case DOUBLE:
                calculator = new DoubleCalculator();
                break;
            case STRING:
                calculator = new StringCalculator();
                break;
        }
        if (calculator == null) {
            main.reportFatalError();
            return;
        }

        for (int i = 1; i <= comm; i++) {
            String operation = main.scanner.next();
            OperationType operationType = main.parseOperation(operation);

            String a = main.scanner.next();
            String b = main.scanner.next();
            String result = null;
            switch (operationType) {
                case ADDITION:
                    result = calculator.add(a, b);
                    break;
                case SUBTRACTION:
                    result = calculator.subtract(a, b);
                    break;
                case MULTIPLICATION:
                    result = calculator.multiply(a, b);
                    break;
                case DIVISION:
                    result = calculator.divide(a, b);
                    break;
            }
            if (result == null) {
                System.out.println("Wrong operation type");
            } else {
                System.out.println(result);
            }
        }
    }
}

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

    public abstract String divide(String a, String b);
}

class IntegerCalculator extends Calculator {
    @Override
    public String add(String a, String b) {
        try {
            int a1 = Integer.parseInt(a);
            int b1 = Integer.parseInt(b);
            return String.valueOf(a1 + b1);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }

    @Override
    public String subtract(String a, String b) {
        try {
            int a1 = Integer.parseInt(a);
            int b1 = Integer.parseInt(b);
            return String.valueOf(a1 - b1);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }

    @Override
    public String multiply(String a, String b) {
        try {
            int a1 = Integer.parseInt(a);
            int b1 = Integer.parseInt(b);
            return String.valueOf(a1 * b1);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }

    @Override
    public String divide(String a, String b) {
        try {
            if (Integer.parseInt(b) == 0) {
                return "Division by zero";
            }
            int a1 = Integer.parseInt(a);
            int b1 = Integer.parseInt(b);
            return String.valueOf(a1 / b1);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
}

class DoubleCalculator extends Calculator {
    @Override
    public String add(String a, String b) {
        try {
            double a1 = Double.parseDouble(a);
            double b1 = Double.parseDouble(b);
            return String.valueOf(a1 + b1);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }

    @Override
    public String subtract(String a, String b) {
        try {
            double a1 = Double.parseDouble(a);
            double b1 = Double.parseDouble(b);
            return String.valueOf(a1 - b1);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }

    @Override
    public String multiply(String a, String b) {
        try {
            double a1 = Double.parseDouble(a);
            double b1 = Double.parseDouble(b);
            return String.valueOf(a1 * b1);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }

    @Override
    public String divide(String a, String b) {
        try {
            if (Double.parseDouble(b) == 0) {
                return "Division by zero";
            }
            double a1 = Double.parseDouble(a);
            double b1 = Double.parseDouble(b);
            return String.valueOf(a1 / b1);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
}

class StringCalculator extends Calculator {
    @Override
    public String add(String a, String b) {
        try {
            return a + b;
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }

    @Override
    public String subtract(String a, String b) {
        return "Unsupported operation for strings";
    }

    @Override
    public String multiply(String a, String b) {
        StringBuilder result = new StringBuilder();
        if (Character.isLetter(b.charAt(0))) {
            return "Wrong argument type";
        } else
            try {
                int n = Integer.parseInt(b);
                if (n < 0) {
                    return "Wrong argument type";
                } else {
                    for (int i = 0; i < n; i++) {
                        result.append(a);
                    }
                }
            } catch (NumberFormatException e) {
                return "Wrong argument type";
            }
        return result.toString();
    }

    @Override
    public String divide(String a, String b) {
        return "Unsupported operation for strings";
    }
}



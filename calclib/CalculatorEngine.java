package calclib;


public class CalculatorEngine {
    private double accumulator = 0.0;
    private double currentOperand = 0.0;
    private String pendingOperator = "";
    private boolean waitingForOperand = true;
    private String displayString = "0";
    
    private final MemoryStore memoryStore = new MemoryStore();

    public MemoryStore getMemoryStore() {
        return memoryStore;
    }

    public String getDisplay() {
        return displayString;
    }

    public void inputDigit(String digit) {
        if (waitingForOperand) {
            displayString = digit;
            waitingForOperand = false;
        } else {
            if (displayString.equals("0")) {
                displayString = digit;
            } else {
                displayString += digit;
            }
        }
    }

    public void inputDecimalPoint() {
        if (waitingForOperand) {
            displayString = "0.";
            waitingForOperand = false;
        } else if (!displayString.contains(".")) {
            displayString += ".";
        }
    }

    public void performOperation(String operator) throws CalcException {
        double inputValue = Double.parseDouble(displayString);

        if (!pendingOperator.isEmpty() && !waitingForOperand) {
            calculateResult(inputValue);
        } else {
            accumulator = inputValue;
        }

        pendingOperator = operator;
        waitingForOperand = true;
    }

    private void calculateResult(double inputValue) throws CalcException {
        switch (pendingOperator) {
            case "+":
                accumulator += inputValue;
                break;
            case "-":
                accumulator -= inputValue;
                break;
            case "*":
                accumulator *= inputValue;
                break;
            case "/":
                if (Math.abs(inputValue) < 1e-12) {
                    throw new CalcException("Ділення на нуль неможливе!");
                }
                accumulator /= inputValue;
                break;
            default:
                accumulator = inputValue;
                break;
        }
        displayString = formatNumber(accumulator);
    }

    public void calculateEquals() throws CalcException {
        if (!pendingOperator.isEmpty()) {
            double inputValue = Double.parseDouble(displayString);
            calculateResult(inputValue);
            pendingOperator = "";
            waitingForOperand = true;
        }
    }

    public void clearAll() {
        accumulator = 0.0;
        currentOperand = 0.0;
        pendingOperator = "";
        waitingForOperand = true;
        displayString = "0";
    }

    public void clearEntry() {
        displayString = "0";
        waitingForOperand = true;
    }

    private String formatNumber(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%s", value);
        }
    }
}
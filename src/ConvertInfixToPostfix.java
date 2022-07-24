import java.util.Stack;

public class ConvertInfixToPostfix {
    public String convertInfixToPostfix(String stringInfix) {
        String stringPostfix = "";
        Stack<Character> stack = new Stack<>();
        stack.push('(');// Для корректного завершения программы берем в скобки все пришедшее выражение
        stringInfix = stringInfix.concat(String.valueOf(')'));
        for (int i = 0; i < stringInfix.length(); i++) {
            if (Character.isAlphabetic(stringInfix.charAt(i)) || Character.isDigit(stringInfix.charAt(i))) {
                if (Character.isDigit(stringInfix.charAt(i))) {
                    int k = i;
                    while (Character.isDigit(stringInfix.charAt(k))) {
                        stringPostfix = stringPostfix.concat(String.valueOf(stringInfix.charAt(k)));
                        k++;
                    }
                    stringPostfix = stringPostfix.concat(";");
                    i = k - 1;
                } else {
                    if (Character.isLetter(stringInfix.charAt(i))) {
                        int k = i;
                        while (Character.isLetter(stringInfix.charAt(k))) {
                            stringPostfix = stringPostfix.concat(String.valueOf(stringInfix.charAt(k)));
                            k++;
                        }
                        stringPostfix = stringPostfix.concat(";");
                        i = k - 1;
                    } else {
                        stringPostfix = stringPostfix.concat(String.valueOf(stringInfix.charAt(i)));
                    }

                }
            } else if (stringInfix.charAt(i) == '(') {
                stack.push(stringInfix.charAt(i));
            } else if (isOperator(stringInfix.charAt(i))) {
                while (!stack.empty()) {
                    if (precedence(stack.peek()) >=
                            precedence(stringInfix.charAt(i))) {
                        stringPostfix = stringPostfix.concat(String.valueOf(stack.pop()));
                    } else {
                        stack.push(stringInfix.charAt(i));
                        break;
                    }
                }
            } else if (stringInfix.charAt(i) == ')') {
                while (!stack.empty()) {
                    if (stack.peek() != '(') {
                        stringPostfix = stringPostfix.concat(String.valueOf(stack.pop()));
                    } else {
                        stack.pop();
                        break;
                    }
                }
            }
        }
        return stringPostfix;
    }

    private boolean isOperator(char character) {
        boolean isOperator = false;
        switch (character) {
            case '^':
            case '/':
            case '*':
            case '+':
            case '-':
                isOperator = true;
        }
        return isOperator;
    }

    private boolean isOperatorTrigonometry(String ch) {
        boolean isOperatorTrigonometry = false;
        switch (ch.toUpperCase()) {
            case "SIN":
            case "COS":
            case "TG":
            case "CTG":
                isOperatorTrigonometry = true;
        }

        return isOperatorTrigonometry;
    }

    private boolean isPi(String ch) {
        boolean isPi = false;
        switch (ch.toUpperCase()) {
            case "PI":
                isPi = true;
        }
        return isPi;
    }


    private int precedence(char operator) {
        int priority = 0;
        switch (operator) {
            case '^':
                priority = 3;
                break;
            case '/':
            case '*':
                priority = 2;
                break;
            case '+':
            case '-':
                priority = 1;
        }
        return priority;
    }

    public double postfixEvaluation(String stringPostfix) {
        double result;
        boolean flagTrigan = false;
        Stack<Double> stack = new Stack<>();
        Stack<String> stackTrigan = new Stack<>();
        stringPostfix = stringPostfix.concat(String.valueOf(')'));
        for (int i = 0; i < stringPostfix.length(); i++) {
            if (Character.isDigit(stringPostfix.charAt(i))) {
                int k = i;
                String num = "";
                while (Character.isDigit(stringPostfix.charAt(k))) {
                    num = num.concat(String.valueOf(stringPostfix.charAt(k)));
                    k++;
                }
                i = k;
                if (flagTrigan) {
                    String first = stackTrigan.pop();
                    System.out.printf("%.3f", stack.push(evaluate(Double.parseDouble(num),first)));
                    System.out.println();
                    flagTrigan = false;
                }
                else {
                    stack.push(Double.valueOf(num));
                }

            } else if (isOperator(stringPostfix.charAt(i))) {
                double second = stack.pop();
                double first = stack.pop();
                System.out.printf("%.3f", stack.push(evaluate(first, second, stringPostfix.charAt(i))));
                System.out.println();
            }
            else if (!Character.toString(stringPostfix.charAt(i)).equals(";") && i != stringPostfix.length() -1){
                int k = i;
                String num = "";
                while (Character.isLetter(stringPostfix.charAt(k))) {
                    num = num.concat(String.valueOf(stringPostfix.charAt(k)));
                    k++;
                }
                i = k;
                if (isPi(num)) {
                    String first = stackTrigan.pop();
                    System.out.printf("%.3f", stack.push(evaluatePI(first)));
                    System.out.println();
                    flagTrigan = false;
                }
                else if(isOperatorTrigonometry(num)){
                    stackTrigan.push(num);
                    flagTrigan = true;
                }

            }

        }
        result = stack.peek();
        return result;
    }

    private double evaluate(double first, double second, char operator) {
        System.out.printf("%.3f %c %.3f = ", first, operator, second);
        double result = 0;
        switch (operator) {
            case '^':
                result = Math.pow(first, second);
                break;
            case '/':
                result = first / second;
                break;
            case '*':
                result = first * second;
                break;
            case '+':
                result = first + second;
                break;
            case '-':
                result = first - second;
        }
        return result;
    }

    private double evaluate(double first, String operator) {
        System.out.printf("%s(%.3f) = ", operator, first);
        double result = 0;
        switch (operator.toUpperCase()) {
            case "SIN":
                result = Math.sin(Math.toRadians(first));
                break;
            case "COS":
                result = Math.cos(Math.toRadians(first));
                break;
            case "TG":
                result = Math.tan(Math.toRadians(first));
                break;
            case "CTG":
                result = 1 / Math.tan(Math.toRadians(first));
                break;
        }
        return result;
    }

    private double evaluatePI(String operator) {
        System.out.printf("%s(%f) ", operator, Math.PI);
        double result = 0;
        switch (operator.toUpperCase()) {
            case "SIN":
                result = Math.sin(Math.PI);
                break;
            case "COS":
                result = Math.cos(Math.PI);
                break;
            case "TG":
                result = Math.tan(Math.PI);
                break;
            case "CTG":
                result = 1 / Math.tan(Math.PI);
                break;
        }
        return result;
    }
}
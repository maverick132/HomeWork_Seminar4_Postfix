import java.util.Stack;

public class ConvertInfixToPostfix {
    public String convertInfixToPostfix(String stringInfix){
        String stringPostfix = "";
        Stack<Character> stack = new Stack<>();
        stack.push('(');// Для корректного завершения программы берем в скобки все пришедшее выражение
        stringInfix = stringInfix.concat(String.valueOf(')'));
        for (int i = 0; i < stringInfix.length() ; i++) {
            if(Character.isAlphabetic(stringInfix.charAt(i)) || Character.isDigit(stringInfix.charAt(i))){
                if(Character.isDigit(stringInfix.charAt(i))){
                    int k = i;
                    while (Character.isDigit(stringInfix.charAt(k))){
                        stringPostfix = stringPostfix.concat(String.valueOf(stringInfix.charAt(k)));
                        k++;
                    }
                    stringPostfix = stringPostfix.concat(";");
                    i = k-1;
                }
                else {
                        stringPostfix = stringPostfix.concat(String.valueOf(stringInfix.charAt(i)));

                }
            }
            else if(stringInfix.charAt(i) == '('){
                stack.push(stringInfix.charAt(i));
            }
            else if(isOperator(stringInfix.charAt(i))){
                while(!stack.empty()){
                    if(precedence(stack.peek()) >=
                            precedence(stringInfix.charAt(i)) ){
                        stringPostfix = stringPostfix.concat(String.valueOf(stack.pop()));
                    }
                    else{
                        stack.push(stringInfix.charAt(i));
                        break;
                    }
                }
            }
            else if(stringInfix.charAt(i) == ')'){
                while (!stack.empty()){
                    if(stack.peek() != '('){
                        stringPostfix = stringPostfix.concat(String.valueOf(stack.pop()));
                    }
                    else{
                        stack.pop();
                        break;
                    }
                }
            }
        }
        return stringPostfix;
    }
    private boolean isOperator(char character){
        boolean isOperator = false;
        switch (character){
            case '^':
            case '/':
            case '*':
            case '+':
            case '-':
                isOperator = true;
        }
        return isOperator;
    }

    private int precedence(char operator){
        int priority = 0;
        switch (operator){
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
    public double postfixEvaluation(String stringPostfix){
        double  result;
        Stack<Double> stack = new Stack<>();
        stringPostfix = stringPostfix.concat(String.valueOf(')'));
        for (int i = 0; i < stringPostfix.length(); i++) {
            if(Character.isDigit(stringPostfix.charAt(i))){
                int k = i;
                String num = "";
                while (Character.isDigit(stringPostfix.charAt(k)))
                {
                    num = num.concat(String.valueOf(stringPostfix.charAt(k)));
                    k++;
                }
                i=k;
                stack.push(Double.valueOf(num));
            }else if(isOperator(stringPostfix.charAt(i))){
                double second = stack.pop();
                double first = stack.pop();

                System.out.printf("%.3f" , stack.push(evaluate(first, second,stringPostfix.charAt(i))));
                System.out.println();
            }
        }
        result = stack.peek();
        return result;
    }
    private double evaluate(double first, double second, char operator){
        System.out.printf("%.3f %c %.3f = ",first, operator, second);
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


}
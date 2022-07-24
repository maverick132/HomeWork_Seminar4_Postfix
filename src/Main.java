public class Main {
    public static void main(String[] args) {
        ConvertInfixToPostfix convertor = new ConvertInfixToPostfix();
        String infix = InputFromConsole.expressionInput();
        String postfix = convertor.convertInfixToPostfix(infix);
        System.out.println("postfix = " + postfix);
        System.out.print("Notation in postfix form = ");
        for (int i = 0; i < postfix.length(); i++) {
            if (!Character.toString(postfix.charAt(i)).equals(";") ) {
                System.out.print(postfix.charAt(i));
            }
        }
        System.out.println();
        double result = convertor.postfixEvaluation(postfix);
        if (!Double.toString(result).equals("Infinity")) {
            System.out.printf("Excellent! Result = %.3f" , result);
        }
        else {
            System.out.println("You expression cannot be evaluated");
        }

    }


}
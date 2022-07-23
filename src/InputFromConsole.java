import java.util.Scanner;

public class InputFromConsole {

    public static String expressionInput() {
        String expression = "";
        Scanner iScanner = new Scanner(System.in);
        System.out.print("Enter expression: ");
        expression = iScanner.nextLine();
        System.out.printf("You enter: %s",expression);
        System.out.println();
        return expression;
    }
}

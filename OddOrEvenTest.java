import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OddOrEvenTest {
    public static void main(String[] args) {
        System.out.printf("Please input an integer between %d and %d: ", OddOrEven.min, OddOrEven.max);

        int n = 0;

        Scanner input = new Scanner(System.in);
        while(true) {
            try{
                n = input.nextInt();
                OddOrEven.output(n);
                break;
            }
            catch(Exception e) {
                if(e instanceof InvalidParameterException && e.getMessage().charAt(0) == '"') {
                    System.out.printf("Your Input (%d) is not in range (%d, %d). Try again: ", n, OddOrEven.min, OddOrEven.max);
                }
                else if(e instanceof InputMismatchException) {
                    System.out.print("That wasn't an integer. Try again: ");
                    input.nextLine();
                }
                else { // if the error isn't something I was expecting, just throw it
                    input.close();
                    throw e;
                }
            }
        }

        input.close();
    }
}

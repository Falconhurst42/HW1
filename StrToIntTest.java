import java.security.InvalidParameterException;
import java.util.Scanner;

public class StrToIntTest {
    public static void main(String[] args) {
        System.out.printf("Input an integer: ");

        String str = "";

        Scanner input = new Scanner(System.in);
        // keep looping until the user gives valid input
        while(true) {
            try {
                str = input.next();
                System.out.printf("\nYou input %d.\n", StrToInt.string_to_int(str));
                break;
            }
            catch(Exception e) {
                if(e instanceof InvalidParameterException && e.getMessage().charAt(0) == '"') {
                    System.out.printf("Your input (%s) was not an integer. Try again: ", str);
                    input.nextLine();
                }
                else { // if the exception wasn't throw intentionally by the code, just give up and throw it
                    input.close();
                    throw e;
                }
            }
        }

        input.close();
    }
}

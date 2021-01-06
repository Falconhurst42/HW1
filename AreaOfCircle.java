import java.util.Scanner;

public class AreaOfCircle {
    public static void main(String[] args) {
        final float PI = 3.14F;
        System.out.printf("Input the radius of a circle: ");

        Scanner input = new Scanner(System.in);
        while(true) {
            try{
                float n = input.nextFloat();
                
                if(n < 0) {
                    System.out.printf("A circle cannot have a negative radius. Try again: ");
                }
                else {
                    System.out.printf("A circle with radius %f has area %f", n, PI*n*n);
                    break;
                }
            }
            catch(Exception e) {
                System.out.printf("That was not a number. Please try again: ");
                input.nextLine();
            }
        }

        input.close();
    }
}

import java.util.Scanner;

public class BMI {
    final static private float lbs2kg = 0.453592F;
    final static private float ft2m = 0.3048F;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        float w, h;

        System.out.printf("Please input an your weight in pounds: ");
        while(true) {
            try{
                w = input.nextFloat();
                
                if(w < 0) {
                    System.out.printf("Your weight must be positive. Try again: ");
                }
                else {
                    System.out.printf("You weight %f kg.\n", w*=lbs2kg);
                    break;
                }
            }
            catch(Exception e) {
                System.out.printf("That was not an number. Please try again: ");
                input.nextLine();
            }
        }

        System.out.printf("Please input an your height in feet: ");
        while(true) {
            try{
                h = input.nextFloat();
                
                if(h < 0) {
                    System.out.printf("Your height must be positive. Try again: ");
                }
                else {
                    System.out.printf("You are %f meters tall.\n", h*=ft2m);
                    break;
                }
            }
            catch(Exception e) {
                System.out.printf("That was not an number. Please try again: ");
                input.nextLine();
            }
        }

        input.close();

        System.out.printf("Your BMI is %f.\n", w/(h*h));
    }
}

import java.security.InvalidParameterException;

public class OddOrEven {
    static final public int min = 10;
    static final public int max = 100;

    public static void output(int num) {
        // if the number is out of range throw an error
        if(num < min || num > max) {
            throw new InvalidParameterException(String.format("\"%d\" is not in range.\n", num));
        }

        for(int i = 1; i <= num; i++) {
            System.out.printf("%d is %s\n", i, (i%2 == 0 ? "even" : "odd"));
        }
    }
}
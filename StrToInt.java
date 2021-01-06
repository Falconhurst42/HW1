import java.security.InvalidParameterException;

public class StrToInt {
    public static int string_to_int(String str) {
        // strip whitespace

        str = str.strip();
        int n = 0;
        boolean neg = false;

        // iterate through chars or string
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // catch negative signs
            if(i == 0) {
                if(ch == '-') {
                    neg = true;
                    continue;
                }
            }

            // check against acceptable ASCII range
            if(ch >= 48 && ch <= 57) {
                // calculate exponent and value
                // add that to our number
                n += Math.pow(10, str.length()-i-1) * (ch - 48);
            }
            else {
                throw new InvalidParameterException(String.format("\"%s\" is not an integer. Error at character %d", str, i));
            }
        }

        return (neg ? -n : n);

    }
}

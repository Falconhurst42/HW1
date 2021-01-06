import java.util.InputMismatchException;
import java.time.LocalDate;

public class Date {
    // object info
    private int day, month, year;

    // static Date info
    private static final String[] months = {
        "January",  "February", "March", 
        "April",    "May",      "June", 
        "July",     "August",   "September", 
        "October",  "November", "December"
    };
    private static final int[] month_lengths = {
        31, 28, 31, 
        30, 31, 30, 
        31, 31, 30, 
        31, 30, 31
    };
    private static final String[] leap_months = {
        "February"
    };
    // how often leap years occurs and how much leap years are offset for an even interval
    // for leap years:  year % l_y_i == l_y_o
    public static final int leap_year_interval = 4;
    public static final int leap_year_offset = 0;

    // initializer takes all ints (d/m/y)
    public Date(int d, int m, int y) {
        // set Date
        day = d;
        month = m;
        year = y;

        // validate Date
        if(!is_valid_Date(day, month, year)) {
            throw new IllegalArgumentException(String.format("\"%d-%02d-%02d\" is not a valid date", year, month, day));
        }
    }

    // initializer takes month name as string (d/m/y)
    public Date(int d, String m, int y) throws InputMismatchException {
        // set Date
        day = d;
        // find month in month array
        month = -1;
        for(int i = 0; i < months.length; i++) {
            if(months[i].compareToIgnoreCase(m) == 0) {
                month = i+1;
                break;
            }
        }
        // if we did't find the month, throw an exception
        if(month == -1) {
            throw new InputMismatchException(String.format("Invalid month name \"%s\" in Date initialization.", m));
        }
        year = y;

        // validate Date
        if(!is_valid_Date(day, month, year)) {
            throw new IllegalArgumentException(String.format("\"%d-%02d-%02d\" is not a valid date", year, month, day));
        }
    }

    // get Date a certain number of days after this Dates current value
    public Date get_future_date(int offset) {
        Date returning = new Date(day, month, year);

        if(offset < 0) {
            throw new IllegalArgumentException("get_future_date does not currently support negative offsets.");
        }

        while(offset > 0) {
            // get days left in month
            int month_length_remaining = get_month_length(returning.month, returning.year) - returning.day + 1;

            // if offset <= days remaining
            // add remaining offset to date day and update offset
            if(offset <= month_length_remaining) {
                returning.day += offset;
                offset = 0;
            }
            // else incriment month and/or year, reset days, and update offset
            else {
                // set day to 1 (since going to next month)
                returning.day = 1;
                // update offset
                offset -= month_length_remaining;
                // update month
                returning.month++;
                // check for month overflow
                if(returning.month > months.length) {
                    returning.year++;
                    returning.month = 1;
                }
            }
        }

        return returning;
    }

    // determine the number of the days between this date and the given date object
    public int get_day_difference(Date d2) {
        return get_day_difference(this, d2);
    }

    // determine the number of days between two Date objects
    public static int get_day_difference(Date d1, Date d2) {
        // represents d1 - d2, will return with abs
        int difference = 0;

        // adjust based on day difference
        difference += d1.day - d2.day;

        // adjust based on month difference
        // iterate from d1.month to d2.month, going up or down based off the comparison
        int incrimenter = ((d1.month > d2.month) ? -1 : 1);
        // adjust start and stop months to make sure we're counting the right ones
        int start = ((d1.month > d2.month) ? d1.month-1 : d1.month);
        int stop = ((d1.month > d2.month) ? d2.month-1 : d2.month);
        for(int i = start; i != stop; i += incrimenter) {
            // either add or subtract month length from difference based on which direction we are iterating
            difference -= get_month_length(i, d2.year) * incrimenter; 
            // using d2.year becuase we never iterate over that year in our year adjustment
            // thus, this month adjustment is effectively occuring in the year of d2
        }

        // adjust based on year difference
        if(d1.year != d2.year) {
            // get days in year / leap year
            int days_in_year = 0;
            for(int ml: month_lengths) {
                days_in_year += ml;
            }

            // add (days_in_year) * (difference in years)
            difference += days_in_year * (d1.year - d2.year);

            // add days for intervening leap years
            // iterate from d1.year to d2.year, going up or down based off the comparison
            incrimenter = ((d1.year > d2.year) ? -1 : 1);
            for(int i = d1.year; i != d2.year; i += incrimenter) {
                if(is_leap_year(i)) {
                    // either add or subtract 1 from difference based on which direction we are iterating
                    difference -= incrimenter;
                }
            }
        }       

        return Math.abs(difference);
    }

    private static boolean is_valid_Date(int d, int m, int y) {
        // check for obvious invalid dates
        if(d < 1 || m < 1 || m > months.length || y < 0) {
            return false;
        }

        // check month length
        int month_length = get_month_length(m, y);
        // check day against month length
        if(d > month_length) {
            return false;
        }

        // if none of the previous tests return flase, the date is valid
        return true;
    }

    private static int get_month_length(int m, int y) {
        return (is_leap_month(m, y) ? month_lengths[m-1] + 1 : month_lengths[m-1]);
    }

    private static boolean is_leap_year(int y) {
        // use l_y_i and l_y_o as well as accounting for 100 year rule
        boolean is_leap = ( (y % leap_year_interval == leap_year_offset) && !((y % 100 == 0) && !(y % 400 == 0)));
        return is_leap;
    }

    private static boolean is_leap_month(int m, int y) {
        // check if this is a leap year
        if(is_leap_year(y)) {
            // check if the month name is in the leap_months list
            String month_name = months[m-1];
            for(String mon: leap_months) {
                if(mon.compareToIgnoreCase(month_name) == 0) {
                    // if so return true
                    return true;
                }
            }
        }

        // else return false
        return false;
    }

    // get current date
    public static Date get_date() {
        LocalDate today = LocalDate.now();

        return new Date(today.getDayOfMonth(), today.getMonthValue(), today.getYear());
    }

    // get current date offset by given number of days
    public static Date get_date(int offset) {
        return get_date().get_future_date(offset);
    }

    public int get_day() {
        return day;
    }
    public int get_month() {
        return month;
    }
    public int get_month_num() {
        return month;
    }
    public String get_month_name() {
        return months[month-1];
    }
    public int get_year() {
        return year;
    }
    // get day in format Month D, YYYY
    public String get_date_text() {
        return String.format("%s %d, %d", months[month-1], day, year);
    }
    // get date in format YYYY-MM-DD
    public String get_date_ISO() {
        return String.format("%d-%02d-%02d", year, month, day);
    }
}

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateTester {
    public static void main(String[] args) {
        int successes = 0;
        int failures = 0;

        // test constructors
            Date birthday, christmas_truce, leap_day;
            // normal case (month int)
            try {
                birthday = new Date(3, 5, 2001);
                System.out.printf(" -- SUCCESS: Month int construcor successfully constructed date %s\n", birthday.get_date_ISO());
                successes++;
            } catch (Exception e) {
                System.out.printf(" -- FAILURE: Month int constructor incorrectly failed on valid date.\n             %s\n", e.getMessage());
                failures++;
                // gotta put this initialization in here or the compiler bugs me
                birthday = new Date(3, 5, 2001);
            }

            // normal case (month string)
            try {
                christmas_truce = new Date(24, "December", 1914);
                System.out.printf(" -- SUCCESS: Month string construcor successfully constructed date %s\n", christmas_truce.get_date_text());
                successes++;
            } catch (Exception e) {
                System.out.printf(" -- FAILURE: Month string constructor incorrectly failed on valid date.\n             %s\n", e.getMessage());
                failures++;
            }

            // obviously invalid day
            try {
                leap_day = new Date(0, 1, 2000);
                System.out.printf(" -- FAILURE: Constructor allowed invalid date: %s\n", leap_day.get_date_ISO());
                failures++;
            } catch (Exception e) {
                System.out.println(" -- SUCCESS: Constructor correctly failed on day=0.");
                successes++;
            }

            // obviously invalid month
            try {
                leap_day = new Date(1, -42, 2000);
                System.out.printf(" -- FAILURE: Constructor allowed invalid date: %s\n", leap_day.get_date_ISO());
                failures++;
            } catch (Exception e) {
                System.out.println(" -- SUCCESS: Constructor correctly failed on month=-42.");
                successes++;
            }

            // obviously invalid year
            try {
                leap_day = new Date(1, 1, -2000);
                System.out.printf(" -- FAILURE: Constructor allowed invalid date: %s\n", leap_day.get_date_ISO());
                failures++;
            } catch (Exception e) {
                System.out.println(" -- SUCCESS: Constructor correctly failed on year=-2000.");
                successes++;
            }

            // invalid day given specific month
            try {
                leap_day = new Date(31, 6, 2000);
                System.out.printf(" -- FAILURE: Constructor allowed invalid date: %s\n", leap_day.get_date_ISO());
                failures++;
            } catch (Exception e) {
                System.out.println(" -- SUCCESS: Constructor correctly failed on June 31.");
                successes++;
            }

            // invalid day given leap month
            try {
                leap_day = new Date(29, 2, 1900);
                System.out.printf(" -- FAILURE: Constructor allowed invalid date: %s\n", leap_day.get_date_ISO());
                failures++;
            } catch (Exception e) {
                System.out.println(" -- SUCCESS: Constructor correctly failed on non-leap-year Feb 29.");
                successes++;
            }

            // valid day given leap month
            try {
                leap_day = new Date(28, 2, 2004);
                System.out.printf(" -- SUCCESS: Month string construcor successfully constructed date %s\n", leap_day.get_date_text());
                successes++;
            } catch (Exception e) {
                System.out.printf(" -- FAILURE: Constructor incorrectly failed on leap-year Feb 29.\n             %s\n", e.getMessage());
                failures++;
            }
        
        // Test get_date, compare result to LocalDate result
        // (also: possibly test get_date_ISO and get_date_string)
            Date today;
            try {
                // get current Date
                today = Date.get_date();
                
                // compare it to result from LocalDate
                LocalDate Ltoday = LocalDate.now();
                if(today.get_day() != Ltoday.getDayOfMonth() || today.get_month() != Ltoday.getMonthValue() || today.get_year() != Ltoday.getYear()) {
                    System.out.printf(" -- FAILURE: get_date() does not agree with LocalDate.now()\n  get_date(): %s\n    LocalDate: %d-%02d-%02d\n", today.get_date_ISO(), Ltoday.getYear(), Ltoday.getMonthValue(), Ltoday.getDayOfMonth());
                    try {
                        today = new Date(Ltoday.getDayOfMonth(), Ltoday.getMonthValue(), Ltoday.getYear());
                    } catch (Exception e2) {
                        System.out.printf(" -- FAILURE: Month int constructor incorrectly failed on today's date from LocalDate.\n             %s\n", e2.getMessage());
                        failures++;
                    }
                    failures++;
                }
                else {
                    System.out.printf(" -- SUCCESS: get_date() returned proper value (%s)\n", today.get_date_text());
                    successes++;
                }
            } catch (Exception e) {
                System.out.printf(" -- FAILURE: get_date() threw error.\n             %s\n", e.getMessage());
                failures++;

                try {
                    // set today using LocalDate
                    LocalDate Ltoday = LocalDate.now();
                    today = new Date(Ltoday.getDayOfMonth(), Ltoday.getMonthValue(), Ltoday.getYear());
                } catch (Exception e2) {
                    System.out.printf(" -- FAILURE: Month int constructor incorrectly failed on today's date from LocalDate.\n             %s\n", e2.getMessage());
                    today = new Date(1, 1, 2000);
                    failures++;
                }
                
            }
        
        // Test get_date(offset) and get_future_date()
        // (also: possibly test get_date_ISO and get_date_string)
            Date thousand_days;
            // Test valid get_date(offset): get_date(1000)
            try {
                // get date offset by 1000
                thousand_days = Date.get_date(1000);
                
                // compare it to result from LocalDate
                LocalDate Lthousand = LocalDate.now().plusDays(1000);
                if(thousand_days.get_day() != Lthousand.getDayOfMonth() || thousand_days.get_month() != Lthousand.getMonth().getValue() || thousand_days.get_year() != Lthousand.getYear()) {
                    System.out.printf(" -- FAILURE: get_date(1000) does not agree with LocalDate.now()\n  get_date(): %s\n    LocalDate: %d-%02d-%02d\n", thousand_days.get_date_ISO(), Lthousand.getYear(), Lthousand.getMonth().getValue(), Lthousand.getDayOfMonth());
                    failures++;
                }
                else {
                    System.out.printf(" -- SUCCESS: get_date(1000) returned proper value (%s)\n", thousand_days.get_date_text());
                    successes++;
                }
            } catch (Exception e) {
                System.out.printf(" -- FAILURE: get_date(1000) threw error.\n             %s\n", e.getMessage());
                failures++;
            }

            // Test valid get_future_date(offset): get_future_date(1000)
            try {
                // get date offset by 1000
                thousand_days = today.get_future_date(1000);
                
                // compare it to result from LocalDate
                LocalDate Lthousand = LocalDate.now().plusDays(1000);
                if(thousand_days.get_day() != Lthousand.getDayOfMonth() || thousand_days.get_month() != Lthousand.getMonth().getValue() || thousand_days.get_year() != Lthousand.getYear()) {
                    System.out.printf(" -- FAILURE: get_future_date(1000) does not agree with LocalDate.now()\n  get_date(): %s\n    LocalDate: %d-%02d-%02d\n", thousand_days.get_date_ISO(), Lthousand.getYear(), Lthousand.getMonth().getValue(), Lthousand.getDayOfMonth());
                    failures++;
                }
                else {
                    System.out.printf(" -- SUCCESS: get_future_date(1000) returned proper value (%s)\n", thousand_days.get_date_text());
                    successes++;
                }
            } catch (Exception e) {
                System.out.printf(" -- FAILURE: get_future_date(1000) threw error.\n             %s\n", e.getMessage());
                failures++;
            }

            // test negative offset in get_date(offset)
            try {
                Date d = Date.get_date(-1000);
                System.out.println(" -- FAILURE: get_date(-1000) failed to throw error.");
                failures++;
            } catch (Exception e) {
                if(e.getMessage() == "get_future_date does not currently support negative offsets.") {
                    System.out.println(" -- SUCCESS: get_date(-1000) threw proper error.");
                    successes++;
                }
                else {
                    System.out.printf(" -- FAILURE: get_date(-1000) threw wrong error message\n             \"%s\"\n", e.getMessage());
                    failures++;
                }
            }

            // test negative offest in get_future_date(offset)
            try {
                Date d = new Date(1, 1, 2000).get_future_date(-1000);
                System.out.println(" -- FAILURE: get_future_date(-1000) failed to throw error.");
                failures++;
            } catch (Exception e) {
                if(e.getMessage() == "get_future_date does not currently support negative offsets.") {
                    System.out.println(" -- SUCCESS: get_future_date(-1000) threw proper error.");
                    successes++;
                }
                else {
                    System.out.printf(" -- FAILURE: get_future_date(-1000) threw wrong error message\n             \"%s\"\n", e.getMessage());
                    failures++;
                }
            }
        
        // Tests get_day_difference() and compare results to LocalDate
            // Test just days
            if(test_date_difference(new Date(1, 1, 2000), new Date(10, 1, 2000))) {
                successes++;
            }
            else {
                failures++;
            }

            // Test just months
            if(test_date_difference(new Date(1, 1, 2000), new Date(1, 2, 2000))) {
                successes++;
            }
            else {
                failures++;
            }

            // Test just years
            if(test_date_difference(new Date(1, 1, 2001), new Date(1, 1, 2002))) {
                successes++;
            }
            else {
                failures++;
            }

            // Test leap month
            if(test_date_difference(new Date(1, 2, 2004), new Date(1, 3, 2004))) {
                successes++;
            }
            else {
                failures++;
            }

            // Test leap year
            if(test_date_difference(new Date(1, 1, 2004), new Date(1, 1, 2005))) {
                successes++;
            }
            else {
                failures++;
            }

            // Test millenium false leap year
            if(test_date_difference(new Date(1, 1, 2000), new Date(1, 1, 2001))) {
                successes++;
            }
            else {
                failures++;
            }

            // Test four years
            if(test_date_difference(new Date(1, 1, 2001), new Date(1, 1, 2005))) {
                successes++;
            }
            else {
                failures++;
            }

            // test end leap multiyear
            if(test_date_difference(new Date(1, 1, 2000), new Date(1, 2, 2020))) {
                successes++;
            }
            else {
                failures++;
            }

            // Test static g_d_d(birthday, today)
            if(test_date_difference(birthday, today)) {
                successes++;
            }
            else {
                failures++;
            }

            // Test static g_d_d(today, birthday)
            if(test_date_difference(today, birthday)) {
                successes++;
            }
            else {
                failures++;
            }

            // Test static g_d_d(2004-02-05, 2008-05-02)
            if(test_date_difference(new Date(5, 2, 2004), new Date(2, 5, 2008))) {
                successes++;
            }
            else {
                failures++;
            }

            // Test static g_d_d(2008-05-02, 2004-02-05)
            if(test_date_difference(new Date(2, 5, 2008), new Date(5, 2, 2004))) {
                successes++;
            }
            else {
                failures++;
            }
        
        // Summary:
            System.out.printf("\n-~+~- Summary -~+~-\n -- SUCCESS: %d\n -- FAILURE: %d\n", successes, failures);
    }

    private static boolean test_date_difference(Date d1, Date d2) {
        try {
            int dif = Date.get_day_difference(d1, d2);
            // this method of computing a difference in days came from https://beginnersbook.com/2017/10/java-8-calculate-days-between-two-dates/
            // specifically the usage of "until(LocalDate, ChronoUnit.DAYS)"
            int Ldif = Math.round(Math.abs(LocalDate.of(d1.get_year(), d1.get_month(), d1.get_day()).until(LocalDate.of(d2.get_year(), d2.get_month(), d2.get_day()), ChronoUnit.DAYS)));

            if(dif != Ldif) {
                System.out.printf(" -- FAILURE: get_day_difference(%s, %s) returned %d when it should've returned %d.\n", d1.get_date_ISO(), d2.get_date_ISO(), dif, Ldif);
                return false;
            }
            else {
                System.out.printf(" -- SUCCESS: get_day_difference(%s, %s) returned proper difference (%d).\n", d1.get_date_ISO(), d2.get_date_ISO(), dif);
                return true;
            }
        } catch (Exception e) {
            System.out.printf(" -- FAILURE: get_day_difference(%s, %s) threw error.\n             %s\n", d1.get_date_ISO(), d2.get_date_ISO(), e.getMessage());
            return false;
        }
    }
}

import java.time.LocalDate;

public class Employee {
    public int ID;
    public String name, position;
    public LocalDate hire_date;
    public Employee reports_to;

    public Employee(int I, String n, String p, LocalDate h) {
        ID = I;
        name = n;
        position = p;
        hire_date = h;
    }

    public Employee(int I, String n, String p, LocalDate h, Employee r) {
        ID = I;
        name = n;
        position = p;
        hire_date = h;
        reports_to = r;
    }

    public void set_superior(Employee e) {
        reports_to = e;
    }
}

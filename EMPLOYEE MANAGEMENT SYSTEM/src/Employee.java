import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int empId;
    private String name;
    private String ssn; // without dashes
    private String jobTitle;
    private String division;
    private double salary;
    private List<PayStatement> payHistory;

    // Constructor
    public Employee(int empId, String name, String ssn, String jobTitle, String division, double salary) {
        this.empId = empId;
        this.name = name;
        this.ssn = ssn;
        this.jobTitle = jobTitle;
        this.division = division;
        this.salary = salary;
        this.payHistory = new ArrayList<>();
    }

    // Getters and setters
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public List<PayStatement> getPayHistory() { return payHistory; }
    public void addPayStatement(PayStatement payStatement) { this.payHistory.add(payStatement); }
}

class PayStatement {
    private String month;
    private double amount;

    public PayStatement(String month, double amount) {
        this.month = month;
        this.amount = amount;
    }

    public String getMonth() { return month; }
    public double getAmount() { return amount; }
}

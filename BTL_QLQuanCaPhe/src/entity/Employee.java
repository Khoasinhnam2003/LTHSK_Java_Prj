package entity;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Employee {
    private String employeeId;
    private String employeeName;
    private Date dob;
    private String email;
    private Role role;
    private String password;

    public Employee() {
        // Default constructor
    }
    public Employee(String employeeId) {
        this.employeeId = employeeId;
    }
    public Employee(String employeeId, String employeeName, Date dob, String email, Role role, String password) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.dob = dob;
		this.email = email;
		this.role = role;
		this.password = password;
	}
    
    

	public Employee(String employeeId, String password) {
		super();
		this.employeeId = employeeId;
		this.password = password;
	}
	public Object[] toRow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new Object[] {
        	Boolean.FALSE,
            this.employeeId,
            this.employeeName,
            this.dob,
            this.email,
            this.role==Role.ADMIN?"Admin":"Staff",
            this.password
        };
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", dob=" + dob + ", email="
                + email + ", role=" + role + ", password=" + password + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (employeeId == null) {
            if (other.employeeId != null)
                return false;
        } else if (!employeeId.equals(other.employeeId))
            return false;
        return true;
    }
}
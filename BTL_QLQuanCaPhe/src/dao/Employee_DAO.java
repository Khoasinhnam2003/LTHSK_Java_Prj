package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import connectDB.ConnectDB;
import entity.Employee;
import entity.Role;

public class Employee_DAO {

    public Employee_DAO() {
        super();
    }
    public static ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        try {
            // Thêm dòng này để kết nối đến DB trước khi sử dụng
            ConnectDB.getInstance().connect();
            
            Connection connection = ConnectDB.getConnection();
            String sql = "select top 20 * from employee";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String employeeId = resultSet.getString("employee_id");
                String employeeName = resultSet.getString("employee_name"); 
                Date dob = resultSet.getDate("dob");
                String email = resultSet.getString("email");
                Role role = resultSet.getString("role").equalsIgnoreCase("ADMIN") ? Role.ADMIN : Role.STAFF;
                String password = resultSet.getString("password");
                
                Employee employee = new Employee(employeeId, employeeName, dob, email, role, password);
                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
    public static Employee findEmployeeById(String id) {
        ArrayList<Employee> list = Employee_DAO.getAllEmployees();
        Employee foundEmployee = null;
        for (Employee employee : list) {
            if (employee.getEmployeeId().equalsIgnoreCase(id))
                foundEmployee = employee;
        }
        return foundEmployee;
    }
    
    public int getIndex(Employee employee) {
        ArrayList<Employee> list = Employee_DAO.getAllEmployees(); 
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEmployeeId().equals(employee.getEmployeeId())) { 
                return i;  
            }
        }
        return -1;  
    }
    
    public int getNewId(ArrayList<Employee> list) {
        ArrayList<Integer> listSo = new ArrayList<Integer>();
        for (Employee employee : list) {
            int number = Integer.parseInt(employee.getEmployeeId().replaceAll("\\D+", ""));
            listSo.add(number);
        }
        return listSo.size()==0 ? 0 : listSo.stream().max(Integer::compareTo).orElseThrow()+1;
    }
    
    public ArrayList<Employee> findEmployeesByName(String name) {
        ArrayList<Employee> employees = new ArrayList<>();
        Connection connection = null;
        try {
            ConnectDB.getInstance();
            connection = ConnectDB.getConnection();

            String sql = "SELECT * FROM employee WHERE employee_name LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String employeeId = resultSet.getString("employee_id");
                String employeeName = resultSet.getString("employee_name");
                Date dob = resultSet.getDate("dob");
                String email = resultSet.getString("email");
                Role role = resultSet.getString("role").equalsIgnoreCase("ADMIN") ? Role.ADMIN : Role.STAFF;
                String password = resultSet.getString("password");

                Employee employee = new Employee(employeeId, employeeName, dob, email, role, password);
                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }
    
    public boolean addEmployee(Employee employee) {
        ArrayList<Employee> list = Employee_DAO.getAllEmployees();
        if (!list.contains(employee)) {
            ConnectDB.getInstance();
            Connection connection = ConnectDB.getConnection();
            try {
                String sql = "INSERT INTO employee (employee_id, employee_name, dob, email, role, password) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, employee.getEmployeeId());
                statement.setString(2, employee.getEmployeeName());
                statement.setDate(3, new java.sql.Date(employee.getDob().getTime()));
                statement.setString(4, employee.getEmail());
                statement.setString(5, employee.getRole()==Role.ADMIN?"Admin":"Staff");
                statement.setString(6, employee.getPassword());
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public ArrayList<Employee> deleteEmployees(ArrayList<Integer> indicesToDelete) {
        ArrayList<Employee> list = Employee_DAO.getAllEmployees();
        indicesToDelete.sort((a, b) -> b - a);
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "DELETE FROM employee WHERE employee_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int index : indicesToDelete) {
                if (index >= 0 && index < list.size()) {
                    Employee employeeToDelete = list.get(index);
                    
                    statement.setString(1, employeeToDelete.getEmployeeId());
                    statement.executeUpdate();
                    
                    list.remove(index);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public boolean deleteAll() {
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "DELETE FROM employee";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateEmployee(Employee employee) throws SQLException {
        ArrayList<Employee> list = Employee_DAO.getAllEmployees();
        if (list.contains(employee)) {
            ConnectDB.getInstance();
            Connection connection = ConnectDB.getConnection();
            try {
                String sql = "UPDATE employee SET employee_name=?, dob=?, email=?, role=?, password=? WHERE employee_id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, employee.getEmployeeName());
                statement.setDate(2, new java.sql.Date(employee.getDob().getTime()));
                statement.setString(3, employee.getEmail());
                statement.setString(4, employee.getRole()==Role.ADMIN?"Admin":"Staff");
                statement.setString(5, employee.getPassword());
                statement.setString(6, employee.getEmployeeId());
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
 
    public String getEmployeeIdByName(String employeeName) {
        String employeeId = null;
        Connection connection = null;
        try {
            ConnectDB.getInstance();
            connection = ConnectDB.getConnection();

            String sql = "SELECT employee_id FROM employee WHERE employee_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employeeName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                employeeId = resultSet.getString("employee_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeId;
    }
}
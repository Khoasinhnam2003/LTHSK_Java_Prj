package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Customer;

public class Customer_DAO {
    public Customer_DAO() {
        super();
    }

    // Lấy tất cả khách hàng từ bảng customer
    public static ArrayList<Customer> getAllCustomer() {
        ArrayList<Customer> customers = new ArrayList<>();
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM customer";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String customerId = resultSet.getString("customer_id");
                String customerName = resultSet.getString("customer_name");
                String phone = resultSet.getString("phone");
                int point = resultSet.getInt("point");

                Customer customer = new Customer(customerId, customerName, phone, point);
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Tìm khách hàng theo ID
    public static Customer findCustomerById(String id) {
        Customer customer = null;
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM customer WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String customerId = resultSet.getString("customer_id");
                String customerName = resultSet.getString("customer_name");
                String phone = resultSet.getString("phone");
                int point = resultSet.getInt("point");
                customer = new Customer(customerId, customerName, phone, point);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Lấy chỉ số của khách hàng trong danh sách
    public int getIndex(Customer customer1) {
        ArrayList<Customer> list = Customer_DAO.getAllCustomer();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCustomerID().equals(customer1.getCustomerID())) {
                return i;
            }
        }
        return -1;
    }

    // Tạo ID mới cho khách hàng
    public int getNewId(ArrayList<Customer> list) {
        int maxId = 0;
        for (Customer customer : list) {
            String customerID = customer.getCustomerID();
            if (customerID != null && customerID.startsWith("Cus")) {
                try {
                    int number = Integer.parseInt(customerID.replace("Cus", ""));
                    maxId = Math.max(maxId, number);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid customerID format: " + customerID);
                }
            }
        }
        return maxId + 1;
    }

    // Tìm khách hàng theo tên
    public ArrayList<Customer> findCustomerByName(String name) {
        ArrayList<Customer> customers = new ArrayList<>();
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM customer WHERE customer_name LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String customerId = resultSet.getString("customer_id");
                String customerName = resultSet.getString("customer_name");
                String phone = resultSet.getString("phone");
                int point = resultSet.getInt("point");
                Customer customer = new Customer(customerId, customerName, phone, point);
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Thêm khách hàng mới
    public boolean addCustomer(Customer customer) {
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "INSERT INTO customer (customer_id, customer_name, phone, point) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, customer.getCustomerID());
            stmt.setString(2, customer.getCustomerName());
            stmt.setString(3, customer.getPhone());
            stmt.setInt(4, customer.getPoint());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa danh sách khách hàng theo danh sách chỉ số
    public ArrayList<Customer> deleteCustomeries(ArrayList<Integer> indicesToDelete) {
        ArrayList<Customer> list = Customer_DAO.getAllCustomer();
        indicesToDelete.sort((a, b) -> b - a);
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "DELETE FROM customer WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int index : indicesToDelete) {
                if (index >= 0 && index < list.size()) {
                    Customer customerToDelete = list.get(index);
                    statement.setString(1, customerToDelete.getCustomerID());
                    statement.executeUpdate();
                    list.remove(index);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Xóa toàn bộ khách hàng
    public boolean deleteAll() {
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "DELETE FROM customer";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin khách hàng
    public boolean updateCustomer(Customer customer) throws SQLException {
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "UPDATE customer SET customer_name = ?, phone = ?, point = ? WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getPhone());
            statement.setInt(3, customer.getPoint());
            statement.setString(4, customer.getCustomerID());
            int result = statement.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy mã khách hàng ngẫu nhiên
    public String getRandomCustomerId() {
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        String randomCustomerId = null;
        try {
            String sql = "SELECT TOP 1 customer_id FROM customer ORDER BY NEWID()";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                randomCustomerId = resultSet.getString("customer_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return randomCustomerId;
    }
}
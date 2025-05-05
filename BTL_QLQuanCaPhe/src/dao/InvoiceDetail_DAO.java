package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.InvoiceDetail;

public class InvoiceDetail_DAO {
    private Connection connection;

    // Constructor
    public InvoiceDetail_DAO() {
        ConnectDB.getInstance().connect();
        this.connection = ConnectDB.getConnection();

        if (this.connection == null) {
            System.out.println("Kết nối cơ sở dữ liệu không thành công!");
        }
    }

    // Lấy danh sách chi tiết hóa đơn theo mã hóa đơn (hàng 1)
    public List<InvoiceDetail> getInvoiceDetailsByInvoiceId(String invoiceId) {
        List<InvoiceDetail> invoiceDetails = new ArrayList<>();
        String sql = "SELECT invoice_id, product_id, product_name, quantity, unit_price, total_amount, total_price " +
                     "FROM InvoiceDetail WHERE invoice_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, invoiceId); // Thiết lập mã hóa đơn
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                InvoiceDetail invoiceDetail = new InvoiceDetail(
                    resultSet.getString("invoice_id"),
                    resultSet.getString("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("unit_price"),
                    resultSet.getDouble("total_amount"),
                    resultSet.getDouble("total_price")
                );
                invoiceDetails.add(invoiceDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoiceDetails;
    }

    // Thêm chi tiết hóa đơn
    public boolean addInvoiceDetail(InvoiceDetail invoiceDetail) {
        String sql = "INSERT INTO InvoiceDetail (invoice_id, product_id, product_name, quantity, unit_price, total_amount, total_price) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, invoiceDetail.getInvoiceId());  // Thêm mã hóa đơn
            statement.setString(2, invoiceDetail.getProductId());
            statement.setString(3, invoiceDetail.getProductName());
            statement.setInt(4, invoiceDetail.getQuantity());
            statement.setDouble(5, invoiceDetail.getUnitPrice());   // Chuyển sang setDouble
            statement.setDouble(6, invoiceDetail.getTotalAmount()); // Chuyển sang setDouble
            statement.setDouble(7, invoiceDetail.getTotalPrice());  // Thêm biến tổng tiền

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu thêm thất bại
    }
    
}
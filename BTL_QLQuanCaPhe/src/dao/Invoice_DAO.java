package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Invoice;

public class Invoice_DAO {
    private Connection connection;

    // Constructor
    public Invoice_DAO() {
        ConnectDB.getInstance().connect();
        this.connection = ConnectDB.getConnection();

        if (this.connection == null) {
            System.out.println("Kết nối cơ sở dữ liệu không thành công!");
        }
    }

    // Lấy danh sách hóa đơn
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoice";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                LocalDate invoiceDate = resultSet.getDate("invoice_date").toLocalDate();

                Invoice invoice = new Invoice(
                    resultSet.getString("invoice_id"),
                    invoiceDate,
                    resultSet.getString("table_id"),
                    resultSet.getString("employee_name"),
                    resultSet.getString("customer_id"),
                    resultSet.getString("employee_id"),
                    resultSet.getString("promotion_id")
                );
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    // Thêm hóa đơn mới
    public boolean addInvoice(Invoice invoice) {
        String sql = "INSERT INTO Invoice (invoice_id, invoice_date, table_id, employee_name, customer_id, employee_id, promotion_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rowsInserted = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, invoice.getInvoiceId());
            stmt.setDate(2, Date.valueOf(invoice.getinvoiceDate()));
            stmt.setString(3, invoice.getTableId());
            stmt.setString(4, invoice.getEmployeeName());
            stmt.setString(5, invoice.getCustomerId());
            stmt.setString(6, invoice.getEmployeeId());
            stmt.setString(7, invoice.getPromotionId());

            rowsInserted = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsInserted > 0;
    }

    // Lấy mã hóa đơn tiếp theo
    public String getNextInvoiceId() {
        String lastId = null;
        String newId = "HD0001"; // Mặc định

        String sql = "SELECT MAX(invoice_id) FROM Invoice";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                lastId = rs.getString(1);
            }

            if (lastId != null && !lastId.isEmpty()) {
                int num = Integer.parseInt(lastId.substring(2)) + 1;
                newId = "HD" + String.format("%04d", num);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return newId;
    }
 // Lấy ID mới cho hóa đơn
    public String getNewId(List<Invoice> invoices) {
        int maxId = 0;

        // Duyệt qua danh sách các hóa đơn để tìm ID lớn nhất
        for (Invoice invoice : invoices) {
            String id = invoice.getInvoiceId().replaceAll("\\D+", ""); // Loại bỏ các ký tự không phải số
            maxId = Math.max(maxId, Integer.parseInt(id));
        }

        // Trả về ID mới với định dạng 'HDxxxx', trong đó xxx là số kế tiếp
        return String.format("%03d", maxId + 1);
    }

}

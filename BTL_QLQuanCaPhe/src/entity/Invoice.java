package entity;

import java.time.LocalDate;

public class Invoice {
    private String invoiceId; // Mã hóa đơn
    private LocalDate invoiceDate; // Ngày đặt
    private String tableId; // Mã bàn
    private String employeeName; // Tên nhân viên
    private String customerId; // Mã khách hàng
    private String employeeId; // Mã nhân viên
    private String promotionId; // Mã khuyến mãi

    // Constructor không tham số
    public Invoice() {
    }
    //contrustor chỉ gôm các tham số cần thiết 
    public Invoice(String invoiceId, LocalDate invoiceDate, String tableId, String employeeName) {
    	this.invoiceId = invoiceId;
    	this.invoiceDate = invoiceDate;
    	this.tableId = tableId;
    	this.employeeName = employeeName;
 
}
    // Constructor đầy đủ tham số
    public Invoice(String invoiceId, LocalDate invoiceDate, String tableId, String employeeName, 
                   String customerId, String employeeId, String promotionId) {
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.tableId = tableId;
        this.employeeName = employeeName;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.promotionId = promotionId;
    }

    // Getter và Setter
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDate getinvoiceDate() {
        return invoiceDate;
    }

    public void setinvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

}

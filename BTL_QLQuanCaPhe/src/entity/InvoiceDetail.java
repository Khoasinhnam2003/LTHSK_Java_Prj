package entity;

public class InvoiceDetail {
    private String invoiceId;         // Mã hóa đơn
    private String productId;         // Mã sản phẩm
    private String productName;       // Tên sản phẩm
    private int quantity;             // Số lượng
    private double unitPrice;         // Đơn giá
    private double totalAmount;       // Thành tiền (số lượng * đơn giá)
    private double totalPrice;        // Tổng tiền (bao gồm VAT)

    // Constructor
    public InvoiceDetail(String invoiceId, String productId, String productName, int quantity, 
                         double unitPrice, double totalAmount, double totalPrice) {
        this.invoiceId = invoiceId;        // Mã hóa đơn
        this.productId = productId;        // Mã sản phẩm
        this.productName = productName;    // Tên sản phẩm
        this.quantity = quantity;          // Số lượng
        this.unitPrice = unitPrice;        // Đơn giá
        this.totalAmount = totalAmount;    // Thành tiền
        this.totalPrice = totalPrice;      // Tổng tiền
    }

    // Getters và Setters
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}

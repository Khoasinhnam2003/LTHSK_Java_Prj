package gui;

import com.toedter.calendar.JDateChooser;
import dao.Invoice_DAO;
import dao.Promotion_DAO;
import dao.InvoiceDetail_DAO;
import entity.Invoice;
import entity.InvoiceDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Orders_GUI extends JPanel {
    private JTable tableInvoice, tableInvoiceDetail;
    private DefaultTableModel tableModelInvoice, tableModelInvoiceDetail;
    private JTextField txtTotalAmount, txtSearchInvoiceId;
    private JDateChooser dateChooser;
    private JButton btnSearchByDate, btnSearchByInvoiceId;
	private JTextField txtgiamgia;

    private static final long serialVersionUID = 1L;

    public Orders_GUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(254,169,107));

        // Bảng bên trái - Thông tin chung của hóa đơn
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(254,169,107));

        JLabel lbInvoiceTitle = new JLabel("Thông tin hóa đơn");
        lbInvoiceTitle.setForeground(Color.black);
        lbInvoiceTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbInvoiceTitle.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(lbInvoiceTitle, BorderLayout.NORTH);

        tableModelInvoice = new DefaultTableModel();
        tableModelInvoice.addColumn("Mã hóa đơn");
        tableModelInvoice.addColumn("Ngày đặt");
        tableModelInvoice.addColumn("Mã bàn");
        tableModelInvoice.addColumn("  Tên nhân viên   ");
        tableModelInvoice.addColumn("Mã khuyến mãi");
        tableInvoice = new JTable(tableModelInvoice);
        JTableHeader tableHeaderInvoice = tableInvoice.getTableHeader();
        tableHeaderInvoice.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHeaderInvoice.setForeground(Color.black);
        tableHeaderInvoice.setBackground(new Color(254,169,107));
        tableInvoice.getColumnModel().getColumn(3).setPreferredWidth(150); // Đặt chiều rộng cho cột "Tên nhân viên"
        JScrollPane scrollPaneInvoice = new JScrollPane(tableInvoice);
        leftPanel.add(scrollPaneInvoice, BorderLayout.CENTER);
        
        // Thêm sự kiện nhấn vào bảng bên trái để hiển thị chi tiết hóa đơn
        tableInvoice.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableInvoice.getSelectedRow();
                if (row != -1) {
                    String invoiceId = (String) tableInvoice.getValueAt(row, 0);  // Lấy mã hóa đơn
                    String promotionId = (String) tableInvoice.getValueAt(row, 4);  // Lấy mã khuyến mãi
                    loadInvoiceDetails(invoiceId); // Gọi phương thức hiển thị chi tiết hóa đơn
                    // Lấy giảm giá từ mã khuyến mãi
                    if (promotionId != null && !promotionId.isEmpty()) {
                        Promotion_DAO promotionDAO = new Promotion_DAO();
                        double discountValue = promotionDAO.getDiscountByPromotionId(promotionId);
                        txtgiamgia.setText(String.format("%.1f%%", discountValue));  // Hiển thị giảm giá
                    } else {
                        txtgiamgia.setText("0%");  // Nếu không có mã khuyến mãi, đặt giảm giá là 0
                    }
                }
            }
        });

        // Tìm kiếm theo ngày và mã hóa đơn - Chuyển xuống dưới bảng bên trái
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 2, 10, 10));  // Sử dụng GridLayout để sắp xếp các nút tìm kiếm

        JLabel lbSearchDate = new JLabel("Tìm kiếm theo ngày:");
        searchPanel.add(lbSearchDate);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");  // Định dạng ngày
        searchPanel.add(dateChooser);

        // Thêm nút tìm kiếm theo ngày
        btnSearchByDate = new JButton("Tìm kiếm");
        btnSearchByDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchInvoiceByDate();  // Gọi phương thức tìm kiếm khi nút được nhấn
            }
        });
        searchPanel.add(btnSearchByDate);

        JLabel lbSearchInvoiceId = new JLabel("Tìm kiếm theo mã hóa đơn:");
        searchPanel.add(lbSearchInvoiceId);

        txtSearchInvoiceId = new JTextField(15);
        searchPanel.add(txtSearchInvoiceId);

        // Thêm nút tìm kiếm theo mã hóa đơn
        btnSearchByInvoiceId = new JButton("Tìm kiếm");
        btnSearchByInvoiceId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchInvoiceByInvoiceId();  // Gọi phương thức tìm kiếm theo mã hóa đơn
            }
        });
        searchPanel.add(btnSearchByInvoiceId);

        // Thêm panel tìm kiếm vào dưới bảng bên trái
        leftPanel.add(searchPanel, BorderLayout.SOUTH);

        // Bảng bên phải - Chi tiết hóa đơn
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(254,169,107));

        JLabel lbInvoiceDetailTitle = new JLabel("Chi tiết hóa đơn");
        lbInvoiceDetailTitle.setForeground(Color.black);
        lbInvoiceDetailTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbInvoiceDetailTitle.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(lbInvoiceDetailTitle, BorderLayout.NORTH);

        tableModelInvoiceDetail = new DefaultTableModel();
        tableModelInvoiceDetail.addColumn("Mã sản phẩm");
        tableModelInvoiceDetail.addColumn("Tên sản phẩm");
        tableModelInvoiceDetail.addColumn("Số lượng");
        tableModelInvoiceDetail.addColumn("Đơn giá");
        tableModelInvoiceDetail.addColumn("Thành tiền");

        tableInvoiceDetail = new JTable(tableModelInvoiceDetail);
        JTableHeader tableHeaderInvoiceDetail = tableInvoiceDetail.getTableHeader();
        tableHeaderInvoiceDetail.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHeaderInvoiceDetail.setForeground(Color.black);
        tableHeaderInvoiceDetail.setBackground(new Color(254,169,107));
        JScrollPane scrollPaneInvoiceDetail = new JScrollPane(tableInvoiceDetail);
        rightPanel.add(scrollPaneInvoiceDetail, BorderLayout.CENTER);

        // Phần hiển thị tổng tiền
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(new Color(254,169,107));
        JLabel giamgia = new JLabel("Giảm giá: ");
        txtgiamgia = new JTextField(5);
        txtgiamgia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtgiamgia.setHorizontalAlignment(JTextField.RIGHT);
        txtgiamgia.setEditable(false);
        txtgiamgia.setBackground(Color.WHITE);
        //tạo khoản cách theo chiều ngang
        JLabel Vat = new JLabel("VAT = 0.1");
        JLabel lbTotalAmount = new JLabel("Tổng tiền(đã bao gồm VAT và khuyến mãi):");
        lbTotalAmount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbTotalAmount.setForeground(Color.black);

        txtTotalAmount = new JTextField(15);
        txtTotalAmount.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTotalAmount.setHorizontalAlignment(JTextField.RIGHT);
        txtTotalAmount.setEditable(false);
        txtTotalAmount.setBackground(Color.WHITE);
        //tạo khoản cách theo chiều ngang
        JPanel spacePanel = new JPanel();
        spacePanel.setPreferredSize(new Dimension(50, 0));
        totalPanel.add(giamgia);
        totalPanel.add(txtgiamgia);
        totalPanel.add(Vat);
        totalPanel.add(spacePanel);
        totalPanel.add(lbTotalAmount);
        totalPanel.add(txtTotalAmount);
     // Tạo panel cho nút "Tải lại" và căn trái
        JPanel reloadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        reloadPanel.setBackground(new Color(254,169,107));

        // Tạo nút "Tải lại"
        JButton btnReload = new JButton("Reload");
        btnReload.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnReload.setForeground(Color.GREEN);
        btnReload.setIcon(new ImageIcon("images/refresh.png"));
        btnReload.setPreferredSize(new Dimension(130,40));
        btnReload.setBackground(Color.GRAY);
        btnReload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức tải lại chi tiết hóa đơn
                refreshInvoiceData();
                JOptionPane.showMessageDialog(Orders_GUI.this, "Dữ liệu đã được làm mới!");
            }
        });

        // Thêm nút vào reloadPanel
        reloadPanel.add(btnReload);

        // Thêm reloadPanel vào totalPanel
        totalPanel.add(reloadPanel);

        // Thêm totalPanel vào phần dưới của bảng chi tiết hóa đơn
        rightPanel.add(totalPanel, BorderLayout.SOUTH);


        // Thêm các bảng vào giao diện chính
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        
        loadInvoiceData(); // Gọi phương thức để tải dữ liệu hóa đơn ban đầu
    }

    // Phương thức để tải danh sách hóa đơn từ cơ sở dữ liệu và hiển thị lên bảng
    private void loadInvoiceData() {
        Invoice_DAO invoiceDAO = new Invoice_DAO();
        List<Invoice> invoices = invoiceDAO.getAllInvoices(); // Lấy tất cả hóa đơn từ cơ sở dữ liệu

        // Xóa tất cả dữ liệu cũ trong bảng hóa đơn
        tableModelInvoice.setRowCount(0);
        // Thêm dữ liệu hóa đơn vào bảng
        for (Invoice invoice : invoices) {
            Object[] row = new Object[] {
                invoice.getInvoiceId(),
                invoice.getinvoiceDate(),
                invoice.getTableId(),
                invoice.getEmployeeName(),
                invoice.getPromotionId()
            };
            tableModelInvoice.addRow(row);
        }
        // Đảm bảo rằng bảng được làm mới sau khi dữ liệu được cập nhật
        tableModelInvoice.fireTableDataChanged();
        
    }

    // Phương thức để tải chi tiết hóa đơn vào bảng
    private void loadInvoiceDetails(String invoiceId) {
        InvoiceDetail_DAO invoiceDetailDAO = new InvoiceDetail_DAO();
        List<InvoiceDetail> invoiceDetails = invoiceDetailDAO.getInvoiceDetailsByInvoiceId(invoiceId);

        // Xóa tất cả dữ liệu cũ trong bảng chi tiết hóa đơn
        tableModelInvoiceDetail.setRowCount(0);

        double totalAmount = 0;
        // Thêm chi tiết hóa đơn vào bảng
        for (InvoiceDetail detail : invoiceDetails) {
            Object[] row = new Object[] {
                detail.getProductId(),
                detail.getProductName(),
                detail.getQuantity(),
                detail.getUnitPrice(),
                detail.getTotalAmount()
            };
            tableModelInvoiceDetail.addRow(row);
            totalAmount = detail.getTotalPrice(); // Tổng tiền
        }

        // Cập nhật tổng tiền vào ô nhập liệu
        txtTotalAmount.setText(String.format("%.1f", totalAmount));
    }

    // Phương thức tìm kiếm hóa đơn theo ngày
    private void searchInvoiceByDate() {
        Date selectedDate = dateChooser.getDate();
        if (selectedDate != null) {
            // Chuyển đổi ngày sang LocalDate
            LocalDate searchDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // Lọc hóa đơn theo ngày
            loadInvoiceDataByDate(searchDate);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ngày để tìm kiếm.");
        }
    }

    // Phương thức tìm kiếm hóa đơn theo mã hóa đơn
    private void searchInvoiceByInvoiceId() {
        String invoiceId = txtSearchInvoiceId.getText().trim();
        if (!invoiceId.isEmpty()) {
            loadInvoiceDetails(invoiceId);  // Gọi phương thức tải chi tiết hóa đơn theo mã hóa đơn
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn để tìm kiếm.");
        }
    }

    // Phương thức tìm kiếm và hiển thị hóa đơn theo ngày
    private void loadInvoiceDataByDate(LocalDate searchDate) {
        Invoice_DAO invoiceDAO = new Invoice_DAO();
        List<Invoice> invoices = invoiceDAO.getAllInvoices();

        // Xóa dữ liệu cũ trong bảng hóa đơn
        tableModelInvoice.setRowCount(0);

        // Thêm hóa đơn có ngày khớp vào bảng
        for (Invoice invoice : invoices) {
            if (invoice.getinvoiceDate().equals(searchDate)) {
                Object[] row = new Object[] {
                    invoice.getInvoiceId(),
                    invoice.getinvoiceDate(),
                    invoice.getTableId(),
                    invoice.getEmployeeName()
                };
                tableModelInvoice.addRow(row);
            }
        }
    }
    public void refreshInvoiceData() {
    	loadInvoiceData();
        // Đảm bảo rằng bảng được làm mới sau khi dữ liệu được cập nhật
        tableModelInvoice.fireTableDataChanged();

    }

}




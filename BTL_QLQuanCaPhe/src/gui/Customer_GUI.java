package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.Customer_DAO;
import entity.Customer;

public class Customer_GUI extends JPanel implements MouseListener, ActionListener {

    private static final long serialVersionUID = 1L;
    private Color background = new Color(254, 169, 107);
    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtPoint;
    private JTextField txtPhone;
    private DefaultTableModel modelCustomer;
    private JTable tableCustomer;
    private JComboBox<String> cmbMa;
    private JButton btnTimTheoMa;
    private JComboBox<String> cmbTen;
    private JButton btnTimTheoTen;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnXoaTrang;
    private JButton btnBoLoc;
    private JButton btnXoaHet;
    private Customer_DAO customer_DAO;
    private ArrayList<Customer> ds;

    public Customer_GUI() {
        setLayout(new BorderLayout());

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        customer_DAO = new Customer_DAO();
        ds = Customer_DAO.getAllCustomer();

        // Tiêu đề
        JLabel lblTieuDe = new JLabel("THÔNG TIN KHÁCH HÀNG");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));
        lblTieuDe.setForeground(background);
        JPanel jpTieuDe = new JPanel();
        jpTieuDe.add(lblTieuDe);

        // Form nhập liệu
        Box b = Box.createVerticalBox();
        Box b1 = Box.createHorizontalBox();
        Box b2 = Box.createHorizontalBox();
        Box b3 = Box.createHorizontalBox();
        Box b4 = Box.createHorizontalBox();
        Box b5 = Box.createHorizontalBox();

        JLabel lblMa = new JLabel("Mã khách hàng: ");
        b1.add(Box.createRigidArea(new Dimension(200, 0)));
        b1.add(lblMa);
        b1.add(Box.createRigidArea(new Dimension(40, 0)));
        b1.add(txtMa = new JTextField());
        txtMa.setEnabled(false);
        txtMa.setText("Cus" + customer_DAO.getNewId(ds));
        b1.add(Box.createRigidArea(new Dimension(200, 0)));
        b1.setBorder(new EmptyBorder(10, 10, 0, 10));

        JLabel lblTen = new JLabel("Tên khách hàng:");
        b2.add(Box.createRigidArea(new Dimension(200, 0)));
        b2.add(lblTen);
        b2.add(Box.createRigidArea(new Dimension(40, 0)));
        b2.add(txtTen = new JTextField());
        b2.add(Box.createRigidArea(new Dimension(200, 0)));
        b2.setBorder(new EmptyBorder(10, 10, 0, 10));

        JLabel lblPoint = new JLabel("Điểm tích lũy:");
        b4.add(Box.createRigidArea(new Dimension(200, 0)));
        b4.add(lblPoint);
        b4.add(Box.createRigidArea(new Dimension(40, 0)));
        b4.add(txtPoint = new JTextField());
        b4.add(Box.createRigidArea(new Dimension(200, 0)));
        b4.setBorder(new EmptyBorder(10, 10, 0, 10));

        JLabel lblPhone = new JLabel("Số điện thoại:");
        b5.add(Box.createRigidArea(new Dimension(200, 0)));
        b5.add(lblPhone);
        b5.add(Box.createRigidArea(new Dimension(40, 0)));
        b5.add(txtPhone = new JTextField());
        b5.add(Box.createRigidArea(new Dimension(200, 0)));
        b5.setBorder(new EmptyBorder(10, 10, 0, 10));

        // Thêm các nút chức năng
        b3.setBorder(new EmptyBorder(10, 10, 10, 10));
        b3.add(Box.createRigidArea(new Dimension(140, 0)));
        b3.add(new JLabel("Nhập mã khách hàng: "));
        b3.add(Box.createRigidArea(new Dimension(5, 0)));
        cmbMa = new JComboBox<>();
        for (Customer customer : ds) {
            cmbMa.addItem(customer.getCustomerID());
        }
        cmbMa.setPreferredSize(new Dimension(150, 25));
        b3.add(cmbMa);
        b3.add(Box.createRigidArea(new Dimension(5, 0)));
        btnTimTheoMa = new JButton("Tìm");
        b3.add(btnTimTheoMa);

        b3.add(Box.createRigidArea(new Dimension(40, 0)));
        b3.add(new JLabel("Nhập tên khách hàng: "));
        b3.add(Box.createRigidArea(new Dimension(5, 0)));
        cmbTen = new JComboBox<>();
        cmbTen.setEditable(true);
        cmbTen.setPreferredSize(new Dimension(150, 25));
        for (Customer customer : ds) {
            cmbTen.addItem(customer.getCustomerName());
        }
        b3.add(cmbTen);
        b3.add(Box.createRigidArea(new Dimension(5, 0)));
        btnTimTheoTen = new JButton("Tìm");
        b3.add(btnTimTheoTen);

        b3.add(Box.createRigidArea(new Dimension(40, 0)));
        btnThem = new JButton("Thêm");
        btnThem.setBackground(Color.green);
        b3.add(btnThem);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnSua = new JButton("Cập Nhật");
        btnSua.setEnabled(false);
        b3.add(btnSua);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnXoa = new JButton("Xóa");
        btnXoa.setEnabled(false);
        b3.add(btnXoa);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnXoaHet = new JButton("Xóa tất cả");
        btnXoaHet.setBackground(Color.red);
        b3.add(btnXoaHet);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnXoaTrang = new JButton("Làm mới");
        b3.add(btnXoaTrang);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnBoLoc = new JButton("Bỏ Lọc");
        b3.add(btnBoLoc);
        b3.add(Box.createRigidArea(new Dimension(140, 0)));

        // Thêm các Box vào form chính
        b.add(jpTieuDe);
        b.add(b1);
        b.add(b2);
        b.add(b4);
        b.add(b5);
        b.add(Box.createRigidArea(new Dimension(0, 30)));
        b.add(b3);
        b.add(Box.createRigidArea(new Dimension(0, 30)));

        // Tạo panel chứa form và tiêu đề
        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(b, BorderLayout.NORTH);

        // Tạo bảng hiển thị khách hàng
        String[] colHeader = { "Mã khách hàng", "Tên khách hàng", "Số điểm", "Số điện thoại", "Chọn để xóa" };
        modelCustomer = new DefaultTableModel(colHeader, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Boolean.class;
                }
                return String.class;
            }
        };
        tableCustomer = new JTable(modelCustomer);
        panelCenter.add(new JScrollPane(tableCustomer), BorderLayout.CENTER);
        showTable();

        // Thêm panelCenter vào Customer_GUI
        panelCenter.setBorder(new EmptyBorder(10, 10, 0, 10));
        add(panelCenter, BorderLayout.CENTER);

        // Gắn sự kiện
        btnThem.addActionListener(this);
        btnTimTheoMa.addActionListener(this);
        btnBoLoc.addActionListener(this);
        btnTimTheoTen.addActionListener(this);
        tableCustomer.addMouseListener(this);
        btnXoaTrang.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnXoaHet.addActionListener(this);
    }

    // Lấy danh sách các hàng được chọn để xóa
    public ArrayList<Integer> getCheckedRows() {
        ArrayList<Integer> checkedRows = new ArrayList<>();
        for (int i = 0; i < modelCustomer.getRowCount(); i++) {
            Boolean isChecked = (Boolean) modelCustomer.getValueAt(i, 4);
            if (isChecked != null && isChecked) {
                checkedRows.add(i);
            }
        }
        return checkedRows;
    }

    // Hiển thị danh sách khách hàng lên bảng
    public void showTable() {
        deleteTable();
        ds = Customer_DAO.getAllCustomer();
        for (Customer customer : ds) {
            Object[] rowData = { customer.getCustomerID(), customer.getCustomerName(), customer.getPoint(),
                    customer.getPhone(), false };
            modelCustomer.addRow(rowData);
        }
    }

    // Xóa toàn bộ dữ liệu trong bảng
    public void deleteTable() {
        modelCustomer.setRowCount(0);
    }

    // Làm mới form nhập liệu
    public void xoaTrang() {
        ds = Customer_DAO.getAllCustomer();
        txtMa.setText("Cus" + customer_DAO.getNewId(ds));
        txtTen.setText("");
        txtPoint.setText("");
        txtPhone.setText("");
        txtTen.requestFocus();
        btnThem.setEnabled(true);
        btnThem.setBackground(Color.green);
        btnSua.setEnabled(false);
        btnSua.setBackground(UIManager.getColor("Button.background"));
        btnXoa.setEnabled(false);
        btnXoa.setBackground(UIManager.getColor("Button.background"));
    }

    // Cập nhật lại combo box
    public void refreshComboBoxes() {
        cmbMa.removeAllItems();
        cmbTen.removeAllItems();
        ds = Customer_DAO.getAllCustomer();
        for (Customer customer : ds) {
            cmbMa.addItem(customer.getCustomerID());
            cmbTen.addItem(customer.getCustomerName());
        }
    }

    // Xử lý sự kiện
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnThem) {
            if (validation()) {
                String ma = txtMa.getText();
                String ten = txtTen.getText();
                String phone = txtPhone.getText();
                int point = Integer.parseInt(txtPoint.getText());
                

                Customer customer = new Customer(ma, ten, phone, point);
                if (customer_DAO.addCustomer(customer)) {
                    modelCustomer.addRow(new Object[] { ma, ten, point, phone, false });
                    refreshComboBoxes();
                    xoaTrang();
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!");
                }
            }

        } else if (o == btnTimTheoMa) {
            String selectedId = (String) cmbMa.getSelectedItem();
            if (selectedId != null) {
                Customer customer = customer_DAO.findCustomerById(selectedId);
                if (customer != null) {
                    deleteTable();
                    modelCustomer.addRow(new Object[] { customer.getCustomerID(), customer.getCustomerName(),
                            customer.getPoint(), customer.getPhone(), false });
                    tableCustomer.setRowSelectionInterval(0, 0);
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
                    showTable();
                }
            }

        } else if (o == btnTimTheoTen) {
            String selectedName = (String) cmbTen.getSelectedItem();
            if (selectedName != null && !selectedName.trim().isEmpty()) {
                ArrayList<Customer> list = customer_DAO.findCustomerByName(selectedName);
                if (!list.isEmpty()) {
                    deleteTable();
                    for (Customer customer : list) {
                        modelCustomer.addRow(new Object[] { customer.getCustomerID(), customer.getCustomerName(),
                                customer.getPoint(), customer.getPhone(), false });
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
                    showTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc nhập tên khách hàng cần tìm!");
            }

        } else if (o == btnBoLoc) {
            showTable();
            refreshComboBoxes();

        } else if (o == btnXoaTrang) {
            xoaTrang();

        } else if (o == btnSua) {
            if (validation()) {
                String ma = txtMa.getText();
                String ten = txtTen.getText();
                String phone = txtPhone.getText();
                int point = Integer.parseInt(txtPoint.getText());
                

                Customer customer = new Customer(ma, ten, phone, point);
                try {
                    if (customer_DAO.updateCustomer(customer)) {
                        deleteTable();
                        showTable();
                        refreshComboBoxes();
                        xoaTrang();
                        JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        } else if (o == btnXoa) {
            ArrayList<Integer> rowsToDelete = getCheckedRows();
            if (!rowsToDelete.isEmpty()) {
                if (JOptionPane.showConfirmDialog(this, "Bạn có chắc xóa khách hàng?") == JOptionPane.OK_OPTION) {
                    customer_DAO.deleteCustomeries(rowsToDelete);
                    deleteTable();
                    showTable();
                    refreshComboBoxes();
                    xoaTrang();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
            }

        } else if (o == btnXoaHet) {
            if (JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa hết toàn bộ khách hàng?") == JOptionPane.OK_OPTION) {
                if (JOptionPane.showConfirmDialog(this, "Bạn chắc chưa?") == JOptionPane.OK_OPTION) {
                    if (customer_DAO.deleteAll()) {
                        deleteTable();
                        showTable();
                        refreshComboBoxes();
                        xoaTrang();
                        JOptionPane.showMessageDialog(this, "Xóa tất cả thành công!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa tất cả thất bại!");
                    }
                }
            }
        }
    }

    // Xử lý sự kiện click chuột trên bảng
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tableCustomer.getSelectedRow();
        if (row != -1) {
            txtMa.setText(tableCustomer.getValueAt(row, 0).toString());
            txtTen.setText(tableCustomer.getValueAt(row, 1).toString());
            txtPoint.setText(tableCustomer.getValueAt(row, 2).toString());
            txtPhone.setText(tableCustomer.getValueAt(row, 3).toString());
            txtTen.requestFocus();
            btnThem.setEnabled(false);
            btnThem.setBackground(UIManager.getColor("Button.background"));
            btnSua.setEnabled(true);
            btnSua.setBackground(Color.green);
            btnXoa.setEnabled(true);
            btnXoa.setBackground(Color.red);
        }
    }

    // Kiểm tra dữ liệu nhập vào
    public boolean validation() {
        if (txtMa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã khách hàng không được để trống.");
            return false;
        }

        if (txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng không được để trống.");
            return false;
        }

        if (txtPoint.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Điểm tích lũy không được để trống.");
            return false;
        } else {
            try {
                int point = Integer.parseInt(txtPoint.getText().trim());
                if (point < 0) {
                    JOptionPane.showMessageDialog(null, "Điểm tích lũy phải là số nguyên lớn hơn hoặc bằng 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Điểm tích lũy phải là một số nguyên.");
                return false;
            }
        }

        if (txtPhone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không được để trống.");
            return false;
        } else {
            String phone = txtPhone.getText().trim();
            if (phone.length() != 10 || !phone.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(null, "Số điện thoại phải là dãy 10 số và bắt đầu bằng số 0.");
                return false;
            }
        }

        return true;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
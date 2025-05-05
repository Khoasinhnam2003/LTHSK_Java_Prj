package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.Drink_DAO;
import entity.Drink;

public class Drink_GUI extends JPanel implements ActionListener, MouseListener {
    private static final long serialVersionUID = 1L;
    private Color background = new Color(254, 169, 107);

    private JTextField txtMa, txtTen, txtGia, txtVAT, txtImage, txtCategory, txtSearch;
    private JTextArea txtMoTa;
    private DefaultTableModel modelDrink;
    private JTable tableDrink;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang, btnXoaHet, btnBoLoc, btnSearch;

    private Drink_DAO drink_DAO;
    private List<Drink> ds;

    public Drink_GUI() {
        setLayout(new BorderLayout());

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        drink_DAO = new Drink_DAO();
        ds = drink_DAO.getAllDrinks();

        // Tiêu đề
        JLabel lblTieuDe = new JLabel("THÔNG TIN ĐỒ UỐNG");
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
        Box bSearch = Box.createHorizontalBox();

        // Nhập mã đồ uống
        JLabel lblMa = new JLabel("Mã đồ uống:");
        b1.add(lblMa);
        b1.add(txtMa = new JTextField(15));
        txtMa.setEnabled(false);
        txtMa.setText("D" + drink_DAO.getNewId(ds));

        // Nhập tên đồ uống
        JLabel lblTen = new JLabel("Tên đồ uống:");
        b2.add(lblTen);
        b2.add(txtTen = new JTextField(15));

        // Nhập giá và VAT
        JLabel lblGia = new JLabel("Giá:");
        b3.add(lblGia);
        b3.add(txtGia = new JTextField(15));

        JLabel lblVAT = new JLabel("VAT:");
        b3.add(lblVAT);
        b3.add(txtVAT = new JTextField(15));

        // Nhập mô tả
        JLabel lblMoTa = new JLabel("Mô tả:");
        b4.add(lblMoTa);
        b4.add(txtMoTa = new JTextArea(3, 20));
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        b4.add(scrollMoTa);

        // Nhập ảnh và danh mục
        JLabel lblImage = new JLabel("Ảnh:");
        b5.add(lblImage);
        b5.add(txtImage = new JTextField(15));

        JLabel lblCategory = new JLabel("Danh mục:");
        b5.add(lblCategory);
        b5.add(txtCategory = new JTextField(15));


        // Thêm các nút chức năng
        btnThem = new JButton("Thêm");
        btnThem.setBackground(Color.green);
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnXoaTrang = new JButton("Làm mới");
        btnXoaHet = new JButton("Xóa tất cả");
        btnBoLoc = new JButton("Bỏ Lọc");
        btnSearch = new JButton("Tìm kiếm theo tên");
        txtSearch = new JTextField(15);
        JPanel panelButton = new JPanel();
        panelButton.add(btnThem);
        panelButton.add(btnSua);
        panelButton.add(btnXoa);
        panelButton.add(btnXoaTrang);
        panelButton.add(btnXoaHet);
        panelButton.add(btnBoLoc);
        panelButton.add(btnSearch);
        panelButton.add(txtSearch);
        // Bảng hiển thị dữ liệu
        String[] colHeader = { "Mã đồ uống", "Tên đồ uống", "Giá", "VAT", "Mô tả", "Ảnh", "Danh mục" };
        modelDrink = new DefaultTableModel(colHeader, 0);
        tableDrink = new JTable(modelDrink);
        JScrollPane scrollTable = new JScrollPane(tableDrink);

        // Thêm các thành phần vào panel
        b.add(jpTieuDe);
        b.add(b1);
        b.add(b2);
        b.add(b3);
        b.add(b4);
        b.add(b5);
        b.add(bSearch);
        b.add(panelButton);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(b, BorderLayout.NORTH);
        panelCenter.add(scrollTable, BorderLayout.CENTER);

        add(panelCenter, BorderLayout.CENTER);

        // Hiển thị dữ liệu trong bảng
        showTable();

        // Thêm sự kiện cho các nút
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnXoaTrang.addActionListener(this);
        btnXoaHet.addActionListener(this);
        btnBoLoc.addActionListener(this);
        btnSearch.addActionListener(this);
        tableDrink.addMouseListener(this);
    }

    // Hiển thị bảng dữ liệu
    private void showTable() {
        modelDrink.setRowCount(0);  // Xóa hết dữ liệu cũ trong bảng
        for (Drink drink : ds) {
            modelDrink.addRow(new Object[] {
                drink.getDrinkId(),
                drink.getDrinkName(),
                drink.getPrice(),
                drink.getVat(),
                drink.getDescription(),
                drink.getImage(),
                drink.getCategoryId()
            });
        }
    }

    // Các sự kiện khi click vào các nút
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem) {
            // Thêm đồ uống mới
            String ten = txtTen.getText();
            String gia = txtGia.getText();
            String vat = txtVAT.getText();
            String moTa = txtMoTa.getText();
            String image = txtImage.getText();
            String category = txtCategory.getText();

            if (ten.isEmpty() || gia.isEmpty() || vat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            try {
                double price = Double.parseDouble(gia);
                double vatValue = Double.parseDouble(vat);
                Drink newDrink = new Drink("D" + drink_DAO.getNewId(ds), ten, image, moTa, price, category, vatValue);
                if (drink_DAO.addDrink(newDrink)) {
                    JOptionPane.showMessageDialog(this, "Thêm đồ uống thành công!");
                    ds = drink_DAO.getAllDrinks();  // Cập nhật danh sách
                    showTable();  // Cập nhật bảng
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm đồ uống thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá và VAT phải là số hợp lệ!");
            }
        } else if (e.getSource() == btnSua) {
            // Cập nhật đồ uống
            int selectedRow = tableDrink.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đồ uống cần sửa!");
                return;
            }

            String ma = modelDrink.getValueAt(selectedRow, 0).toString();
            String ten = txtTen.getText();
            String gia = txtGia.getText();
            String vat = txtVAT.getText();
            String moTa = txtMoTa.getText();
            String image = txtImage.getText();
            String category = txtCategory.getText();

            if (ten.isEmpty() || gia.isEmpty() || vat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            try {
                double price = Double.parseDouble(gia);
                double vatValue = Double.parseDouble(vat);
                Drink updatedDrink = new Drink(ma, ten, image, moTa, price, category, vatValue);
                if (drink_DAO.updateDrink(updatedDrink)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật đồ uống thành công!");
                    ds = drink_DAO.getAllDrinks();  // Cập nhật danh sách
                    showTable();  // Cập nhật bảng
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật đồ uống thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá và VAT phải là số hợp lệ!");
            }
        } else if (e.getSource() == btnXoa) {
            // Xóa đồ uống
            int selectedRow = tableDrink.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đồ uống cần xóa!");
                return;
            }

            String ma = modelDrink.getValueAt(selectedRow, 0).toString();
            if (drink_DAO.deleteDrink(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa đồ uống thành công!");
                ds = drink_DAO.getAllDrinks();  // Cập nhật danh sách
                showTable();  // Cập nhật bảng
            } else {
                JOptionPane.showMessageDialog(this, "Xóa đồ uống thất bại!");
            }
        } else if (e.getSource() == btnXoaTrang) {
            // Làm mới form nhập liệu
            txtMa.setText("Drink" + drink_DAO.getNewId(ds));
            txtTen.setText("");
            txtGia.setText("");
            txtVAT.setText("");
            txtMoTa.setText("");
            txtImage.setText("");
            txtCategory.setText("");
        } else if (e.getSource() == btnXoaHet) {
            // Xóa tất cả đồ uống
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tất cả đồ uống?", "Xóa tất cả", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (drink_DAO.deleteAllDrinks()) {
                    JOptionPane.showMessageDialog(this, "Xóa tất cả đồ uống thành công!");
                    ds.clear();
                    showTable();  // Cập nhật bảng
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa tất cả đồ uống thất bại!");
                }
            }
        } else if (e.getSource() == btnBoLoc) {
            // Bỏ lọc (Hiển thị lại tất cả đồ uống)
            ds = drink_DAO.getAllDrinks();
            showTable();  // Cập nhật bảng
        } else if (e.getSource() == btnSearch) {
            // Tìm kiếm theo tên đồ uống
            String searchTerm = txtSearch.getText().trim().toLowerCase();
            if (!searchTerm.isEmpty()) {
                ds = drink_DAO.searchDrinks(searchTerm);
                showTable();  // Cập nhật bảng
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = tableDrink.getSelectedRow();
        if (selectedRow != -1) {
            txtMa.setText(modelDrink.getValueAt(selectedRow, 0).toString());
            txtTen.setText(modelDrink.getValueAt(selectedRow, 1).toString());
            txtGia.setText(modelDrink.getValueAt(selectedRow, 2).toString());
            txtVAT.setText(modelDrink.getValueAt(selectedRow, 3).toString());
            txtMoTa.setText(modelDrink.getValueAt(selectedRow, 4).toString());
            txtImage.setText(modelDrink.getValueAt(selectedRow, 5).toString());
            txtCategory.setText(modelDrink.getValueAt(selectedRow, 6).toString());
        }
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

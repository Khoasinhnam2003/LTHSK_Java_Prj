package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.Ban_DAO;
import entity.Ban;
import java.sql.SQLException;
import java.util.List;

public class Table_GUI extends JPanel implements ActionListener, MouseListener {
    private static final long serialVersionUID = 1L;
    private Color background = new Color(254, 169, 107);

    private JTextField txtMaBan, txtSoCho, txtKhuVuc, txtTrangThai, txtSearchMaBan;
    private DefaultTableModel modelTable;
    private JTable tableTable;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang, btnXoaHet, btnSearch;

    private Ban_DAO banDAO;
    private List<Ban> dsBan;

    public Table_GUI() throws SQLException {
        setLayout(new BorderLayout());

        banDAO = new Ban_DAO();
        dsBan = banDAO.getAllBans();

        // Tiêu đề
        JLabel lblTieuDe = new JLabel("QUẢN LÝ BÀN");
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

        // Nhập mã bàn
        JLabel lblMaBan = new JLabel("Mã bàn:");
        b1.add(lblMaBan);
        b1.add(txtMaBan = new JTextField(15));
        txtMaBan.setEnabled(false);
        txtMaBan.setText("B"+banDAO.getNewId(dsBan));

        // Nhập số chỗ
        JLabel lblSoCho = new JLabel("Số chỗ:");
        b2.add(lblSoCho);
        b2.add(txtSoCho = new JTextField(15));

        // Nhập khu vực
        JLabel lblKhuVuc = new JLabel("Khu vực:");
        b3.add(lblKhuVuc);
        b3.add(txtKhuVuc = new JTextField(15));

        // Nhập trạng thái
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        b4.add(lblTrangThai);
        b4.add(txtTrangThai = new JTextField(15));

        // Tìm kiếm theo mã bàn

        // Thêm các nút chức năng
        btnThem = new JButton("Thêm");
        btnThem.setBackground(Color.green);
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnXoaTrang = new JButton("Làm mới");
        btnXoaHet = new JButton("Xóa tất cả");
        btnSearch = new JButton("Tìm kiếm theo mã");
        txtSearchMaBan = new JTextField(10);
        JPanel panelButton = new JPanel();
        panelButton.add(btnThem);
        panelButton.add(btnSua);
        panelButton.add(btnXoa);
        panelButton.add(btnXoaTrang);
        panelButton.add(btnXoaHet);
        panelButton.add(btnSearch);
        panelButton.add(txtSearchMaBan);
        // Bảng hiển thị dữ liệu
        String[] colHeader = { "Mã bàn", "Số chỗ", "Khu vực", "Trạng thái" };
        modelTable = new DefaultTableModel(colHeader, 0);
        tableTable = new JTable(modelTable);
        JScrollPane scrollTable = new JScrollPane(tableTable);

        // Thêm các thành phần vào panel
        b.add(jpTieuDe);
        b.add(b1);
        b.add(b2);
        b.add(b3);
        b.add(b4);
        b.add(panelButton);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(b, BorderLayout.NORTH);
        panelCenter.add(scrollTable, BorderLayout.CENTER);

        add(panelCenter, BorderLayout.CENTER);

        // Hiển thị dữ liệu trong bảng
        loadTableData();

        // Thêm sự kiện cho các nút
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnXoaTrang.addActionListener(this);
        btnXoaHet.addActionListener(this);
        btnSearch.addActionListener(this);
        tableTable.addMouseListener(this);
    }

    private void loadTableData() {
        modelTable.setRowCount(0);
        for (Ban ban : dsBan) {
            modelTable.addRow(new Object[] { 
                ban.getMaBan(),
                ban.getSoCho(),
                ban.getKhuVuc(),
                ban.getTrangThai() 
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem) {
            addNewTable();
        } else if (e.getSource() == btnSua) {
            updateTable();
        } else if (e.getSource() == btnXoa) {
            deleteTable();
        } else if (e.getSource() == btnXoaTrang) {
            clearInputFields();
            loadTableData();
        } else if (e.getSource() == btnSearch) {
            searchTable();
        }
    }

    private void addNewTable() {
        String maBan = txtMaBan.getText();
        String soCho = txtSoCho.getText();
        String khuVuc = txtKhuVuc.getText();
        String trangThai = txtTrangThai.getText();

        if (maBan.isEmpty() || soCho.isEmpty() || khuVuc.isEmpty() || trangThai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            int soChoInt = Integer.parseInt(soCho);
            Ban newBan = new Ban(maBan, soChoInt, khuVuc, trangThai);
            if (banDAO.addBan(newBan)) {
                JOptionPane.showMessageDialog(this, "Thêm bàn thành công!");
                dsBan = banDAO.getAllBans();
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm bàn thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số chỗ phải là số!");
        }
    }

    private void updateTable() {
        int selectedRow = tableTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn cần sửa!");
            return;
        }

        String maBan = txtMaBan.getText();
        String soCho = txtSoCho.getText();
        String khuVuc = txtKhuVuc.getText();
        String trangThai = txtTrangThai.getText();

        try {
            int soChoInt = Integer.parseInt(soCho);
            Ban updatedBan = new Ban(maBan, soChoInt, khuVuc, trangThai);
            if (banDAO.updateBan(updatedBan)) {
                JOptionPane.showMessageDialog(this, "Cập nhật bàn thành công!");
                dsBan = banDAO.getAllBans();
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật bàn thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số chỗ phải là số!");
        }
    }

    private void deleteTable() {
        int selectedRow = tableTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn cần xóa!");
            return;
        }
        String maBan = modelTable.getValueAt(selectedRow, 0).toString();
        if (banDAO.deleteBan(maBan)) {
            JOptionPane.showMessageDialog(this, "Xóa bàn thành công!");
            dsBan = banDAO.getAllBans();
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa bàn thất bại!");
        }
    }

    private void clearInputFields() {
        txtMaBan.setText("");
        txtSoCho.setText("");
        txtKhuVuc.setText("");
        txtTrangThai.setText("");
    }

    private void searchTable() {
        String searchMaBan = txtSearchMaBan.getText();
        if (searchMaBan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã bàn cần tìm!");
            return;
        }

        Ban ban = banDAO.getBanById(searchMaBan);
        if (ban != null) {
            dsBan = List.of(ban);  // Update list with only the found table
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy bàn với mã bàn: " + searchMaBan);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = tableTable.getSelectedRow();
        txtMaBan.setText(modelTable.getValueAt(selectedRow, 0).toString());
        txtSoCho.setText(modelTable.getValueAt(selectedRow, 1).toString());
        txtKhuVuc.setText(modelTable.getValueAt(selectedRow, 2).toString());
        txtTrangThai.setText(modelTable.getValueAt(selectedRow, 3).toString());
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

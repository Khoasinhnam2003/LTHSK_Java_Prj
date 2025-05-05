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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import connectDB.ConnectDB;
import dao.Employee_DAO;
import dao.Ingredient_DAO;
import entity.Employee;
import entity.Role;

public class Employee_GUI extends JPanel implements ActionListener, MouseListener {
    private static final long serialVersionUID = 1L;
    private Color background = new Color(254, 169, 107);
    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JComboBox<String> cboRole;
    private JDateChooser ngaySinh;
    private DefaultTableModel modelEmployee;
    private JTable tableEmployee;
    private JComboBox<String> cmbMa;
    private JComboBox<String> cmbTen;
    private JButton btnTimTheoMa;
    private JButton btnTimTheoTen;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnXoaHet;
    private JButton btnXoaTrang;
    private JButton btnBoLoc;
    private Employee_DAO employee_DAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ArrayList<Employee> employeeList;
    private static final int MIN_EMPLOYEE_AGE = 16;
   
    public Employee_GUI() {
    	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	setLayout(new BorderLayout()); // Đặt layout cho Employee_GUI là BorderLayout
        try {
        	ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
        employee_DAO=new Employee_DAO();
        employeeList=Employee_DAO.getAllEmployees();
        
     // Tiêu đề
	    JLabel lblTieuDe = new JLabel("THÔNG TIN NHÂN VIÊN");
	    lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));
	    lblTieuDe.setForeground(background);
	    JPanel jpTieuDe=new JPanel();
	    jpTieuDe.add(lblTieuDe);
	    
	    // Form nhập liệu
	    Box b = Box.createVerticalBox();
	    Box b1 = Box.createHorizontalBox();
	    Box b2 = Box.createHorizontalBox();
	    Box b3 = Box.createHorizontalBox();
	    Box b4 = Box.createHorizontalBox();
	    Box b5 = Box.createHorizontalBox();
	    Box b6 = Box.createHorizontalBox();
	    Box b7 = Box.createHorizontalBox();
	  
	    b.add(jpTieuDe);
        b.add(b1);
        b.add(b2);
        b.add(b4);
        b.add(Box.createRigidArea(new Dimension(0, 10)));
        b.add(b5);
        b.add(b6);
        b.add(Box.createRigidArea(new Dimension(0, 10)));
        b.add(b7);
        b.add(Box.createRigidArea(new Dimension(0, 30)));
        b.add(b3);
        b.add(Box.createRigidArea(new Dimension(0, 30)));
        
     // Employee ID
        JLabel lblMa = new JLabel("Mã Nhân Viên: ");
        b1.add(Box.createRigidArea(new Dimension(200, 0)));
        b1.add(lblMa);
        b1.add(Box.createRigidArea(new Dimension(40, 0)));
        b1.add(txtMa = new JTextField());
        txtMa.setEnabled(false);
        txtMa.setText("NV" + employee_DAO.getNewId(employeeList));
        b1.add(Box.createRigidArea(new Dimension(200, 0)));
        b1.setBorder(new EmptyBorder(10, 10, 0, 10));
        
     // Employee Name
        JLabel lblTen = new JLabel("Tên Nhân Viên:");
        b2.add(Box.createRigidArea(new Dimension(200, 0)));
        b2.add(lblTen);
        b2.add(Box.createRigidArea(new Dimension(40, 0)));
        b2.add(txtTen = new JTextField());
        b2.add(Box.createRigidArea(new Dimension(200, 0)));
        b2.setBorder(new EmptyBorder(10, 10, 0, 10));
        
     // Birth Date
        b7.add(Box.createRigidArea(new Dimension(200, 0)));
        JLabel lblngaySinh = new JLabel("Ngày Sinh :");
        lblngaySinh.setPreferredSize(new Dimension(100, 25));
        b7.add(Box.createHorizontalStrut(20));
        b7.add(lblngaySinh);
        ngaySinh = new JDateChooser();  // Create it directly
        ngaySinh.setDateFormatString("yyyy-MM-dd");
        ngaySinh.setPreferredSize(new Dimension(200, 25));
        b7.add(ngaySinh);
        b7.add(Box.createHorizontalStrut(20));
        b7.add(Box.createRigidArea(new Dimension(200, 0)));

     // Email
        JLabel lblEmail = new JLabel("Email :");
        b4.add(Box.createRigidArea(new Dimension(200, 0)));
        b4.add(lblEmail);
        b4.add(Box.createRigidArea(new Dimension(40, 0)));
        b4.add(txtEmail = new JTextField());
        b4.add(Box.createRigidArea(new Dimension(200, 0)));
        b4.setBorder(new EmptyBorder(10, 10, 0, 10));
        
     // Role
        b5.add(Box.createRigidArea(new Dimension(200, 0)));
        b5.add(new JLabel("Chức vụ:"));
        b5.add(Box.createRigidArea(new Dimension(40, 0)));
        cboRole = new JComboBox<>(new String[] { "ADMIN", "STAFF" });
        b5.add(cboRole);
        b5.add(Box.createRigidArea(new Dimension(200, 0)));
        
     // Password
        JLabel lblMatKhau = new JLabel("Mật Khẩu :");
        b6.add(Box.createRigidArea(new Dimension(200, 0)));
        b6.add(lblMatKhau);
        b6.add(Box.createRigidArea(new Dimension(40, 0)));
        b6.add(txtPassword = new JTextField());
        b6.add(Box.createRigidArea(new Dimension(200, 0)));
        b6.setBorder(new EmptyBorder(10, 10, 0, 10));
        
        b3.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        b3.add(Box.createRigidArea(new Dimension(100, 0)));
        b3.add(new JLabel("Nhập mã Nhân viên: "));
        b3.add(Box.createRigidArea(new Dimension(5, 0)));  // Thêm khoảng cách ngang nhỏ
        cmbMa = new JComboBox<>();
        for (Employee employee : employeeList) {
			cmbMa.addItem(employee.getEmployeeId());
		}
        cmbMa.setPreferredSize(new Dimension(15, 25));  // Đặt kích thước ComboBox
        b3.add(cmbMa);
        b3.add(Box.createRigidArea(new Dimension(5, 0)));
        btnTimTheoMa = new JButton("Tìm");
        b3.add(btnTimTheoMa);

        b3.add(Box.createRigidArea(new Dimension(40, 0)));  // Thêm khoảng cách lớn giữa các nhóm
        b3.add(new JLabel("Nhập tên Nhân Viên : "));
        b3.add(Box.createRigidArea(new Dimension(5, 0)));
        cmbTen = new JComboBox<>();
        cmbTen.setEditable(true);
        cmbTen.setPreferredSize(new Dimension(15, 25));
        for (Employee employee : employeeList) {
			cmbMa.addItem(employee.getEmployeeName());
		}
        b3.add(cmbTen);
        b3.add(Box.createRigidArea(new Dimension(5, 0)));
        btnTimTheoTen = new JButton("Tìm");
        b3.add(btnTimTheoTen);

        b3.add(Box.createRigidArea(new Dimension(40, 0)));
        btnThem = new JButton("Thêm");
        b3.add(btnThem);
        btnThem.setBackground(Color.green);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnSua = new JButton("Cập Nhật");
        btnSua.setEnabled(false);
        b3.add(btnSua);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnXoa = new JButton("Xóa");
        btnXoa.setEnabled(false);
        b3.add(btnXoa);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnXoaHet = new JButton("Xóa tất cả trong bảng");
        b3.add(btnXoaHet);
        btnXoaHet.setBackground(Color.red);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnXoaTrang = new JButton("Làm mới");
        b3.add(btnXoaTrang);
        b3.add(Box.createRigidArea(new Dimension(20, 0)));
        btnBoLoc = new JButton("Bỏ Lọc");
        b3.add(btnBoLoc);
        b3.add(Box.createRigidArea(new Dimension(100, 0)));
        
        // Tạo panel chứa form và tiêu đề, đặt layout là BorderLayout
        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(b, BorderLayout.NORTH); // Thêm form và tiêu đề vào vùng NORTH

        // Tạo bảng và thêm vào vùng CENTER
        String[] colHeader = { "Chọn", "Mã Nhân Viên", "Tên Nhân Viên", "Ngày Sinh", "Email", "Chức vụ", "Mật khẩu" };
        modelEmployee = new DefaultTableModel(colHeader, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;  // Only checkbox column is editable
            }
        };
        tableEmployee = new JTable(modelEmployee);
        panelCenter.add(new JScrollPane(tableEmployee), BorderLayout.CENTER);
        showTable();
        
        // Thêm panelCenter vào vùng CENTER của Category_GUI
        panelCenter.setBorder(new EmptyBorder(10, 10, 0, 10));
        add(panelCenter, BorderLayout.CENTER);
        
        //action
        btnThem.addActionListener(this);
        btnTimTheoMa.addActionListener(this);
        btnBoLoc.addActionListener(this);
        btnTimTheoTen.addActionListener(this);
        tableEmployee.addMouseListener(this);
        btnXoaTrang.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnXoaHet.addActionListener(this);
    } 
    
    private JDateChooser initializeDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setPreferredSize(new Dimension(200, 25));
        return dateChooser;
    }
    
    public ArrayList<String> getSelectedEmployeeCodes() {
        ArrayList<String> selectedCodes = new ArrayList<>();
        for (int i = 0; i < tableEmployee.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tableEmployee.getValueAt(i, tableEmployee.getColumnCount() - 1);
            if (Boolean.TRUE.equals(isSelected)) { // Null-safe comparison
                String code = (String) tableEmployee.getValueAt(i, 0);
                selectedCodes.add(code);
            }
        }
        return selectedCodes;
    }   
    public ArrayList<Integer> getCheckedRows() {
        ArrayList<Integer> checkedRows = new ArrayList<>();
        for (int i = 0; i < modelEmployee.getRowCount(); i++) {
            Boolean isChecked = (Boolean) modelEmployee.getValueAt(i, 0);
            if (isChecked != null && isChecked) {
                checkedRows.add(i);
            }
        }
        return checkedRows;
    }
    
    public void deleteTable() {
		modelEmployee.getDataVector().removeAllElements();
	}	     
    private void clearFields() {
    	employeeList = Employee_DAO.getAllEmployees();
        txtMa.setText("");
        txtTen.setText("");
        txtEmail.setText("");
        cboRole.setSelectedIndex(0);
        txtPassword.setText("");
        txtMa.setText("NV" + employee_DAO.getNewId(employeeList)); // Reset ID
        txtMa.requestFocus();
    }
    private void loadEmployeeData() throws SQLException {
        try {
            deleteTable();
            ArrayList<Employee> employees = employee_DAO.getAllEmployees();
            for (Employee employee : employees) {
                modelEmployee.addRow(employee.toRow());
            }
        } catch (Exception e) {
            throw new SQLException("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }
    private void showTable() {
        try {
            deleteTable();
            employeeList = Employee_DAO.getAllEmployees();
            for (Employee employee : employeeList) {
                Object[] row = new Object[7];
                row[0] = Boolean.FALSE;  // Make sure this is Boolean.FALSE, not a String
                row[1] = employee.getEmployeeId();
                row[2] = employee.getEmployeeName();
                
                // Format ngày sinh
                if (employee.getDob() != null) {
                    row[3] = new SimpleDateFormat("yyyy-MM-dd").format(employee.getDob());
                } else {
                    row[3] = "";
                }
                
                row[4] = employee.getEmail();
                row[5] = employee.getRole().toString();
                row[6] = employee.getPassword();
                modelEmployee.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi hiển thị dữ liệu: " + e.getMessage());
        }
    }
    
    private Employee createEmployeeFromFields() throws ParseException {
        String employeeId = txtMa.getText().trim();
        String employeeName = txtTen.getText().trim();
        java.util.Date dob = ngaySinh.getDate(); 
        String email = txtEmail.getText().trim();
        Role role = cboRole.getSelectedItem().toString().equals("ADMIN") ? Role.ADMIN : Role.STAFF;
        String password = txtPassword.getText().trim();
		return new Employee(employeeId, employeeName, dob, email, role, password);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == btnThem) {
            if(validateData()) {
                String id = txtMa.getText();
                String name = txtTen.getText();
                Date dob = ngaySinh.getDate(); // Giả sử bạn dùng JDateChooser\
                java.sql.Date dobb=new java.sql.Date(dob.getTime());
                String email = txtEmail.getText();
                Role role = cboRole.getSelectedItem().toString().equalsIgnoreCase("Admin") ? Role.ADMIN : Role.STAFF;
                String password = txtPassword.getText();
                Employee employee = new Employee(id, name, dobb, email, role, password);
                if(!employeeList.contains(employee)) {
                    if(employee_DAO.addEmployee(employee)) {
                        modelEmployee.addRow(employee.toRow());
                        cmbMa.removeAllItems();
                        cmbTen.removeAllItems();
                        employeeList = Employee_DAO.getAllEmployees();
                        clearFields();
                        for(Employee emp : employeeList) {
                            cmbMa.addItem(emp.getEmployeeId());
                            cmbTen.addItem(emp.getEmployeeName());
                        }
                        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!");
                }
            }
        } else if(o == btnTimTheoMa) {
            deleteTable();
            showTable();
            Employee employee = Employee_DAO.findEmployeeById(cmbMa.getSelectedItem().toString());
            if(employee != null) {
                tableEmployee.setRowSelectionInterval(employee_DAO.getIndex(employee), 
                                                   employee_DAO.getIndex(employee));
            }
        } else if(o == cmbTen || o == btnTimTheoTen) {
            if(!(cmbTen.getSelectedItem() == null)) {
                if(!cmbTen.getSelectedItem().toString().trim().equals("")) {
                    ArrayList<Employee> list = employee_DAO.findEmployeesByName(
                        cmbTen.getSelectedItem().toString());
                    if(list.size() != 0) {
                        deleteTable();
                        for(Employee employee : list) {
                            modelEmployee.addRow(employee.toRow());
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                        deleteTable();
                        showTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc nhập tên nhân viên cần tìm!");
                    cmbTen.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên nào!");
            }
        } else if(o == btnBoLoc) {
            deleteTable();
            showTable();
        } else if(o == btnXoaTrang) {
            clearFields();
        } else if(o == btnSua) {
            if(validateData()) {
                if(JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn cập nhật thông tin cho nhân viên có mã " 
                    + txtMa.getText()) == JOptionPane.OK_OPTION) {
                    String id = txtMa.getText();
                    String name = txtTen.getText();
                    Date dob = ngaySinh.getDate();
                    String email = txtEmail.getText();
                    Role role = cboRole.getSelectedItem().toString().equalsIgnoreCase("Admin") ? 
                               Role.ADMIN : Role.STAFF;
                    String password = txtPassword.getText();
                    Employee employee = new Employee(id, name, dob, email, role, password);
                    try {
                        if(employee_DAO.updateEmployee(employee)) {
                            deleteTable();
                            showTable();
                            clearFields();
                            cmbMa.removeAllItems();
                            cmbTen.removeAllItems();
                            employeeList = Employee_DAO.getAllEmployees();
                            for(Employee emp : employeeList) {
                                cmbMa.addItem(emp.getEmployeeId());
                                cmbTen.addItem(emp.getEmployeeName());
                            }
                            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } else if(o == btnXoa) {
            if(getCheckedRows().size() != 0) {
                if(JOptionPane.showConfirmDialog(this, "Bạn có chắc xóa nhân viên?") 
                   == JOptionPane.OK_OPTION) {
                	employeeList = employee_DAO.deleteEmployees(getCheckedRows());
                    deleteTable();
                    showTable();
                    clearFields();
                    cmbMa.removeAllItems();
                    cmbTen.removeAllItems();
                    employeeList = Employee_DAO.getAllEmployees();
                    for(Employee emp : employeeList) {
                        cmbMa.addItem(emp.getEmployeeId());
                        cmbTen.addItem(emp.getEmployeeName());
                    }
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên muốn xóa!");
            }
        }
    }
    private void searchEmployee() {
        String employeeId = btnTimTheoMa.getText().trim();
        if (employeeId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên cần tìm!");
            return;
        }
        Employee employee = employee_DAO.findEmployeeById(employeeId);
        if (employee != null) {
        	deleteTable();
            modelEmployee.addRow(employee.toRow());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên với mã " + employeeId);
        }
    }
    private void addEmployee() throws ParseException {
        if (!validateData()) return;
        Employee employee = createEmployeeFromFields();
        if (employee_DAO.addEmployee(employee)) {
            modelEmployee.addRow(employee.toRow());
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại! Mã nhân viên đã tồn tại.");
        }
    }
    private void deleteEmployee() {
        int selectedRow = tableEmployee.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhân viên này?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            ArrayList<Integer> indicesToDelete = new ArrayList<>();
            indicesToDelete.add(selectedRow);
            employee_DAO.deleteEmployees(indicesToDelete);
            modelEmployee.removeRow(selectedRow);
            clearFields();
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
        }
    }
    private void handleSearchByName() {
        String name = cmbTen.getSelectedItem().toString();
        ArrayList<Employee> results = employee_DAO.findEmployeesByName(name);
        if (!results.isEmpty()) {
            deleteTable();
            for (Employee employee : results) {
				modelEmployee.addRow(employee.toRow());
			}
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
        }
    }
    
    private void updateEmployee() throws ParseException, SQLException {
        if (!validateData()) return;

        Employee employee = createEmployeeFromFields();
        if (employee_DAO.updateEmployee(employee)) {
            loadEmployeeData();
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thành công!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thất bại!");
        }
    }
    private boolean validateData() {
        String name = txtTen.getText().trim();
        Date dob = ngaySinh.getDate();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        // Kiểm tra tên không chứa ký tự đặc biệt
        if(!name.matches("^[a-zA-Z\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Tên không được chứa ký tự đặc biệt!");
            txtTen.requestFocus();
            return false;
        }
     // Kiểm tra tuổi >= 16
        if(dob != null) {
            Calendar birthCal = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            birthCal.setTime(dob);
            
            // Tính tuổi chính xác hơn bằng cách so sánh năm, tháng và ngày
            int age = today.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
            
            // Kiểm tra tháng và ngày để xác định chính xác hơn
            if (today.get(Calendar.MONTH) < birthCal.get(Calendar.MONTH) || 
                (today.get(Calendar.MONTH) == birthCal.get(Calendar.MONTH) && 
                 today.get(Calendar.DAY_OF_MONTH) < birthCal.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }
            
            if(age < 16) {
                JOptionPane.showMessageDialog(this, "Nhân viên phải từ 16 tuổi trở lên!");
                ngaySinh.requestFocus();
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!");
            ngaySinh.requestFocus();
            return false;
        }
        
        // Kiểm tra email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if(!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
            txtEmail.requestFocus();
            return false;
        }
        // Kiểm tra mật khẩu 4 chữ số
        if(!password.matches("^\\d{4}$")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải là 4 chữ số!");
            txtPassword.requestFocus();
            return false;
        }
        return true;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tableEmployee.getSelectedRow();
        if (row >= 0) {
            txtMa.setText(tableEmployee.getValueAt(row, 1).toString());
            txtTen.setText(tableEmployee.getValueAt(row, 2).toString());
            
            // Improved date handling
            try {
                String dateStr = tableEmployee.getValueAt(row, 3).toString();
                if (dateStr != null && !dateStr.trim().isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    Date parsedDate = dateFormat.parse(dateStr);
                    ngaySinh.setDate(parsedDate);
                } else {
                    ngaySinh.setDate(null);
                }
            } catch (ParseException ex) {
                System.err.println("Error parsing date: " + ex.getMessage());
                ngaySinh.setDate(null);
                JOptionPane.showMessageDialog(this, 
                    "Invalid date format in selected row. Please select a valid date.",
                    "Date Error",
                    JOptionPane.WARNING_MESSAGE);
            }
            
            txtEmail.setText(tableEmployee.getValueAt(row, 4).toString());
            cboRole.setSelectedItem(tableEmployee.getValueAt(row, 5).toString());
            txtPassword.setText(tableEmployee.getValueAt(row, 6).toString());
            btnSua.setEnabled(true);
            btnXoa.setEnabled(true);
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
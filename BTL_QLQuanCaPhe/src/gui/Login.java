package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.Employee_DAO;
import entity.Employee;
import entity.UserSession;

public class Login extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    private Color background = new Color(254, 169, 107);
    private JTextField txtMa;
    private JPasswordField txtMK;
    private JButton btnDangNhap;

    public Login() {
        setSize(600, 300);
        setTitle("Tiệm cà phê của tui");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main container (BorderLayout)
        JPanel jpMain = new JPanel(new BorderLayout());

        // Left side (Logo)
        JPanel jpLeft = new JPanel();
        ImageIcon logo = new ImageIcon("images/logo.jpg");
        jpLeft.add(new JLabel(logo));
        jpLeft.setBackground(background);
        jpMain.add(jpLeft, BorderLayout.WEST);

        // Right side (Form area) 
        JPanel jpRight = new JPanel();
        GroupLayout layout = new GroupLayout(jpRight);
        jpRight.setLayout(layout);

        // Create components
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblMa = new JLabel("Mã nhân viên:");
        lblMa.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMa = new JTextField(20);
        txtMa.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblMK = new JLabel("Mật khẩu:      ");
        lblMK.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMK = new JPasswordField(20);
        txtMK.setFont(new Font("Arial", Font.PLAIN, 16));

        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setFont(new Font("Arial", Font.BOLD, 16));
        btnDangNhap.setBackground(new Color(0, 122, 255));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setPreferredSize(new Dimension(150, 40));

        // Define GroupLayout
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGap(50)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(lblTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMa)
                        .addComponent(txtMa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMK)
                        .addComponent(txtMK, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDangNhap)
                )
                .addGap(50)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(30)
                .addComponent(lblTitle)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMa)
                    .addComponent(txtMa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMK)
                    .addComponent(txtMK, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(20)
                .addComponent(btnDangNhap)
                .addGap(30)
        );

        // Add right panel to the main container
        jpMain.add(jpRight, BorderLayout.CENTER);

        // Add main panel to JFrame
        add(jpMain);
        
        //action
        btnDangNhap.addActionListener(this);
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
    
    public void login(Employee employee) throws SQLException {
        ArrayList<Employee> list = Employee_DAO.getAllEmployees();
        for (Employee employee2 : list) {
            if (employee.getEmployeeId().equalsIgnoreCase(employee2.getEmployeeId())) {
                if (employee.getPassword().equalsIgnoreCase(employee2.getPassword())) {
                    UserSession.setCurrentEmployee(employee2);
                    this.dispose();
                    new Index().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Mật khẩu sai!");
                    txtMK.selectAll();
                    txtMK.requestFocus();
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại!");
        txtMa.selectAll();
        txtMa.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDangNhap) {
            String ma = txtMa.getText();
            @SuppressWarnings("deprecation")
            String mk = txtMK.getText();
            Employee employee = new Employee(ma, mk);
            try {
                login(employee);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
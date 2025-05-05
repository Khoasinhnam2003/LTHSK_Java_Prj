package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import entity.Employee;
import entity.UserSession;

public class Index extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnDangXuat;
	private JButton btnDatMon;
	private JButton btnConNguoi;
	private JButton btnNguyenLieu;
	private JButton btnLoaiSanPham;
	private JButton btnSanPham;
	private JButton btnHoaDon;
	private Color background=new Color(254,169,107);
	private JButton btnKhuyenMai;
	private JPanel centerPanel;
	private CardLayout cardLayout;
	private JButton btnKhachHang;
	private JButton btnBan;
	public Index() throws SQLException {
		//Frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setLocationRelativeTo(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width,screenSize.height-40);
		setTitle("Cà Phê TIEMCUATUI");
		 
		//north
		Box b;
		b=Box.createVerticalBox();
		JPanel jpNorth=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		b.setBackground(background);
		b.add(jpNorth);
		Employee employee=UserSession.getCurrentEmployee();
		jpNorth.add(new JLabel("Xin chào "+employee.getEmployeeName()));
		jpNorth.add(btnDangXuat=new JButton("Đăng xuất"));
		jpNorth.setBackground(background);
		
		ImageIcon logo=new ImageIcon("images/logo.jpg");
		JPanel jpLogo=new JPanel();
		jpLogo.add(new JLabel(logo));
		jpLogo.setBackground(background);
		b.add(jpLogo);
		
		add(b,BorderLayout.NORTH);
		
		//west
		JPanel jpWest=new JPanel(new GridLayout(9,1));
		jpWest.add(btnDatMon=new JButton("Đặt món"));
		btnDatMon.setBackground(background);
		jpWest.add(btnHoaDon=new JButton("Quản lý hóa đơn"));
		jpWest.add(btnBan=new JButton("Quản lý bàn"));
		jpWest.add(btnSanPham=new JButton("Quản lý sản phẩm"));
		jpWest.add(btnLoaiSanPham=new JButton("Quản lý loại sản phẩm"));
		jpWest.add(btnNguyenLieu=new JButton("Quản lý nguyên liệu"));
		jpWest.add(btnKhachHang=new JButton("Quản lý khách hàng"));
		jpWest.add(btnConNguoi=new JButton("Quản lý nhân viên"));
		jpWest.add(btnKhuyenMai=new JButton("Quản lý khuyến mãi"));
		add(jpWest,BorderLayout.WEST);
		
		//center
		cardLayout=new CardLayout();
		centerPanel=new JPanel(cardLayout);
		centerPanel.add(new Order_GUI(),"order");
		centerPanel.add(new Category_GUI(),"category");
		centerPanel.add(new Ingredient_GUI(),"ingredient");
		centerPanel.add(new Customer_GUI(),"customer");
		centerPanel.add(new Employee_GUI(),"employee");
		centerPanel.add(new Orders_GUI(),"orders");
		centerPanel.add(new Drink_GUI(),"product");
		centerPanel.add(new Promotion_GUI(),"promotion");
		centerPanel.add(new Table_GUI(),"table");
		
		add(centerPanel);
		
		
		
		//action
		btnLoaiSanPham.addActionListener(this);
		btnNguyenLieu.addActionListener(this);
		btnDatMon.addActionListener(this);
		btnConNguoi.addActionListener(this);
		btnDangXuat.addActionListener(this);
		btnHoaDon.addActionListener(this);
		btnKhachHang.addActionListener(this);
		btnKhuyenMai.addActionListener(this);
		btnSanPham.addActionListener(this);
		btnBan.addActionListener(this);
	}
//	public static void main(String[] args) {
//		new Index().setVisible(true);
//	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		if(o==btnLoaiSanPham) { 
			//set color on press button
			btnDatMon.setBackground(UIManager.getColor("Button.background"));
			btnHoaDon.setBackground(UIManager.getColor("Button.background"));
			btnBan.setBackground(UIManager.getColor("Button.background"));
			btnSanPham.setBackground(UIManager.getColor("Button.background"));
			btnNguyenLieu.setBackground(UIManager.getColor("Button.background"));
			btnKhachHang.setBackground(UIManager.getColor("Button.background"));
			btnConNguoi.setBackground(UIManager.getColor("Button.background"));
			btnKhuyenMai.setBackground(UIManager.getColor("Button.background"));
			btnLoaiSanPham.setBackground(background);
			
			
			cardLayout.show(centerPanel, "category");
		}else if(o==btnNguyenLieu) {
			btnDatMon.setBackground(UIManager.getColor("Button.background"));
			btnHoaDon.setBackground(UIManager.getColor("Button.background"));
			btnBan.setBackground(UIManager.getColor("Button.background"));
			btnSanPham.setBackground(UIManager.getColor("Button.background"));
			btnNguyenLieu.setBackground(background);
			btnKhachHang.setBackground(UIManager.getColor("Button.background"));
			btnConNguoi.setBackground(UIManager.getColor("Button.background"));
			btnKhuyenMai.setBackground(UIManager.getColor("Button.background"));
			btnLoaiSanPham.setBackground(UIManager.getColor("Button.background"));
			
			cardLayout.show(centerPanel, "ingredient");
		}else if(o==btnDatMon) {
			btnDatMon.setBackground(background);
			btnHoaDon.setBackground(UIManager.getColor("Button.background"));
			btnSanPham.setBackground(UIManager.getColor("Button.background"));
			btnBan.setBackground(UIManager.getColor("Button.background"));
			btnNguyenLieu.setBackground(UIManager.getColor("Button.background"));
			btnKhachHang.setBackground(UIManager.getColor("Button.background"));
			btnConNguoi.setBackground(UIManager.getColor("Button.background"));
			btnKhuyenMai.setBackground(UIManager.getColor("Button.background"));
			btnLoaiSanPham.setBackground(UIManager.getColor("Button.background"));
			
			cardLayout.show(centerPanel, "order");
		}else if(o==btnConNguoi) {
			btnDatMon.setBackground(UIManager.getColor("Button.background"));
			btnHoaDon.setBackground(UIManager.getColor("Button.background"));
			btnBan.setBackground(UIManager.getColor("Button.background"));
			btnSanPham.setBackground(UIManager.getColor("Button.background"));
			btnNguyenLieu.setBackground(UIManager.getColor("Button.background"));
			btnKhachHang.setBackground(UIManager.getColor("Button.background"));
			btnConNguoi.setBackground(background);
			btnKhuyenMai.setBackground(UIManager.getColor("Button.background"));
			btnLoaiSanPham.setBackground(UIManager.getColor("Button.background"));
			cardLayout.show(centerPanel, "employee");
		}
		else if(o==btnDangXuat) {
			if(JOptionPane.showConfirmDialog(this, "Bạn có chắc thoát ứng dụng?")==JOptionPane.OK_OPTION) {
				System.exit(0);
				new Login().setVisible(true);
				
			}
			
		}else if(o==btnHoaDon) {
			btnDatMon.setBackground(UIManager.getColor("Button.background"));
			btnHoaDon.setBackground(background);
			btnBan.setBackground(UIManager.getColor("Button.background"));
			btnSanPham.setBackground(UIManager.getColor("Button.background"));
			btnNguyenLieu.setBackground(UIManager.getColor("Button.background"));
			btnKhachHang.setBackground(UIManager.getColor("Button.background"));
			btnConNguoi.setBackground(UIManager.getColor("Button.background"));
			btnKhuyenMai.setBackground(UIManager.getColor("Button.background"));
			btnLoaiSanPham.setBackground(UIManager.getColor("Button.background"));
			
			cardLayout.show(centerPanel, "orders");
			
		}else if(o==btnKhachHang) {
			btnDatMon.setBackground(UIManager.getColor("Button.background"));
			btnHoaDon.setBackground(UIManager.getColor("Button.background"));
			btnBan.setBackground(UIManager.getColor("Button.background"));
			btnSanPham.setBackground(UIManager.getColor("Button.background"));
			btnNguyenLieu.setBackground(UIManager.getColor("Button.background"));
			btnKhachHang.setBackground(background);
			btnConNguoi.setBackground(UIManager.getColor("Button.background"));
			btnKhuyenMai.setBackground(UIManager.getColor("Button.background"));
			btnLoaiSanPham.setBackground(UIManager.getColor("Button.background"));
			
			cardLayout.show(centerPanel, "customer");
		}else if(o==btnKhuyenMai) {
			btnDatMon.setBackground(UIManager.getColor("Button.background"));
			btnHoaDon.setBackground(UIManager.getColor("Button.background"));
			btnBan.setBackground(UIManager.getColor("Button.background"));
			btnSanPham.setBackground(UIManager.getColor("Button.background"));
			btnNguyenLieu.setBackground(UIManager.getColor("Button.background"));
			btnKhachHang.setBackground(UIManager.getColor("Button.background"));
			btnConNguoi.setBackground(UIManager.getColor("Button.background"));
			btnKhuyenMai.setBackground(background);
			btnLoaiSanPham.setBackground(UIManager.getColor("Button.background"));
			
			cardLayout.show(centerPanel, "promotion");
		}else if(o==btnSanPham) {
			btnDatMon.setBackground(UIManager.getColor("Button.background"));
			btnHoaDon.setBackground(UIManager.getColor("Button.background"));
			btnBan.setBackground(UIManager.getColor("Button.background"));
			btnSanPham.setBackground(background);
			btnNguyenLieu.setBackground(UIManager.getColor("Button.background"));
			btnKhachHang.setBackground(UIManager.getColor("Button.background"));
			btnConNguoi.setBackground(UIManager.getColor("Button.background"));
			btnKhuyenMai.setBackground(UIManager.getColor("Button.background"));
			btnLoaiSanPham.setBackground(UIManager.getColor("Button.background"));
			
			cardLayout.show(centerPanel, "product");
		}
		else if(o==btnBan) {
			btnDatMon.setBackground(UIManager.getColor("Button.background"));
			btnHoaDon.setBackground(UIManager.getColor("Button.background"));
			btnBan.setBackground(background);
			btnSanPham.setBackground(UIManager.getColor("Button.background"));
			btnNguyenLieu.setBackground(UIManager.getColor("Button.background"));
			btnKhachHang.setBackground(UIManager.getColor("Button.background"));
			btnConNguoi.setBackground(UIManager.getColor("Button.background"));
			btnKhuyenMai.setBackground(UIManager.getColor("Button.background"));
			btnLoaiSanPham.setBackground(UIManager.getColor("Button.background"));
			
			cardLayout.show(centerPanel, "table");
		}
	}
}

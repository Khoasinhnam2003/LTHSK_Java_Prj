package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import dao.Ban_DAO;
import dao.Customer_DAO;
import dao.Drink_DAO;
import dao.Employee_DAO;
import dao.InvoiceDetail_DAO;
import dao.Invoice_DAO;
import dao.Promotion_DAO;
import entity.Ban;
import entity.Drink;
import entity.Invoice;
import entity.InvoiceDetail;
import entity.Promotion;
import entity.UserSession;

public class Order_GUI extends JPanel implements ActionListener,MouseListener{
	private DefaultTableModel tableModelMenu, tableModelCTHD, tableModelBan;
    private JTable tableMenu, tableCTHD, tableBan;
    private JButton btnThemSP, btnCong, btnTru, btnXoaSP, btnLuuDonDB, btnLamMoi, btnTimKH;
	private JPanel pTongTien;
    private JLabel lbMaMon,lbTenMon, lbDonGia, lbSoLuong, lbThanhTien, lbTongTien;
    private JTextField txtMaSP, txtTenSP, txtDonGia, txtSoLuong, txtThanhTien;
	private JTextField txtTongTien;
	private JTextField txtThue;
	private JTextField  txtBan;
    private List<Invoice> dshd;
	private JTextField txtKM;
	private JTextField txtTenNV;
	private JComboBox<String> comboKM;
	private ArrayList<Promotion> dsKM;
	private JTextField txtGG;
	private double GG;
	private static final long serialVersionUID = 1L;
	private Promotion_DAO km_DAO = new Promotion_DAO();
	public Order_GUI() {
		setLayout(new BorderLayout());
		setBackground(new Color(254,169,107));
	// Tạo panel bên trái chứa danh sách món nước
	JPanel leftPanel = new JPanel(new BorderLayout());
	leftPanel.setBackground(new Color(254,169,107));

	//tdOD
	JPanel khungOrder = new JPanel(new BorderLayout());
	khungOrder.setBackground(new Color(254,169,107));

	JLabel tdOD = new JLabel("Order here");
	tdOD.setForeground(Color.black);
	tdOD.setFont(new Font("Segoe UI", Font.BOLD, 18));
	tdOD.setHorizontalAlignment(SwingConstants.CENTER);
	khungOrder.add(tdOD, BorderLayout.NORTH);

	// Panel chứa bảng danh sách các món nước
	JPanel panelTables = new JPanel(new GridLayout(1, 2));
	// Tạo table model và table để hiển thị danh sách món nước
	tableModelMenu = new DefaultTableModel();
	tableModelMenu.addColumn("Mã món");
	tableModelMenu.addColumn("Tên món");
	tableModelMenu.addColumn("Đơn giá");
	tableMenu = new JTable(tableModelMenu);
	JTableHeader tableHeader = tableMenu.getTableHeader();
	tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));  // Tăng font chữ lên 18px
	tableHeader.setForeground(Color.black);
	tableHeader.setBackground(new Color(254,169,107));  // Màu nền cho header
	JScrollPane scrollPaneL = new JScrollPane(tableMenu);
	panelTables.add(scrollPaneL);

	// Panel chứa nút lập hóa đơn
	JPanel panelButton = new JPanel();
	panelButton.setBackground(new Color(254,169,107));

	btnThemSP = new JButton("Thêm sản phẩm");
	btnThemSP.setForeground(Color.black);
	btnThemSP.setIcon(new ImageIcon("images/add.png"));
	btnThemSP.setBackground(new Color(254,169,107));
	btnThemSP.setFont(new Font("Segoe UI", Font.BOLD, 18));
	panelButton.add(btnThemSP);

	btnLamMoi = new JButton("Làm mới");
	btnLamMoi.setForeground(Color.black);
	btnLamMoi.setIcon(new ImageIcon("images/refresh.png"));
	btnLamMoi.setBackground(new Color(254,169,107));
	btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 18));
	panelButton.add(btnLamMoi);

	// Thêm panel danh sách món nước và panel nút lập hóa đơn vào leftPanel
	khungOrder.add(panelTables, BorderLayout.CENTER);
	leftPanel.add(khungOrder, BorderLayout.NORTH);
	leftPanel.add(panelButton, BorderLayout.SOUTH);


	//vùng chính giữa nhập liệu
	lbMaMon = new JLabel("Mã SP: ");
	lbTenMon = new JLabel("Tên SP: ");
	lbDonGia = new JLabel("Đơn Giá: ");
	lbSoLuong = new JLabel("Số lượng: ");
	lbThanhTien = new JLabel("Thành tiền: ");
	txtMaSP = new JTextField();
	txtTenSP = new JTextField();
	txtDonGia = new JTextField();
	txtSoLuong = new JTextField();
	txtThanhTien = new JTextField();


	// Thiết lập thuộc tính cho các JTextField
	txtMaSP.setEditable(false);
	txtTenSP.setEditable(false);
	txtDonGia.setEditable(false);
	txtSoLuong.setEditable(false);
	txtThanhTien.setEditable(false);

	txtMaSP.setPreferredSize(new Dimension(100, txtMaSP.getPreferredSize().height));
	// Tạo một Box chứa các JLabel và JTextField ở cùng một hàng
	Box boxMaMon = Box.createHorizontalBox();
	boxMaMon.setBackground(new Color(254,169,107));
	boxMaMon.add(lbMaMon);
	boxMaMon.add(Box.createHorizontalStrut(10)); // Khoảng cách giữa label và text field
	boxMaMon.add(txtMaSP);

	Box boxTenMon = Box.createHorizontalBox();
	boxTenMon.setBackground(new Color(254,169,107));
	boxTenMon.add(lbTenMon);
	boxTenMon.add(Box.createHorizontalStrut(10));
	boxTenMon.add(txtTenSP);

	Box boxDonGia = Box.createHorizontalBox();
	boxDonGia.add(lbDonGia);
	boxDonGia.add(Box.createHorizontalStrut(10));
	boxDonGia.add(txtDonGia);

	Box boxSoLuong = Box.createHorizontalBox();
	boxSoLuong.add(lbSoLuong);
	boxSoLuong.add(Box.createHorizontalStrut(10));
	boxSoLuong.add(btnTru = new JButton("-"));
	btnTru.setBackground(Color.GRAY);
	btnTru.setForeground(Color.black);
	boxSoLuong.add(Box.createHorizontalStrut(20));
	boxSoLuong.add(txtSoLuong);
	boxSoLuong.add(Box.createHorizontalStrut(20));
	boxSoLuong.add(btnCong = new JButton("+"));
	btnCong.setBackground(Color.GRAY);
	btnCong.setForeground(Color.black);

	Box boxThanhTien = Box.createHorizontalBox();
	boxThanhTien.add(lbThanhTien);
	boxThanhTien.add(Box.createHorizontalStrut(10));
	boxThanhTien.add(txtThanhTien);

	lbMaMon.setPreferredSize(lbThanhTien.getPreferredSize());
	lbTenMon.setPreferredSize(lbThanhTien.getPreferredSize());
	lbDonGia.setPreferredSize(lbThanhTien.getPreferredSize());
	lbSoLuong.setPreferredSize(lbThanhTien.getPreferredSize());
	//Tạo một Box chứa các Box hàng
	Box boxCenterMon = Box.createVerticalBox();
	boxCenterMon.setBackground(new Color(254,169,107));

	boxCenterMon.add(Box.createVerticalStrut(10)); // Khoảng cách giữa các thành phần
	boxCenterMon.add(boxMaMon);
	boxCenterMon.add(Box.createVerticalStrut(10)); // Khoảng cách giữa các hàng
	boxCenterMon.add(boxTenMon);
	boxCenterMon.add(Box.createVerticalStrut(10));
	boxCenterMon.add(boxDonGia);
	boxCenterMon.add(Box.createVerticalStrut(10));
	boxCenterMon.add(boxSoLuong);
	boxCenterMon.add(Box.createVerticalStrut(10));
	boxCenterMon.add(boxThanhTien);
	boxCenterMon.add(Box.createVerticalStrut(10));
	//panel.add(boxCenterMon);
	boxCenterMon.setSize(500,500);
	leftPanel.add(boxCenterMon, BorderLayout.CENTER);
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	panel.setBackground(new Color(254,169,107));
	leftPanel.add(panel, BorderLayout.WEST);
	leftPanel.add(panel, BorderLayout.EAST);

	// Tạo một DefaultTableModel cho JTable hiển thị danh sách các bàn
	tableModelBan = new DefaultTableModel();
	tableModelBan.addColumn("Mã bàn");
	tableModelBan.addColumn("Số chỗ");
	tableModelBan.addColumn("Khu vực"); 
	tableModelBan.addColumn("Trạng thái"); 

	// Tạo JTable với DefaultTableModel đã tạo
	tableBan = new JTable(tableModelBan);

	// Tăng font chữ lên 18px // Màu nền cho header
	JTableHeader tableHeader1 = tableBan.getTableHeader();
	tableHeader1.setFont(new Font("Segoe UI", Font.BOLD, 13));  
	tableHeader1.setForeground(Color.black);
	tableHeader1.setBackground(new Color(254,169,107));

	// Tạo JScrollPane để cho phép cuộn nếu danh sách bàn quá dài   
	JScrollPane scrollPaneBan = new JScrollPane(tableBan);

	// Load danh sách bàn từ cơ sở dữ liệu vào JTable

	tableBan.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	panelTables.add(scrollPaneBan, BorderLayout.EAST);


	// phần bên phải/////////////////////////////////////////////////////////////////////////////////////////////////
	JPanel rightPanel = new JPanel(new BorderLayout());
	rightPanel.setBackground(new Color(254,169,107));
	//tạo khung chi tiết hóa đơn chứa các món được thêm vào phần north
	JPanel khungCTHD = new JPanel(new BorderLayout());
	khungCTHD.setBackground(new Color(254,169,107));
	JLabel tdCTHD = new JLabel("Chi tiết đơn đặt bàn");
	tdCTHD.setForeground(Color.black);
	tdCTHD.setFont(new Font("Segoe UI", Font.BOLD, 18));
	tdCTHD.setHorizontalAlignment(SwingConstants.CENTER);
	khungCTHD.add(tdCTHD, BorderLayout.NORTH);

	tableModelCTHD = new DefaultTableModel();

	tableModelCTHD.addColumn("Mã sản phẩm");
	tableModelCTHD.addColumn("Tên sản phẩm");
	tableModelCTHD.addColumn("Đơn Giá");
	tableModelCTHD.addColumn("Số lượng");
	tableModelCTHD.addColumn("Thành Tiền");
	tableCTHD = new JTable(tableModelCTHD);
	// Tăng font chữ lên 18px // Màu nền cho header
	JTableHeader tableHeader2 = tableCTHD.getTableHeader();
	tableHeader2.setFont(new Font("Segoe UI", Font.BOLD, 13));  
	tableHeader2.setForeground(Color.black);
	tableHeader2.setBackground(new Color(254,169,107));
	JScrollPane scrollPaneCTHD = new JScrollPane(tableCTHD);
	khungCTHD.add(scrollPaneCTHD, BorderLayout.CENTER); 

	rightPanel.add(khungCTHD, BorderLayout.NORTH);


	//vùng cen bên phải//////////////////////////////////////////
	JPanel lapHD = new JPanel();
	lapHD.setLayout(new BoxLayout(lapHD, BoxLayout.Y_AXIS));
	lapHD.setBackground(new Color(254,169,107));
		/////hàng 1 chứa thông tin hóa đơn//////////////////////
	    JPanel cthd = new JPanel(new GridLayout(2, 3));
	    cthd.setBackground(new Color(254,169,107));
	    //bàn
	    JPanel pBan = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pBan.setBackground(new Color(254,169,107));
	    JLabel lbMaBan = new JLabel("Mã bàn:");
	    txtBan = new JTextField();
	    txtBan.setEditable(false);
	    txtBan.setPreferredSize(new Dimension(150, 25));

	    pBan.add(lbMaBan);
	    pBan.add(txtBan);

	    cthd.add(pBan);
	    // Nhân viên 
	    JPanel pNV = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pNV.setBackground(new Color(254,169,107));
	    JLabel lbTenNV = new JLabel(" Tên nhân viên: ");
	    txtTenNV = new JTextField();
	    txtTenNV.setText(UserSession.getCurrentEmployee().getEmployeeName());
	    txtTenNV.setPreferredSize(new Dimension(150,25));
	    pNV.add(lbTenNV);
	    txtTenNV.setEditable(false);
	    pNV.add(txtTenNV);
	    cthd.add(pNV);
	    //Khuyến mãi đơn đặt bàn
	    JPanel pKM = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pKM.setBackground(new Color(254,169,107));
	    JLabel lbKM = new JLabel("Khuyến mãi: ");
	    comboKM = new JComboBox<>();
	    comboKM.setVisible(true);
	    Promotion_DAO  km_dao = new Promotion_DAO();
	    dsKM = km_dao.getAllPromotion();
	    for(Promotion k : dsKM) {
	    	comboKM.addItem(k.getPromotionName());
	    }
	    comboKM.setPreferredSize(new Dimension(150,30));
	    pKM.add(lbKM);
	    pKM.add(comboKM);
	    cthd.add(pKM);
	    
	    //VAT
	    JPanel pVAT = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pVAT.setBackground(new Color(254,169,107));
	    JLabel lbVAT = new JLabel("Thuế VAT: ");
	    txtThue = new JTextField();
	    txtThue.setPreferredSize(new Dimension(80, 25));
	    txtThue.setText("0.1"); // Đặt giá trị mặc định là 0.1
	    txtThue.setEditable(false);
	    pVAT.add(lbVAT);
	    pVAT.add(txtThue);
	    cthd.add(pVAT);
	    // Giảm giá
	    String tenKM = ((String)comboKM.getSelectedItem()).trim();
	     GG = km_dao.getDiscountByPromotionName(tenKM);
	    String str =String.valueOf(GG)+"%";
	    JPanel pGG = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pGG.setBackground(new Color(254,169,107));
	    JLabel lbGG = new JLabel("Giảm giá: ");
	    txtGG = new JTextField();
	    txtGG.setText(str);
	    txtGG.setPreferredSize(new Dimension(80,25));
	    txtGG.setEditable(false);
	    pGG.add(lbGG);
	    pGG.add(txtGG);
	    cthd.add(pGG);
	 // Đặt ActionListener cho ComboBox
	    comboKM.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Lấy tên khuyến mãi từ ComboBox và tìm giảm giá tương ứng
	            String tenKM = ((String) comboKM.getSelectedItem()).trim();
	             GG = km_dao.getDiscountByPromotionName(tenKM);
	            
	            // Cập nhật giá trị giảm giá vào JTextField
	            String str = String.valueOf(GG)+"%";
	            txtGG.setText(str);  // Cập nhật giảm giá mới vào JTextField
	            capNhatTongTien();
	        }
	    });
	    //tong tiền
	    pTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pTongTien.setBackground(new Color(254,169,107));
	    lbTongTien = new JLabel("Tổng tiền(Gồm VAT+Khuyến mãi): ");
	    txtTongTien = new JTextField();
	    txtTongTien.setEditable(false);
	    txtTongTien.setPreferredSize(new Dimension(150, 25));
	    pTongTien.add(lbTongTien);
	    pTongTien.add(txtTongTien);
	    cthd.add(pTongTien);

	lapHD.add(cthd);
	  

	btnTimKH = new JButton("Tìm");
	btnTimKH.setForeground(Color.black);
	btnTimKH.setBackground(new Color(254,169,107));
	btnTimKH.setFont(new Font("Segoe UI", Font.BOLD, 13));
	
	rightPanel.add(lapHD, BorderLayout.CENTER);
	
	JPanel pCapNhat = new JPanel();
	pCapNhat.setBackground(new Color(76, 63, 84));
	
	btnLuuDonDB = new JButton("Lưu đơn đặt bàn");
	btnLuuDonDB.setForeground(Color.black);
	btnLuuDonDB.setIcon(new ImageIcon("images/add.png"));
	btnLuuDonDB.setBackground(new Color(254,169,107));
	btnLuuDonDB.setFont(new Font("Segoe UI", Font.BOLD, 18));
	btnLuuDonDB.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	// Khởi tạo DAO cho Hóa đơn và Chi tiết hóa đơn
	        Invoice_DAO hoadon_dao = new Invoice_DAO();
	        dshd = hoadon_dao.getAllInvoices();
	        InvoiceDetail_DAO cthd_dao = new InvoiceDetail_DAO();
	        Employee_DAO nv_DAO = new Employee_DAO();
	        Promotion_DAO km_DAO = new Promotion_DAO();
	        Customer_DAO kh_DAO = new Customer_DAO();
	        String maHD = "HD"+hoadon_dao.getNewId(dshd);
	        LocalDate now = LocalDate.now();
	        String tenNV = txtTenNV.getText().trim();
	        String maBan = txtBan.getText().trim();
	        String maNV = nv_DAO.getEmployeeIdByName(tenNV);    
	        String maKM = km_DAO.getPromotionIdByName(tenKM);
	        String maKH = kh_DAO.getRandomCustomerId();
	        // Kiểm tra xem mã bàn có hợp lệ không
	        if (maBan.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã bàn!");
	            return;
	        }
	        // Kiểm tra nếu bảng chi tiết hóa đơn không có dữ liệu
	        if (tableModelCTHD.getRowCount() == 0) {
	            JOptionPane.showMessageDialog(null, "Vui lòng thêm sản phẩm vào chi tiết đơn đặt bàn trước khi lưu vào hóa đơn!");
	            return;
	        }
	        // Tạo đối tượng Hóa đơn và thêm vào CSDL
	        Invoice hd = new Invoice(maHD, now, maBan, tenNV,maKH,maNV,maKM);
	        hoadon_dao.addInvoice(hd);

	        // Thêm chi tiết hóa đơn vào CSDL
	        for (int i = 0; i < tableModelCTHD.getRowCount(); i++) {
	            String maMon = tableModelCTHD.getValueAt(i, 0).toString();
	            String tenMon = tableModelCTHD.getValueAt(i, 1).toString();
	            
	            try {
	                double donGiaStr = Double.parseDouble(tableModelCTHD.getValueAt(i, 2).toString());
	                int soLuong = Integer.parseInt(tableModelCTHD.getValueAt(i, 3).toString());
	                double thanhTien = Double.parseDouble(tableModelCTHD.getValueAt(i, 4).toString());
	                double tongTien = Double.parseDouble(txtTongTien.getText());
	                // Tạo chi tiết hóa đơn và thêm vào CSDL
	                InvoiceDetail ctdb = new InvoiceDetail( maHD,maMon, tenMon, soLuong, donGiaStr, thanhTien, tongTien);
	                cthd_dao.addInvoiceDetail(ctdb);
	            	} catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ ở dòng " + (i + 1));
	                return;
	            }
	        }
	       
	        // Hiển thị thông báo thành công
	        JOptionPane.showMessageDialog(null, "Đã lưu hóa đơn thành công!");
	    }
	});	  
	btnXoaSP = new JButton("Xóa sản phẩm");
	btnXoaSP.setForeground(Color.black);
	btnXoaSP.setIcon(new ImageIcon("images/trash.png"));
	btnXoaSP.setBackground(new Color(254,169,107));
	btnXoaSP.setFont(new Font("Segoe UI", Font.BOLD, 18));
	pCapNhat.add(btnLuuDonDB);
	pCapNhat.add(btnXoaSP);



	rightPanel.add(pCapNhat, BorderLayout.SOUTH);
	// Tạo JSplitPane để chia layout
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
	splitPane.setDividerLocation(600);
	splitPane.setBackground(new Color(254,169,107));
	splitPane.setUI(new javax.swing.plaf.basic.BasicSplitPaneUI());
	splitPane.setEnabled(false);

	// Đặt JSplitPane vào frame
	add(splitPane);
	loadDrinkToTable();
	loadBanToTable();
	
	tableMenu.addMouseListener(this);
	tableCTHD.addMouseListener(this);
	tableBan.addMouseListener(this);
	btnCong.addActionListener(this);//
	btnTru.addActionListener(this);//

	btnThemSP.addActionListener(this);
	btnXoaSP.addActionListener(this);
	btnLuuDonDB.addActionListener(this);
	btnLamMoi.addActionListener(this);//
	btnTimKH.addActionListener(this);
	btnLuuDonDB.addActionListener(this);
	comboKM.addActionListener(this);

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == tableMenu) {
			   
	        int row = tableMenu.getSelectedRow();
	        if (row != -1) { 
	            String maMon = tableModelMenu.getValueAt(row, 0).toString();
	            String tenMon = tableModelMenu.getValueAt(row, 1).toString();
	            String donGiaStr = tableModelMenu.getValueAt(row, 2).toString();                
	            txtMaSP.setText(maMon);
	            txtTenSP.setText(tenMon);
	            txtDonGia.setText(donGiaStr);
	            txtSoLuong.setText("1");                
	            try {
	                double donGia = Double.parseDouble(donGiaStr);
	                int soLuong = Integer.parseInt(txtSoLuong.getText());
	                double thanhTien = donGia * soLuong;
	                txtThanhTien.setText(String.valueOf(thanhTien));
	            } catch (NumberFormatException ex) {
	              
	                txtThanhTien.setText("0.0");
	            }
	        }
	    } else if (e.getSource() == tableCTHD ) {
	        int selectedRow = tableCTHD.getSelectedRow();
	        if (selectedRow != -1) { 
	            txtMaSP.setText(tableModelCTHD.getValueAt(selectedRow, 0).toString());
	            txtTenSP.setText(tableModelCTHD.getValueAt(selectedRow, 1).toString());
	            txtDonGia.setText(tableModelCTHD.getValueAt(selectedRow, 2).toString());
	            txtSoLuong.setText(tableModelCTHD.getValueAt(selectedRow, 3).toString());
	            txtThanhTien.setText(tableModelCTHD.getValueAt(selectedRow, 4).toString());
	        }
	    }else if (e.getSource() == tableBan ) {

	        int selectedRow = tableBan.getSelectedRow();
	        if (selectedRow != -1) {
	                String maBan = tableModelBan.getValueAt(selectedRow, 0).toString();
	                txtBan.setText(maBan);
	            }
	        }
	    
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	    // TODO Auto-generated method stub
	    Object o = e.getSource();

	    // Tăng số lượng
	    if (o.equals(btnCong)) {
	        try {
	            int soLuong = Integer.parseInt(txtSoLuong.getText());
	            soLuong++;
	            txtSoLuong.setText(String.valueOf(soLuong));
	            double donGia = Double.parseDouble(txtDonGia.getText());
	            double thanhTien = donGia * soLuong;
	            txtThanhTien.setText(String.valueOf(thanhTien));
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(null, "Lỗi: Vui lòng nhập số lượng là số nguyên.");
	        }
	    } 
	    // Giảm số lượng
	    else if (o.equals(btnTru)) {
	        try {
	            int soLuong = Integer.parseInt(txtSoLuong.getText());
	            if (soLuong > 0) {
	                soLuong--;
	                txtSoLuong.setText(String.valueOf(soLuong));

	                double donGia = Double.parseDouble(txtDonGia.getText());
	                double thanhTien = donGia * soLuong;
	                txtThanhTien.setText(String.valueOf(thanhTien));
	            }
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(null, "Lỗi: Vui lòng nhập số lượng là số nguyên.");
	        }
	    } 
	    // Làm mới các trường nhập liệu
	    else if (o.equals(btnLamMoi)) {
	        clearTextFields();
	        tableModelMenu.setRowCount(0);
	        loadDrinkToTable();
	    } 
	    // Xóa sản phẩm khỏi danh sách
	    else if (o.equals(btnXoaSP)) {
	        // Cần logic để xóa sản phẩm trong bảng
	    	int selectedRow = tableCTHD.getSelectedRow();
	        if (selectedRow != -1) {
	            // Xóa dòng được chọn trong table model
	            tableModelCTHD.removeRow(selectedRow);
	            
	            // Cập nhật lại tổng tiền sau khi xóa sản phẩm
	            capNhatTongTien();
	        } else {
	            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần xóa.");
	        }

	    } 
	    // Thêm sản phẩm vào bảng chi tiết hóa đơn
	    else if (o.equals(btnThemSP)) {
	        String maMon = txtMaSP.getText();
	        String tenMon = txtTenSP.getText();
	        String donGiaStr = txtDonGia.getText();
	        String soLuongStr = txtSoLuong.getText();
	        String thanhTienStr = txtThanhTien.getText();

	        if (maMon.isEmpty() || tenMon.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty() || thanhTienStr.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Vui lòng nhập đủ thông tin.");
	            return;
	        }

	        // Parse các giá trị số từ các chuỗi
	        double donGia = Double.parseDouble(donGiaStr);
	        int soLuong = Integer.parseInt(soLuongStr);
	        double thanhTien = Double.parseDouble(thanhTienStr);

	        boolean found = false;

	        for (int i = 0; i < tableModelCTHD.getRowCount(); i++) {
	            String existingMaMon = tableModelCTHD.getValueAt(i, 0).toString();
	            if (existingMaMon.equals(maMon)) {
	                // Nếu tìm thấy sản phẩm đã tồn tại, cộng dồn số lượng và tính lại thành tiền
	                int existingSoLuong = Integer.parseInt(tableModelCTHD.getValueAt(i, 3).toString());
	                int newSoLuong = existingSoLuong + soLuong;
	                double newThanhTien = donGia * newSoLuong;

	                tableModelCTHD.setValueAt(newSoLuong, i, 3);    // Cập nhật số lượng
	                tableModelCTHD.setValueAt(newThanhTien, i, 4);  // Cập nhật thành tiền

	                found = true;
	                break;
	            }
	        }

	        if (!found) {
	            // Nếu không tìm thấy, thêm sản phẩm mới vào bảng
	            Object[] rowData = {maMon, tenMon, donGia, soLuong, thanhTien};
	            tableModelCTHD.addRow(rowData);
	        }

	        tableMenu.clearSelection();
	        clearTextFields();
	    }

	

	    // Cập nhật tổng tiền
	    capNhatTongTien();
	}

	// Hàm tính tổng tiền của hóa đơn
	private double tinhTongTien() {
	    double tongTien = 0.0;
	    for (int i = 0; i < tableModelCTHD.getRowCount(); i++) {
	        double thanhTien = Double.parseDouble(tableModelCTHD.getValueAt(i, 4).toString());
	        tongTien += thanhTien;
	    }
	    double thueVAT = Double.parseDouble(txtThue.getText());
	    double giatriKM = tongTien*(GG/100);
	    double tongThu = (tongTien-giatriKM) + (tongTien-giatriKM) * thueVAT;
	    return tongThu;
	}

	// Cập nhật tổng tiền trên giao diện
	private void capNhatTongTien() {
	    double tongTien = tinhTongTien();
	    txtTongTien.setText(String.valueOf(tongTien));
	}

	// Hàm tải danh sách đồ uống vào bảng
	private void loadDrinkToTable() {
	    // Tạo đối tượng DAO để truy xuất dữ liệu
	    Drink_DAO ds = new Drink_DAO();
	    
	    // Lấy danh sách đồ uống từ cơ sở dữ liệu
	    List<entity.Drink> list = ds.getAllDrinks();
	    
	    // Duyệt qua danh sách đồ uống và thêm từng dòng vào bảng
	    for (Drink s : list) {
	        String[] rowData = {
	            s.getDrinkId(),       // Mã đồ uống
	            s.getDrinkName(),     // Tên đồ uống
	            String.valueOf(s.getPrice())  // Giá tiền, chuyển thành String
	        };
	        tableModelMenu.addRow(rowData);  // Thêm dòng vào tableModel
	    }
	    
	    // Cập nhật lại table với model mới
	    tableMenu.setModel(tableModelMenu);
	}


	// Hàm tải danh sách bàn vào bảng
	 private void loadBanToTable() {
	    	
    	 Ban_DAO b;
		try {
			b = new Ban_DAO();
			List<entity.Ban> list = b.getAllBans();
			
			for(Ban s : list){
				String [] rowData = {s.getMaBan(),s.getSoCho()+"",s.getKhuVuc(),s.getTrangThai()};
				tableModelBan.addRow(rowData);			
			}
				tableBan.setModel(tableModelBan);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			
    	
    	
    }


	// Hàm làm mới các trường nhập liệu
	private void clearTextFields() {
	    txtMaSP.setText("");
	    txtTenSP.setText("");
	    txtDonGia.setText("");
	    txtSoLuong.setText("");
	    txtThanhTien.setText("");
	}
	
//	private int ranDom2() {
//		 int num = (int) (Math.random() * 100) + 1;
//	      return num;
//	}
//	
}
	
	

	
	


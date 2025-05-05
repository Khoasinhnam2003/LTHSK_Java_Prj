package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
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
import java.util.Random;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.Promotion_DAO;
import entity.Promotion;

public class Promotion_GUI extends JPanel implements MouseListener, ActionListener {
    private static final long serialVersionUID = 1L;
    private Color background = new Color(254, 169, 107);
    private JTextField txtPromotionId;
    private JTextField txtPromotionName;
    private JTextField txtDiscount;
    private JDateChooser ngayBatDau;
    private JDateChooser ngayKetThuc;
    private DefaultTableModel modelPromotion;
    private JTable tablePromotion;
    private JComboBox<String> cmbPromotionId;
    private JButton btnSearchById;
    private JComboBox<String> cmbPromotionName;
    private JButton btnSearchByName;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnClearFilter;
    private Promotion_DAO promotion_DAO;
    private ArrayList<Promotion> promotions;
	private JTextField txtPromotionSoLuong;

    public Promotion_GUI() {
        setLayout(new BorderLayout());

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        promotion_DAO = new Promotion_DAO();
        promotions = Promotion_DAO.getAllPromotion();

        // Title
        JLabel lblTitle = new JLabel("QUẢN LÝ KHUYẾN MÃI");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(background);
        
        JPanel jpTitle = new JPanel();
        jpTitle.add(lblTitle);

        // Input form
        Box b = Box.createVerticalBox();
        Box b1 = Box.createHorizontalBox();
        Box b2 = Box.createHorizontalBox();
        Box b3 = Box.createHorizontalBox();
        Box b4 = Box.createHorizontalBox();
        Box b5 = Box.createHorizontalBox();
        Box b6 = Box.createHorizontalBox();

        b.add(Box.createVerticalStrut(10));
        b.add(jpTitle);
        b.add(Box.createVerticalStrut(20));
        b.add(b1);
        b.add(Box.createVerticalStrut(10));
        b.add(b2);
        b.add(Box.createVerticalStrut(10));
        b.add(b3);
        b.add(Box.createVerticalStrut(10));
        b.add(b4);
        b.add(Box.createVerticalStrut(10));
        b.add(b5);
        b.add(Box.createVerticalStrut(10));
        b.add(b6);
        b.add(Box.createVerticalStrut(10));

        JLabel lblPromotionId = new JLabel("Mã khuyến mãi:");
        lblPromotionId.setPreferredSize(new Dimension(100, 25));
        b1.add(Box.createHorizontalStrut(20));
        b1.add(lblPromotionId);
        b1.add(txtPromotionId = new JTextField());
        txtPromotionId.setEnabled(false);
        b1.add(Box.createHorizontalStrut(20));

        JLabel lblPromotionName = new JLabel("Tên khuyến mãi:");
        lblPromotionName.setPreferredSize(new Dimension(100, 25));
        b2.add(Box.createHorizontalStrut(20));
        b2.add(lblPromotionName);
        b2.add(txtPromotionName = new JTextField());
        b2.add(Box.createHorizontalStrut(20));
        
        JLabel lblPromotionSoLuong = new JLabel("Số lượng :");
        lblPromotionSoLuong.setPreferredSize(new Dimension(100, 25));
        b2.add(Box.createHorizontalStrut(20));
        b2.add(lblPromotionSoLuong);
        b2.add(txtPromotionSoLuong = new JTextField());
        b2.add(Box.createHorizontalStrut(20));
        

        JLabel lblDiscount = new JLabel("Giảm giá (%):");
        lblDiscount.setPreferredSize(new Dimension(100, 25));
        b3.add(Box.createHorizontalStrut(20));
        b3.add(lblDiscount);
        b3.add(txtDiscount = new JTextField());
        b3.add(Box.createHorizontalStrut(20));

        JLabel lblStartDate = new JLabel("Ngày bắt đầu:");
        lblStartDate.setPreferredSize(new Dimension(100, 25));
        b4.add(Box.createHorizontalStrut(20));
        b4.add(lblStartDate);
        ngayBatDau = new JDateChooser();
        ngayBatDau.setDateFormatString("yyyy-MM-dd");
        b4.add(ngayBatDau);
        b4.add(Box.createHorizontalStrut(20));

        JLabel lblEndDate = new JLabel("Ngày kết thúc:");
        lblEndDate.setPreferredSize(new Dimension(100, 25));
        b5.add(Box.createHorizontalStrut(20));
        b5.add(lblEndDate);
        ngayKetThuc = new JDateChooser();
        ngayKetThuc.setDateFormatString("yyyy-MM-dd");
        b5.add(ngayKetThuc);
        b5.add(Box.createHorizontalStrut(20));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Search controls
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Tìm theo mã:"));
        cmbPromotionId = new JComboBox<>();
        for (Promotion promotion : promotions) {
            cmbPromotionId.addItem(promotion.getPromotionId());
        }
        searchPanel.add(cmbPromotionId);
        btnSearchById = new JButton("Tìm");
        searchPanel.add(btnSearchById);

        searchPanel.add(new JLabel("Tìm theo tên:"));
        cmbPromotionName = new JComboBox<>();
        cmbPromotionName.setEditable(true);
        ArrayList<Promotion> listPromotion = new ArrayList<Promotion>();
        ArrayList<String> dsmatrongcombobox = new ArrayList<String>();
        for (Promotion promotion : listPromotion) { 
			cmbPromotionId.addItem(promotion.getPromotionId());
			if(cmbPromotionName.getItemCount() != 0) {
				for(int i = 0; i < cmbPromotionName.getItemCount(); i++) {
				    String item = (String) cmbPromotionName.getItemAt(i);
				    dsmatrongcombobox.add(item);
				}
				for (String string : dsmatrongcombobox) {
					if(!promotion.getPromotionId().equals(string)) {
						cmbPromotionName.addItem(string);
					}
				}
			} else 
			cmbPromotionName.addItem(promotion.getPromotionName());
		}
        searchPanel.add(cmbPromotionName);
        btnSearchByName = new JButton("Tìm");
        searchPanel.add(btnSearchByName);

        // Button Panel
        buttonPanel.add(btnAdd = new JButton("Thêm"));
        buttonPanel.add(btnUpdate = new JButton("Cập nhật"));
        buttonPanel.add(btnDelete = new JButton("Xóa"));
        buttonPanel.add(btnClear = new JButton("Xóa trắng"));
        buttonPanel.add(btnClearFilter = new JButton("Xóa tìm kiếm"));

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        // Table
        String[] colHeader = {"Mã khuyến mãi", "Tên khuyến mãi", "Giảm giá (%)", "Ngày bắt đầu", "Ngày kết thúc"};
        modelPromotion = new DefaultTableModel(colHeader, 0);
        tablePromotion = new JTable(modelPromotion);
        JScrollPane scrollPane = new JScrollPane(tablePromotion);
        

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(b, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Load data
        loadData();

        // Add listeners
        tablePromotion.addMouseListener(this);
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClear.addActionListener(this);
        btnSearchById.addActionListener(this);
        btnSearchByName.addActionListener(this);
        btnClearFilter.addActionListener(this);
    }

    private void loadData() {
    	clearTable();
        promotions = Promotion_DAO.getAllPromotion();
        for (Promotion promotion : promotions) {
            Object[] row = new Object[] {
                promotion.getPromotionId(),
                promotion.getPromotionName(),
              (int)promotion.getDiscount(), // Ép kiểu sang int để bỏ phần thập phân
                promotion.getStartDate(),
                promotion.getEndDate()
            };
            modelPromotion.addRow(row);
        }
    }

    private void clearTable() {
        while (modelPromotion.getRowCount() > 0) {
            modelPromotion.removeRow(0);
        }
    }

    private void clearFields() {
    	clearTable();
    	loadData();
        txtPromotionId.setText("");
        txtPromotionName.setText("");
        txtPromotionSoLuong.setText("");
        txtDiscount.setText("");
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnAdd) {
        		handleAddPromotion();
            
        } else if (o == btnUpdate) {
            try {
				handleUpdatePromotion();
			} catch (HeadlessException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } else if (o == btnDelete) {
            handleDeletePromotion();
        } else if (o == btnClear) {
            clearFields();
        } else if (o == btnSearchById) {
            handleSearchById();
        } else if (o == btnSearchByName) {
            handleSearchByName();
        } else if (o == btnClearFilter) {
            loadData();
        }
    }
    
    private boolean validateData() {
        // Validate tên khuyến mãi
        String promotionName = txtPromotionName.getText().trim();
        if (promotionName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khuyến mãi không được để trống!");
            txtPromotionName.requestFocus();
            return false;
        }

        // Validate số lượng
        String soLuongStr = txtPromotionSoLuong.getText().trim();
        if (soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống!");
            txtPromotionSoLuong.requestFocus();
            return false;
        }
        try {
            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                txtPromotionSoLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!");
            txtPromotionSoLuong.requestFocus();
            return false;
        }

        // Validate giảm giá
        String discountStr = txtDiscount.getText().trim();
        if (discountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giảm giá không được để trống!");
            txtDiscount.requestFocus();
            return false;
        }
        try {
            int discount = Integer.parseInt(discountStr);
            if (discount <= 0 || discount > 100) {
                JOptionPane.showMessageDialog(this, "Giảm giá phải là số nguyên lớn hơn 0 và nhỏ hơn hoặc bằng 100!");
                txtDiscount.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giảm giá phải là số nguyên!");
            txtDiscount.requestFocus();
            return false;
        }

        // Validate ngày bắt đầu
        Date startDate = ngayBatDau.getDate();
        if (startDate == null) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được để trống!");
            ngayBatDau.requestFocus();
            return false;
        }
        
        // Lấy ngày hiện tại (không tính giờ phút giây)
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        
        if (startDate.before(today.getTime())) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải từ ngày hiện tại trở đi!");
            ngayBatDau.requestFocus();
            return false;
        }

        // Validate ngày kết thúc
        Date endDate = ngayKetThuc.getDate();
        if (endDate == null) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được để trống!");
            ngayKetThuc.requestFocus();
            return false;
        }
        
        if (endDate.before(startDate) || endDate.equals(startDate)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu!");
            ngayKetThuc.requestFocus();
            return false;
        }

        return true;
    }
    
    
    
    
    

    private void handleAddPromotion() {
        if (!validateData()) {
            return;
        }

    	ArrayList<String> listMa = new ArrayList<String>();
    	ArrayList<Promotion> listPromotion = new ArrayList<Promotion>();
        try {
        	for (int i = 0; i < Integer.parseInt(txtPromotionSoLuong.getText()); i++) {
        		listMa.add(generatePromotionId());
			}
        	System.out.println(listMa);
        	for (String string : listMa) {
        		String id = string;
                String name = txtPromotionName.getText().trim();
                double discount = Double.parseDouble(txtDiscount.getText().trim());
                
                java.util.Date ngayNhapD = ngayBatDau.getDate();
    			Date ngayNhapN = new Date(ngayNhapD.getTime());
    			
    			java.util.Date ngayKetThucD = ngayKetThuc.getDate();
    			Date ngayKetThucN = new Date(ngayKetThucD.getTime());

                Promotion promotion = new Promotion(id, name, discount, ngayNhapN, ngayKetThucN);
                listPromotion.add(promotion);
                
			}
        	int flag = 1;
        	
        	for (Promotion promotion2 : listPromotion) {
        		if (promotion_DAO.addPromotion(promotion2)) {
                	clearTable();	
                    loadData();
                    clearFields();
                   flag = 1;
                } else {
                    flag =0;
                }
			}
        	if(flag == 0) {
        		JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thất bại!");
        	} else {
        		 JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
        	}
        	ArrayList<String> dsmatrongcombobox = new ArrayList<String>();
            cmbPromotionId.removeAll();
            cmbPromotionName.removeAll();
            promotions = promotion_DAO.getAllPromotion();
            for (Promotion promotion : listPromotion) { 
				cmbPromotionId.addItem(promotion.getPromotionId());
				if(cmbPromotionName.getItemCount() != 0) {
					for(int i = 0; i < cmbPromotionName.getItemCount(); i++) {
					    String item = (String) cmbPromotionName.getItemAt(i);
					    dsmatrongcombobox.add(item);
					}
					for (String string : dsmatrongcombobox) {
						if(!promotion.getPromotionId().equals(string)) {
							cmbPromotionName.addItem(string);
						}
					}
				} else 
				cmbPromotionName.addItem(promotion.getPromotionName());
			}
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm khuyến mãi!");
        }
    }
    
    private String generatePromotionId() {
    	Random random = new Random();
        int randomNumber = random.nextInt(1000); // Tạo số ngẫu nhiên từ 0-999
        
        // Đảm bảo số không trùng với các ID đã tồn tại
        while (isPromotionIdExists("KM" + String.format("%03d", randomNumber))) {
            randomNumber = random.nextInt(1000);
        }
        
        return String.format("KM%03d", randomNumber);
    }
    
    private boolean isPromotionIdExists(String promotionId) {
        for (Promotion promotion : promotions) {
            if (promotion.getPromotionId().equals(promotionId)) {
                return true;
            }
        }
        return false;
    }

    
    private void handleUpdatePromotion() throws HeadlessException, SQLException {
        if (!validateData()) {
            return;
        }
        
        try {
            String id = txtPromotionId.getText();
            String name = txtPromotionName.getText().trim();
            int soLuong = Integer.parseInt(txtPromotionSoLuong.getText().trim());
            double discount = Integer.parseInt(txtDiscount.getText().trim());
            Date startDate = ngayBatDau.getDate();
            Date endDate = ngayKetThuc.getDate();

            Promotion promotion = new Promotion(id, name, soLuong, startDate, endDate);

            if (promotion_DAO.updatePromotion(promotion)) {
                loadData();
                clearFields();
                ArrayList<String> dsmatrongcombobox = new ArrayList<String>();
                ArrayList<Promotion> listPromotion = new ArrayList<Promotion>();
                cmbPromotionId.removeAll();
                cmbPromotionName.removeAll();
                promotions = promotion_DAO.getAllPromotion();
                for (Promotion promotion1 : listPromotion) { 
    				cmbPromotionId.addItem(promotion1.getPromotionId());
    				if(cmbPromotionName.getItemCount() != 0) {
    					for(int i = 0; i < cmbPromotionName.getItemCount(); i++) {
    					    String item = (String) cmbPromotionName.getItemAt(i);
    					    dsmatrongcombobox.add(item);
    					}
    					for (String string : dsmatrongcombobox) {
    						if(!promotion1.getPromotionId().equals(string)) {
    							cmbPromotionName.addItem(string);
    						}
    					}
    				} else 
    				cmbPromotionName.addItem(promotion1.getPromotionName());
    			}
                JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật khuyến mãi!");
        }
    }

    private void handleDeletePromotion() {
        if (JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa khuyến mãi này?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            
            String id = txtPromotionId.getText();
            ArrayList<Integer> indicesToDelete = new ArrayList<>();
            indicesToDelete.add(tablePromotion.getSelectedRow());
            
            if (promotion_DAO.deletePromotions(indicesToDelete) != null) {
                loadData();
                clearFields();
                ArrayList<String> dsmatrongcombobox = new ArrayList<String>();
                ArrayList<Promotion> listPromotion = new ArrayList<Promotion>();
                cmbPromotionId.removeAll();
                cmbPromotionName.removeAll();
                promotions = promotion_DAO.getAllPromotion();
                for (Promotion promotion1 : listPromotion) { 
    				cmbPromotionId.addItem(promotion1.getPromotionId());
    				if(cmbPromotionName.getItemCount() != 0) {
    					for(int i = 0; i < cmbPromotionName.getItemCount(); i++) {
    					    String item = (String) cmbPromotionName.getItemAt(i);
    					    dsmatrongcombobox.add(item);
    					}
    					for (String string : dsmatrongcombobox) {
    						if(!promotion1.getPromotionId().equals(string)) {
    							cmbPromotionName.addItem(string);
    						}
    					}
    				} else 
    				cmbPromotionName.addItem(promotion1.getPromotionName());
    			}
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thất bại!");
            }
        }
    }

    private void handleSearchById() {
        String id = cmbPromotionId.getSelectedItem().toString();
        Promotion promotion = Promotion_DAO.findPromotionById(id);
        if (promotion != null) {
            clearTable();
            modelPromotion.addRow(promotion.toRow());
        } else {
        	JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi!");
        }
    }

    private void handleSearchByName() {
        String name = cmbPromotionName.getSelectedItem().toString();
        ArrayList<Promotion> results = promotion_DAO.findPromotionsByName(name);
        if (!results.isEmpty()) {
            clearTable();
            for (Promotion promotion : results) {
                modelPromotion.addRow(promotion.toRow());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi!");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tablePromotion.getSelectedRow();
        if (row != -1) {
            txtPromotionId.setText(tablePromotion.getValueAt(row, 0).toString());
            txtPromotionName.setText(tablePromotion.getValueAt(row, 1).toString());
            txtDiscount.setText(tablePromotion.getValueAt(row, 2).toString());
            
            // Đếm số lượng khuyến mãi có cùng tên
            String selectedName = tablePromotion.getValueAt(row, 1).toString();
            int count = 0;
            for (int i = 0; i < tablePromotion.getRowCount(); i++) {
                if (tablePromotion.getValueAt(i, 1).toString().equals(selectedName)) {
                    count++;
                }
            }
            txtPromotionSoLuong.setText(String.valueOf(count));
            
            try {
                // Parse dates from table and set to JDateChooser
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = dateFormat.parse(tablePromotion.getValueAt(row, 3).toString());
                Date endDate = dateFormat.parse(tablePromotion.getValueAt(row, 4).toString());
                
                ngayBatDau.setDate(startDate);
                ngayKetThuc.setDate(endDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            btnAdd.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
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
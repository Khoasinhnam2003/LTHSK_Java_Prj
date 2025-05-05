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
import dao.Category_DAO;
import entity.Category;

public class Category_GUI extends JPanel implements MouseListener, ActionListener{
    private static final long serialVersionUID = 1L;
    private Color background = new Color(254, 169, 107);
    private JTextField txtMa;
    private JTextField txtTen;
    private DefaultTableModel modelCategory;
    private JTable tableCategory;
    private JComboBox<String> cmbMa;
    private JButton btnTimTheoMa;
    private JComboBox<String> cmbTen;
    private JButton btnTimTheoTen;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnXoaTrang;
	private JButton btnBoLoc;
	private Category_DAO category_DAO;
	private ArrayList<Category> ds;
	private JButton btnXoaHet;

    public Category_GUI() {
        setLayout(new BorderLayout()); // Đặt layout cho Category_GUI là BorderLayout

        try {
        	ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
        category_DAO=new Category_DAO();
        ds=Category_DAO.getAllCategory();
        // Tiêu đề
        JLabel lblTieuDe = new JLabel("THÔNG TIN LOẠI SẢN PHẨM");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));
        lblTieuDe.setForeground(background);
        JPanel jpTieuDe=new JPanel();
        jpTieuDe.add(lblTieuDe);
        // Form nhập liệu
        Box b = Box.createVerticalBox();
        Box b1 = Box.createHorizontalBox();
        Box b2 = Box.createHorizontalBox();
        Box b3 = Box.createHorizontalBox();

        b.add(jpTieuDe); 
        b.add(b1);
        b.add(b2);
        b.add(Box.createRigidArea(new Dimension(0, 30)));
        b.add(b3);
        b.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel lblMa = new JLabel("Mã loại sản phẩm: ");
        b1.add(Box.createRigidArea(new Dimension(200, 0)));
        b1.add(lblMa);
        b1.add(Box.createRigidArea(new Dimension(40, 0)));
        b1.add(txtMa = new JTextField());
        txtMa.setEnabled(false);
        txtMa.setText("CAT"+category_DAO.getNewId(ds));
        b1.add(Box.createRigidArea(new Dimension(200, 0)));
        b1.setBorder(new EmptyBorder(10, 10, 0, 10));

        JLabel lblTen = new JLabel("Tên loại sản phẩm:");
        b2.add(Box.createRigidArea(new Dimension(200, 0)));
        b2.add(lblTen);
        b2.add(Box.createRigidArea(new Dimension(40, 0)));
        b2.add(txtTen = new JTextField());
        b2.add(Box.createRigidArea(new Dimension(200, 0)));
        b2.setBorder(new EmptyBorder(10, 10, 0, 10));

        // Thêm các nút chức năng
        b3.setBorder(new EmptyBorder(10, 10, 10, 10));

     // Nhãn và ComboBox cho mã
        b3.add(Box.createRigidArea(new Dimension(100, 0)));
        b3.add(new JLabel("Nhập mã loại: "));
        b3.add(Box.createRigidArea(new Dimension(5, 0)));  // Thêm khoảng cách ngang nhỏ
        cmbMa = new JComboBox<>();
        for (Category category : ds) {
			cmbMa.addItem(category.getCategoryId());
		}
        cmbMa.setPreferredSize(new Dimension(15, 25));  // Đặt kích thước ComboBox
        b3.add(cmbMa);
        b3.add(Box.createRigidArea(new Dimension(5, 0)));
        btnTimTheoMa = new JButton("Tìm");
        b3.add(btnTimTheoMa);

        b3.add(Box.createRigidArea(new Dimension(40, 0)));  // Thêm khoảng cách lớn giữa các nhóm
        b3.add(new JLabel("Nhập tên loại: "));
        b3.add(Box.createRigidArea(new Dimension(5, 0)));
        cmbTen = new JComboBox<>();
        cmbTen.setEditable(true);
        cmbTen.setPreferredSize(new Dimension(15, 25));
        for (Category category : ds) {
			cmbTen.addItem(category.getCategoryName());
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
        String[] colHeader = { "Mã loại sản phẩm", "Tên loại sản phẩm","Chọn để xóa" };
        modelCategory = new DefaultTableModel(colHeader, 0){
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 2) {
                    return Boolean.class;  // Cột "Chọn tất cả" là kiểu Boolean
                }
                return String.class;
            }
        };
        tableCategory = new JTable(modelCategory);
        panelCenter.add(new JScrollPane(tableCategory), BorderLayout.CENTER);
        showTable();
        
        // Thêm panelCenter vào vùng CENTER của Category_GUI
        panelCenter.setBorder(new EmptyBorder(10, 10, 0, 10));
        add(panelCenter, BorderLayout.CENTER);
        
        //action
        btnThem.addActionListener(this);
        btnTimTheoMa.addActionListener(this);
        btnBoLoc.addActionListener(this);
        btnTimTheoTen.addActionListener(this);
        tableCategory.addMouseListener(this);
        btnXoaTrang.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnXoaHet.addActionListener(this);
    }
    private ArrayList<String> getSelectedCategoryCodes() {
        ArrayList<String> selectedCategoryCodes = new ArrayList<>();
        
        for (int i = 0; i < modelCategory.getRowCount(); i++) {
            Boolean isSelected = (Boolean) modelCategory.getValueAt(i, 2); // Kiểm tra giá trị checkbox
            if (isSelected != null && isSelected) {
                String categoryCode = (String) modelCategory.getValueAt(i, 0); // Lấy mã loại sản phẩm
                selectedCategoryCodes.add(categoryCode);
            }
        }
        
        return selectedCategoryCodes;
    }
    public ArrayList<Integer> getCheckedRows() {
        ArrayList<Integer> checkedRows = new ArrayList<>();
        for (int i = 0; i < modelCategory.getRowCount(); i++) {
            Boolean isChecked = (Boolean) modelCategory.getValueAt(i, 2);
            if (isChecked != null && isChecked) {
                checkedRows.add(i); 
            }
        }
        return checkedRows;
    }

    
    public void showTable() {
    	ds = Category_DAO.getAllCategory();
        for (Category category : ds) {
            Object[] rowData = { category.getCategoryId(), category.getCategoryName(), false };
            modelCategory.addRow(rowData);
        }
	}
    public void deleteTable() {
		modelCategory.getDataVector().removeAllElements();
	}
    public void xoaTrang() {
    	ds=Category_DAO.getAllCategory();
    	txtMa.setText("Category"+category_DAO.getNewId(ds));
    	txtTen.setText("");
    	txtTen.requestFocus();
    	btnThem.setEnabled(true);
        btnThem.setBackground(Color.green);
    	btnSua.setEnabled(false);
        btnSua.setBackground(UIManager.getColor("Button.background"));
    	btnXoa.setEnabled(false);
    	btnXoa.setBackground(UIManager.getColor("Button.background"));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		if(o==btnThem) {
			if(!txtTen.getText().equals("")) {
				Category category=new Category(txtMa.getText(), txtTen.getText());
				if(!ds.contains(category)) {
					if(category_DAO.addCategory(category)) {
						modelCategory.addRow(category.toRow());
						cmbMa.removeAllItems();
						cmbTen.removeAllItems();
						ds=Category_DAO.getAllCategory();
						xoaTrang();
						for (Category category1 : ds) {
							cmbMa.addItem(category1.getCategoryId());
							cmbTen.addItem(category1.getCategoryName());
						}

						JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
					}else {
						JOptionPane.showMessageDialog(this, "Thêm loại sản phẩm thất bại!");
					}
				}else JOptionPane.showMessageDialog(this, "Mã loại sản phẩm đã bị trùng!");
			}else {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập tên loại sản phẩm!");
				txtTen.requestFocus();
			}
		}else if(o==btnTimTheoMa) {
			deleteTable();
			showTable();
			Category category=Category_DAO.findCategoryById(cmbMa.getSelectedItem().toString());
			if(category!=null) {
				tableCategory.setRowSelectionInterval(category_DAO.getIndex(category), category_DAO.getIndex(category));
				
			}
		}else if(o==cmbTen ||o==btnTimTheoTen) {
			if(!(cmbTen.getSelectedItem()==null)) {
				if(!cmbTen.getSelectedItem().toString().trim().equals("")) {
					ArrayList<Category> list=category_DAO.findCategoriesByName(cmbTen.getSelectedItem().toString());
					if(list.size()!=0) {
						deleteTable();
						for (Category category : list) {
							modelCategory.addRow(category.toRow());
						}
					}else {
						JOptionPane.showMessageDialog(this, "Không tìm thấy loại sản phẩm!");
						deleteTable();
						showTable();
					}
				}else {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc nhập tên loại sản phẩm cần tìm!");
					cmbTen.requestFocus();
				}
			}else JOptionPane.showMessageDialog(this, "Không tìm thấy loại sản phẩm nào!");
		}else if(o==btnBoLoc) {
			deleteTable();
			showTable();
		}else if(o==btnXoaTrang) xoaTrang();
		else if(o==btnSua) {
			if(!txtTen.getText().trim().equals("")) {
				if(JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn cập nhật thông tin cho loại sản phẩm có mã "+txtMa.getText())==JOptionPane.OK_OPTION) {
					Category category=new Category(txtMa.getText(), txtTen.getText());
					try {
						if(category_DAO.updateCategory(category)){
							deleteTable();
							showTable();
							xoaTrang();
							cmbMa.removeAllItems();
							cmbTen.removeAllItems();
							ds=Category_DAO.getAllCategory();
							for (Category category1 : ds) {
								cmbMa.addItem(category1.getCategoryId());
								cmbTen.addItem(category1.getCategoryName());
							}
							JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}else {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập tên loại sản phẩm!");
				txtTen.requestFocus();
			}
		}else if(o==btnXoa) {
			if(getCheckedRows().size()!=0) {
				if(JOptionPane.showConfirmDialog(this, "Bạn có chắc xóa loại sản phẩm? ")==JOptionPane.OK_OPTION) {
					ds=category_DAO.deleteCategories(getCheckedRows());
					deleteTable();
					showTable();
					xoaTrang();
					cmbMa.removeAllItems();
					cmbTen.removeAllItems();
					ds=Category_DAO.getAllCategory();
					for (Category category1 : ds) {
						cmbMa.addItem(category1.getCategoryId());
						cmbTen.addItem(category1.getCategoryName());
					}
					JOptionPane.showMessageDialog(this, "Xóa thành công!");
				}
			}else JOptionPane.showMessageDialog(this, "Vui chọn tích vào loại sản phẩm muốn xóa!");
		}else if(o==btnXoaHet) {
			for (int i = 0; i < modelCategory.getRowCount(); i++) {
	            modelCategory.setValueAt(true, i, 2); // Đánh dấu checkbox ở cột thứ 3
	        }
			if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hết loại sản phẩm trong table?")==JOptionPane.OK_OPTION) {
				if(JOptionPane.showConfirmDialog(this, "Bạn chắc chưa???????????")==JOptionPane.OK_OPTION) {
					ArrayList<String> listCategories = getSelectedCategoryCodes();
					System.out.println(getSelectedCategoryCodes());
					System.out.println(listCategories);
					if(category_DAO.deleteCategoriesById(listCategories)) {
						deleteTable();
						showTable();
						xoaTrang();
						cmbMa.removeAllItems();
						cmbTen.removeAllItems();
						ds=Category_DAO.getAllCategory();
						for (Category category1 : ds) {
							cmbMa.addItem(category1.getCategoryId());
							cmbTen.addItem(category1.getCategoryName());
						}
						JOptionPane.showMessageDialog(this, "Xóa tất cả thành công!");
					}else JOptionPane.showMessageDialog(this, "Xóa tất cả thất bại!");
				}
			}
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int row=tableCategory.getSelectedRow();
		if(row!=-1) {
			txtMa.setText(tableCategory.getValueAt(row, 0).toString());
			txtTen.setText(tableCategory.getValueAt(row, 1).toString());
			txtTen.requestFocus();
			btnThem.setEnabled(false);
			btnThem.setBackground(UIManager.getColor("Button.background"));
			btnSua.setEnabled(true);
	        btnSua.setBackground(Color.green);
			btnXoa.setEnabled(true);
	        btnXoa.setBackground(Color.red);
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
}

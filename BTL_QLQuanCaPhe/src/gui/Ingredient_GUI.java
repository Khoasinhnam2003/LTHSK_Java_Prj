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
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.Ingredient_DAO;
import entity.Ingredient;
import entity.Unit;

public class Ingredient_GUI extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private Color background = new Color(254, 169, 107);
	private JTextField txtMa;
	private JTextField txtTen;
	private DefaultTableModel modelIngredient;
	private JTable tableIngredient;
	private JComboBox<String> cmbMa;
	private JButton btnTimTheoMa;
	private JComboBox<String> cmbTen;
	private JButton btnTimTheoTen;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnXoaTrang;
	private JButton btnBoLoc;
	private Ingredient_DAO ingredient_DAO;
	private ArrayList<Ingredient> ds;
	private JButton btnXoaHet;
	private JTextField txtSoLuong;
	private JTextField txtMoTa;
	private JDateChooser ngayNhap;
	private JDateChooser ngayHetHan;
	private JComboBox<String> cmbDonVi;
	private JTextField txtGia;

	@SuppressWarnings("deprecation")
	public Ingredient_GUI() {
		setLayout(new BorderLayout()); // Đặt layout cho Category_GUI là BorderLayout

		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ingredient_DAO = new Ingredient_DAO();
		ds = Ingredient_DAO.getAllIngredient();
		// Tiêu đề
		JLabel lblTieuDe = new JLabel("THÔNG TIN NGUYÊN LIỆU");
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
		Box b6 = Box.createHorizontalBox();
		Box b7 = Box.createHorizontalBox();
		Box b8 = Box.createHorizontalBox();
		Box b9 = Box.createHorizontalBox();

		b.add(jpTieuDe);
		b.add(b1);
		b.add(b2);
		b.add(b4);
		b.add(b5);
		b.add(b6);
		b.add(b7);
		b.add(b8);
		b.add(b9);
		b.add(Box.createRigidArea(new Dimension(0, 30)));
		b.add(b3);
		b.add(Box.createRigidArea(new Dimension(0, 30)));

		JLabel lblMa = new JLabel("Mã NL: ");
		b1.add(Box.createRigidArea(new Dimension(150, 0)));
		b1.add(lblMa);
		b1.add(Box.createRigidArea(new Dimension(40, 0)));
		b1.add(txtMa = new JTextField());
		txtMa.setEnabled(false);
		txtMa.setText("NL" + ingredient_DAO.getNewId(ds));
		b1.add(Box.createRigidArea(new Dimension(150, 0)));
		b1.setBorder(new EmptyBorder(10, 10, 0, 10));

		JLabel lblTen = new JLabel("Tên NL:");
		b2.add(Box.createRigidArea(new Dimension(150, 0)));
		b2.add(lblTen);
		b2.add(Box.createRigidArea(new Dimension(40, 0)));
		b2.add(txtTen = new JTextField());
		b2.add(Box.createRigidArea(new Dimension(150, 0)));
		b2.setBorder(new EmptyBorder(10, 10, 0, 10));

		JLabel lblSoLuong = new JLabel("Số lượng nhập:");
		b4.add(Box.createRigidArea(new Dimension(150, 0)));
		b4.add(lblSoLuong);
		b4.add(Box.createRigidArea(new Dimension(40, 0)));
		b4.add(txtSoLuong = new JTextField());
		b4.add(Box.createRigidArea(new Dimension(150, 0)));
		b4.setBorder(new EmptyBorder(10, 10, 0, 10));

		JLabel lblMoTa = new JLabel("Mô tả:");
		b5.add(Box.createRigidArea(new Dimension(150, 0)));
		b5.add(lblMoTa);
		b5.add(Box.createRigidArea(new Dimension(40, 0)));
		b5.add(txtMoTa = new JTextField());
		b5.add(Box.createRigidArea(new Dimension(150, 0)));
		b5.setBorder(new EmptyBorder(10, 10, 0, 10));

		JLabel lblNgayNhap = new JLabel("Ngày nhập:");
		b6.add(Box.createRigidArea(new Dimension(150, 0)));
		b6.add(lblNgayNhap);
		b6.add(Box.createRigidArea(new Dimension(40, 0)));
		ngayNhap = new JDateChooser();
		ngayNhap.setDateFormatString("yyyy-MM-dd");
		ngayNhap.disable();
		b6.add(ngayNhap);
		b6.add(Box.createRigidArea(new Dimension(150, 0)));
		b6.setBorder(new EmptyBorder(10, 10, 0, 10));

		JLabel lblNgayhetHan = new JLabel("Ngày hết hạn:");
		b7.add(Box.createRigidArea(new Dimension(150, 0)));
		b7.add(lblNgayhetHan);
		b7.add(Box.createRigidArea(new Dimension(40, 0)));
		ngayHetHan = new JDateChooser();
		ngayHetHan.setDateFormatString("yyyy-MM-dd");
		b7.add(ngayHetHan);
		b7.add(Box.createRigidArea(new Dimension(150, 0)));
		b7.setBorder(new EmptyBorder(10, 10, 0, 10));

		JLabel lblDonVi = new JLabel("Đơn vị tính:");
		b8.add(Box.createRigidArea(new Dimension(150, 0)));
		b8.add(lblDonVi);
		b8.add(Box.createRigidArea(new Dimension(40, 0)));
		b8.add(cmbDonVi = new JComboBox<String>());
		cmbDonVi.addItem("Gram");
		cmbDonVi.addItem("Liter");
		b8.add(Box.createRigidArea(new Dimension(150, 0)));
		b8.setBorder(new EmptyBorder(10, 10, 0, 10));

		JLabel lblPrice = new JLabel("Giá / đơn vị tính:");
		b9.add(Box.createRigidArea(new Dimension(150, 0)));
		b9.add(lblPrice);
		b9.add(Box.createRigidArea(new Dimension(40, 0)));
		b9.add(txtGia = new JTextField());
		b9.add(Box.createRigidArea(new Dimension(150, 0)));
		b9.setBorder(new EmptyBorder(10, 10, 0, 10));

		lblSoLuong.setPreferredSize(lblPrice.getPreferredSize());
		lblMa.setPreferredSize(lblPrice.getPreferredSize());
		lblMoTa.setPreferredSize(lblPrice.getPreferredSize());
		lblNgayNhap.setPreferredSize(lblPrice.getPreferredSize());
		lblDonVi.setPreferredSize(lblPrice.getPreferredSize());
		lblNgayhetHan.setPreferredSize(lblPrice.getPreferredSize());
		lblTen.setPreferredSize(lblPrice.getPreferredSize());

		// Thêm các nút chức năng
		b3.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Nhãn và ComboBox cho mã
		b3.add(Box.createRigidArea(new Dimension(100, 0)));
		b3.add(new JLabel("Mã NL: "));
		b3.add(Box.createRigidArea(new Dimension(5, 0))); // Thêm khoảng cách ngang nhỏ
		cmbMa = new JComboBox<>();
		for (Ingredient ingredient : ds) {
			cmbMa.addItem(ingredient.getIngredientId());
		}
		cmbMa.setPreferredSize(new Dimension(15, 25)); // Đặt kích thước ComboBox
		b3.add(cmbMa);
		b3.add(Box.createRigidArea(new Dimension(5, 0)));
		btnTimTheoMa = new JButton("Tìm");
		b3.add(btnTimTheoMa);

		b3.add(Box.createRigidArea(new Dimension(40, 0))); // Thêm khoảng cách lớn giữa các nhóm
		b3.add(new JLabel("Nhập tên NL: "));
		b3.add(Box.createRigidArea(new Dimension(5, 0)));
		cmbTen = new JComboBox<>();
		cmbTen.setEditable(true);
		cmbTen.setPreferredSize(new Dimension(15, 25));
		for (Ingredient ingredient : ds) {
			cmbTen.addItem(ingredient.getIngredientName());
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
		String[] colHeader = { "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng trong kho", "Mô tả", "Ngày nhập",
				"Ngày hết hạn", "Đơn vị tính", "Giá trên 1 đơn vị tính", "Chọn để xóa" };
		modelIngredient = new DefaultTableModel(colHeader, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 8) {
					return Boolean.class; // Cột "Chọn tất cả" là kiểu Boolean
				}
				return String.class;
			}
		};
		tableIngredient = new JTable(modelIngredient);
		panelCenter.add(new JScrollPane(tableIngredient), BorderLayout.CENTER);
		showTable();

		// Thêm panelCenter vào vùng CENTER của Category_GUI
		panelCenter.setBorder(new EmptyBorder(10, 10, 0, 10));
		add(panelCenter, BorderLayout.CENTER);

		// action
		btnThem.addActionListener(this);
		btnTimTheoMa.addActionListener(this);
		btnBoLoc.addActionListener(this);
		btnTimTheoTen.addActionListener(this);
		tableIngredient.addMouseListener(this);
		btnXoaTrang.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaHet.addActionListener(this);
	}

	private ArrayList<String> getSelectedCategoryCodes() {
		ArrayList<String> selectedCategoryCodes = new ArrayList<>();

		for (int i = 0; i < modelIngredient.getRowCount(); i++) {
			Boolean isSelected = (Boolean) modelIngredient.getValueAt(i, 8); // Kiểm tra giá trị checkbox
			if (isSelected != null && isSelected) {
				String categoryCode = (String) modelIngredient.getValueAt(i, 0); // Lấy mã loại sản phẩm
				selectedCategoryCodes.add(categoryCode);
			}
		}

		return selectedCategoryCodes;
	}

	public ArrayList<Integer> getCheckedRows() {
		ArrayList<Integer> checkedRows = new ArrayList<>();
		for (int i = 0; i < modelIngredient.getRowCount(); i++) {
			Boolean isChecked = (Boolean) modelIngredient.getValueAt(i, 8);
			if (isChecked != null && isChecked) {
				checkedRows.add(i);
			}
		}
		return checkedRows;
	}

	public void showTable() {
		ds = Ingredient_DAO.getAllIngredient();
		for (Ingredient ingredient : ds) {
			Object[] rowData = { ingredient.getIngredientId(), ingredient.getIngredientName(), ingredient.getQuantity(),
					ingredient.getDescription(), ingredient.getDateOfEntry(), ingredient.getExpirationDate(),
					ingredient.getUnit() == Unit.GRAM ? "Gram" : "Liter", ingredient.getPrice(), false };
			modelIngredient.addRow(rowData);
		}
	}

	public void deleteTable() {
		modelIngredient.getDataVector().removeAllElements();
	}

	public void xoaTrang() {
		ds = Ingredient_DAO.getAllIngredient();
		txtMa.setText("NL" + ingredient_DAO.getNewId(ds));
		txtTen.setText("");
		txtTen.requestFocus();
		txtSoLuong.setText("");
		txtMoTa.setText("");
		txtGia.setText("");
		cmbDonVi.setSelectedIndex(0);
		btnThem.setEnabled(true);
		btnThem.setBackground(Color.green);
		btnSua.setEnabled(false);
		btnSua.setBackground(UIManager.getColor("Button.background"));
		btnXoa.setEnabled(false);
		btnXoa.setBackground(UIManager.getColor("Button.background"));
	}

	public boolean validation() {
	    // Kiểm tra tên nguyên liệu
	    if (txtTen.getText().trim().equalsIgnoreCase("")) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nguyên liệu!");
	        txtTen.requestFocus();
	        return false;
	    }
	    
	    // Kiểm tra số lượng
	    String soLuongText = txtSoLuong.getText().trim();
	    if (soLuongText.equals("")) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!");
	        txtSoLuong.requestFocus();
	        return false;
	    } else {
	        try {
	            double soLuong = Double.parseDouble(soLuongText);
	            if (soLuong <= 0) {
	                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
	                txtSoLuong.selectAll();
	                txtSoLuong.requestFocus();
	                return false;
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Số lượng phải là một số hợp lệ!");
	            txtSoLuong.selectAll();
	            txtSoLuong.requestFocus();
	            return false;
	        }
	    }

	    // Kiểm tra mô tả nguyên liệu
	    if (txtMoTa.getText().trim().equalsIgnoreCase("")) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả cho nguyên liệu!");
	        txtMoTa.selectAll();
	        txtMoTa.requestFocus();
	        return false;
	    }

	    // Kiểm tra ngày nhập (Ngày nhập phải là ngày hôm nay)
	    java.util.Date ngayNhapD = ngayNhap.getDate();
	    if (ngayNhapD == null) {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhập!");
	        ngayNhap.requestFocus();
	        return false;
	    }
	    Date ngayNhapN = new Date(ngayNhapD.getTime());
	    if (!ngayNhapN.toString().equalsIgnoreCase(LocalDate.now().toString())) {
	    	System.out.println(ngayNhapN.toString());
	    	System.out.println(LocalDate.now().toString());
	        JOptionPane.showMessageDialog(this, "Ngày nhập phải là ngày hôm nay!");
	        ngayNhap.requestFocus();
	        return false;
	    }

	    // Kiểm tra ngày hết hạn (Ngày hết hạn phải từ ngày hôm nay trở về sau)
	    java.util.Date ngayHetHanD = ngayHetHan.getDate();
	    if (ngayHetHanD == null) {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày hết hạn!");
	        ngayHetHan.requestFocus();
	        return false;
	    }
	    Date ngayHetHanN = new Date(ngayHetHanD.getTime());
	    if (ngayHetHanN.before(Date.valueOf(LocalDate.now()))) {
	        JOptionPane.showMessageDialog(this, "Ngày hết hạn phải từ ngày hôm nay trở về sau!");
	        ngayHetHan.requestFocus();
	        return false;
	    }

	    // Kiểm tra đơn giá
	    String giaText = txtGia.getText().trim();
	    if (giaText.equals("")) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập đơn giá!");
	        txtGia.requestFocus();
	        return false;
	    } else {
	        try {
	            double gia = Double.parseDouble(giaText);
	            if (gia <= 0) {
	                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!");
	                txtGia.selectAll();
	                txtGia.requestFocus();
	                return false;
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Đơn giá phải là một số hợp lệ!");
	            txtGia.selectAll();
	            txtGia.requestFocus();
	            return false;
	        }
	    }

	    return true;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == btnThem) {
			if (validation()) {
				String ma = txtMa.getText();
				String ten = txtTen.getText();
				double soLuong = Double.parseDouble(txtSoLuong.getText());
				String moTa = txtMoTa.getText();

				// Lấy ngày nhập từ JDateChooser
				java.util.Date ngayNhapD = ngayNhap.getDate();
				Date ngayNhapN = new Date(ngayNhapD.getTime());  // Chuyển từ java.util.Date sang java.sql.Date

				// Lấy ngày hết hạn từ JDateChooser (sửa lỗi trùng lặp)
				java.util.Date ngayHetHanD = ngayHetHan.getDate();
				Date ngayHetHanN = new Date(ngayHetHanD.getTime());  // Chuyển từ java.util.Date sang java.sql.Date

				// Lấy đơn vị
				Unit unit = cmbDonVi.getSelectedItem().toString().equalsIgnoreCase("Gram") ? Unit.GRAM : Unit.LITER;

				// Lấy giá
				double gia = Double.parseDouble(txtGia.getText());

				// Tạo đối tượng Ingredient
				Ingredient ingredient = new Ingredient(ma, ten, soLuong, moTa, ngayNhapN, ngayHetHanN, unit, gia);

				if (!ds.contains(ingredient)) {
					if (ingredient_DAO.addIngredient(ingredient)) {
						modelIngredient.addRow(ingredient.toRow());
						cmbMa.removeAllItems();
						cmbTen.removeAllItems();
						ds = Ingredient_DAO.getAllIngredient();
						xoaTrang();
						for (Ingredient ingredient2 : ds) {
							cmbMa.addItem(ingredient2.getIngredientId());
							cmbTen.addItem(ingredient2.getIngredientName());
						}

						JOptionPane.showMessageDialog(this, "Thêm nguyên liệu thành công!");
					} else {
						JOptionPane.showMessageDialog(this, "Thêm nguyên liệu thất bại!");
					}
				} else
					JOptionPane.showMessageDialog(this, "Mã nguyên liệu đã bị trùng!");
			}
		} else if (o == btnTimTheoMa) {
			deleteTable();
			showTable();
			Ingredient ingredient = Ingredient_DAO.findIngredientById(cmbMa.getSelectedItem().toString());
			if (ingredient != null) {
				tableIngredient.setRowSelectionInterval(ingredient_DAO.getIndex(ingredient),
						ingredient_DAO.getIndex(ingredient));

			}
		} else if (o == cmbTen || o == btnTimTheoTen) {
			if (!(cmbTen.getSelectedItem() == null)) {
				if (!cmbTen.getSelectedItem().toString().trim().equals("")) {
					ArrayList<Ingredient> list = ingredient_DAO
							.findIngredientsByName(cmbTen.getSelectedItem().toString());
					if (list.size() != 0) {
						deleteTable();
						for (Ingredient ingredient : list) {
							modelIngredient.addRow(ingredient.toRow());
						}
					} else {
						JOptionPane.showMessageDialog(this, "Không tìm thấy nguyên liệu!");
						deleteTable();
						showTable();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc nhập tên nguyên liệu cần tìm!");
					cmbTen.requestFocus();
				}
			} else
				JOptionPane.showMessageDialog(this, "Không tìm thấy nguyên liệu nào!");
		} else if (o == btnBoLoc) {
			deleteTable();
			showTable();
		} else if (o == btnXoaTrang)
			xoaTrang();
		else if (o == btnSua) {
			if (validation()) {
				String ma = txtMa.getText();
				String ten = txtTen.getText();
				double soLuong = Double.parseDouble(txtSoLuong.getText());
				String moTa = txtMoTa.getText();

				// Lấy ngày nhập từ JDateChooser
				java.util.Date ngayNhapD = ngayNhap.getDate();
				Date ngayNhapN = new Date(ngayNhapD.getTime());  // Chuyển từ java.util.Date sang java.sql.Date

				// Lấy ngày hết hạn từ JDateChooser (sửa lỗi trùng lặp)
				java.util.Date ngayHetHanD = ngayHetHan.getDate();
				Date ngayHetHanN = new Date(ngayHetHanD.getTime());  // Chuyển từ java.util.Date sang java.sql.Date

				// Lấy đơn vị
				Unit unit = cmbDonVi.getSelectedItem().toString().equalsIgnoreCase("Gram") ? Unit.GRAM : Unit.LITER;

				// Lấy giá
				double gia = Double.parseDouble(txtGia.getText());

				// Tạo đối tượng Ingredient
				Ingredient ingredient = new Ingredient(ma, ten, soLuong, moTa, ngayNhapN, ngayHetHanN, unit, gia);
				try {
					if (ingredient_DAO.updateIngredient(ingredient)) {
						deleteTable();
						showTable();
						cmbMa.removeAllItems();
						cmbTen.removeAllItems();
						ds = Ingredient_DAO.getAllIngredient();
						xoaTrang();
						for (Ingredient ingredient2 : ds) {
							cmbMa.addItem(ingredient2.getIngredientId());
							cmbTen.addItem(ingredient2.getIngredientName());
						}

						JOptionPane.showMessageDialog(this, "Cập nhật nguyên liệu thành công!");
					} else {
						JOptionPane.showMessageDialog(this, "Cập nhật nguyên liệu thất bại!");
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (o == btnXoa) {
			if (getCheckedRows().size() != 0) {
				if (JOptionPane.showConfirmDialog(this, "Bạn có chắc xóa nguyên liệu? ") == JOptionPane.OK_OPTION) {
					ds = ingredient_DAO.deleteIngredients(getCheckedRows());
					deleteTable();
					showTable();
					xoaTrang();
					cmbMa.removeAllItems();
					cmbTen.removeAllItems();
					ds = Ingredient_DAO.getAllIngredient();
					for (Ingredient ingredient : ds) {
						cmbMa.addItem(ingredient.getIngredientId());
						cmbTen.addItem(ingredient.getIngredientName());
					}
					JOptionPane.showMessageDialog(this, "Xóa thành công!");
				}
			} else
				JOptionPane.showMessageDialog(this, "Vui chọn tích vào nguyên liệu muốn xóa!");
		} else if (o == btnXoaHet) {
			for (int i = 0; i < modelIngredient.getRowCount(); i++) {
				modelIngredient.setValueAt(true, i, 8); // Đánh dấu checkbox ở cột thứ 3
			}
			if (JOptionPane.showConfirmDialog(this,
					"Bạn có chắc chắn muốn xóa hết nguyên liệu trong table?") == JOptionPane.OK_OPTION) {
				if (JOptionPane.showConfirmDialog(this, "Bạn chắc chưa???????????") == JOptionPane.OK_OPTION) {
					ArrayList<String> listIngredient = getSelectedCategoryCodes();
					System.out.println(getSelectedCategoryCodes());
					System.out.println(listIngredient);
					if (ingredient_DAO.deleteIngredientsById(listIngredient)) {
						deleteTable();
						showTable();
						xoaTrang();
						cmbMa.removeAllItems();
						cmbTen.removeAllItems();
						ds = Ingredient_DAO.getAllIngredient();
						for (Ingredient ingredient : ds) {
							cmbMa.addItem(ingredient.getIngredientId());
							cmbTen.addItem(ingredient.getIngredientName());
						}
						JOptionPane.showMessageDialog(this, "Xóa tất cả thành công!");
					} else
						JOptionPane.showMessageDialog(this, "Xóa tất cả thất bại!");
				}
			}
		}

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tableIngredient.getSelectedRow();
		if (row != -1) {
		    txtMa.setText(tableIngredient.getValueAt(row, 0).toString());
		    txtTen.setText(tableIngredient.getValueAt(row, 1).toString());
		    txtSoLuong.setText(tableIngredient.getValueAt(row, 2).toString());
		    txtMoTa.setText(tableIngredient.getValueAt(row, 3).toString());
		    
		    // Lấy ngày nhập và ngày hết hạn từ bảng dưới dạng chuỗi
		    String ngayNhapStr = tableIngredient.getValueAt(row, 4).toString();
		    String ngayHetHanStr = tableIngredient.getValueAt(row, 5).toString();

		    // Định dạng ngày để chuyển chuỗi thành Date
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		    // Chuyển chuỗi thành Date (java.util.Date) và đặt vào JDateChooser
		    try {
		        java.util.Date ngayNhapDate = dateFormat.parse(ngayNhapStr);
		        java.util.Date ngayHetHanDate = dateFormat.parse(ngayHetHanStr);

		        // Đặt giá trị cho JDateChooser
		        ngayNhap.setDate(ngayNhapDate);  // Lưu ý: JDateChooser làm việc với java.util.Date
		        ngayHetHan.setDate(ngayHetHanDate);  // Lưu ý: JDateChooser làm việc với java.util.Date
		    } catch (ParseException e1) {
		        e1.printStackTrace();  // Xử lý ngoại lệ nếu không thể phân tích chuỗi ngày
		    }

		    // Thiết lập đơn vị (Gram hoặc Liter)
		    if (tableIngredient.getValueAt(row, 6).toString().equals("Gram"))
		        cmbDonVi.setSelectedItem("Gram");
		    else 
		        cmbDonVi.setSelectedItem("Liter");

		    // Thiết lập giá
		    txtGia.setText(tableIngredient.getValueAt(row, 7).toString());

		    // Focus vào trường Tên
		    txtTen.requestFocus();

		    // Cập nhật trạng thái của các nút
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

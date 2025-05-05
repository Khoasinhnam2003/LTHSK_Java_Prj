package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Category;

public class Category_DAO {

	public Category_DAO() {
		super();
	}

	public static ArrayList<Category> getAllCategory() {
		ArrayList<Category> categories = new ArrayList<Category>();
		ConnectDB.getInstance();
		Connection connection = ConnectDB.getConnection();
		try {
			String sql = "select top 20 * from category";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String categoryId = resultSet.getString(1);
				String categoryName = resultSet.getString(2);
				Category category = new Category(categoryId, categoryName);
				categories.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}

	public static Category findCategoryById(String id) {
		ArrayList<Category> list = Category_DAO.getAllCategory();
		Category category1 = null;
		for (Category category : list) {
			if (category.getCategoryId().equalsIgnoreCase(id))
				category1 = category;
		}
		return category1;
	}
	
	public int getIndex(Category category1) {
	    ArrayList<Category> list = Category_DAO.getAllCategory(); 
	    for (int i = 0; i < list.size(); i++) {
	        if (list.get(i).getCategoryId().equals(category1.getCategoryId())) { 
	            return i;  
	        }
	    }
	    return -1;  
	}

	
	public int getNewId(ArrayList<Category> list) {
		ArrayList<Integer> listSo=new ArrayList<Integer>();
		for (Category category : list) {
			int number = Integer.parseInt(category.getCategoryId().replaceAll("\\D+", ""));
			listSo.add(number);
		}
		return listSo.size()==0?0: listSo.stream().max(Integer::compareTo).orElseThrow()+1;
	}
	public ArrayList<Category> findCategoriesByName(String name) {
		ArrayList<Category> categories = new ArrayList<>();
		Connection connection = null;

		try {
			// Kết nối đến cơ sở dữ liệu
			ConnectDB.getInstance();
			connection = ConnectDB.getConnection();

			// SQL tìm kiếm category theo tên
			String sql = "SELECT * FROM category WHERE category_name LIKE ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			// Đặt giá trị tham số cho câu truy vấn
			statement.setString(1, "%" + name + "%"); // Tìm kiếm tên chứa "name", không phân biệt chữ hoa/thường

			// Thực thi truy vấn và nhận kết quả
			ResultSet resultSet = statement.executeQuery();

			// Duyệt qua kết quả và thêm vào danh sách
			while (resultSet.next()) {
				String categoryId = resultSet.getString("category_id");
				String categoryName = resultSet.getString("category_name");

				Category category = new Category(categoryId, categoryName); // Giả sử bạn có một constructor cho
																			// Category
				categories.add(category); // Thêm category vào danh sách
			}
		} catch (Exception e) {
			e.printStackTrace(); // In lỗi nếu có ngoại lệ
		} 

		return categories; // Trả về danh sách category tìm được
	}

	public boolean addCategory(Category category) {
		ArrayList<Category> list = Category_DAO.getAllCategory();
		if (!list.contains(category)) {
			ConnectDB.getInstance();
			Connection connection = ConnectDB.getConnection();
			try {
				String sql = "insert into category values(?,?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, category.getCategoryId());
				statement.setString(2, category.getCategoryName());
				statement.executeUpdate();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public ArrayList<Category> deleteCategories(ArrayList<Integer> indicesToDelete) {
	    ArrayList<Category> list = Category_DAO.getAllCategory();
	    // Sắp xếp danh sách chỉ mục theo thứ tự giảm dần để tránh lỗi khi xóa
	    indicesToDelete.sort((a, b) -> b - a);
	    ConnectDB.getInstance();
	    Connection connection = ConnectDB.getConnection();
	    try {
	    	String sql = "DELETE FROM category WHERE category_id = ?";
	    	PreparedStatement statement = connection.prepareStatement(sql);
	    	for (int index : indicesToDelete) {
	            if (index >= 0 && index < list.size()) {
	                Category categoryToDelete = list.get(index);
	                
	                // Xóa khỏi cơ sở dữ liệu
	                statement.setString(1, categoryToDelete.getCategoryId());
	                statement.executeUpdate();
	                
	                // Xóa khỏi danh sách
	                list.remove(index);
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return list;
	}
	public boolean deleteCategoriesById(ArrayList<String> categoriesID) {
	    ConnectDB.getInstance();
	    Connection connection = ConnectDB.getConnection();
	    try {
	    	for (String string : categoriesID) {
	    		String sql = "DELETE FROM category WHERE category_id = ?";
		    	PreparedStatement statement = connection.prepareStatement(sql);
	                statement.setString(1, string);
	                statement.executeUpdate();
	        }
	    	return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return false;
	}
	public boolean updateCategory(Category category) throws SQLException {
		ArrayList<Category> list = Category_DAO.getAllCategory();
		if (list.contains(category)) {
			ConnectDB.getInstance();
			Connection connection = ConnectDB.getConnection();
			try {
				String sql = "update category set category_name=? where category_id=?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, category.getCategoryName());
				statement.setString(2, category.getCategoryId());
				statement.executeUpdate();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}

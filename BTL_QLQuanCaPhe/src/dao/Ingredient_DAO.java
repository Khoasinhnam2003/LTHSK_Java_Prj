package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Ingredient;
import entity.Unit;

public class Ingredient_DAO {

	public Ingredient_DAO() {
		super();
	}
	public static ArrayList<Ingredient> getAllIngredient() {
		ArrayList<Ingredient> ingredients=new ArrayList<Ingredient>();
		ConnectDB.getInstance();
		Connection connection=ConnectDB.getConnection();
		try {
			String sql="select top 20 * from ingredient";
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			while (resultSet.next()) {
				String ingredientId=resultSet.getString(1);
				String ingredientName=resultSet.getString(2);
				double quantity=resultSet.getFloat(3);
				String description=resultSet.getString(4);
				Date dateOfEnty=resultSet.getDate(5);
				Date expirationDate=resultSet.getDate(6);
				String unit=resultSet.getString(7);
				double price=resultSet.getFloat(8);
				Ingredient ingredient=new Ingredient(ingredientId, ingredientName, quantity, description, dateOfEnty, expirationDate, unit.equalsIgnoreCase("GRAM")?Unit.GRAM:Unit.LITER, price);
				ingredients.add(ingredient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ingredients;
	}
	public static Ingredient findIngredientById(String id) {
		ArrayList<Ingredient> list=Ingredient_DAO.getAllIngredient();
		Ingredient ingredient1=null;
		for (Ingredient ingredient : list) {
			if(ingredient.getIngredientId().equalsIgnoreCase(id))
				ingredient1=ingredient;
		}
		return ingredient1;
	}
	
	public int getIndex(Ingredient ingredient) {
	    ArrayList<Ingredient> list = Ingredient_DAO.getAllIngredient();
	    for (int i = 0; i < list.size(); i++) {
	        if (list.get(i).getIngredientId().equals(ingredient.getIngredientId())) { 
	            return i;  
	        }
	    }
	    return -1;  
	}
	
	public int getNewId(ArrayList<Ingredient> list) {
		ArrayList<Integer> listSo=new ArrayList<Integer>();
		for (Ingredient ingredient : list) {
			int number = Integer.parseInt(ingredient.getIngredientId().replaceAll("\\D+", ""));
			listSo.add(number);
		}
		return listSo.size()==0?0: listSo.stream().max(Integer::compareTo).orElseThrow()+1;
	}
	
	public ArrayList<Ingredient> findIngredientsByName(String name) {
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		Connection connection = null;

		try {
			// Kết nối đến cơ sở dữ liệu
			ConnectDB.getInstance();
			connection = ConnectDB.getConnection();

			// SQL tìm kiếm category theo tên
			String sql = "SELECT * FROM ingredient WHERE ingredient_name LIKE ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			// Đặt giá trị tham số cho câu truy vấn
			statement.setString(1, "%" + name + "%"); // Tìm kiếm tên chứa "name", không phân biệt chữ hoa/thường

			// Thực thi truy vấn và nhận kết quả
			ResultSet resultSet = statement.executeQuery();

			// Duyệt qua kết quả và thêm vào danh sách
			while (resultSet.next()) {
				String ingredientId=resultSet.getString("ingredient_id");
				String ingredientName=resultSet.getString("ingredient_name");
				double quantity=resultSet.getFloat("quantity");
				String description=resultSet.getString("description");
				Date dateOfEntry=resultSet.getDate("date_of_entry");
				Date expirationDate=resultSet.getDate("expiration_date");
				Unit unit=resultSet.getString("unit")=="Gram"?Unit.GRAM:Unit.LITER;
				double price=resultSet.getFloat("price");
				
				Ingredient ingredient=new Ingredient(ingredientId, ingredientName, quantity, description, dateOfEntry, expirationDate, unit, price);
				ingredients.add(ingredient);
			}
		} catch (Exception e) {
			e.printStackTrace(); // In lỗi nếu có ngoại lệ
		} 

		return ingredients; // Trả về danh sách category tìm được
	}
	
	
	
	public boolean addIngredient(Ingredient ingredient) {
		ArrayList<Ingredient> list=Ingredient_DAO.getAllIngredient();
		if(!list.contains(ingredient)) {
			ConnectDB.getInstance();
			Connection connection=ConnectDB.getConnection();
			try {
				String sql="insert into ingredient values(?,?,?,?,?,?,?,?)";
				PreparedStatement statement=connection.prepareStatement(sql);
				statement.setString(1,ingredient.getIngredientId());
				statement.setString(2, ingredient.getIngredientName());
				statement.setFloat(3, (float) ingredient.getQuantity());
				statement.setString(4, ingredient.getDescription());
				statement.setDate(5, ingredient.getDateOfEntry());
				statement.setDate(6, ingredient.getExpirationDate());
				statement.setString(7, ingredient.getUnit()==Unit.GRAM?"Gram":"Liter");
				statement.setFloat(8, (float) ingredient.getPrice());
				statement.executeUpdate();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public ArrayList<Ingredient> deleteIngredients(ArrayList<Integer> indicesToDelete) {
	    ArrayList<Ingredient> list =Ingredient_DAO.getAllIngredient();
	    // Sắp xếp danh sách chỉ mục theo thứ tự giảm dần để tránh lỗi khi xóa
	    indicesToDelete.sort((a, b) -> b - a);
	    ConnectDB.getInstance();
	    Connection connection = ConnectDB.getConnection();
	    try {
	    	String sql = "DELETE FROM ingredient WHERE ingredient_id = ?";
	    	PreparedStatement statement = connection.prepareStatement(sql);
	    	for (int index : indicesToDelete) {
	            if (index >= 0 && index < list.size()) {
	                Ingredient ingredientToDelete = list.get(index);
	                
	                // Xóa khỏi cơ sở dữ liệu
	                statement.setString(1, ingredientToDelete.getIngredientId());
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
	
	public boolean deleteAll() {
		ConnectDB.getInstance();
	    Connection connection = ConnectDB.getConnection();
	    try {
	    	String sql = "DELETE FROM ingredient";
	    	PreparedStatement statement = connection.prepareStatement(sql);
	    	statement.executeUpdate();
	    	return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return false;
	}
	
	public boolean deleteIngredient(String id) {
		ArrayList<Ingredient> list=Ingredient_DAO.getAllIngredient();
		if(list.contains(Ingredient_DAO.findIngredientById(id))) {
			ConnectDB.getInstance();
			Connection connection=ConnectDB.getConnection();
			try {
				String sql="delete from ingredient where ingredient_id=?";
				PreparedStatement statement=connection.prepareStatement(sql);
				statement.setString(1, id);
				statement.executeUpdate();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean updateIngredient(Ingredient ingredient) throws SQLException {
		ArrayList<Ingredient> list=Ingredient_DAO.getAllIngredient();
		if(list.contains(ingredient)) {
			ConnectDB.getInstance();
			Connection connection=ConnectDB.getConnection();
			try {
				String sql="update ingredient set ingredient_name=?,quantity=?, description=?, date_of_entry=?, expiration_date=?, unit=?, price=? where ingredient_id=?";
				PreparedStatement statement=connection.prepareStatement(sql);
				statement.setString(1, ingredient.getIngredientName());
				statement.setFloat(2, (float) ingredient.getQuantity());
				statement.setString(3, ingredient.getDescription());
				statement.setDate(4, ingredient.getDateOfEntry());
				statement.setDate(5, ingredient.getExpirationDate());
				statement.setString(6, ingredient.getUnit()==Unit.GRAM?"Gram":"Liter");
				statement.setFloat(7, (float) ingredient.getPrice());
				statement.setString(8,ingredient.getIngredientId());
				statement.executeUpdate();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	} 
	public boolean deleteIngredientsById(ArrayList<String> ingredientID) {
	    ConnectDB.getInstance();
	    Connection connection = ConnectDB.getConnection();
	    try {
	    	for (String string : ingredientID) {
	    		String sql = "DELETE FROM ingredient WHERE ingredient_id = ?";
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
}

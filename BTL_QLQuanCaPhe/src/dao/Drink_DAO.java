package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

import entity.Drink;
public class Drink_DAO {
    private Connection connection;

    // Constructor
    public Drink_DAO() {
        // Gọi phương thức connect để thiết lập kết nối
        ConnectDB.getInstance().connect();
        this.connection = ConnectDB.getConnection();
        
        if (this.connection == null) {
            System.out.println("Kết nối cơ sở dữ liệu không thành công!");
        }
    }

    // Lấy danh sách đồ uống
    public List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<>();
        String sql = "SELECT * FROM drink";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                Drink drink = new Drink(
                    resultSet.getString("drink_id"),
                    resultSet.getString("drink_name"),
                    resultSet.getString("image"),
                    resultSet.getString("description"),
                    resultSet.getFloat("price"),
                    resultSet.getString("category_id"),
                    resultSet.getFloat("vat")
                );
                drinks.add(drink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return drinks;
    }
    // Thêm đồ uống
    public boolean addDrink(Drink drink) {
        String sql = "INSERT INTO drink (drink_id, drink_name, image, description, price, category_id, vat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, drink.getDrinkId());
            preparedStatement.setString(2, drink.getDrinkName());
            preparedStatement.setString(3, drink.getImage());
            preparedStatement.setString(4, drink.getDescription());
            preparedStatement.setDouble(5, drink.getPrice());
            preparedStatement.setString(6, drink.getCategoryId());
            preparedStatement.setDouble(7, drink.getVat());
            
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa đồ uống theo ID
    public boolean deleteDrink(String drinkId) {
        String sql = "DELETE FROM drink WHERE drink_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, drinkId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin đồ uống
    public boolean updateDrink(Drink drink) {
        String sql = "UPDATE drink SET drink_name = ?, image = ?, description = ?, price = ?, category_id = ?, vat = ? WHERE drink_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, drink.getDrinkName());
            preparedStatement.setString(2, drink.getImage());
            preparedStatement.setString(3, drink.getDescription());
            preparedStatement.setDouble(4, drink.getPrice());
            preparedStatement.setString(5, drink.getCategoryId());
            preparedStatement.setDouble(6, drink.getVat());
            preparedStatement.setString(7, drink.getDrinkId());
            
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm kiếm đồ uống theo tên
    public List<Drink> searchDrinkByName(String name) {
        List<Drink> drinks = new ArrayList<>();
        String sql = "SELECT * FROM drink WHERE drink_name LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Drink drink = new Drink(
                        resultSet.getString("drink_id"),
                        resultSet.getString("drink_name"),
                        resultSet.getString("image"),
                        resultSet.getString("description"),
                        resultSet.getFloat("price"),
                        resultSet.getString("category_id"),
                        resultSet.getFloat("vat")
                    );
                    drinks.add(drink);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }
 // Lấy ID mới cho đồ uống
    public String getNewId(List<Drink> drinks) {
        int maxId = 0;
        for (Drink drink : drinks) {
            String id = drink.getDrinkId().replaceAll("\\D+", "");
            maxId = Math.max(maxId, Integer.parseInt(id));
        }
        return String.format("%03d", maxId + 1);
    }
 // Xóa tất cả đồ uống
    public boolean deleteAllDrinks() {
        String sql = "DELETE FROM drink";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 // Xóa đồ uống theo ID
    public boolean deleteDrinkById(String drinkId) {
        String sql = "DELETE FROM drink WHERE drink_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, drinkId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 // Phương thức tìm kiếm đồ uống theo tên
    public List<Drink> searchDrinks(String searchTerm) {
        List<Drink> drinks = new ArrayList<>();
        String sql = "SELECT * FROM drink WHERE drink_name LIKE ?"; // Dùng LIKE để tìm kiếm theo tên

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Cài đặt tham số tìm kiếm, sử dụng dấu '%' để tìm kiếm theo mẫu
            statement.setString(1, "%" + searchTerm + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Drink drink = new Drink(
                        resultSet.getString("drink_id"),
                        resultSet.getString("drink_name"),
                        resultSet.getString("image"),
                        resultSet.getString("description"),
                        resultSet.getFloat("price"),
                        resultSet.getString("category_id"),
                        resultSet.getFloat("vat")
                    );
                    drinks.add(drink);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return drinks;
    }
}


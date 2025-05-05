package dao;

import java.sql.*;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Drink;
import entity.DrinkDetail;
import entity.Ingredient;

public class DrinkDetail_DAO {

    public DrinkDetail_DAO() {
        super();
    }

    // Lấy tất cả DrinkDetail từ cơ sở dữ liệu
    public static ArrayList<DrinkDetail> getAllDrinkDetails() {
        ArrayList<DrinkDetail> drinkDetails = new ArrayList<>();
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();

        try {
            String sql = "SELECT * FROM drink_detail";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String ingredientId = resultSet.getString("ingredient_id");
                String drinkId = resultSet.getString("drink_id");
                double quantity = resultSet.getDouble("quantity");

                Ingredient ingredient = new Ingredient(ingredientId);
//                Drink drink = new Drink(drinkId);

//                DrinkDetail drinkDetail = new DrinkDetail(ingredient, drink, quantity);
//                drinkDetails.add(drinkDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drinkDetails;
    }

    // Thêm DrinkDetail mới
    public boolean addDrinkDetail(DrinkDetail drinkDetail) {
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();

        try {
            String sql = "INSERT INTO drink_detail (ingredient_id, drink_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, drinkDetail.getIngredient().getIngredientId());
            statement.setString(2, drinkDetail.getDrink().getDrinkId());
            statement.setDouble(3, drinkDetail.getQuantity());

            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật DrinkDetail
    public boolean updateDrinkDetail(DrinkDetail drinkDetail) {
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();

        try {
            String sql = "UPDATE drink_detail SET quantity=? WHERE ingredient_id=? AND drink_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, drinkDetail.getQuantity());
            statement.setString(2, drinkDetail.getIngredient().getIngredientId());
            statement.setString(3, drinkDetail.getDrink().getDrinkId());

            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa DrinkDetail
    public boolean deleteDrinkDetail(String ingredientId, String drinkId) {
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();

        try {
            String sql = "DELETE FROM drink_detail WHERE ingredient_id=? AND drink_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ingredientId);
            statement.setString(2, drinkId);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm DrinkDetail theo drinkId
    public ArrayList<DrinkDetail> findDrinkDetailsByDrinkId(String drinkId) {
        ArrayList<DrinkDetail> drinkDetails = new ArrayList<>();
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();

        try {
            String sql = "SELECT * FROM drink_detail WHERE drink_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, drinkId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String ingredientId = resultSet.getString("ingredient_id");
                double quantity = resultSet.getDouble("quantity");

                Ingredient ingredient = new Ingredient(ingredientId);
//                Drink drink = new Drink(drinkId);
//
//                DrinkDetail drinkDetail = new DrinkDetail(ingredient, drink, quantity);
//                drinkDetails.add(drinkDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drinkDetails;
    }
}

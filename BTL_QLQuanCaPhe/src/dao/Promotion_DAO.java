package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Promotion;

public class Promotion_DAO {

    public Promotion_DAO() {
        super();
    }

    public static ArrayList<Promotion> getAllPromotion() {
        ArrayList<Promotion> promotions = new ArrayList<Promotion>();
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "select top 20 * from promotion";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String promotionId = resultSet.getString(1);
                String promotionName = resultSet.getString(2);
                double discount = resultSet.getDouble(3);  
                java.util.Date startDate = resultSet.getDate(4);
                java.util.Date endDate = resultSet.getDate(5);
                
                Promotion promotion = new Promotion(promotionId, promotionName, discount, startDate, endDate);
                promotions.add(promotion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public static Promotion findPromotionById(String id) {
        ArrayList<Promotion> list = Promotion_DAO.getAllPromotion();
        Promotion promotion1 = null;
        for (Promotion promotion : list) {
            if (promotion.getPromotionId().equalsIgnoreCase(id))
                promotion1 = promotion;
        }
        return promotion1;
    }

    public int getIndex(Promotion promotion1) {
        ArrayList<Promotion> list = Promotion_DAO.getAllPromotion();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPromotionId().equals(promotion1.getPromotionId())) {
                return i;
            }
        }
        return -1;
    }

//    public int getNewId(ArrayList<Promotion> list) {
//        ArrayList<Integer> listSo = new ArrayList<Integer>();
//        for (Promotion promotion : list) {
//            int number = Integer.parseInt(promotion.getPromotionId().replaceAll("\\D+", ""));
//            listSo.add(number);
//        }
//        return listSo.size() == 0 ? 0 : listSo.stream().max(Integer::compareTo).orElseThrow() + 1;
//    }

    public ArrayList<Promotion> findPromotionsByName(String name) {
        ArrayList<Promotion> promotions = new ArrayList<>();
        Connection connection = null;

        try {
            ConnectDB.getInstance();
            connection = ConnectDB.getConnection();

            String sql = "SELECT * FROM promotion WHERE promotion_name LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String promotionId = resultSet.getString("promotion_id");
                String promotionName = resultSet.getString("promotion_name");
                double discount = resultSet.getDouble("discount");
                java.util.Date startDate = resultSet.getDate("startdate");
                java.util.Date endDate = resultSet.getDate("enddate");

                Promotion promotion = new Promotion(promotionId, promotionName, discount, startDate, endDate);
                promotions.add(promotion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return promotions;
    }

    public boolean addPromotion(Promotion promotion) {
        ArrayList<Promotion> list = Promotion_DAO.getAllPromotion();
        if (!list.contains(promotion)) {
            ConnectDB.getInstance();
            Connection connection = ConnectDB.getConnection();
            try {
                String sql = "insert into promotion values(?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, promotion.getPromotionId());
                statement.setString(2, promotion.getPromotionName());
                //setFloat á, database là flaot mà convert qua float
                statement.setDouble(3, promotion.getDiscount());
                statement.setDate(4, new java.sql.Date(promotion.getStartDate().getTime()));
                statement.setDate(5, new java.sql.Date(promotion.getEndDate().getTime()));
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<Promotion> deletePromotions(ArrayList<Integer> indicesToDelete) {
        ArrayList<Promotion> list = Promotion_DAO.getAllPromotion();
        indicesToDelete.sort((a, b) -> b - a);
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        try {
            String sql = "DELETE FROM promotion WHERE promotion_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int index : indicesToDelete) {
                if (index >= 0 && index < list.size()) {
                    Promotion promotionToDelete = list.get(index);
                    
                    statement.setString(1, promotionToDelete.getPromotionId());
                    statement.executeUpdate();
                    
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
            String sql = "DELETE FROM promotion";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePromotion(Promotion promotion) throws SQLException {
        ArrayList<Promotion> list = Promotion_DAO.getAllPromotion();
        if (list.contains(promotion)) {
            ConnectDB.getInstance();
            Connection connection = ConnectDB.getConnection();
            try {
                String sql = "update promotion set promotion_name=?, discount=?, startdate=?, enddate=? where promotion_id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, promotion.getPromotionName());
                statement.setDouble(2, promotion.getDiscount());
                statement.setDate(3, new java.sql.Date(promotion.getStartDate().getTime()));
                statement.setDate(4, new java.sql.Date(promotion.getEndDate().getTime()));
                statement.setString(5, promotion.getPromotionId());
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static String getPromotionIdByName(String promotionName) {
        String promotionId = null;
        Connection connection = null;
        try {
            ConnectDB.getInstance();
            connection = ConnectDB.getConnection();

            String sql = "SELECT promotion_id FROM promotion WHERE promotion_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, promotionName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                promotionId = resultSet.getString("promotion_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return promotionId;
    }
    public static double getDiscountByPromotionName(String promotionName) {
        double discount = 0.0;
        Connection connection = null;
        try {
            ConnectDB.getInstance();
            connection = ConnectDB.getConnection();

            String sql = "SELECT discount FROM promotion WHERE promotion_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, promotionName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                discount = resultSet.getDouble("discount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discount;
    }
    public static double getDiscountByPromotionId(String promotionId) {
        double discount = 0.0;
        Connection connection = null;
        try {
            // Kết nối cơ sở dữ liệu
            ConnectDB.getInstance();
            connection = ConnectDB.getConnection();

            // Truy vấn giá trị giảm giá dựa trên mã khuyến mãi
            String sql = "SELECT discount FROM promotion WHERE promotion_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, promotionId);

            ResultSet resultSet = statement.executeQuery();

            // Nếu tìm thấy, lấy giá trị giảm giá
            if (resultSet.next()) {
                discount = resultSet.getDouble("discount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discount;
    }


}
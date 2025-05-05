package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ban;

public class Ban_DAO {

    private Connection connection;

    // Constructor
    public Ban_DAO() throws SQLException {
        // Kết nối cơ sở dữ liệu
        ConnectDB.getInstance().connect();
        this.connection = ConnectDB.getConnection();

        if (this.connection == null) {
            System.out.println("Kết nối cơ sở dữ liệu không thành công!");
        }
    }

    // Lấy danh sách bàn
    public List<Ban> getAllBans() {
        List<Ban> bans = new ArrayList<>();
        String sql = "SELECT * FROM tabless";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Ban ban = new Ban(
                    resultSet.getString("table_id"),
                    resultSet.getInt("num_of_seats"),
                    resultSet.getString("floors"),
                    resultSet.getString("table_status")
                );
                bans.add(ban);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bans;
    }

    // Tìm bàn theo mã bàn
    public Ban getBanById(String maBan) {
        Ban ban = null;
        String sql = "SELECT * FROM tabless WHERE table_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, maBan);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ban = new Ban(
                    resultSet.getString("table_id"),
                    resultSet.getInt("num_of_seats"),
                    resultSet.getString("floors"),
                    resultSet.getString("table_status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ban;
    }

    // Thêm bàn mới
    public boolean addBan(Ban ban) {
        String sql = "INSERT INTO tabless (table_id, num_of_seats, floors, table_status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ban.getMaBan());
            statement.setInt(2, ban.getSoCho());
            statement.setString(3, ban.getKhuVuc());
            statement.setString(4, ban.getTrangThai());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin bàn
    public boolean updateBan(Ban ban) {
        String sql = "UPDATE tabless SET num_of_seats = ?, floors = ?, table_status = ? WHERE table_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ban.getSoCho());
            statement.setString(2, ban.getKhuVuc());
            statement.setString(3, ban.getTrangThai());
            statement.setString(4, ban.getMaBan());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa bàn
    public boolean deleteBan(String maBan) {
        String sql = "DELETE FROM tabless WHERE table_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, maBan);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 // Lấy ID mới cho bàn
    public String getNewId(List<Ban> bans) {
        int maxId = 0;
        for (Ban ban : bans) {
            String id = ban.getMaBan().replaceAll("\\D+", ""); // Loại bỏ chữ cái, chỉ lấy số
            maxId = Math.max(maxId, Integer.parseInt(id)); // Tìm giá trị ID lớn nhất
        }
        return String.format("%03d", maxId + 1); // Trả về ID mới với định dạng "B" + số tăng dần
    }
}

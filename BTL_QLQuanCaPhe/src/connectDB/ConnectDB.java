package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection connection = null; // Kết nối cơ sở dữ liệu
    private static ConnectDB instance = new ConnectDB(); // Đối tượng singleton

    // Phương thức lấy instance của ConnectDB (Singleton)
    public static ConnectDB getInstance() {
        return instance; 
    }

    // Phương thức thiết lập kết nối cơ sở dữ liệu
    public void connect()  {
        try {
			if (connection == null || connection.isClosed()) { // Kiểm tra kết nối có tồn tại và mở
			    String url = "jdbc:sqlserver://localhost:1433;databasename=QLCaPhe";
			    String user = "sa";
			    String password = "sapassword";
			    connection = DriverManager.getConnection(url, user, password);
			    System.out.println("Kết nối cơ sở dữ liệu thành công.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Phương thức ngắt kết nối cơ sở dữ liệu
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Đã ngắt kết nối cơ sở dữ liệu.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức trả về kết nối cơ sở dữ liệu
    public static Connection getConnection() {
        return connection;
    }
}

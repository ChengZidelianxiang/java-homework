import java.sql.*;

public class GetDBConnection {
    public static Connection connectDB(String DBName, String id, String p) {
        Connection con = null;
        String uri = "jdbc:mysql://localhost:3306/" + DBName +
                     "?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e) {}

        try {
            con = DriverManager.getConnection(uri, "root", "123456");
        } catch(SQLException e) {
            System.err.println("连接失败：" + e.getMessage());
        }

        return con;
    }
}

import java.sql.*;
public class GetDBConnection {
    public static Connection connectDB(String DBName,String id,String p) {
        Connection con = null;
        String uri = "jdbc:mysql://localhost:3306/" + DBName +
        "?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=utf-8";
        try{ Class.forName("com.mysql.cj.jdbc.Driver");      //加载JDBC-MySQL驱
        }
        catch(Exception e){
            System.out.println("驱动加载失败:"+e.getMessage());
        }
        try{
            con = DriverManager.getConnection(uri,id,p);      //连接代码
            System.out.println("数据库连接成功");
        }
        catch(SQLException e){
            System.out.println("连接失败:"+e.getMessage());
        }
        return con;
    }
}

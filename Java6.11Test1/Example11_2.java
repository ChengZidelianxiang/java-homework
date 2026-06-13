import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

// 数据库连接工具类
class GetDBConnection {
    public static Connection connectDB(String DBName, String id, String p) {
        Connection con = null;
        String uri = "jdbc:mysql://localhost:3306/" + DBName +
                     "?useSSL=true&serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载MySQL驱动
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(uri, id, p); // 获取连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}

// 主程序类
public class Example11_2 {
    public static void main(String args[]) {
        Connection con;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("students", "root", "123456");
        if (con == null) {
            System.out.println("数据库连接失败！");
            return;
        }
        try {
            sql = con.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            rs = sql.executeQuery("select * from mess");

            // 获取总记录数
            rs.last();
            int max = rs.getRow();
            System.out.println("表共有" + max + "条记录，随机抽取2条记录：");

            // 生成2个不重复的随机行号（1~max）
            int[] randomRows = ThreadLocalRandom.current()
                .ints(1, max + 1)
                .distinct()
                .limit(2)
                .toArray();

            // 遍历随机行，输出数据
            for (int i : randomRows) {
                rs.absolute(i); // 游标移到第i行
                String number = rs.getString(1);
                String name = rs.getString(2);
                Date date = rs.getDate(3);
                float h = rs.getFloat(4);
                System.out.printf("%s\t%s\t%s\t%.2f\n", number, name, date, h);
            }

            con.close();
        } catch (SQLException e) {
            System.out.println("SQL异常：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

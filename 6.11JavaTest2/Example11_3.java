import java.sql.*;

// 数据库连接工具类（和之前一致）
class GetDBConnection {
    public static Connection connectDB(String DBName, String id, String p) {
        Connection con = null;
        String uri = "jdbc:mysql://localhost:3306/" + DBName +
                     "?useSSL=true&serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(uri, id, p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}

// 主程序类
public class Example11_3 {
    public static void main(String args[]) {
        Connection con;
        Statement sql;
        ResultSet rs;

        // 1. 获取数据库连接
        con = GetDBConnection.connectDB("students", "root", "123456");
        if (con == null) {
            System.out.println("数据库连接失败！");
            return;
        }

        try {
            // 2. 定义查询条件
            String c1 = "year(birthday) <= 2000 and month(birthday) > 7"; // 条件1：出生年份≤2000，月份>7
            String c2 = "name like '张_%'"; // 条件2：姓名以"张"开头
            String c3 = "height > 1.65"; // 条件3：身高>1.65

            // 3. 拼接完整SQL语句
            String sqlStr = "select * from mess where " + c1 + " and " + c2 + " and " + c3 + " order by birthday";

            // 4. 执行查询
            sql = con.createStatement();
            rs = sql.executeQuery(sqlStr);

            System.out.println("符合条件的学生记录：");
            System.out.println("编号\t姓名\t生日\t\t身高");
            // 5. 遍历结果集并打印
            while(rs.next()) {
                String number = rs.getString(1);
                String name = rs.getString(2);
                Date date = rs.getDate(3);
                float height = rs.getFloat(4);
                System.out.printf("%s\t%s\t%s\t%.2f\n", number, name, date, height);
            }

            // 6. 关闭资源
            con.close();
        } catch (SQLException e) {
            System.out.println("SQL异常：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

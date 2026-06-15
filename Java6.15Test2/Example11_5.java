import java.sql.*;

public class Example11_5 {
    public static void main(String args[]) {
        Connection con;
        PreparedStatement preSql;
        ResultSet rs;
        con = GetDBConnection.connectDB("students", "root", "");
        if (con == null) return;
        String sqlStr = "insert into mess values(?, ?, ?, ?)";

        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, "A001");
            preSql.setString(2, "刘伟");
            preSql.setDate(3, Date.valueOf("1999-9-10"));
            preSql.setFloat(4, 1.77f);

            try {
                Statement del = con.createStatement();
                del.executeUpdate("DELETE FROM mess WHERE number = 'A001'");
                del.close();
            } catch (SQLException ignored) {}

            int ok = preSql.executeUpdate();
            System.out.println("成功插入 " + ok + " 条记录");

            sqlStr = "select * from mess where name like ?";
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, "张%");

            rs = preSql.executeQuery();
            while (rs.next()) {
                String number = rs.getString(1);
                String name = rs.getString(2);
                Date date = rs.getDate(3);
                float height = rs.getFloat(4);

                System.out.printf("%s\t%s\t%s\t%.2f\n", number, name, date, height);
            }

            con.close();
        } catch (SQLException e) {
            System.out.println("记录中 number 的值不能重复" + e);
        }
    }
}

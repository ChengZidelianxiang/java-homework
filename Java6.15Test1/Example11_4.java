import java.sql.*;

public class Example11_4 {
    public static void main(String[] args) {
        String insertSql = "insert into mess (number, name, birth, height) values (?, ?, ?, ?)";
        String querySql = "select * from mess";

        try (Connection con = GetDBConnection.connectDB("students", "root", "");
             Statement cleanStmt = con.createStatement();
             PreparedStatement pstmt = con.prepareStatement(insertSql);
             Statement stmt = con.createStatement()) {

            // 每次运行前清空表，避免主键冲突
            cleanStmt.executeUpdate("DELETE FROM mess");

            // 插入第一条数据
            pstmt.setString(1, "R11");
            pstmt.setString(2, "将三");
            pstmt.setDate(3, Date.valueOf("2000-10-23"));
            pstmt.setFloat(4, 1.66f);
            pstmt.executeUpdate();

            // 插入第二条数据
            pstmt.setString(1, "R10");
            pstmt.setString(2, "李武");
            pstmt.setDate(3, Date.valueOf("1989-07-22"));
            pstmt.setFloat(4, 1.76f);
            pstmt.executeUpdate();

            // 查询数据
            try (ResultSet rs = stmt.executeQuery(querySql)) {
                System.out.println("查询结果：");
                while (rs.next()) {
                    System.out.printf("%s\t%s\t%s\t%.2f%n",
                            rs.getString("number"),
                            rs.getString("name"),
                            rs.getDate("birth"),
                            rs.getFloat("height"));
                }
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("错误：number 字段值重复（主键冲突）");
        } catch (SQLException e) {
            System.err.println("数据库操作失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

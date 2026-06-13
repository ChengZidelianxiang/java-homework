import java.sql.*;
import java.util.Scanner;

public class HeritageQuery {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/red_culture"
                   + "?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "123456";

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功！\n");

            // 查询所有记录
            System.out.println("========== 红色遗址信息列表 ==========");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM heritage_site");

            System.out.printf("%-10s %-20s %-20s %-40s%n",
                    "编号", "遗址名称", "地点", "简介");
            System.out.println("------------------------------------------------"
                    + "--------------------------------------------");

            while (rs.next()) {
                String id = rs.getString("site_id");
                String name = rs.getString("site_name");
                String loc = rs.getString("location");
                String desc = rs.getString("description");

                if (loc != null && loc.length() > 18)
                    loc = loc.substring(0, 15) + "...";
                if (desc != null && desc.length() > 38)
                    desc = desc.substring(0, 35) + "...";

                System.out.printf("%-10s %-20s %-20s %-40s%n",
                        id, name, loc, desc);
            }
            rs.close();
            stmt.close();

            // 添加记录
            System.out.println("\n========== 添加红色遗址记录 ==========");
            System.out.print("请输入编号：");
            String siteId = sc.nextLine().trim();
            System.out.print("请输入遗址名称：");
            String siteName = sc.nextLine().trim();
            System.out.print("请输入地点：");
            String location = sc.nextLine().trim();
            System.out.print("请输入简介：");
            String description = sc.nextLine().trim();

            String sql = "INSERT INTO heritage_site VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, siteId);
            pstmt.setString(2, siteName);
            pstmt.setString(3, location);
            pstmt.setString(4, description);
            int rows = pstmt.executeUpdate();
            System.out.println("\n添加成功！影响了 " + rows + " 条记录。\n");
            pstmt.close();

            // 重新显示
            System.out.println("========== 更新后的红色遗址列表 ==========");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM heritage_site");
            System.out.printf("%-10s %-20s %-20s %-40s%n",
                    "编号", "遗址名称", "地点", "简介");
            System.out.println("------------------------------------------------"
                    + "--------------------------------------------");
            while (rs.next()) {
                String id = rs.getString("site_id");
                String name = rs.getString("site_name");
                String loc = rs.getString("location");
                String desc = rs.getString("description");
                if (loc != null && loc.length() > 18) loc = loc.substring(0, 15) + "...";
                if (desc != null && desc.length() > 38) desc = desc.substring(0, 35) + "...";
                System.out.printf("%-10s %-20s %-20s %-40s%n", id, name, loc, desc);
            }
            rs.close();
            stmt.close();

        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败：" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("数据库错误：" + e.getMessage());
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
            sc.close();
        }
    }
}

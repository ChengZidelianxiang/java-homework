import java.sql.*;
import java.util.Scanner;

public class PestQuery {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/citrus_pest"
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
            System.out.println("========== 病虫害信息列表 ==========");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pest_info");

            System.out.printf("%-4s %-12s %-30s %-30s%n",
                    "编号", "病虫害名称", "症状描述", "防治方法");
            System.out.println("------------------------------------------------"
                    + "------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("pest_name");
                String symp = rs.getString("symptoms");
                String method = rs.getString("control_method");

                if (symp != null && symp.length() > 28)
                    symp = symp.substring(0, 25) + "...";
                if (method != null && method.length() > 28)
                    method = method.substring(0, 25) + "...";

                System.out.printf("%-4d %-12s %-30s %-30s%n",
                        id, name, symp, method);
            }
            rs.close();
            stmt.close();

            // 添加记录
            System.out.println("\n========== 添加病虫害记录 ==========");
            System.out.print("请输入病虫害名称：");
            String pestName = sc.nextLine().trim();
            System.out.print("请输入症状描述：");
            String symptoms = sc.nextLine().trim();
            System.out.print("请输入防治方法：");
            String control = sc.nextLine().trim();

            String sql = "INSERT INTO pest_info (pest_name, symptoms, control_method) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pestName);
            pstmt.setString(2, symptoms);
            pstmt.setString(3, control);
            int rows = pstmt.executeUpdate();
            System.out.println("\n添加成功！影响了 " + rows + " 条记录。\n");
            pstmt.close();

            // 重新显示
            System.out.println("========== 更新后的病虫害列表 ==========");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM pest_info");
            System.out.printf("%-4s %-12s %-30s %-30s%n",
                    "编号", "病虫害名称", "症状描述", "防治方法");
            System.out.println("------------------------------------------------"
                    + "------------------------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("pest_name");
                String symp = rs.getString("symptoms");
                String method = rs.getString("control_method");
                if (symp != null && symp.length() > 28)
                    symp = symp.substring(0, 25) + "...";
                if (method != null && method.length() > 28)
                    method = method.substring(0, 25) + "...";
                System.out.printf("%-4d %-12s %-30s %-30s%n",
                        id, name, symp, method);
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeInfo {
    static final String URL = "jdbc:mysql://localhost:3306/Employee";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");

            String insertSQL = "INSERT INTO Employee_Info (EmpId, name, salary) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setInt(1, 4);
            pstmt.setString(2, "neymar");
            pstmt.setDouble(3, 70000);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

            stmt = conn.createStatement();
            String selectSQL = "SELECT EmpId, name, salary FROM Employee_Info";
            rs = stmt.executeQuery(selectSQL);

            System.out.println("Employee Data:");
            while (rs.next()) {
                int empId = rs.getInt("EmpId");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                System.out.println("Employee ID: " + empId + ", Name: " + name + ", Salary: " + salary);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
                System.out.println("Resources closed.");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

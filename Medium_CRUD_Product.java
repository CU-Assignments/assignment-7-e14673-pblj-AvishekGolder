import java.sql.*;
import java.util.Scanner;

public class CrudProduct {
    static final String URL = "jdbc:mysql://localhost:3306/crud";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n--- Product CRUD Operations ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public static void addProduct() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Product ID: ");
            int productId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Product Name: ");
            String productName = scanner.nextLine();
            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();

            pstmt.setInt(1, productId);
            pstmt.setString(2, productName);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " product(s) added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewProducts() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM product")) {

            System.out.println("\n--- Product List ---");
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price + ", Quantity: " + quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Product ID to update: ");
            int productId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new Product Name: ");
            String productName = scanner.nextLine();
            System.out.print("Enter new Price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter new Quantity: ");
            int quantity = scanner.nextInt();

            pstmt.setString(1, productName);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, productId);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " product(s) updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM product WHERE ProductID = ?")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Product ID to delete: ");
            int productId = scanner.nextInt();

            pstmt.setInt(1, productId);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " product(s) deleted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

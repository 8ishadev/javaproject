import java.sql.*;
import java.util.Scanner;
import java.util.Vector;

class Menu1 {
    private final Vector<String> scoopFlavors = new Vector<>();
    private final Vector<Integer> scoopPrices = new Vector<>();
    private final Vector<String> coneFlavors = new Vector<>();
    private final Vector<Integer> conePrices = new Vector<>();
    private final Vector<String> tubFlavors = new Vector<>();
    private final Vector<Integer> tubPrices = new Vector<>();
    private final Vector<String> barFlavors = new Vector<>();
    private final Vector<Integer> barPrices = new Vector<>();

    private final Scanner scan = new Scanner(System.in);

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://database-2.czk2cmc4k8m4.us-east-1.rds.amazonaws.com:3306/icecream_shop2";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "ishadev08";

    public Menu1() {
        initializeFlavorsAndPrices();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void initializeFlavorsAndPrices() {
        try (Connection conn = connect()) {
            loadFlavorsAndPrices(conn, "scoop", scoopFlavors, scoopPrices);
            loadFlavorsAndPrices(conn, "cone", coneFlavors, conePrices);
            loadFlavorsAndPrices(conn, "tub", tubFlavors, tubPrices);
            loadFlavorsAndPrices(conn, "bar", barFlavors, barPrices);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFlavorsAndPrices(Connection conn, String tableName, Vector<String> flavors, Vector<Integer> prices) throws SQLException {
        String query = "SELECT flavor_name, price FROM " + tableName;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                flavors.add(rs.getString("flavor_name"));
                prices.add(rs.getInt("price"));
            }
        }
    }

    public void displayMenu() {
        System.out.println("* SATISFY YOUR SWEET CRAVINGS WITH EVERY SCOOP *");
        System.out.println("");
        System.out.println("1. SCOOP");
        System.out.println("2. CONE");
        System.out.println("3. TUBS");
        System.out.println("4. BAR");
        System.out.println("5. EXIT");
    }

    public void scoopMenu() {
        handleOrder(scoopFlavors, scoopPrices);
    }

    public void coneMenu() {
        handleOrder(coneFlavors, conePrices);
    }

    public void tubMenu() {
        handleOrder(tubFlavors, tubPrices);
    }

    public void barMenu() {
        handleOrder(barFlavors, barPrices);
    }

    private void handleOrder(Vector<String> flavors, Vector<Integer> prices) {
        System.out.println("Available Flavours:");
        for (int i = 0; i < flavors.size(); i++) {
            System.out.printf("%d. %s - %d/-\n", i + 1, flavors.get(i), prices.get(i));
        }
        System.out.println("Which one would you like to order?");
        int choice = scan.nextInt();
        if (choice < 1 || choice > prices.size()) {
            System.out.println("Invalid choice. Please select again.");
            scan.nextLine(); 
            return;
        }
        System.out.println("Please enter the quantity:");
        int quantity = scan.nextInt();
        int total = quantity * prices.get(choice - 1);
        System.out.println("Your total payable amount: Rs " + total);
        scan.nextLine(); 
    }

    public void addFlavor(String table, String flavor, int price) {
        try (Connection conn = connect()) {
            String query = "INSERT INTO " + table + " (flavor_name, price) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, flavor);
                stmt.setInt(2, price);
                stmt.executeUpdate();
                System.out.println("Flavor added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFlavor(String table, String flavor) {
        try (Connection conn = connect()) {
            String query = "DELETE FROM " + table + " WHERE flavor_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, flavor);
                stmt.executeUpdate();
                System.out.println("Flavor removed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePrice(String table, String flavor, int newPrice) {
        try (Connection conn = connect()) {
            String query = "UPDATE " + table + " SET price = ? WHERE flavor_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, newPrice);
                stmt.setString(2, flavor);
                stmt.executeUpdate();
                System.out.println("Price updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class LoginPage {
    private final Scanner scan = new Scanner(System.in);
    private final Menu1 menu = new Menu1();

    public void login() {
        System.out.println("1. Login as Customer");
        System.out.println("2. Login as Manager");
        System.out.println("3. Exit");
    }

    public void customer() {
        while (true) {
            menu.displayMenu();  
            System.out.println("Enter your choice:");
            int choice = scan.nextInt();
            scan.nextLine(); 
            switch (choice) {
                case 1 -> menu.scoopMenu();
                case 2 -> menu.coneMenu();
                case 3 -> menu.tubMenu();
                case 4 -> menu.barMenu();
                case 5 -> {
                    System.out.println("Exiting customer menu.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void manager() {
        while (true) {
            System.out.println("Manager Menu:");
            System.out.println("1. Add Flavor");
            System.out.println("2. Remove Flavor");
            System.out.println("3. Update Price");
            System.out.println("4. Exit to Main Menu");
            int choice = scan.nextInt();
            scan.nextLine(); 
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter table (scoop, cone, tub, bar): ");
                    String table = scan.nextLine();
                    System.out.print("Enter flavor: ");
                    String flavor = scan.nextLine();
                    System.out.print("Enter price: ");
                    int price = scan.nextInt();
                    scan.nextLine();
                    menu.addFlavor(table, flavor, price);
                }
                case 2 -> {
                    System.out.print("Enter table (scoop, cone, tub, bar): ");
                    String table = scan.nextLine();
                    System.out.print("Enter flavor to remove: ");
                    String flavor = scan.nextLine();
                    menu.removeFlavor(table, flavor);
                }
                case 3 -> {
                    System.out.print("Enter table (scoop, cone, tub, bar): ");
                    String table = scan.nextLine();
                    System.out.print("Enter flavor to update: ");
                    String flavor = scan.nextLine();
                    System.out.print("Enter new price: ");
                    int newPrice = scan.nextInt();
                    scan.nextLine();
                    menu.updatePrice(table, flavor, newPrice);
                }
                case 4 -> {
                    System.out.println("Exiting manager menu.");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void mainMenu() {
        while (true) {
            login();
            System.out.println("Enter your choice:");
            int choice = scan.nextInt();
            scan.nextLine(); 
            switch (choice) {
                case 1 -> customer();
                case 2 -> manager();
                case 3 -> {
                    System.out.println("Thank you for visiting! Have a great day.");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }
}

class Main3 {
    public static void main(String[] args) {
        LoginPage main = new LoginPage();
        main.mainMenu();
    }
}

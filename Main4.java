import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Vector;

class MenuGUI {
    private final Vector<String> scoopFlavors = new Vector<>();
    private final Vector<Integer> scoopPrices = new Vector<>();
    private final Vector<String> coneFlavors = new Vector<>();
    private final Vector<Integer> conePrices = new Vector<>();
    private final Vector<String> tubFlavors = new Vector<>();
    private final Vector<Integer> tubPrices = new Vector<>();
    private final Vector<String> barFlavors = new Vector<>();
    private final Vector<Integer> barPrices = new Vector<>();

    
    private static final String DB_URL = "jdbc:mysql://database-1.czk2cmc4k8m4.us-east-1.rds.amazonaws.com:3306/icecream_shop2";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "ishadev08";

    public MenuGUI() {
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

    public void showMainMenu() {
        JFrame frame = new JFrame("Ice Cream Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JLabel label = new JLabel("* SATISFY YOUR SWEET CRAVINGS WITH EVERY SCOOP *", SwingConstants.CENTER);
        panel.add(label);

        JButton customerButton = new JButton("Login as Customer");
        customerButton.addActionListener(e -> showCustomerMenu(frame));
        panel.add(customerButton);

        JButton managerButton = new JButton("Login as Manager");
        managerButton.addActionListener(e -> showManagerMenu(frame));
        panel.add(managerButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showCustomerMenu(JFrame parentFrame) {
        parentFrame.setVisible(false);

        JFrame frame = new JFrame("Customer Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JLabel label = new JLabel("Choose an option:", SwingConstants.CENTER);
        panel.add(label);

        JButton scoopButton = new JButton("Scoop Menu");
        scoopButton.addActionListener(e -> showOrderMenu("Scoop", scoopFlavors, scoopPrices, frame));
        panel.add(scoopButton);

        JButton coneButton = new JButton("Cone Menu");
        coneButton.addActionListener(e -> showOrderMenu("Cone", coneFlavors, conePrices, frame));
        panel.add(coneButton);

        JButton tubButton = new JButton("Tub Menu");
        tubButton.addActionListener(e -> showOrderMenu("Tub", tubFlavors, tubPrices, frame));
        panel.add(tubButton);

        JButton barButton = new JButton("Bar Menu");
        barButton.addActionListener(e -> showOrderMenu("Bar", barFlavors, barPrices, frame));
        panel.add(barButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            frame.setVisible(false);
            parentFrame.setVisible(true);
        });
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showOrderMenu(String category, Vector<String> flavors, Vector<Integer> prices, JFrame parentFrame) {
        parentFrame.setVisible(false);

        JFrame frame = new JFrame(category + " Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(flavors.size() + 2, 1));

        JLabel label = new JLabel("Select a flavor:", SwingConstants.CENTER);
        panel.add(label);

        for (int i = 0; i < flavors.size(); i++) {
            String flavor = flavors.get(i);
            int price = prices.get(i);
            JButton flavorButton = new JButton(flavor + " - Rs " + price);
            flavorButton.addActionListener(e -> handleOrder(category, flavor, price, frame));
            panel.add(flavorButton);
        }

        JButton backButton = new JButton("Back to Customer Menu");
        backButton.addActionListener(e -> {
            frame.setVisible(false);
            parentFrame.setVisible(true);
        });
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void handleOrder(String category, String flavor, int price, JFrame parentFrame) {
        String quantityStr = JOptionPane.showInputDialog(parentFrame, "Enter quantity for " + flavor + ":");
        if (quantityStr != null) {
            try {
                int quantity = Integer.parseInt(quantityStr);
                int total = quantity * price;
                JOptionPane.showMessageDialog(parentFrame, "Your total is Rs " + total);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid quantity. Please enter a number.");
            }
        }
    }

    private void showManagerMenu(JFrame parentFrame) {
        parentFrame.setVisible(false);

        JFrame frame = new JFrame("Manager Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton addFlavorButton = new JButton("Add Flavor");
        addFlavorButton.addActionListener(e -> handleAddFlavor(frame));
        panel.add(addFlavorButton);

        JButton removeFlavorButton = new JButton("Remove Flavor");
        removeFlavorButton.addActionListener(e -> handleRemoveFlavor(frame));
        panel.add(removeFlavorButton);

        JButton updatePriceButton = new JButton("Update Price");
        updatePriceButton.addActionListener(e -> handleUpdatePrice(frame));
        panel.add(updatePriceButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            frame.setVisible(false);
            parentFrame.setVisible(true);
        });
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void handleAddFlavor(JFrame parentFrame) {
        String table = JOptionPane.showInputDialog(parentFrame, "Enter table (scoop, cone, tub, bar):");
        String flavor = JOptionPane.showInputDialog(parentFrame, "Enter flavor:");
        String priceStr = JOptionPane.showInputDialog(parentFrame, "Enter price:");
        if (table != null && flavor != null && priceStr != null) {
            try {
                int price = Integer.parseInt(priceStr);
                addFlavor(table, flavor, price);
                JOptionPane.showMessageDialog(parentFrame, "Flavor added successfully.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid price. Please enter a number.");
            }
        }
    }

    private void handleRemoveFlavor(JFrame parentFrame) {
        String table = JOptionPane.showInputDialog(parentFrame, "Enter table (scoop, cone, tub, bar):");
        String flavor = JOptionPane.showInputDialog(parentFrame, "Enter flavor to remove:");
        if (table != null && flavor != null) {
            removeFlavor(table, flavor);
            JOptionPane.showMessageDialog(parentFrame, "Flavor removed successfully.");
        }
    }

    private void handleUpdatePrice(JFrame parentFrame) {
        String table = JOptionPane.showInputDialog(parentFrame, "Enter table (scoop, cone, tub, bar):");
        String flavor = JOptionPane.showInputDialog(parentFrame, "Enter flavor to update:");
        String priceStr = JOptionPane.showInputDialog(parentFrame, "Enter new price:");
        if (table != null && flavor != null && priceStr != null) {
            try {
                int price = Integer.parseInt(priceStr);
                updatePrice(table, flavor, price);
                JOptionPane.showMessageDialog(parentFrame, "Price updated successfully.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid price. Please enter a number.");
            }
        }
    }

    public void addFlavor(String table, String flavor, int price) {
        try (Connection conn = connect()) {
            String query = "INSERT INTO " + table + " (flavor_name, price) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, flavor);
                stmt.setInt(2, price);
                stmt.executeUpdate();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Main4 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuGUI menu = new MenuGUI();
            menu.showMainMenu();
        });
    }
}


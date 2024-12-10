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

    public Menu1() {
        initializeFlavorsAndPrices();
    }

    private void initializeFlavorsAndPrices() {
        scoopFlavors.add("Kesar Pista");
        scoopFlavors.add("Malai Kulfi");
        scoopFlavors.add("Chocolate");
        scoopFlavors.add("Mango");
        scoopPrices.add(80);
        scoopPrices.add(60);
        scoopPrices.add(50);
        scoopPrices.add(100);

        coneFlavors.add("Red Velvet");
        coneFlavors.add("Double Chocolate");
        coneFlavors.add("Kesar Pista");
        coneFlavors.add("Black Currant");
        conePrices.add(120);
        conePrices.add(120);
        conePrices.add(100);
        conePrices.add(100);

        tubFlavors.add("Kesar Pista");
        tubFlavors.add("Malai Kulfi");
        tubFlavors.add("Chocolate");
        tubFlavors.add("Mango");
        tubPrices.add(80);
        tubPrices.add(60);
        tubPrices.add(50);
        tubPrices.add(100);

        barFlavors.add("Vanilla");
        barFlavors.add("Chocolate Bar");
        barFlavors.add("Almond Crush");
        barPrices.add(60);
        barPrices.add(60);
        barPrices.add(80);
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

    public void menu1() {
        while (true) {
            displayMenu();
            System.out.println("Enter your choice:");
            int choice = scan.nextInt();
            scan.nextLine(); 
            switch (choice) {
                case 1:
                    scoopMenu();
                    break;
                case 2:
                    coneMenu();
                    break;
                case 3:
                    tubMenu();
                    break;
                case 4:
                    barMenu();
                    break;
                case 5:
                    System.out.println("Thank you for visiting! Have a great day.");
                    return;

                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void addFlavor() {
        System.out.println("Choose the type (1: SCOOP, 2: CONE, 3: TUB, 4: BAR): ");
        int type = scan.nextInt();
        scan.nextLine(); 
        System.out.println("Enter the flavor name: ");
        String flavor = scan.nextLine();
        System.out.println("Enter the price: ");
        int price = scan.nextInt();
        scan.nextLine(); 

        switch (type) {
            case 1:
                scoopFlavors.add(flavor);
                scoopPrices.add(price);
                break;
            case 2:
                coneFlavors.add(flavor);
                conePrices.add(price);
                break;
            case 3:
                tubFlavors.add(flavor);
                tubPrices.add(price);
                break;
            case 4:
                barFlavors.add(flavor);
                barPrices.add(price);
                break;
            default:
                System.out.println("Invalid type selected.");
                return;
        }
        System.out.println("Flavor added successfully.");
    }

    public void removeFlavor() {
        System.out.println("Choose the type (1: SCOOP, 2: CONE, 3: TUB, 4: BAR): ");
        int type = scan.nextInt();
        scan.nextLine(); 

        Vector<String> flavors = null;
        Vector<Integer> prices = null;

        switch (type) {
            case 1:
                flavors = scoopFlavors;
                prices = scoopPrices;
                break;
            case 2:
                flavors = coneFlavors;
                prices = conePrices;
                break;
            case 3:
                flavors = tubFlavors;
                prices = tubPrices;
                break;
            case 4:
                flavors = barFlavors;
                prices = barPrices;
                break;
            default:
                System.out.println("Invalid type selected.");
                return;
        }

        System.out.println("Available Flavors:");
        for (int i = 0; i < flavors.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, flavors.get(i));
        }
        System.out.println("Enter the number of the flavor to remove:");
        int choice = scan.nextInt();
        if (choice < 1 || choice > flavors.size()) {
            System.out.println("Invalid choice. Please select again.");
            return;
        }
        flavors.remove(choice - 1);
        prices.remove(choice - 1);
        System.out.println("Flavor removed successfully.");
    }

    public void updatePrice() {
        System.out.println("Choose the type (1: SCOOP, 2: CONE, 3: TUB, 4: BAR): ");
        int type = scan.nextInt();
        scan.nextLine(); 

        Vector<String> flavors = null;
        Vector<Integer> prices = null;

        switch (type) {
            case 1:
                flavors = scoopFlavors;
                prices = scoopPrices;
                break;
            case 2:
                flavors = coneFlavors;
                prices = conePrices;
                break;
            case 3:
                flavors = tubFlavors;
                prices = tubPrices;
                break;
            case 4:
                flavors = barFlavors;
                prices = barPrices;
                break;
            default:
                System.out.println("Invalid type selected.");
                return;
        }

        System.out.println("Available Flavors:");
        for (int i = 0; i < flavors.size(); i++) {
            System.out.printf("%d. %s - %d/-\n", i + 1, flavors.get(i), prices.get(i));
        }
        System.out.println("Enter the number of the flavor to update the price:");
        int choice = scan.nextInt();
        if (choice < 1 || choice > flavors.size()) {
            System.out.println("Invalid choice. Please select again.");
            return;
        }
        System.out.println("Enter the new price:");
        int newPrice = scan.nextInt();
        prices.set(choice - 1, newPrice);
        System.out.println("Price updated successfully.");
    }
}

class LoginPage {
    private final String[] userID = {"chotabala", "isha"};
    private final int[] password = {18005, 18011};
    private final Scanner scan = new Scanner(System.in);
    private final Menu1 menu = new Menu1();

    public void login() {
        System.out.println("1. Login as Customer");
        System.out.println("2. Login as Manager");
        System.out.println("3. Exit");
    }

    public void customer() {
        menu.menu1();
    }

    public void manager() {
    System.out.println("Enter Manager name:");
    String name = scan.nextLine();
    System.out.println("Enter Password:");
    int pass = scan.nextInt();
    scan.nextLine(); 

    // Check if the name and password combination is correct
    boolean isValidUser = false;
    for (int i = 0; i < userID.length; i++) {
        if (userID[i].equals(name) && password[i] == pass) {
            isValidUser = true;
            break;
        }
    }

    if (!isValidUser) {
        System.out.println("ACCESS DENIED");
        return;
    }

    while (true) {
        System.out.println("Manager Menu:");
        System.out.println("1. Add Flavor");
        System.out.println("2. Remove Flavor");
        System.out.println("3. Update Price");
        System.out.println("4. Exit to Main Menu");
        int choice = scan.nextInt();
        scan.nextLine(); 
        switch (choice) {
            case 1:
                menu.addFlavor();
                break;
            case 2:
                menu.removeFlavor();
                break;
            case 3:
                menu.updatePrice();
                break;
            case 4:
                return;

            default:
                System.out.println("Invalid input. Please try again.");
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
                case 1:
                    customer();
                    break;
                case 2:
                    manager();
                    break;
                case 3:
                    System.out.println("Thank you for visiting! Have a great day.");
                    return;

                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
}


class Main2 {
    public static void main(String[] args) {
        LoginPage main = new LoginPage();
        main.mainMenu();
    }
}
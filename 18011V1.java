import java.util.Scanner;

class Menu1 {
    private final int[] scoopPrices = {80, 60, 50, 100, 100, 80};
    private final int[] conePrices = {120, 120, 100, 100, 100, 120};
    private final int[] tubPrices = {80, 60, 50, 100, 100, 80};
    private final int[] barPrices = {60, 60, 80};
    private final Scanner scan = new Scanner(System.in);

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
        String[] flavors = {"Kesar Pista", "Malai Kulfi", "Chocolate", "Mango", "Dark Chocolate", "Tender Coconut"};
        handleOrder(flavors, scoopPrices);
    }

    public void coneMenu() {
        String[] flavors = {"Red Velvet", "Double Chocolate", "Kesar Pista", "Black Currant", "Butter Scotch", "Chocolate Truffle"};
        handleOrder(flavors, conePrices);
    }

    public void tubMenu() {
        String[] flavors = {"Kesar Pista", "Malai Kulfi", "Chocolate", "Mango", "Dark Chocolate", "Tender Coconut"};
        handleOrder(flavors, tubPrices);
    }

    public void barMenu() {
        String[] flavors = {"Vanilla", "Chocolate Bar", "Almond Crush"};
        handleOrder(flavors, barPrices);
    }

    private void handleOrder(String[] flavors, int[] prices) {
        System.out.println("Available Flavours:");
        for (int i = 0; i < flavors.length; i++) {
            System.out.printf("%d. %s - %d/-\n", i + 1, flavors[i], prices[i]);
        }
        System.out.println("Which one would you like to order?");
        int choice = scan.nextInt();
        if (choice < 1 || choice > prices.length) {
            System.out.println("Invalid choice. Please select again.");
            scan.nextLine(); 
            return;
        }
        System.out.println("Please enter the quantity:");
        int quantity = scan.nextInt();
        int total = quantity * prices[choice - 1];
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
                    scan.close(); 
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
}

class LoginPage {
    private final String[] userID = {"chotabala", "isha"};
    private final int[] password = {18005, 18011};
    private final Scanner scanner = new Scanner(System.in);
    private final Menu1 menu = new Menu1();

    public void login() {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter Password:");
        int pass = scanner.nextInt();
        scanner.nextLine(); 

        // Check if the name and password combination is correct
        boolean isValidUser = false;
        for (int i = 0; i < userID.length; i++) {
            if (userID[i].equals(name) && password[i] == pass) {
                isValidUser = true;
                break;
            }
        }

        if (isValidUser) {
            menu.menu1();
        } else {
            System.out.println("ACCESS DENIED");
        }
    }
}

class Main1 {
    public static void main(String[] args) {
        LoginPage login = new LoginPage();
        login.login();
    }
}
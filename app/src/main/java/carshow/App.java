package carshow;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Console;

public class App {
    List<Product> products;
    List<UserAccount> userAccounts;
    List<Sale> sales;
    Scanner input;

    App() {
        products = new ArrayList<>();
        userAccounts = new ArrayList<>();
        sales = new ArrayList<>();
        input = new Scanner(System.in);
    }

    public static void main(String[] args) {
        App app = new App();

        while (app.userAccounts.isEmpty()) {
            System.out.println("Please create an admin account, since no one is available.");
            app.createAdminAccount();
        }

        while (true) {
            app.mainOptionsPage();
        }
    }

    void mainOptionsPage() {
        System.out.println("\n1. Login as Admin/Salesman/Costumer\n"
                .concat("2. List available vehicles\n")
                .concat("3. Search in available vehicles\n")
                .concat("0. Exit\n")
                .concat("Select an option:"));

        int option = this.input.nextInt();

        if (option == 0) {
            this.input.close();
            System.exit(0);
        } else if (option == 1) {
            this.loginPage();
        } else if (option == 2) {
            // TODO: Implement products list.
        } else if (option == 3) {
            // TODO: Implement products search.
        } else {
            System.out.println("Error: The selected option is invalid, please selection another option.");
        }
    }

    void loginPage() {
        System.out.print("Enter username:");
        String userName = this.input.next();
        System.out.print("Enter password:");
        String password = this.input.next();

        for (UserAccount user : userAccounts) {
            if (user.getUserName().equals(userName)) {
                if (user.validatePassword(password)) {
                    if (user instanceof AdminAccount) {
                        adminPage((AdminAccount) user);
                    } else if (user instanceof SalesManAccount) {
                        salesManPage((SalesManAccount) user);
                    } else if (user instanceof CostomerAccount) {
                        costomerPage((CostomerAccount) user);
                    }
                } else {
                    System.out.println("You password is incorrect, please login again.");
                }
                return;
            }
        }

        System.out.println("The username you enterd is not available.");
    }

    void adminPage(AdminAccount adminAccount) {
        System.out.println("Select an option:\n"
                .concat("1. Create new admin account\n")
                .concat("2. Create new sales man account\n")
                .concat("3. \n")
                .concat("0. Logout\n"));

        int option = this.input.nextInt();

        if (option == 0) {
            return;
        } else if (option == 1) {
            createAdminAccount();
        } else if (option == 2) {
            createSalesManAccount();
        } else if (option == 3) {
            // TODO: Implement more admin options.
        } else {
            System.out.println("Error: The selected option is invalid, please selection another option.");
        }
    }

    void salesManPage(SalesManAccount salesManAccount) {
        System.out.println("Select an option:\n"
                .concat("1. Create new costomer account\n")
                .concat("2. \n")
                .concat("3. \n")
                .concat("0. Logout\n"));

        int option = this.input.nextInt();

        if (option == 0) {
            return;
        } else if (option == 1) {
            createCostomerAccount();
        } else if (option == 2) {
            // TODO: Implement more sales man options.
        } else if (option == 3) {
        } else {
            System.out.println("Error: The selected option is invalid, please selection another option.");
        }
    }

    void costomerPage(CostomerAccount costomerAccount) {
        System.out.println("Select an option:\n"
                .concat("1. \n")
                .concat("0. Logout\n"));

        int option = this.input.nextInt();

        if (option == 0) {
            return;
        } else if (option == 1) {
            // TODO: Implement more costomer options.
        } else if (option == 2) {
        } else if (option == 3) {
        } else {
            System.out.println("Error: The selected option is invalid, please selection another option.");
        }
    }

    void createAdminAccount() {
        // TODO: Check for user name rules.
        // TODO: Catch errors and return to home page.
        System.out.print("Enter a user name (Must not contain white spaces):");
        String userName = input.next();
        System.out.print("Enter a password:");
        String password = readPassword();
        System.out.print("Enter a first name:");
        String firstName = input.next();
        System.out.print("Enter a last name:");
        String lastName = input.next();
        System.out.print("Enter a branch:");
        String branch = input.next();
        System.out.print("Enter a salary:");
        Double salary = input.nextDouble();
        System.out.print("Enter an office number:");
        int officeNumber = input.nextInt();

        AdminAccount newAccount = new AdminAccount(userName, password, firstName, lastName, branch, salary,
                officeNumber);

        userAccounts.add(newAccount);
        System.out.println("Admin account created with uuid: " + newAccount.getId());
    }

    void createSalesManAccount() {
        // TODO: Check for user name rules.
        // TODO: Catch errors and return to home page.
        System.out.print("Enter a user name (Must not contain white spaces):");
        String userName = input.next();
        System.out.print("Enter a password:");
        String password = readPassword();
        System.out.print("Enter a first name:");
        String firstName = input.next();
        System.out.print("Enter a last name:");
        String lastName = input.next();
        System.out.print("Enter a branch:");
        String branch = input.next();
        System.out.print("Enter a salary:");
        Double salary = input.nextDouble();
        System.out.println("\nProduct types:-\n".concat("- Car\n").concat("- Carvan\n").concat("- Bus"));
        System.out.print("Select a product type:");
        // TODO: Hnalde error when type not available.
        ProductType productType = ProductType.valueOf(input.next());

        SalesManAccount newSalesMan = new SalesManAccount(userName, password, firstName, lastName, branch, salary,
                productType);

        userAccounts.add(newSalesMan);
        System.out.println("Sales man account created with uuid: " + newSalesMan.getId());
    }

    void createCostomerAccount() {
        // TODO: Check for user name rules.
        // TODO: Catch errors and return to home page.
        System.out.print("Enter a user name (Must not contain white spaces):");
        String userName = input.next();
        System.out.print("Enter a password:");
        String password = readPassword();
        System.out.print("Enter a first name:");
        String firstName = input.next();
        System.out.print("Enter a last name:");
        String lastName = input.next();
        System.out.print("Enter a phone number:");
        String phoneNumber = input.next();
        System.out.print("Enter an email address:");
        // TODO: Check for valid email address.
        String emailAddress = input.next();

        CostomerAccount newCostomer = new CostomerAccount(userName, password, firstName, lastName, phoneNumber,
                emailAddress);

        userAccounts.add(newCostomer);
        System.out.println("Sales man account created with uuid: " + newCostomer.getId());
    }

    String readPassword() {
        Console console = System.console();
        if (console != null) {
            return System.console().readPassword().toString();
        } else {
            return this.input.next();
        }
    }
}

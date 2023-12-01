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
        System.out.print("\n1. Login as Admin/Salesman/Costumer\n"
                .concat("2. List available products\n")
                .concat("3. Search in all products\n")
                .concat("0. Exit (Reset the system)\n"));

        int option = readInt("Select an option: ");

        if (option == 0) {
            this.input.close();
            System.exit(0);
        } else if (option == 1) {
            this.loginPage();
        } else if (option == 2) {
            listProducts(1);
        } else if (option == 3) {
            String searchTerm = readString("Enter a search term: ");

            List<Product> matches = searchProducts(searchTerm);

            for (Product product : matches) {
                System.out.println(product);
            }

            if (matches.isEmpty()) {
                System.out.println("Your search term doesn't match any product, please use another term.");
            }
        } else {
            System.out.println("Error: The selected option is invalid, please selection another option.");
        }
    }

    void loginPage() {
        String userName = readString("\nEnter username: ");
        String password = readPassword("Enter password: ");

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

        System.out.println("The username you entered is not available.");
    }

    void listProducts() {
        if (this.products.isEmpty()) {
            System.out.println("Error: No products are sorted in the system.");
            return;
        } else {
            for (Product product : this.products) {
                System.out.println(product);
            }
        }
    }

    void listProducts(int minAvailableCount) {
        if (this.products.isEmpty()) {
            System.out.println("Error: No products are sorted in the system.");
            return;
        } else {
            for (Product product : this.products) {
                if (product.getAvailableCount() >= minAvailableCount) {
                    System.out.println(product);
                }
            }
        }
    }

    void listCostomers() {
        for (UserAccount user : this.userAccounts) {
            if (user instanceof CostomerAccount) {
                System.out.println(user);
            }
        }
    }

    List<Product> searchProducts(String searchTerm) {
        List<Product> matches = new ArrayList<>();

        for (Product product : this.products) {
            Searchable productS = (Searchable) product;

            if (productS.passSearchTerm(searchTerm)) {
                matches.add(product);
            }
        }

        return matches;
    }

    List<UserAccount> searchUsers(String searchTerm) {
        List<UserAccount> matches = new ArrayList<>();

        for (UserAccount user : this.userAccounts) {
            Searchable userS = (Searchable) user;

            if (userS.passSearchTerm(searchTerm)) {
                matches.add(user);
            }
        }

        return matches;
    }

    List<CostomerAccount> searchCostomers(String searchTerm) {
        List<CostomerAccount> matches = new ArrayList<>();

        for (UserAccount user : this.userAccounts) {
            if (user instanceof CostomerAccount) {
                Searchable userS = (Searchable) user;

                if (userS.passSearchTerm(searchTerm)) {
                    matches.add((CostomerAccount) user);
                }
            }
        }

        return matches;
    }

    void adminPage(AdminAccount adminAccount) {
        while (true) {
            System.out.print("\n1. Create new admin account\n"
                    .concat("2. Create new sales man account\n")
                    .concat("3. Add new product\n")
                    .concat("4. Update product's available count\n")
                    .concat("5. Apply a discount on a product\n")
                    .concat("6. Query total sales report\n")
                    .concat("7. Query available products report\n")
                    .concat("8. Query system users report\n")
                    .concat("9. List all system users\n")
                    .concat("10. Search in system users\n")
                    .concat("11. Change my password\n")
                    .concat("0. Logout\n"));

            int option = readInt("Select an option: ");

            if (option == 0) {
                return;
            } else if (option == 1) {
                createAdminAccount();
            } else if (option == 2) {
                createSalesManAccount();
            } else if (option == 3) {
                createProduct();
            } else if (option == 4) {
                Product targetProduct = selectProduct();

                if (targetProduct != null) {
                    System.out.println("Previous available count: " + targetProduct.getAvailableCount());

                    int newCount = readInt("Enter the new count: ");

                    targetProduct.setAvailableCount(newCount);
                }
            } else if (option == 5) {
                Product targetProduct = selectProduct();

                if (targetProduct != null) {
                    System.out.println("Current price: " + targetProduct.getPrice());

                    double discountPercentage = readDouble("Enter discount percentage: ");

                    targetProduct.applyDiscount(discountPercentage);
                }
            } else if (option == 6) {
                double totalIncome = 0;

                for (Sale sale : this.sales) {
                    totalIncome += sale.getTotalBill();
                }

                System.out.println("Number of sales: " + this.sales.size());
                System.out.println("Total income: " + totalIncome);
                if (!sales.isEmpty()) {
                    System.out.println("Last sale time: " + (sales.get(sales.size() - 1).getSaleDateString()));
                }
                // TODO: Print most soled product data and least sold product.
            } else if (option == 7) {
                int availableCount = 0;
                int totalPiecesCount = 0;

                for (Product product : this.products) {
                    if (product.getAvailableCount() > 0) {
                        availableCount += 1;
                    }
                    totalPiecesCount += product.getAvailableCount();
                }

                System.out.println("Number of products: " + products.size());
                System.out.println("Number of available products: " + availableCount);
                System.out.println("Number of all products pieces available: " + totalPiecesCount);
            } else if (option == 8) {
                int adminsCount = 0;
                int salesmenCount = 0;
                int costomersCount = 0;

                for (UserAccount user : this.userAccounts) {
                    if (user instanceof AdminAccount) {
                        adminsCount += 1;
                    } else if (user instanceof SalesManAccount) {
                        salesmenCount += 1;
                    } else if (user instanceof CostomerAccount) {
                        costomersCount += 1;
                    }
                }

                System.out.println("Total system users count: " + this.userAccounts.size());
                System.out.println("Admins count: " + adminsCount);
                System.out.println("Sales men count: " + salesmenCount);
                System.out.println("Customer count: " + costomersCount);
            } else if (option == 9) {
                for (UserAccount user : this.userAccounts) {
                    System.out.println(user);
                }
            } else if (option == 10) {
                String searchTerm = readString("Enter a search term: ");

                List<UserAccount> matches = searchUsers(searchTerm);

                for (UserAccount user : matches) {
                    System.out.println(user);
                }

                if (matches.isEmpty()) {
                    System.out.println("Your search term doesn't match any user, please use another term.");
                }
            } else if (option == 11) {
                changeUserPassword(adminAccount);
            } else {
                System.out.println("Error: The selected option is invalid, please selection another option.");
            }
        }
    }

    void salesManPage(SalesManAccount salesManAccount) {
        while (true) {
            System.out.print("\n1. Create new Customer account\n"
                    .concat("2. Sale a product to a Customer\n")
                    .concat("3. Search in Customer\n")
                    .concat("4. Change password\n")
                    .concat("5. Delete my account\n")
                    .concat("0. Logout\n"));

            int option = readInt("Select an option: ");

            if (option == 0) {
                return;
            } else if (option == 1) {
                createCostomerAccount();
            } else if (option == 2) {
                saleProduct(salesManAccount);
            } else if (option == 3) {
                String searchTerm = readString("Enter a search term: ");

                List<CostomerAccount> matches = searchCostomers(searchTerm);

                for (CostomerAccount costomer : matches) {
                    System.out.println(costomer);
                }

                if (matches.isEmpty()) {
                    System.out.println("Your search term doesn't match any Customer, please use another term.");
                }
            } else if (option == 4) {
                changeUserPassword(salesManAccount);
            } else if (option == 5) {
                deleteUserAccount(salesManAccount);
                System.out.println("You were logged out from your account.");
                return;
            } else {
                System.out.println("Error: The selected option is invalid, please selection another option.");
            }
        }
    }

    void costomerPage(CostomerAccount costomerAccount) {
        while (true) {
            System.out.print("\n1. Select and pay a product (self payment)\n"
                    .concat("2. Query previous payments")
                    .concat("3. Change password\n")
                    .concat("4. Delete my account\n")
                    .concat("0. Logout\n"));

            int option = readInt("Select an option: ");

            if (option == 0) {
                return;
            } else if (option == 1) {
                saleProduct(costomerAccount);
            } else if (option == 2) {
                for (Sale sale : this.sales) {
                    if (sale.getCostomer() == costomerAccount) {
                        System.out.println(sale);
                    }
                }
            } else if (option == 3) {
                changeUserPassword(costomerAccount);
            } else if (option == 4) {
                deleteUserAccount(costomerAccount);
                System.out.println("Your was logged out from your account.");
                return;
            } else {
                System.out.println("Error: The selected option is invalid, please selection another option.");
            }
        }
    }

    void createAdminAccount() {
        String userName = readString("Enter a user name: ");
        String password = readPassword("Enter a password: ");
        String firstName = readString("Enter a first name: ");
        String lastName = readString("Enter a last name: ");
        String branch = readString("Enter a branch: ");
        Double salary = readDouble("Enter a salary: ");
        int officeNumber = readInt("Enter an office number: ");

        AdminAccount newAccount = new AdminAccount(userName, password, firstName, lastName, branch, salary,
                officeNumber);

        userAccounts.add(newAccount);
        System.out.println("Admin account created with uuid: " + newAccount.getId());
    }

    void createSalesManAccount() {
        String userName = readString("Enter a user name: ");
        String password = readPassword("Enter a password: ");
        String firstName = readString("Enter a first name: ");
        String lastName = readString("Enter a last name: ");
        String branch = readString("Enter a branch: ");
        Double salary = readDouble("Enter a salary: ");
        ProductType productType = selectProductType();

        SalesManAccount newSalesMan = new SalesManAccount(userName, password, firstName, lastName, branch, salary,
                productType);

        userAccounts.add(newSalesMan);
        System.out.println("Sales man account created with uuid: " + newSalesMan.getId());
    }

    void createCostomerAccount() {
        String userName = readString("Enter a user name: ");
        String password = readPassword("Enter a password: ");
        String firstName = readString("Enter a first name: ");
        String lastName = readString("Enter a last name: ");
        String phoneNumber = readPhoneNumber("Enter a phone number: ");
        String emailAddress = readEmailAddress("Enter an email address: ");

        CostomerAccount newCostomer = new CostomerAccount(userName, password, firstName, lastName, phoneNumber,
                emailAddress);

        userAccounts.add(newCostomer);
        System.out.println("Sales man account created with uuid: " + newCostomer.getId());
    }

    String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            System.out.print(prompt);
            return System.console().readPassword().toString();
        } else {
            return readString(prompt);
        }
    }

    String readPhoneNumber(String prompt) {
        // TODO: Check for valid phone number.
        return readString(prompt);
    }

    String readEmailAddress(String prompt) {
        // TODO: Check for valid email address.
        return readString(prompt);
    }

    String readString(String prompt) {
        System.out.print(prompt);

        return this.input.nextLine();
    }

    int readInt(String prompt) {
        int integer;

        while (true) {
            System.out.print(prompt);
            try {
                integer = Integer.parseInt(this.input.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Please enter a valid integer number.");
            }
        }

        return integer;
    }

    double readDouble(String prompt) {
        double num;

        while (true) {
            System.out.print(prompt);
            try {
                num = Double.parseDouble(this.input.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Please enter a valid double number.");
            }
        }

        return num;
    }

    boolean readBoolean(String prompt) {
        boolean bool;

        while (true) {
            System.out.print(prompt);
            try {
                bool = Boolean.parseBoolean(this.input.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Please enter a valid boolean (ether `true` or `false`).");
            }
        }

        return bool;
    }

    void changeUserPassword(UserAccount user) {
        while (true) {
            String newPassword = readPassword("Enter a new password: ");
            if (newPassword.equals(readPassword("Repeat the password: "))) {
                user.setPassword(newPassword);
                System.out.println("Your password was updated.");
                break;
            } else {
                System.out.println("The passwords doesn't match, please try again.");
            }
        }
    }

    void deleteUserAccount(UserAccount user) {
        System.out.println(
                "Warning: Your account will be deleted, but your data associated with sales will stay in the system.");
        this.userAccounts.remove(user);
        System.out.println(
                "Account " + user.getId() + " with userName: " + user.getUserName() + " was deleted successfully.");
    }

    void saleProduct(SalesManAccount salesMan) {
        Product targetProduct = selectProduct();
        CostomerAccount targetCostomer = selectCostomer();
        PaymentMethod targetPaymentMethod = selectPaymentMethod();

        // TODO: Provide way to apply a limited disccount per sale by the sales man.
        if (targetProduct != null && targetCostomer != null) {
            this.sales
                    .add(new Sale(targetCostomer, salesMan, targetProduct, targetProduct.getPrice(),
                            targetPaymentMethod));
        }
    }

    void saleProduct(CostomerAccount costomer) {
        Product targetProduct = selectProduct();
        PaymentMethod targetPaymentMethod = selectPaymentMethod();

        if (targetProduct != null) {
            this.sales.add(new Sale(costomer, targetProduct, targetProduct.getPrice(), targetPaymentMethod));
        }
    }

    PaymentMethod selectPaymentMethod() {
        System.out.printf("1. %s\n2. %s\n3. %s\n4. %s\n", PaymentMethod.Mada, PaymentMethod.Visa, PaymentMethod.Cache,
                PaymentMethod.Mastercard);

        PaymentMethod paymentMethod;

        while (true) {
            System.out.print("Select a payment method: ");
            try {
                paymentMethod = PaymentMethod.valueOf(this.input.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid choise, please try again.");
            }
        }

        return paymentMethod;
    }

    ProductType selectProductType() {
        System.out.printf("\nProduct types:-\n- %s\n- %s\n- %s\n", ProductType.Car, ProductType.Carvan,
                ProductType.Bus);

        ProductType productType;

        while (true) {
            System.out.print("Select a product type: ");
            try {
                productType = ProductType.valueOf(this.input.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid choise, please try again.");
            }
        }

        return productType;
    }

    FuelType selectFuelType() {
        System.out.printf("Fuel Types:-\n- %s\n- %s\n- %s\n- %s\n", FuelType.Diesel, FuelType.Ethanol,
                FuelType.Gasoline, FuelType.CompressedNatural);

        FuelType fuelType;

        while (true) {
            System.out.print("Select a fuel type: ");
            try {
                fuelType = FuelType.valueOf(this.input.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid choise, please try again.");
            }
        }

        return fuelType;
    }

    Product selectProduct() {
        if (this.products.isEmpty()) {
            System.out.println("Error: There is not product in the system, please create one before.");
            return null;
        }
        listProducts(1);

        while (true) {
            String productID = readString("Select a product by ID: ");

            for (Product product : this.products) {
                if (product.getId().equals(productID)) {
                    return product;
                }
            }
            System.out.println("Error: This ID doesn't point to any product, please try again.");
        }
    }

    CostomerAccount selectCostomer() {
        boolean empty = true;
        for (UserAccount user : this.userAccounts) {
            if (user instanceof CostomerAccount) {
                empty = false;
            }
        }
        if (empty) {
            System.out.println("Error: There is no Customer in the system, please create one before.");
            return null;
        }

        listCostomers();

        while (true) {
            String id = readString("Select a Customer ID: ");
            for (UserAccount user : this.userAccounts) {
                if (user instanceof CostomerAccount) {
                    CostomerAccount costomer = (CostomerAccount) user;
                    if (costomer.getId().equals(id)) {
                        return costomer;
                    }
                }
            }
            System.out.println("Error No Customer has this ID, please try again.");
        }
    }

    void createProduct() {
        String productName = readString("Enter a product name: ");
        double price = readDouble("Enter a price: ");
        int availableCount = readInt("Available Count: ");

        // There is only one product type: Vehicle.
        createVehicle(productName, price, availableCount);
    }

    void createVehicle(String vehicleName, double price, int availableCount) {
        ProductType productType = selectProductType();

        int year = readInt("Year: ");
        String model = readString("Model: ");
        String vehicleId = readString("VIN: ");
        String color = readString("Color: ");
        String manufacturer = readString("Manufacturer: ");
        FuelType fuelType = selectFuelType();

        switch (productType) {
            case Car:
                createCar(vehicleName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);
                break;
            case Carvan:
                createCarvan(vehicleName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);
                break;
            case Bus:
                createBus(vehicleName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);
                break;
        }
    }

    void createCar(String carName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType) {
        boolean hasSencsors = readBoolean("Has Sensors (true/false): ");
        boolean hasCameras = readBoolean("Has Cameras (true/false): ");
        boolean hasBlindSpotRadar = readBoolean("Has BlindSpot Radar (true/false): ");
        String shifterType = readString("Shifter Type: ");

        Car newCar = new Car(carName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType,
                hasSencsors, hasCameras, hasBlindSpotRadar, shifterType);

        this.products.add(newCar);
        System.out.println("Product Car added with uuid: " + newCar.getId());
    }

    void createCarvan(String carvanName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType) {
        int numberOfRooms = readInt("Number of rooms: ");
        boolean hasKitchen = readBoolean("Has Kitchen (true/false): ");
        boolean hasBathroom = readBoolean("Has Bathroom (true/false): ");
        double waterCapacity = readDouble("Water Capacity: ");

        Carvan newCarvan = new Carvan(carvanName, price, availableCount, year, model, vehicleId, color, manufacturer,
                numberOfRooms, hasKitchen, hasBathroom, waterCapacity, fuelType);

        this.products.add(newCarvan);
        System.out.println("Product Carvan added with uuid: " + newCarvan.getId());
    }

    void createBus(String busName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType) {
        int passengerCapacity = readInt("Passenger Capacity: ");
        boolean isDoubleDecker = readBoolean("Is Double Decker (true/false): ");
        boolean hasWifi = readBoolean("Has WiFi (true/false): ");
        boolean hasBathroom = readBoolean("Has Bathroom (true/false): ");

        Bus newBus = new Bus(busName, price, availableCount, year, model, vehicleId, color, manufacturer,
                passengerCapacity, isDoubleDecker, hasWifi, hasBathroom, fuelType);

        this.products.add(newBus);
        System.out.println("Product Bus added with uuid: " + newBus.getId());
    }
}

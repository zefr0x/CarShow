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
                .concat("0. Exit (Reset the system)\n")
                .concat("Select an option: "));

        int option = Integer.valueOf(this.input.nextLine());

        if (option == 0) {
            this.input.close();
            System.exit(0);
        } else if (option == 1) {
            this.loginPage();
        } else if (option == 2) {
            listProducts(1);
        } else if (option == 3) {
            System.out.print("Enter a search term: ");
            String searchTerm = input.nextLine();

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
        System.out.print("\nEnter username: ");
        String userName = this.input.nextLine();
        System.out.print("Enter password: ");
        String password = this.input.nextLine();

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

    void listProducts() {
        if (this.products.isEmpty()) {
            System.out.println("Error: No products are sotred in the system.");
            return;
        } else {
            for (Product product : this.products) {
                System.out.println(product);
            }
        }
    }

    void listProducts(int minAvailableCount) {
        if (this.products.isEmpty()) {
            System.out.println("Error: No products are sotred in the system.");
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
                    .concat("5. Applay a disscount on a product\n")
                    .concat("6. Query total sales report\n")
                    .concat("7. Query available products report\n")
                    .concat("8. Query system users report\n")
                    .concat("9. List all system users\n")
                    .concat("10. Search in system users\n")
                    .concat("11. Change my password\n")
                    .concat("0. Logout\n")
                    .concat("Select an option: "));

            int option = Integer.valueOf(this.input.nextLine());

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
                    System.out.println("Previos available count: " + targetProduct.getAvailableCount());

                    System.out.print("Enter the new count: ");
                    int newCount = Integer.valueOf(input.nextLine());

                    targetProduct.setAvailableCount(newCount);
                }
            } else if (option == 5) {
                Product targetProduct = selectProduct();

                if (targetProduct != null) {
                    System.out.println("Current price: " + targetProduct.getPrice());

                    System.out.print("Enter discount percentage: ");
                    double discountPercentage = Double.valueOf(input.nextLine());

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
                System.out.println("Cosomers count: " + costomersCount);
            } else if (option == 9) {
                for (UserAccount user : this.userAccounts) {
                    System.out.println(user);
                }
            } else if (option == 10) {
                System.out.print("Enter a search term: ");
                String searchTerm = input.nextLine();

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
            System.out.print("\n1. Create new costomer account\n"
                    .concat("2. Sale a product to a costomer\n")
                    .concat("3. Search in costomers\n")
                    .concat("4. Change password\n")
                    .concat("5. Delete my account\n")
                    .concat("0. Logout\n")
                    .concat("Select an option: "));

            int option = Integer.valueOf(this.input.nextLine());

            if (option == 0) {
                return;
            } else if (option == 1) {
                createCostomerAccount();
            } else if (option == 2) {
                saleProduct(salesManAccount);
            } else if (option == 3) {
                System.out.print("Enter a search term: ");
                String searchTerm = input.nextLine();

                List<CostomerAccount> matches = searchCostomers(searchTerm);

                for (CostomerAccount costomer : matches) {
                    System.out.println(costomer);
                }

                if (matches.isEmpty()) {
                    System.out.println("Your search term doesn't match any costomer, please use another term.");
                }
            } else if (option == 4) {
                changeUserPassword(salesManAccount);
            } else if (option == 5) {
                deleteUserAccount(salesManAccount);
                System.out.println("You was loged out from your account.");
                return;
            } else {
                System.out.println("Error: The selected option is invalid, please selection another option.");
            }
        }
    }

    void costomerPage(CostomerAccount costomerAccount) {
        while (true) {
            System.out.print("\n1. Select and pay a product (self payment)\n"
                    .concat("2. Query previos payments")
                    .concat("3. Change password\n")
                    .concat("4. Delete my account\n")
                    .concat("0. Logout\n")
                    .concat("Select an option:"));

            int option = Integer.valueOf(this.input.nextLine());

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
                System.out.println("Your was loged out from your account.");
                return;
            } else {
                System.out.println("Error: The selected option is invalid, please selection another option.");
            }
        }
    }

    void createAdminAccount() {
        System.out.print("Enter a user name (Must not contain white spaces): ");
        String userName = input.nextLine();
        System.out.print("Enter a password: ");
        String password = readPassword();
        System.out.print("Enter a first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter a last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter a branch: ");
        String branch = input.nextLine();
        System.out.print("Enter a salary: ");
        Double salary = Double.valueOf(input.nextLine());
        System.out.print("Enter an office number: ");
        int officeNumber = Integer.valueOf(input.nextLine());

        AdminAccount newAccount = new AdminAccount(userName, password, firstName, lastName, branch, salary,
                officeNumber);

        userAccounts.add(newAccount);
        System.out.println("Admin account created with uuid: " + newAccount.getId());
    }

    void createSalesManAccount() {
        System.out.print("Enter a user name (Must not contain white spaces): ");
        String userName = input.nextLine();
        System.out.print("Enter a password: ");
        String password = readPassword();
        System.out.print("Enter a first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter a last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter a branch: ");
        String branch = input.nextLine();
        System.out.print("Enter a salary: ");
        Double salary = Double.valueOf(input.nextLine());
        ProductType productType = selectProductType();

        SalesManAccount newSalesMan = new SalesManAccount(userName, password, firstName, lastName, branch, salary,
                productType);

        userAccounts.add(newSalesMan);
        System.out.println("Sales man account created with uuid: " + newSalesMan.getId());
    }

    void createCostomerAccount() {
        System.out.print("Enter a user name (Must not contain white spaces): ");
        String userName = input.nextLine();
        System.out.print("Enter a password: ");
        String password = readPassword();
        System.out.print("Enter a first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter a last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter a phone number: ");
        String phoneNumber = input.nextLine();
        System.out.print("Enter an email address: ");
        // TODO: Check for valid email address.
        String emailAddress = input.nextLine();

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
            return this.input.nextLine();
        }
    }

    void changeUserPassword(UserAccount user) {
        while (true) {
            System.out.print("Enter a new password: ");
            String newPassword = readPassword();
            System.out.println("Repeat the password: ");
            if (newPassword.equals(readPassword())) {
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
                "Account " + user.getId() + " with userName: " + user.getUserName() + " was deleted sucessfully.");
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
        System.out.print("Select a payment method: ");

        return PaymentMethod.valueOf(input.nextLine());
    }

    ProductType selectProductType() {
        System.out.printf("\nProduct types:-\n- %s\n- %s\n- %s\n", ProductType.Car, ProductType.Carvan,
                ProductType.Bus);
        System.out.print("Select a product type: ");

        return ProductType.valueOf(input.nextLine());
    }

    Product selectProduct() {
        if (this.products.isEmpty()) {
            System.out.println("Error: There is not product in the system, please create one before.");
            return null;
        }
        listProducts(1);

        while (true) {
            System.out.print("Select a product by ID: ");
            String productID = input.nextLine();

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
            System.out.println("Error: There is no costomer in the system, pleace create one before.");
            return null;
        }

        listCostomers();

        while (true) {
            System.out.print("Select a costomer ID: ");
            String id = input.nextLine();
            for (UserAccount user : this.userAccounts) {
                if (user instanceof CostomerAccount) {
                    CostomerAccount costomer = (CostomerAccount) user;
                    if (costomer.getId().equals(id)) {
                        return costomer;
                    }
                }
            }
            System.out.println("Error No costomer has this ID, please try again.");
        }
    }

    void createProduct() {
        System.out.print("Enter a product name: ");
        String productName = input.nextLine();
        System.out.print("Enter a price: ");
        double price = Double.valueOf(input.nextLine());
        System.out.print("Available Count: ");
        int availableCount = Integer.valueOf(input.nextLine());

        // There is only one product type: Vehicle.
        createVehicle(productName, price, availableCount);
    }

    void createVehicle(String vehicleName, double price, int availableCount) {
        ProductType productType = selectProductType();

        System.out.print("Year: ");
        int year = Integer.valueOf(input.nextLine());
        System.out.print("Model: ");
        String model = input.nextLine();
        System.out.print("VIN: ");
        String vehicleId = input.nextLine();
        System.out.print("Color: ");
        String color = input.nextLine();
        System.out.print("Manufacturer: ");
        String manufacturer = input.nextLine();
        System.out.printf("Fuel Types:-\n- %s\n- %s\n- %s\n- %s\n", FuelType.Diesel, FuelType.Ethanol,
                FuelType.Gasoline, FuelType.CompressedNatural);
        System.out.print("Select a fuel type: ");
        FuelType fuelType = FuelType.valueOf(input.nextLine());

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
        System.out.print("Has Sencsors (true/false): ");
        boolean hasSencsors = Boolean.valueOf(input.nextLine());
        System.out.print("Has Cameras (true/false): ");
        boolean hasCameras = Boolean.valueOf(input.nextLine());
        System.out.print("Has BlindSpot Radar (true/false): ");
        boolean hasBlindSpotRadar = Boolean.valueOf(input.nextLine());
        System.out.print("Shifter Type: ");
        String shifterType = input.nextLine();

        Car newCar = new Car(carName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType,
                hasSencsors, hasCameras, hasBlindSpotRadar, shifterType);

        this.products.add(newCar);
        System.out.println("Product Car added with uuid: " + newCar.getId());
    }

    void createCarvan(String carvanName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType) {
        System.out.print("Number of rooms: ");
        int numberOfRooms = Integer.valueOf(input.nextLine());
        System.out.print("Has Kitchen (true/false): ");
        boolean hasKitchen = Boolean.valueOf(input.nextLine());
        System.out.print("Has Bathroom (true/false): ");
        boolean hasBathroom = Boolean.valueOf(input.nextLine());
        System.out.print("Water Capacity: ");
        double waterCapacity = Double.valueOf(input.nextLine());

        Carvan newCarvan = new Carvan(carvanName, price, availableCount, year, model, vehicleId, color, manufacturer,
                numberOfRooms, hasKitchen, hasBathroom, waterCapacity, fuelType);

        this.products.add(newCarvan);
        System.out.println("Product Carvan added with uuid: " + newCarvan.getId());
    }

    void createBus(String busName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType) {
        System.out.print("Passenger Capacity: ");
        int passengerCapacity = Integer.valueOf(input.nextLine());
        System.out.print("Is Double Decker (true/false): ");
        boolean isDoubleDecker = Boolean.valueOf(input.nextLine());
        System.out.print("Has WiFi (true/false): ");
        boolean hasWifi = Boolean.valueOf(input.nextLine());
        System.out.print("Has Bathroom (true/false): ");
        boolean hasBathroom = Boolean.valueOf(input.nextLine());

        Bus newBus = new Bus(busName, price, availableCount, year, model, vehicleId, color, manufacturer,
                passengerCapacity, isDoubleDecker, hasWifi, hasBathroom, fuelType);

        this.products.add(newBus);
        System.out.println("Product Bus added with uuid: " + newBus.getId());
    }
}

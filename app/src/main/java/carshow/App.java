package carshow;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Console;

// FIX: Handle multible line input.

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
                .concat("0. Exit (Reset the system)\n")
                .concat("Select an option: "));

        int option = this.input.nextInt();

        if (option == 0) {
            this.input.close();
            System.exit(0);
        } else if (option == 1) {
            this.loginPage();
        } else if (option == 2) {
            for (Product product : this.products) {
                if (product.getAvailableCount() > 0) {
                    System.out.println(product);
                }
            }
        } else if (option == 3) {
            // TODO: Implement products search.
        } else {
            System.out.println("Error: The selected option is invalid, please selection another option.");
        }
    }

    void loginPage() {
        System.out.print("Enter username: ");
        String userName = this.input.next();
        System.out.print("Enter password: ");
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
        while (true) {
            System.out.println("1. Create new admin account\n"
                    .concat("2. Create new sales man account\n")
                    .concat("3. Add new product\n")
                    .concat("4. Update product's available count\n")
                    .concat("5. Query total sales report\n")
                    .concat("6. Query available products report\n")
                    .concat("7. Query costomers report\n")
                    .concat("8. Applay a disscount on a product\n")
                    .concat("9. Change password\n")
                    .concat("0. Logout\n")
                    .concat("Select an option: "));

            int option = this.input.nextInt();

            if (option == 0) {
                return;
            } else if (option == 1) {
                createAdminAccount();
            } else if (option == 2) {
                createSalesManAccount();
            } else if (option == 3) {
                createProduct();
            } else if (option == 4) {
                // TODO: Implement more admin options.
            } else if (option == 5) {
            } else if (option == 6) {
            } else {
                System.out.println("Error: The selected option is invalid, please selection another option.");
            }
        }
    }

    void salesManPage(SalesManAccount salesManAccount) {
        while (true) {
            System.out.println("1. Create new costomer account\n"
                    .concat("2. Sale a product to a costomer\n")
                    .concat("3. Change password\n")
                    .concat("4. Delete my account\n")
                    .concat("0. Logout\n").concat("Select an option: "));

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
    }

    void costomerPage(CostomerAccount costomerAccount) {
        while (true) {
            System.out.println("1. Select and pay a product (self payment)\n"
                    .concat("2. Query previos payments")
                    .concat("3. Change password\n")
                    .concat("4. Delete my account\n")
                    .concat("0. Logout\n")
                    .concat("Select an option:"));

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
    }

    void createAdminAccount() {
        // TODO: Check for user name rules.
        // TODO: Catch errors and return to home page.
        System.out.print("Enter a user name (Must not contain white spaces): ");
        String userName = input.next();
        System.out.print("Enter a password: ");
        String password = readPassword();
        System.out.print("Enter a first name: ");
        String firstName = input.next();
        System.out.print("Enter a last name: ");
        String lastName = input.next();
        System.out.print("Enter a branch: ");
        String branch = input.next();
        System.out.print("Enter a salary: ");
        Double salary = input.nextDouble();
        System.out.print("Enter an office number: ");
        int officeNumber = input.nextInt();

        AdminAccount newAccount = new AdminAccount(userName, password, firstName, lastName, branch, salary,
                officeNumber);

        userAccounts.add(newAccount);
        System.out.println("Admin account created with uuid: " + newAccount.getId());
    }

    void createSalesManAccount() {
        // TODO: Check for user name rules.
        // TODO: Catch errors and return to home page.
        System.out.print("Enter a user name (Must not contain white spaces): ");
        String userName = input.next();
        System.out.print("Enter a password: ");
        String password = readPassword();
        System.out.print("Enter a first name: ");
        String firstName = input.next();
        System.out.print("Enter a last name: ");
        String lastName = input.next();
        System.out.print("Enter a branch: ");
        String branch = input.next();
        System.out.print("Enter a salary: ");
        Double salary = input.nextDouble();
        System.out.println("\nProduct types:-\n".concat("- Car\n").concat("- Carvan\n").concat("- Bus"));
        System.out.print("Select a product type: ");
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
        System.out.print("Enter a user name (Must not contain white spaces): ");
        String userName = input.next();
        System.out.print("Enter a password: ");
        String password = readPassword();
        System.out.print("Enter a first name: ");
        String firstName = input.next();
        System.out.print("Enter a last name: ");
        String lastName = input.next();
        System.out.print("Enter a phone number: ");
        String phoneNumber = input.next();
        System.out.print("Enter an email address: ");
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

    void createProduct() {
        System.out.print("Enter a product name: ");
        String productName = input.next();
        System.out.print("Enter a price: ");
        double price = input.nextDouble();
        System.out.print("Available Count: ");
        int availableCount = input.nextInt();

        // There is only one product type: Vehicle.
        createVehicle(productName, price, availableCount);
    }

    void createVehicle(String vehicleName, double price, int availableCount) {
        System.out.println("\nProduct types:-\n".concat("- Car\n").concat("- Carvan\n").concat("- Bus"));
        System.out.print("Select a product type: ");
        // TODO: Hnalde error when type not available.
        ProductType productType = ProductType.valueOf(input.next());

        System.out.print("Year: ");
        int year = input.nextInt();
        System.out.print("Model: ");
        String model = input.next();
        System.out.print("VIN: ");
        String vehicleId = input.next();
        System.out.print("Color: ");
        String color = input.next();
        System.out.print("Manufacturer: ");
        String manufacturer = input.next();
        // FIX: Show options.
        System.out.print("Fuel Type: ");
        // TODO: Hnalde error when type not available.
        FuelType fuelType = FuelType.valueOf(input.next());

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
        // TODO: Handle InputMismatchException.
        System.out.print("Has Sencsors (true/false): ");
        boolean hasSencsors = input.nextBoolean();
        System.out.print("Has Cameras (true/false): ");
        boolean hasCameras = input.nextBoolean();
        System.out.print("Has BlindSpot Radar (true/false): ");
        boolean hasBlindSpotRadar = input.nextBoolean();
        System.out.print("Shifter Type: ");
        String shifterType = input.next();

        Car newCar = new Car(carName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType,
                hasSencsors, hasCameras, hasBlindSpotRadar, shifterType);

        this.products.add(newCar);
        System.out.println("Product Car added with uuid: " + newCar.getId());
    }

    void createCarvan(String carvanName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType) {
        System.out.print("Number of rooms: ");
        int numberOfRooms = input.nextInt();
        // TODO: Handle InputMismatchException.
        System.out.print("Has Kitchen (true/false): ");
        boolean hasKitchen = input.nextBoolean();
        System.out.print("Has Bathroom (true/false): ");
        boolean hasBathroom = input.nextBoolean();
        System.out.print("Water Capacity: ");
        double waterCapacity = input.nextDouble();

        Carvan newCarvan = new Carvan(carvanName, price, availableCount, year, model, vehicleId, color, manufacturer,
                numberOfRooms, hasKitchen, hasBathroom, waterCapacity, fuelType);

        this.products.add(newCarvan);
        System.out.println("Product Carvan added with uuid: " + newCarvan.getId());
    }

    void createBus(String busName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType) {
        System.out.print("Passenger Capacity: ");
        int passengerCapacity = input.nextInt();
        System.out.print("Is Double Decker (true/false): ");
        boolean isDoubleDecker = input.nextBoolean();
        System.out.print("Has WiFi (true/false): ");
        boolean hasWifi = input.nextBoolean();
        System.out.print("Has Bathroom (true/false): ");
        boolean hasBathroom = input.nextBoolean();

        Bus newBus = new Bus(busName, price, availableCount, year, model, vehicleId, color, manufacturer,
                passengerCapacity, isDoubleDecker, hasWifi, hasBathroom, fuelType);

        this.products.add(newBus);
        System.out.println("Product Bus added with uuid: " + newBus.getId());
    }
}

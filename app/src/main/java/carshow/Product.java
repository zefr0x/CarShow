package carshow;

import java.util.UUID;

enum FuelType {
    Gasoline,
    Diesel,
    CompressedNatural,
    Ethanol,
}

enum ProductType {
    Car,
    Carvan,
    Bus,
}

class Product {
    private String id;
    private String productName;
    private double price;
    private int availableCount; // number of available products

    Product(String productName, double productPrice, int productAvailableCount) {
        setProductName(productName);
        setPrice(productPrice);
        setAvailableCount(productAvailableCount);

        this.id = UUID.randomUUID().toString();
    }

    private void setProductName(String productName) {
        if (!(productName.contains("!") || productName.contains("@"))) {
            this.productName = productName;
        } else {
            throw new IllegalArgumentException("Product name cannot contain '!' or '@'.");
        }
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }

    public void setAvailableCount(int availableCount) {
        if (availableCount >= 0) {
            this.availableCount = availableCount;
        } else {
            throw new IllegalArgumentException("Number of available products cannot be less than 0");
        }
    }

    public void incrementAvailableCount() {
        this.availableCount++;
    }

    public void decrementAvailableCount() {
        if (this.availableCount > 0) {
            this.availableCount--;
        } else {
            throw new IllegalArgumentException("Number of available products cannot be less than 0");
        }
    }

    public String getId() {
        return this.id;
    }

    public String getProductName() {
        return this.productName;
    }

    public double getPrice() {
        return this.price;
    }

    public int getAvailableCount() {
        return this.availableCount;
    }

    public void applyDiscount(double discountPercentage) {
        if (discountPercentage <= 100 || discountPercentage >= 0) {
            this.price = this.price * (100 - discountPercentage);
        } else {
            throw new IllegalArgumentException("Discount percentage can only be between 0 and 100");
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " / " + "ID: " + this.id + ", Name: " + this.productName + ", Price: "
                + this.price + ", Available Count: " + this.availableCount;
    }
}

class Vehicle extends Product {
    private int year;
    private String model;
    private String vehicleIdentificationNumber;
    private String color;
    private String manufacturer;
    private FuelType fuelType;

    Vehicle(String vehicleName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType) {
        super(vehicleName, price, availableCount);

        this.year = year;
        this.model = model;
        this.vehicleIdentificationNumber = vehicleId;
        this.color = color;
        this.manufacturer = manufacturer;
        this.fuelType = fuelType;
    }

    public int getYear() {
        return this.year;
    }

    public String getModel() {
        return this.model;
    }

    public String getVehicleIdentificationNumber() {
        return this.vehicleIdentificationNumber;
    }

    public String getColor() {
        return this.color;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }

    @Override
    public String toString() {
        return super.toString() + ", Year: " + this.year + ", Model: " + this.model + ", VIN: "
                + this.vehicleIdentificationNumber + ", Color: " + this.color + ", Manufacturer: " + this.manufacturer
                + ", Fuel Type: " + this.fuelType.toString();
    }
}

class Car extends Vehicle implements Searchable {
    private boolean hasSencsors;
    private boolean hasCameras;
    private boolean hasBlindSpotRadar;
    private String shifterType;

    Car(String carName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, FuelType fuelType, boolean hasSencsors, boolean hasCameras,
            boolean hasBlindSpotRadar, String shifterType) {
        super(carName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);

        this.hasSencsors = hasSencsors;
        this.hasCameras = hasCameras;
        this.hasBlindSpotRadar = hasBlindSpotRadar;
        this.shifterType = shifterType;
    }

    public boolean getHasSencsors() {
        return this.hasSencsors;
    }

    public boolean getHasCameras() {
        return this.hasCameras;
    }

    public boolean getHasBlindSportRadar() {
        return this.hasBlindSpotRadar;
    }

    public String getShifterType() {
        return this.shifterType;
    }

    @Override
    public String toString() {
        return super.toString() + ", Has Sencsors: " + this.hasSencsors + ", Has Cameras" + this.hasCameras
                + ", Has BlindSpot Radar: " + this.hasBlindSpotRadar + ", Shifter Type: " + this.shifterType;
    }

    @Override
    public boolean passSearchTerm(String filterTerm) {
        if (this.getId().equals(filterTerm)
                || this.getProductName().toLowerCase().contains(filterTerm)
                || Integer.toString(this.getYear()).equals(filterTerm)
                || this.getModel().toLowerCase().contains(filterTerm)
                || this.getVehicleIdentificationNumber().toLowerCase().equals(filterTerm)
                || this.getColor().toLowerCase().contains(filterTerm)
                || this.getManufacturer().toLowerCase().contains(filterTerm)
                || this.getFuelType().toString().toLowerCase().contains(filterTerm)
                || this.shifterType.contains(filterTerm)
                || (filterTerm.contains("sencsor") && this.hasSencsors)
                || (filterTerm.contains("camera") && this.hasSencsors)
                || (filterTerm.contains("blind") && this.hasBlindSpotRadar)) {
            return true;
        }
        return false;
    }
}

class Carvan extends Vehicle implements Searchable {
    private int numberOfRooms;
    private boolean hasKitchen;
    private boolean hasBathroom;
    private double waterCapacity; // In Litres

    Carvan(String carvanName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, int numberOfRooms, boolean hasKitchen, boolean hasBathroom,
            double waterCapacity, FuelType fuelType) {
        super(carvanName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);

        this.numberOfRooms = numberOfRooms;
        this.hasKitchen = hasKitchen;
        this.hasBathroom = hasBathroom;
        this.waterCapacity = waterCapacity;
    }

    public int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    public boolean getHasKitchen() {
        return this.hasKitchen;
    }

    public boolean getHasBathroom() {
        return this.hasBathroom;
    }

    public double getWaterCapacity() {
        return this.waterCapacity;
    }

    @Override
    public String toString() {
        return ", Number of Rooms: " + this.numberOfRooms + ", Has Kitchen: " + this.hasKitchen + ", Has Bathroom: "
                + this.hasBathroom + ", Water Capacity: " + this.waterCapacity;
    }

    @Override
    public boolean passSearchTerm(String filterTerm) {
        if (this.getId().equals(filterTerm)
                || this.getProductName().toLowerCase().contains(filterTerm)
                || Integer.toString(this.getYear()).equals(filterTerm)
                || this.getModel().toLowerCase().contains(filterTerm)
                || this.getVehicleIdentificationNumber().toLowerCase().equals(filterTerm)
                || this.getColor().toLowerCase().contains(filterTerm)
                || this.getManufacturer().toLowerCase().contains(filterTerm)
                || this.getFuelType().toString().toLowerCase().contains(filterTerm)
                || Integer.toString(this.numberOfRooms).equals(filterTerm)
                || (filterTerm.contains("kitchen") && this.hasKitchen)
                || (filterTerm.contains("bathroom") && this.hasBathroom)) {
            return true;
        }
        return false;
    }
}

class Bus extends Vehicle implements Searchable {
    private int passengerCapacity;
    private boolean isDoubleDecker;
    private boolean hasWifi;
    private boolean hasBathroom;

    Bus(String busName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer, int passengerCapacity, boolean isDoubleDecker, boolean hasWifi,
            boolean hasBathroom, FuelType fuelType) {
        super(busName, price, availableCount, year, model, vehicleId, color, manufacturer, fuelType);

        this.passengerCapacity = passengerCapacity;
        this.isDoubleDecker = isDoubleDecker;
        this.hasWifi = hasWifi;
        this.hasBathroom = hasBathroom;
    }

    public int getPassengerCapacity() {
        return this.passengerCapacity;
    }

    public boolean getIsDoubleDecker() {
        return this.isDoubleDecker;
    }

    public boolean getHasWifi() {
        return this.hasWifi;
    }

    public boolean getHasBathroom() {
        return this.hasBathroom;
    }

    @Override
    public String toString() {
        return ", Passenger Capacity: " + this.passengerCapacity + ", Is Double Decker: " + this.isDoubleDecker
                + ", Has WiFi: " + this.hasWifi + ", Has Bathroom: " + this.hasBathroom;
    }

    @Override
    public boolean passSearchTerm(String filterTerm) {
        if (this.getId().equals(filterTerm)
                || this.getProductName().toLowerCase().contains(filterTerm)
                || Integer.toString(this.getYear()).equals(filterTerm)
                || this.getModel().toLowerCase().contains(filterTerm)
                || this.getVehicleIdentificationNumber().toLowerCase().equals(filterTerm)
                || this.getColor().toLowerCase().contains(filterTerm)
                || this.getManufacturer().toLowerCase().contains(filterTerm)
                || this.getFuelType().toString().toLowerCase().contains(filterTerm)
                || (filterTerm.contains("decker") && this.isDoubleDecker)
                || (filterTerm.contains("wifi") && this.hasWifi)
                || (filterTerm.contains("bathroom") && this.hasBathroom)) {
            return true;
        }
        return false;
    }
}

package carshow.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

enum ProductType {
    Car,
    Carvan,
    Bus,
}

enum FuelType {
    Gasoline,
    Diesel,
    CompressedNatural,
    Ethanol,
}

@DatabaseTable(tableName = "products")
class ProductTypesWrapper {
    public static final String PRODUCT_TYPE_FIELD = "product_type";
    public static final String PRODUCT_ID_FIELD = "product_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = PRODUCT_TYPE_FIELD, canBeNull = false)
    private ProductType productType;

    @DatabaseField(columnName = PRODUCT_ID_FIELD, canBeNull = false)
    private int productId;

    ProductTypesWrapper() {
    }

    ProductTypesWrapper(ProductType productType, int productId) {
        this.productType = productType;
        this.productId = productId;
    }
}

class Product {
    // Table fields names
    public static final String PRODUCT_NAME_FIELD = "product_name";
    public static final String PRICE_FIELD = "price";
    public static final String AVAILABLE_COUNT_FIELD = "product_name";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = PRODUCT_NAME_FIELD, canBeNull = false)
    private String productName;

    @DatabaseField(columnName = PRICE_FIELD, canBeNull = false)
    private double price;

    @DatabaseField(columnName = AVAILABLE_COUNT_FIELD, canBeNull = false)
    private int availableCount; // number of available products

    Product(String productName, double productPrice, int productAvailableCount) {
        setProductName(productName);
        setPrice(productPrice);
        setAvailableCount(productAvailableCount);
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

    public int getId() {
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
}

class Vehicle extends Product {
    public static final String YEAR_FIELD = "year";
    public static final String MODEL_FIELD = "model";
    public static final String VIN_FIELD = "VIN";
    public static final String COLOR_FIELD = "color";
    public static final String MANUFACTURER_FIELD = "manufacturer";
    public static final String FUEL_TYPE_FIELD = "fuel";

    @DatabaseField(columnName = YEAR_FIELD, canBeNull = false)
    private int year;

    @DatabaseField(columnName = MODEL_FIELD, canBeNull = false)
    private String model;

    @DatabaseField(columnName = VIN_FIELD, canBeNull = false)
    private String vehicleIdentificationNumber;

    @DatabaseField(columnName = COLOR_FIELD, canBeNull = false)
    private String color;

    @DatabaseField(columnName = MANUFACTURER_FIELD, canBeNull = false)
    private String manufacturer;

    @DatabaseField(columnName = FUEL_TYPE_FIELD, canBeNull = false)
    private FuelType fuelType;

    Vehicle() {
        this("", 0, 0, 0, "", "", "", "", FuelType.Gasoline);
    }

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
}

@DatabaseTable(tableName = "cars")
class Car extends Vehicle {
    public static final String HAS_SENCSORS_FIELD = "sencsors";
    public static final String HAS_CAMERAS_FIELD = "cameras";
    public static final String HAS_BLIND_SPOT_RADAR_FIELD = "blind_sport_radar";
    public static final String SHIFTER_TYPE_FIELD = "shifter_type";

    @DatabaseField(columnName = HAS_SENCSORS_FIELD, canBeNull = false)
    private boolean hasSencsors;

    @DatabaseField(columnName = HAS_CAMERAS_FIELD, canBeNull = false)
    private boolean hasCameras;

    @DatabaseField(columnName = HAS_BLIND_SPOT_RADAR_FIELD, canBeNull = false)
    private boolean hasBlindSpotRadar;

    @DatabaseField(columnName = SHIFTER_TYPE_FIELD, canBeNull = false)
    private String shifterType;

    Car() {
        this("", 0.0, 0, 0, "", "", "", "", FuelType.Gasoline, false, false, false, "");
    }

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
}

@DatabaseTable(tableName = "carvans")
class Carvan extends Vehicle {
    public static final String ROOMS_COUNT_FIELD = "rooms_count";
    public static final String HAS_KITCHEN_FIELD = "kitchen";
    public static final String HAS_BATHROOM_FIELD = "bathroom";
    public static final String WATER_CAPACITY_FIELD = "water_capacity";

    @DatabaseField(columnName = ROOMS_COUNT_FIELD, canBeNull = false)
    private int numberOfRooms;

    @DatabaseField(columnName = HAS_KITCHEN_FIELD, canBeNull = false)
    private boolean hasKitchen;

    @DatabaseField(columnName = HAS_BATHROOM_FIELD, canBeNull = false)
    private boolean hasBathroom;

    @DatabaseField(columnName = WATER_CAPACITY_FIELD, canBeNull = false)
    private double waterCapacity; // In Litres

    Carvan() {
        this("", 0.0, 0, 0, "", "", "", "", 0, false, false, 0.0, FuelType.Diesel);
    }

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
}

@DatabaseTable(tableName = "buses")
class Bus extends Vehicle {
    public static final String PASSENGER_CAPCITY_FIELD = "passengers";
    public static final String IS_DOUBLE_DECKER_FIELD = "double_decker";
    public static final String HAS_WIFI_FIELD = "wifi";
    public static final String HAS_BATHROOM_FIELD = "bathroom";

    @DatabaseField(columnName = PASSENGER_CAPCITY_FIELD, canBeNull = false)
    private int passengerCapacity;
    @DatabaseField(columnName = IS_DOUBLE_DECKER_FIELD, canBeNull = false)
    private boolean isDoubleDecker;
    @DatabaseField(columnName = HAS_WIFI_FIELD, canBeNull = false)
    private boolean hasWifi;
    @DatabaseField(columnName = HAS_BATHROOM_FIELD, canBeNull = false)
    private boolean hasBathroom;

    Bus() {
        this("", 0.0, 0, 0, "", "", "", "", 0, false, false, false, FuelType.Diesel);
    }

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
}

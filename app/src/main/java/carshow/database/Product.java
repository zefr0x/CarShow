package carshow.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

enum ProductType {
    Car,
    Carvan,
    Bus
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

    public void setProductName(String productName) {
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

    @DatabaseField(columnName = YEAR_FIELD, canBeNull = false)
    private int year;

    @DatabaseField(columnName = MODEL_FIELD, canBeNull = false)
    private String model;

    @DatabaseField(columnName = VIN_FIELD, canBeNull = false)
    private String VehicleIdentificationNumber;

    @DatabaseField(columnName = COLOR_FIELD, canBeNull = false)
    private String color;

    @DatabaseField(columnName = MANUFACTURER_FIELD, canBeNull = false)
    private String manufacturer;

    Vehicle() {
        this("", 0, 0, 0, "", "", "", "");
    }

    Vehicle(String vehicleName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer) {
        super(vehicleName, price, availableCount);
        setYear(year);
        setModel(model);
        setVehicleIdentificationNumber(vehicleId);
        setColor(color);
        setManufacturer(manufacturer);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVehicleIdentificationNumber(String VehicleId) {
        this.VehicleIdentificationNumber = VehicleId;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return this.year;
    }

    public String getModel() {
        return this.model;
    }

    public String getVehicleIdentificationNumber() {
        return this.VehicleIdentificationNumber;
    }

    public String getColor() {
        return this.color;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }
}

@DatabaseTable(tableName = "cars")
class Car extends Vehicle {
    Car() {
        this("", 0.0, 0, 0, "", "", "", "");
    }

    Car(String carName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer) {
        super(carName, price, availableCount, year, model, vehicleId, color, manufacturer);
    }

    // TODO: Implemnet and define car specific methods and parameters.
}

@DatabaseTable(tableName = "carvans")
class Carvan extends Vehicle {
    Carvan() {
        this("", 0.0, 0, 0, "", "", "", "");
    }

    Carvan(String carvanName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer) {
        super(carvanName, price, availableCount, year, model, vehicleId, color, manufacturer);
    }

    // TODO: Implemnet and define carvan specific methods and parameters.
}

@DatabaseTable(tableName = "buses")
class Bus extends Vehicle {
    Bus() {
        this("", 0.0, 0, 0, "", "", "", "");
    }

    Bus(String busName, double price, int availableCount, int year, String model, String vehicleId,
            String color, String manufacturer) {
        super(busName, price, availableCount, year, model, vehicleId, color, manufacturer);
    }

    // TODO: Implemnet and define bus specific methods and parameters.
}

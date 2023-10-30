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

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = PRODUCT_NAME_FIELD, canBeNull = false)
    private String productName;
    private double price;
    private int numAvail; // number of available products/cars

    Product(String productName) {
        setProductName(productName);
    }

    public void setProductName(String productName) {
        if (!productName.contains("!") && !productName.contains("@")) {
            this.productName = productName;
        } else {
            throw new IllegalArgumentException("Product name cannot contain '!' or '@'.");
        }
    }

    public int getNumAvail() {
        return this.numAvail;
    }

    public void setNumAvail(int numAvail) {
        if (numAvail >= 0) {
            this.numAvail = numAvail;
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

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }

    public void applyDiscount(double discountAmount) {
        double discountPercentage = discountAmount / 100;
        double discountedPrice = getPrice() - (getPrice() * discountPercentage);
        setPrice(discountedPrice);
    }
}

class Vehicle extends Product {
    private int year;
    private String model;
    private String VIN;
    private String color;
    private String manufacturer;

    Vehicle() {
        this("", 0, "", "", "", 0, "", "", "", "", 0);
    }

    Vehicle(String productName, double price, String vehicleName, int year, String model, String VIN, String color, String manufacturer, int numAvail) {
        super(productName);
        setVehicleName(vehicleName);
        setYear(year);
        setModel(model);
        setVIN(VIN);
        setColor(color);
        setManufacturer(manufacturer);
        setPrice(price);
        setNumAvail(numAvail);
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVIN() {
        return this.VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }
}
@DatabaseTable(tableName = "cars")
class Car extends Vehicle {
    Car() {
        this("");
    }

    Car(String carName) {
        super(carName);
    }

    // TODO: Implemnet and define car specific methods and parameters.
}

@DatabaseTable(tableName = "carvans")
class Carvan extends Vehicle {
    Carvan() {
        this("");
    }

    Carvan(String carvanName) {
        super(carvanName);
    }

    // TODO: Implemnet and define carvan specific methods and parameters.
}

@DatabaseTable(tableName = "buses")
class Bus extends Vehicle {
    Bus() {
        this("");
    }

    Bus(String busName) {
        super(busName);
    }

    // TODO: Implemnet and define bus specific methods and parameters.
}

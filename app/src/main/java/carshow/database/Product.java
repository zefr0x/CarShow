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

    // TODO: Define product parameters.

    Product(String productName) {
        setProductName(productName);
    }

    public void setProductName(String productName) {
        // TODO: Apply restrictions on names.
        this.productName = productName;
    }

    public int getId() {
        return this.id;
    }

    public String getProductName() {
        return this.productName;
    }

    // TODO: Implement product methods.
}

class Vehicle extends Product {
    Vehicle() {
        this("");
    }

    Vehicle(String vehicleName) {
        super(vehicleName);
    }

    // TODO: Implemnet and define vehicle specific methods and parameters.
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

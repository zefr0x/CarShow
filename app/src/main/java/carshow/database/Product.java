package carshow.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

@DatabaseTable(tableName = "cars")
class Car extends Product {
    Car() {
        this("");
    }

    Car(String carName) {
        super(carName);
    }

    // TODO: Implemnet and define car specific methods and parameters.
}

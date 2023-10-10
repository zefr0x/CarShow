package carshow.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

class Sale {
    // Table fields names
    public static final String COSTOMER_ID_FIELD = "costomer_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = COSTOMER_ID_FIELD, foreign = true)
    private CostomerAccount costomer;

    // TODO: Define sale parameters.

    Sale(CostomerAccount costomer) {
        this.costomer = costomer;
    }

    public int getId() {
        return this.id;
    }

    // TODO: Implement sale methods.
}

@DatabaseTable(tableName = "cars_sales")
class CarSale extends Sale {
    CarSale() {
        // FIX: Should not be valid.
        super(null);
    }

    CarSale(CostomerAccount costomer) {
        super(costomer);
    }

    // TODO: Implement and define car sale specific methods and parameters.
}

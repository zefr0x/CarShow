package carshow.database;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

enum PaymentMethod {
    Cache,
    Visa,
    Mada,
    Mastercard
}

@DatabaseTable(tableName = "sales")
class Sale {
    // Table fields names
    public static final String COSTOMER_ID_FIELD = "costomer_id";
    public static final String SALES_MAN_ID_FIELD = "sales_man_id";
    public static final String PRODUCT_ID_FIELD = "product_id";
    public static final String SALE_TIME_FIELD = "time";
    public static final String TOTAL_BILL_FIELD = "total_bill";
    public static final String PAYMENT_METHOD_FIELD = "payment_method";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = COSTOMER_ID_FIELD, foreign = true)
    private CostomerAccount costomer;

    @DatabaseField(columnName = SALES_MAN_ID_FIELD, foreign = true)
    private SalesManAccount salesMan;

    @DatabaseField(columnName = PRODUCT_ID_FIELD, foreign = true)
    private ProductTypesWrapper product;

    @DatabaseField(columnName = SALE_TIME_FIELD)
    private long saleTime; // In Unix time stamp

    @DatabaseField(columnName = TOTAL_BILL_FIELD)
    private double totalBill;

    @DatabaseField(columnName = PAYMENT_METHOD_FIELD)
    private PaymentMethod paymentMethod;

    Sale() {
    }

    Sale(CostomerAccount costomer, SalesManAccount salesMan, ProductTypesWrapper product, double totalBill,
            PaymentMethod paymentMethod) {
        this.costomer = costomer;
        this.salesMan = salesMan;
        this.product = product;

        this.totalBill = totalBill;
        this.paymentMethod = paymentMethod;
        this.saleTime = System.currentTimeMillis();
    }

    public int getId() {
        return this.id;
    }

    public double getTotalBill() {
        return this.totalBill;
    }

    public long getSaleTime() {
        return this.saleTime;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public String getSaleDateString() {
        return new Date(this.saleTime).toString();
    }
}

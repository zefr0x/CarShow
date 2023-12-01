package carshow;

import java.util.Date;
import java.util.UUID;

enum PaymentMethod {
    Cache,
    Visa,
    Mada,
    Mastercard,
}

class Sale implements Searchable {
    private String id;
    private CostomerAccount costomer;
    private SalesManAccount salesMan;
    private Product product;
    private long saleTime; // In Unix time stamp
    private double totalBill;
    private PaymentMethod paymentMethod;

    // Payment via sales man.
    Sale(CostomerAccount costomer, SalesManAccount salesMan, Product product, double totalBill,
            PaymentMethod paymentMethod) {
        this.costomer = costomer;
        this.salesMan = salesMan;
        this.product = product;

        this.totalBill = totalBill;
        this.paymentMethod = paymentMethod;
        this.saleTime = System.currentTimeMillis();
        this.id = UUID.randomUUID().toString();
    }

    // Self payment without any sales man.
    Sale(CostomerAccount costomer, Product product, double totalBill, PaymentMethod paymentMethod) {
        this(costomer, null, product, totalBill, paymentMethod);
    }

    public String getId() {
        return this.id;
    }

    public CostomerAccount getCostomer() {
        return this.costomer;
    }

    public SalesManAccount getSalesMan() {
        return this.salesMan;
    }

    public Product getProduct() {
        return this.product;
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

    @Override
    public String toString() {
        return "Sale ID: " + this.id + "\n Costomer ID: " + this.costomer.getId() + ", Costomer Full Name: "
                + this.costomer.getFullName()
                + (this.salesMan != null ? "\n SalesMan ID: " + this.salesMan.getId() + ", SalesMan Full Name: "
                        + this.salesMan.getFullName() : "")
                + "\n Product ID: " + this.product.getId() + ", Product Name: "
                + this.product.getProductName() + "\n Total Bill: " + this.totalBill + ", Payment Method: "
                + this.paymentMethod.toString() + ", Sale Time: " + getSaleDateString();
    }

    @Override
    public boolean passSearchTerm(String filterTerm) {
        if (this.getId().equals(filterTerm)
                || this.costomer.passSearchTerm(filterTerm)
                || this.salesMan.passSearchTerm(filterTerm)
                || ((Searchable) this.product).passSearchTerm(filterTerm)
                || this.paymentMethod.toString().toLowerCase().contains(filterTerm)) {
            return true;
        }
        return false;
    }
}

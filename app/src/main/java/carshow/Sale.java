package carshow;

import java.util.Date;
import java.util.UUID;

enum PaymentMethod {
    Cache,
    Visa,
    Mada,
    Mastercard,
}

class Sale {
    private String id;
    private CostomerAccount costomer;
    private SalesManAccount salesMan;
    private Product product;
    private long saleTime; // In Unix time stamp
    private double totalBill;
    private PaymentMethod paymentMethod;

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

    public String getId() {
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

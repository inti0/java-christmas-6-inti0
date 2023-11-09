package christmas.service;

public class DiscountService {

    private final int paymentBeforeDiscount;
    private int discountSum = 0;
    public DiscountService(int paymentBeforeDiscount) {
        this.paymentBeforeDiscount = paymentBeforeDiscount;
    }
}

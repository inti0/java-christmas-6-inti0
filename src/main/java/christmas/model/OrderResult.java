package christmas.model;

import christmas.EventBadge;
import christmas.benefit.Present;
import christmas.benefit.discount.DiscountPolicy;
import christmas.order.Food;
import java.util.List;
import java.util.Map;

public class OrderResult {
    private static final int NO_DISCOUNT = 0;
    private static final int DISCOUNT_MIN_RANGE = 10000;
    private final Map<Food, Integer> orders;
    private final List<DiscountPolicy> discountPolicies;

    public OrderResult(Map<Food, Integer> orders, List<DiscountPolicy> discountPolicies) {
        this.orders = orders;
        this.discountPolicies = discountPolicies;
    }

    public int beforeDiscountSum() {
        return orders.entrySet().stream()
                .mapToInt(entry -> (entry.getKey().getPrice()) * (entry.getValue()))
                .sum();
    }

    public int allOfDiscount() {
        if (beforeDiscountSum() < DISCOUNT_MIN_RANGE) {
            return NO_DISCOUNT;
        }

        return discountPolicies.stream()
                .mapToInt(DiscountPolicy::discountAmount)
                .sum();
    }

    public Present givePresent() {
        return Present.selectPresent(beforeDiscountSum());
    }

    public EventBadge giveEventBadge() {
        return EventBadge.selectEventBadge(allOfDiscount() + givePresent().getPrice());
    }

    public int paymentAfterDiscount() {
        return beforeDiscountSum() - allOfDiscount();
    }

    public int allOfBenefitAmount() {
        return givePresent().getPrice() + allOfDiscount();
    }

    public Map<Food, Integer> getOrders() {
        return orders;
    }

    public List<DiscountPolicy> getDiscountPolicies() {
        return discountPolicies;
    }
}

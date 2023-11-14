package christmas.model;

import christmas.domain.EventBadge;
import christmas.domain.benefit.Present;
import christmas.domain.benefit.discount.DiscountPolicy;
import christmas.domain.order.Food;
import java.util.List;
import java.util.Map;

public record OrderResult(Map<Food, Integer> orders, List<DiscountPolicy> discountPolicies) {
    private static final int NO_DISCOUNT = 0;
    private static final int DISCOUNT_MIN_RANGE = 10000;

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
}

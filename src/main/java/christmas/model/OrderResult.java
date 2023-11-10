package christmas.model;

import christmas.EventBadge;
import christmas.benefit.Present;
import christmas.benefit.discount.DiscountPolicy;
import christmas.foodmenu.Food;
import java.util.List;
import java.util.Map;

/**
 * <할인 전 총주문 금액>
 * 8,500원
 *
 * <증정 메뉴>
 * 없음
 *
 * <혜택 내역>
 * 없음
 *
 * <총혜택 금액>
 * 0원
 *
 * <할인 후 예상 결제 금액>
 */
public class OrderResult {
    private static final int NO_DISCOUNT = 0;
    private static final int DISCOUNT_MIN_RANGE = 10000;
    private final Map<Food, Integer> orders;
    private final List<DiscountPolicy> discountPolicies;

    public OrderResult(Map<Food, Integer> orders, List<DiscountPolicy> discountPolicies) {
        this.orders = orders;
        this.discountPolicies = discountPolicies;
    }
    public int beforeDiscountSum(){
        return orders.entrySet().stream()
                .mapToInt(entry -> (entry.getKey().price) * (entry.getValue()))
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

    public Present givePresent(){
        return Present.selectPresent(beforeDiscountSum());
    }
    public EventBadge giveEventBadge(){
        return EventBadge.selectEventBadge(allOfDiscount());
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

package christmas.service;

import christmas.EventBadge;
import christmas.benefit.Present;
import christmas.foodmenu.Food;
import christmas.service.DiscountService;
import java.time.LocalDate;
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


    private final Map<Food,Integer> orders;
    private final LocalDate localDate;
    private DiscountService discountService;
    private int originalSum;
    private int discountSum;

    public OrderResult(Map<Food, Integer> orders, LocalDate localDate) {
        this.orders = orders;
        this.localDate = localDate;
        discountService = new DiscountService(orders,localDate);
        originalSum = orderOriginalSum();
        discountSum = discountService.discountAmount();
    }

    public int orderOriginalSum(){
        return orders.entrySet().stream()
                .mapToInt(entry -> (entry.getKey().price) * (entry.getValue()))
                .sum();
    }

    public Present givePresent(){
        return Present.selectPresent(originalSum);
    }

    public EventBadge giveEventBadge(){
        return EventBadge.selectEventBadge(discountSum);
    }

    public int totalPaymentAfterDiscount() {
        return originalSum - discountSum;
    }

    public int allOfBenefitAmount() {
        return givePresent().price + discountSum;
    }
}

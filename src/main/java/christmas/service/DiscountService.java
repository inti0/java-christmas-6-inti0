package christmas.service;

import christmas.benefit.discount.DDayDiscount;
import christmas.benefit.discount.DayOfWeekDiscount;
import christmas.benefit.discount.DiscountPolicy;
import christmas.benefit.discount.SpecialDayDiscount;
import christmas.foodmenu.Food;
import java.time.LocalDate;
import java.util.Map;

public class DiscountService {
    private static final int NO_DISCOUNT = 0;
    private static final int DISCOUNT_MIN_RANGE = 10000;
    private final Map<Food, Integer> orders;
    private final LocalDate localDate;
    private DiscountPolicy dayOfWeekDiscount;
    private DiscountPolicy dDayDiscount;
    private DiscountPolicy specialDayDiscount;
    public DiscountService(Map<Food, Integer> orders, LocalDate localDate) {
        this.orders = orders;
        this.localDate = localDate;
        dayOfWeekDiscount = new DayOfWeekDiscount(orders, localDate);
        dDayDiscount = new DDayDiscount(localDate);
        specialDayDiscount = new SpecialDayDiscount(localDate);
    }
    public int discountAmount() {

        if (orderSum() < DISCOUNT_MIN_RANGE) {
            return NO_DISCOUNT;
        }

        int sum = 0;
        sum += dayOfWeekDiscount.discountAmount();
        sum += dDayDiscount.discountAmount();
        sum += specialDayDiscount.discountAmount();

        return sum;
    }

    private int orderSum(){
        return orders.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}

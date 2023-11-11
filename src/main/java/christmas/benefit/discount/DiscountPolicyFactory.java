package christmas.benefit.discount;

import christmas.order.Food;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DiscountPolicyFactory {
    private final Map<Food, Integer> orders;
    private final LocalDate bookDate;

    public DiscountPolicyFactory(Map<Food, Integer> orders, LocalDate bookDate) {
        this.orders = orders;
        this.bookDate = bookDate;
    }

    public List<DiscountPolicy> createDiscountPolicies() {
        DiscountPolicy dayOfWeekDiscount = new DayOfWeekDiscount(orders, bookDate);
        DiscountPolicy dDayDiscount = new DDayDiscount(bookDate);
        DiscountPolicy specialDayDiscount = new SpecialDayDiscount(bookDate);

        return List.of(dayOfWeekDiscount, dDayDiscount, specialDayDiscount);
    }
}

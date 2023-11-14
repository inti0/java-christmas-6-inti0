package christmas.domain.benefit.discount;

import christmas.domain.order.Food;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DiscountPolicyFactory {
    private final LocalDate bookDate;
    private final Map<Food, Integer> orders;

    public DiscountPolicyFactory(LocalDate bookDate, Map<Food, Integer> orders) {
        this.bookDate = bookDate;
        this.orders = orders;
    }

    public List<DiscountPolicy> createDiscountPolicies() {
        DiscountPolicy dayOfWeekDiscount = new DayOfWeekDiscount(bookDate, orders);
        DiscountPolicy dDayDiscount = new DDayDiscount(bookDate);
        DiscountPolicy specialDayDiscount = new SpecialDayDiscount(bookDate);

        return List.of(dayOfWeekDiscount, dDayDiscount, specialDayDiscount);
    }
}

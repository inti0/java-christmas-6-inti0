package christmas.domain.benefit.discount;

import christmas.domain.order.Food;
import christmas.domain.order.FoodType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class DayOfWeekDiscount implements DiscountPolicy {
    private static final int DAY_DISCOUNT = 2023;
    private final LocalDate localDate;
    private final Map<Food, Integer> orders;

    public DayOfWeekDiscount(LocalDate localDate, Map<Food, Integer> orders) {
        this.localDate = localDate;
        this.orders = orders;
    }

    public int discountAmount() {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        int amount = orders.keySet().stream()
                .filter(key -> isDailyDiscountApplicable(key, dayOfWeek))
                .map(orders::get)
                .mapToInt(Integer::intValue)
                .sum();

        return DAY_DISCOUNT * amount;
    }

    private boolean isDailyDiscountApplicable(Food food, DayOfWeek bookDay) {
        if (food.getFoodType().equals(FoodType.MAIN)) {
            return isWeekend(bookDay);
        }

        if (food.getFoodType().equals(FoodType.DESSERT)) {
            return !isWeekend(bookDay);
        }
        return false;
    }

    private boolean isWeekend(DayOfWeek bookDay) {
        return DayOfWeek.FRIDAY == bookDay || DayOfWeek.SATURDAY == bookDay;
    }

    @Override
    public String discountSource() {
        return "요일 할인";
    }
}

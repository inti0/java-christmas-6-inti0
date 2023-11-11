package christmas.benefit.discount;

import christmas.AppConfig;
import christmas.foodmenu.Food;
import christmas.foodmenu.FoodType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class DayOfWeekDiscount implements DiscountPolicy {
    private static final int DAY_DISCOUNT = 2023;
    private final Map<Food, Integer> orders;
    private final LocalDate localDate;

    public DayOfWeekDiscount(Map<Food, Integer> orders, LocalDate localDate) {
        this.orders = orders;
        this.localDate = localDate;
    }

    public int discountAmount() {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int amount = orders.keySet().stream()
                .filter(key -> isDailyDiscountApplicable(key, dayOfWeek))
                .map(key -> orders.getOrDefault(key, 0))
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

package christmas.benefit;

import christmas.foodmenu.Food;
import christmas.foodmenu.FoodType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class DayOfWeekDiscount {
    private static final int DAY_DISCOUNT = 2023;
    private final Map<Food, Integer> orders;
    private final LocalDate localDate;

    public DayOfWeekDiscount(Map<Food, Integer> orders, LocalDate localDate) {
        this.orders = orders;
        this.localDate = localDate;
    }

    public int dayDiscountPrice(){
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int amount = orders.keySet().stream()
                .filter(key -> isDayDiscountFood(key, dayOfWeek))
                .map(key -> orders.getOrDefault(key, 0))
                .mapToInt(Integer::intValue)
                .sum();
        return DAY_DISCOUNT * amount;
    }

    private boolean isDayDiscountFood(Food food, DayOfWeek dayOfWeek) {
        if(food.foodType.equals(FoodType.MAIN)){
            return isWeekend(dayOfWeek);
        }

        if(food.foodType.equals(FoodType.DESSERT)){
            return !isWeekend(dayOfWeek);
        }
        return false;
    }

    private boolean isWeekend(DayOfWeek bookDay) {
        return DayOfWeek.FRIDAY == bookDay || DayOfWeek.SATURDAY == bookDay;
    }
}

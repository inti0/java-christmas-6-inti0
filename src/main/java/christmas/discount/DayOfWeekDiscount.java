package christmas.discount;

import christmas.foodmenu.Food;
import christmas.foodmenu.FoodType;
import java.time.DayOfWeek;
import java.util.Map;

public class DayOfWeekDiscount {
    private static final int DAY_DISCOUNT = 2023;
    private final Map<Food, Integer> orders;
    public DayOfWeekDiscount(Map<Food, Integer> orders) {
        this.orders = orders;
    }
    public int dayDiscountPrice(DayOfWeek dayOfWeek){
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

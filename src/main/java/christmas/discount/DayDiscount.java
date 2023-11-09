package christmas.discount;

import christmas.foodmenu.Food;
import christmas.foodmenu.FoodType;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayDiscount {

    public boolean isDayDiscountFood(Food food, DayOfWeek dayOfWeek) {
        if(food.foodType.equals(FoodType.MAIN)){
            mainIsDiscountInWeekend(dayOfWeek);
        }

        if(food.foodType.equals(FoodType.DESSERT)){
            dessertIsDiscountInWeekday(dayOfWeek);
        }

        return false;
    }

    private boolean mainIsDiscountInWeekend(DayOfWeek dayOfWeek) {
        return isWeekend(dayOfWeek);
    }

    private boolean dessertIsDiscountInWeekday(DayOfWeek dayOfWeek){
        return !isWeekend(dayOfWeek);
    }

    private boolean isWeekend(DayOfWeek bookDay) {
        return DayOfWeek.FRIDAY == bookDay || DayOfWeek.SATURDAY == bookDay;
    }
}

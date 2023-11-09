package christmas.benefit.discount;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class SpecialDayDiscount implements DiscountPolicy {
    private static final int CHRISTMAS_DATE = 25;
    private static final int SPECIAL_DAY_DISCOUNT_PRICE = 1000;
    private final LocalDate localDate;

    public SpecialDayDiscount(LocalDate localDate) {
        this.localDate = localDate;
    }

    public int discountAmount(){
        if(isSpecialDay()){
            return SPECIAL_DAY_DISCOUNT_PRICE;
        }
        return 0;
    }
    private boolean isSpecialDay(){
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int dayOfMonth = localDate.getDayOfMonth();
        return dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfMonth == CHRISTMAS_DATE;
    }
}

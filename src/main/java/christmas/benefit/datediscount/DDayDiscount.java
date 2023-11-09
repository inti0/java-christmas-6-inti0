package christmas.benefit.datediscount;

import java.time.LocalDate;

public class DDayDiscount {
    private static final int CHRISTMAS_DAY = 25;
    private static final int DISCOUNT_PRICE_PER_DAY = 100;
    private static final int DISCOUNT_PRICE_DEFAULT = 900;
    private final LocalDate localDate;
    public DDayDiscount(LocalDate localDate) {
        this.localDate = localDate;
    }
    public int discountAmount(){
        int dayOfMonth = localDate.getDayOfMonth();

        if(dayOfMonth > CHRISTMAS_DAY) {
            return 0;
        }
        return dayOfMonth * DISCOUNT_PRICE_PER_DAY + DISCOUNT_PRICE_DEFAULT;
    }
}

package christmas.domain.benefit.discount;

import christmas.AppConstants;
import java.time.LocalDate;

public class DDayDiscount implements DiscountPolicy {

    private static final int D_DAY_DISCOUNT_INCREASE_PER_DAY = 100;
    private static final int D_DAY_DISCOUNT_DEFAULT = 900;
    private final LocalDate localDate;

    protected DDayDiscount(LocalDate localDate) {
        this.localDate = localDate;
    }

    public int discountAmount() {
        int dayOfMonth = localDate.getDayOfMonth();

        if (dayOfMonth > AppConstants.CHRISTMAS_DATE) {
            return AppConstants.NO_DISCOUNT;
        }
        return dayOfMonth * D_DAY_DISCOUNT_INCREASE_PER_DAY + D_DAY_DISCOUNT_DEFAULT;
    }

    @Override
    public String discountSource() {
        return "크리스마스 디데이 할인";
    }
}

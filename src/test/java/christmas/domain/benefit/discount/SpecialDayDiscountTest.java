package christmas.domain.benefit.discount;

import christmas.domain.benefit.discount.SpecialDayDiscount;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpecialDayDiscountTest {

    LocalDate localDate1;
    LocalDate localDate2;

    @Test
    @DisplayName("스페셜 데이(일요일과 크리스마스)에 1000원 할인된다.")
    void 스페셜_데이_할인(){
        localDate1 = LocalDate.of(2023, 12, 31);
        localDate2 = LocalDate.of(2023,12,25);
        SpecialDayDiscount specialDayDiscount1 = new SpecialDayDiscount(localDate1);
        SpecialDayDiscount specialDayDiscount2 = new SpecialDayDiscount(localDate2);

        int dc1 = specialDayDiscount1.discountAmount();
        int dc2 = specialDayDiscount2.discountAmount();

        Assertions.assertThat(dc1).isEqualTo(1000);
        Assertions.assertThat(dc2).isEqualTo(1000);
    }

    @Test
    @DisplayName("스페셜 데이를 제외하고는 할인되지 않는다.")
    void 스페셜_데이_할인2(){
        localDate1 = LocalDate.of(2023, 12, 11);
        localDate2 = LocalDate.of(2023,12,1);
        SpecialDayDiscount specialDayDiscount1 = new SpecialDayDiscount(localDate1);
        SpecialDayDiscount specialDayDiscount2 = new SpecialDayDiscount(localDate2);

        int dc1 = specialDayDiscount1.discountAmount();
        int dc2 = specialDayDiscount2.discountAmount();

        Assertions.assertThat(dc1).isEqualTo(0);
        Assertions.assertThat(dc2).isEqualTo(0);
    }
}

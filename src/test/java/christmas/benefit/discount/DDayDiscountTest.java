package christmas.benefit.discount;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DDayDiscountTest {

    LocalDate localDate1;
    LocalDate localDate2;

    @Test
    void 디_데이_할인(){
        localDate1 = LocalDate.of(2023, 12, 1);
        localDate2 = LocalDate.of(2023,12,25);
        DDayDiscount dDayDiscount1 = new DDayDiscount(localDate1);
        DDayDiscount dDayDiscount2 = new DDayDiscount(localDate2);

        int dc1 = dDayDiscount1.discountAmount();
        int dc2 = dDayDiscount2.discountAmount();

        Assertions.assertThat(dc1).isEqualTo(1000);
        Assertions.assertThat(dc2).isEqualTo(3400);
    }

    @Test
    void 디_데이_할인2(){
        localDate1 = LocalDate.of(2023, 12, 26);
        localDate2 = LocalDate.of(2023,12,31);
        DDayDiscount dDayDiscount1 = new DDayDiscount(localDate1);
        DDayDiscount dDayDiscount2 = new DDayDiscount(localDate2);

        int dc1 = dDayDiscount1.discountAmount();
        int dc2 = dDayDiscount2.discountAmount();

        Assertions.assertThat(dc1).isEqualTo(0);
        Assertions.assertThat(dc2).isEqualTo(0);
    }
}

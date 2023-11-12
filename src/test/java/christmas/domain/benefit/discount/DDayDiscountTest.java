package christmas.domain.benefit.discount;

import christmas.domain.benefit.discount.DDayDiscount;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DDayDiscountTest {

    LocalDate localDate1;
    LocalDate localDate2;

    @Test
    @DisplayName("1일에 1000원 이후 하루가 지날때마다 100원씩 증가한다.")
    void 디_데이_할인(){
        localDate1 = LocalDate.of(2023, 12, 1);
        localDate2 = LocalDate.of(2023,12,25);
        DDayDiscount dDayDiscount1 = new DDayDiscount(localDate1);
        DDayDiscount dDayDiscount2 = new DDayDiscount(localDate2);

        int discount1 = dDayDiscount1.discountAmount();
        int discount2 = dDayDiscount2.discountAmount();

        Assertions.assertThat(discount1).isEqualTo(1000);
        Assertions.assertThat(discount2).isEqualTo(3400);
    }

    @Test
    @DisplayName("26일 이후엔 적용되지 않는다.")
    void 디_데이_할인2(){
        localDate1 = LocalDate.of(2023, 12, 26);
        localDate2 = LocalDate.of(2023,12,31);
        DDayDiscount dDayDiscount1 = new DDayDiscount(localDate1);
        DDayDiscount dDayDiscount2 = new DDayDiscount(localDate2);

        int discount1 = dDayDiscount1.discountAmount();
        int discount2 = dDayDiscount2.discountAmount();

        Assertions.assertThat(discount1).isEqualTo(0);
        Assertions.assertThat(discount2).isEqualTo(0);
    }
}

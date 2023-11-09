package christmas.service;

import christmas.order.OrderReceiver;
import java.time.LocalDate;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiscountServiceTest {
    OrderReceiver receiver;

    @BeforeEach
    void 생성자주입() {
        receiver = new OrderReceiver(new HashMap<>());
        receiver.orderFood("시저샐러드", 3);
        receiver.orderFood("해산물파스타", 4);
        receiver.orderFood("아이스크림", 5);
        receiver.orderFood("샴페인", 6);
    }

    @Test
    void 할인정책_테스트() {
        LocalDate localDate = LocalDate.of(2023, 12, 10);  // 일요일
        DiscountService discountService = new DiscountService(receiver.getOrders(), localDate);
        int discountAmount = discountService.discountAmount();

        int special = 1000;
        int dessert = 2023 * 5;
        int dDay = 1900;
        Assertions.assertThat(discountAmount).isEqualTo(special + dessert + dDay);
    }
}

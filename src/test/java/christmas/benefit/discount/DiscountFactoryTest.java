package christmas.benefit.discount;

import christmas.order.Food;
import christmas.order.OrderReceiver;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiscountFactoryTest {

    Map<Food, Integer> orders;
    OrderReceiver receiver;
    LocalDate localDate;
    DiscountPolicyFactory discountPolicyFactory;
    List<DiscountPolicy> discountPolicies;

    @BeforeEach
    void 생성자주입() {
        orders = new HashMap<>();
        receiver = new OrderReceiver(orders);
        localDate = LocalDate.of(2023, 12, 10);      // 일요일
        discountPolicyFactory = new DiscountPolicyFactory(orders,localDate);
        discountPolicies = discountPolicyFactory.createDiscountPolicies();

        receiver.orderFood("시저샐러드", 3);
        receiver.orderFood("해산물파스타", 4);
        receiver.orderFood("아이스크림", 5);
        receiver.orderFood("샴페인", 6);
    }
    @Test
    void 할인정책_종합_테스트() {
        int discountAmount = discountPolicies.stream()
                .mapToInt(DiscountPolicy::discountAmount)
                .sum();

        int special = 1000;
        int dessert = 2023 * 5;
        int dDay = 1900;
        Assertions.assertThat(discountAmount).isEqualTo(special + dessert + dDay);
    }
}

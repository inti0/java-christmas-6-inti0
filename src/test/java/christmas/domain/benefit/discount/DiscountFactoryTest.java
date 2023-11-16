package christmas.domain.benefit.discount;

import christmas.domain.order.Food;
import christmas.domain.order.OrderReceiver;
import java.time.LocalDate;
import java.util.EnumMap;
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
        orders = new EnumMap<Food, Integer>(Food.class);
        receiver = new OrderReceiver(orders);
        localDate = LocalDate.of(2023, 12, 10);      // 일요일
        discountPolicyFactory = new DiscountPolicyFactory(localDate, orders);
        discountPolicies = discountPolicyFactory.createDiscountPolicies();

        receiver.orderFood(Food.CAESAR_SALAD, 3);
        receiver.orderFood(Food.SEAFOOD_PASTA, 4);
        receiver.orderFood(Food.ICE_CREAM, 5);
        receiver.orderFood(Food.CHAMPAGNE, 6);
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

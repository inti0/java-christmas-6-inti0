package christmas.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.order.Food;
import christmas.domain.order.OrderReceiver;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderManagerTest {

    @Test
    void 주문_테스트() {
        List<String> allMenu = List.of("양송이수프-1", "타파스-1", "시저샐러드-1", "티본스테이크-1", "바비큐립-1",
                "해산물파스타-1", "크리스마스파스타-1", "초코케이크-1", "아이스크림-1", "제로콜라-1", "레드와인-1", "샴페인-1");
        OrderManager orderManager = new OrderManager(allMenu);

        OrderReceiver orderReceiver = orderManager.handleOrder();
        Map<Food, Integer> orders = orderReceiver.getOrders();

        assertThat(orders.size()).isEqualTo(12);
    }

    @ParameterizedTest
    @ValueSource(strings = {"양송이수프-일", "양송이수프", "양송이수프-i", "제로콜라,아이스크림", ""})
    void 주문_실패_테스트(String input) {
        List<String> menuAndAmount = List.of(input);

        OrderManager orderManager = new OrderManager(menuAndAmount);

        assertThatThrownBy(() -> orderManager.handleOrder())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"제로콜라-3,샴페인-5", "제로콜라-1"})
    @DisplayName("음료만 주문할 수 없다")
    void only_음료_실패_테스트(String input) {
        List<String> menuAndAmount = List.of(input);

        OrderManager orderManager = new OrderManager(menuAndAmount);

        assertThatThrownBy(() -> orderManager.handleOrder())
                .isInstanceOf(IllegalArgumentException.class);
    }
}

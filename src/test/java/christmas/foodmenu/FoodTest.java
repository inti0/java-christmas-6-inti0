package christmas.foodmenu;

import christmas.order.Order;
import java.util.HashMap;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FoodTest {
    Order order = new Order(new HashMap<>());
    @Test
    void 푸드_테스트() {
        String menuName = "양송이수프";
        Food food = order.findMenuBy(menuName);
        Assertions.assertThat(food).isNotNull();
    }

    @Test
    void 푸드메뉴_실패테스트() {
        String menuName = "콜라";
        Assertions.assertThatThrownBy(()->
                order.findMenuBy(menuName))
                .isInstanceOf(NoSuchElementException.class);
    }
}

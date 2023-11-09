package christmas.foodmenu;

import christmas.service.OrderService;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FoodTest {

    OrderService orderService = new OrderService();

    @Test
    void 푸드_테스트() {
        String menuName = "양송이수프";
        Food food = orderService.findMenuBy(menuName);
        Assertions.assertThat(food).isNotNull();
    }

    @Test
    void 푸드메뉴_실패테스트() {
        String menuName = "콜라";
        Assertions.assertThatThrownBy(()->
                orderService.findMenuBy(menuName))
                .isInstanceOf(NoSuchElementException.class);
    }
}

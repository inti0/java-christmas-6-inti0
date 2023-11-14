package christmas.domain.order;

import java.util.EnumMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FoodTest {
    OrderReceiver order = new OrderReceiver(new EnumMap<>(Food.class));

    @Test
    void 음식_찾기_테스트() {
        String menuName = "양송이수프";
        Food food = Food.findMenuByName(menuName);
        Assertions.assertThat(food).isNotNull();
    }

    @Test
    void 음식_찾기_실패테스트() {
        String menuName = "콜라";
        Assertions.assertThatThrownBy(() ->
                        Food.findMenuByName(menuName))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

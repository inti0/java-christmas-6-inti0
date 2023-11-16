package christmas.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.EnumMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderReceiverTest {

    OrderReceiver receiver;

    @BeforeEach
    void 생성자주입() {
        receiver = new OrderReceiver(new EnumMap<Food, Integer>(Food.class));
    }

    @Test
    void 주문테스트() {
        receiver.orderFood(Food.T_BONE_STEAK, 5);
        receiver.orderFood(Food.ZERO_COLA, 15);

        int size = receiver.getOrders().size();

        assertThat(size).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,21})
    @DisplayName("주문 중 주문 수량이 잘못 되면 오류가 발생한다.")
    void 주문량_최소_테스트(int amount) {
        assertThatThrownBy(() ->
                receiver.orderFood(Food.T_BONE_STEAK, amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문한 메뉴의 총량이 20을 넘어가면 예외가 발생한다")
    @Test
    void 메뉴총량_테스트() {
        assertThatThrownBy(() -> {
            receiver.orderFood(Food.T_BONE_STEAK, 4);
            receiver.orderFood(Food.ZERO_COLA, 17);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("같은 메뉴를 두번 주문하면 예외가 발생한다")
    @Test
    void 중복주문_테스트() {
        assertThatThrownBy(() -> {
            receiver.orderFood(Food.T_BONE_STEAK, 4);
            receiver.orderFood(Food.RED_WINE, 4);
            receiver.orderFood(Food.T_BONE_STEAK, 3);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("음료만 주문했는지 확인할 수 있다. 음료만 있다면 true")
    @Test
    void 음료_테스트() {
        receiver.orderFood(Food.RED_WINE, 5);
        receiver.orderFood(Food.ZERO_COLA, 3);
        receiver.orderFood(Food.CHAMPAGNE, 3);

        boolean onlyDrink = receiver.isOrderContainOnlyDrink();

        assertThat(onlyDrink).isEqualTo(true);
    }

    @DisplayName("음료만 주문했는지 확인할 수 있다. 음료 말고도 있다면 false")
    @Test
    void 음료_테스트2() {
        receiver.orderFood(Food.T_BONE_STEAK, 5);
        receiver.orderFood(Food.ZERO_COLA, 3);
        receiver.orderFood(Food.CHAMPAGNE, 3);

        boolean onlyDrink = receiver.isOrderContainOnlyDrink();

        assertThat(onlyDrink).isEqualTo(false);
    }
}
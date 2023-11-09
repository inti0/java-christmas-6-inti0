package christmas.order;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderReceiverTest {

    OrderReceiver receiver;

    @BeforeEach
    void 생성자주입(){
        receiver = new OrderReceiver(new HashMap<>());
    }

    @Test
    void 주문테스트1() {
        receiver.orderFood("티본스테이크",5);
        receiver.orderFood("제로콜라",15);
    }

    @DisplayName("주문 중 이름이 틀리면 오류가 발생한다.")
    @Test
    void 주문테스트2() {
        assertThatThrownBy(()->
                receiver.orderFood("티본 스테이크",7))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("주문 중 주문 수량이 잘못 되면 오류가 발생한다.")
    @Test
    void 주문테스트3() {
        assertThatThrownBy(() ->
                receiver.orderFood("티본스테이크", 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 중 주문 수량이 잘못 되면 오류가 발생한다.")
    @Test
    void 주문테스트4() {
        assertThatThrownBy(() ->
                receiver.orderFood("티본스테이크", 21))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문한 메뉴의 총량이 20을 넘어가면 예외가 발생한다")
    @Test
    void 주문테스트5() {
        assertThatThrownBy(() ->{
            receiver.orderFood("티본스테이크", 4);
            receiver.orderFood("제로콜라", 17);
            }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("같은 메뉴를 두번 주문하면 예외가 발생한다")
    @Test
    void 주문테스트6() {
        assertThatThrownBy(() ->{
            receiver.orderFood("티본스테이크", 4);
            receiver.orderFood("티본스테이크", 3);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
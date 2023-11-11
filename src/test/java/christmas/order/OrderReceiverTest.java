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

        int size = receiver.getOrders().size();

        assertThat(size).isEqualTo(2);
    }

    @DisplayName("주문 중 이름이 틀리면 오류가 발생한다.")
    @Test
    void 주문_이름_오류() {
        assertThatThrownBy(()->
                receiver.orderFood("티본 스테이크",7))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 중 주문 수량이 잘못 되면 오류가 발생한다.")
    @Test
    void 주문량_최소_테스트() {
        assertThatThrownBy(() ->
                receiver.orderFood("티본스테이크", 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 중 주문 수량이 잘못 되면 오류가 발생한다.")
    @Test
    void 주문량_최대_테스트() {
        assertThatThrownBy(() ->
                receiver.orderFood("티본스테이크", 21))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문한 메뉴의 총량이 20을 넘어가면 예외가 발생한다")
    @Test
    void 메뉴총량_테스트() {
        assertThatThrownBy(() ->{
            receiver.orderFood("티본스테이크", 4);
            receiver.orderFood("제로콜라", 17);
            }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("같은 메뉴를 두번 주문하면 예외가 발생한다")
    @Test
    void 중복주문_테스트() {
        assertThatThrownBy(() ->{
            receiver.orderFood("티본스테이크", 4);
            receiver.orderFood("레드와인", 4);
            receiver.orderFood("티본스테이크", 3);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("음료만 주문했는지 확인할 수 있다. 음료만 있다면 true")
    @Test
    void 음료_테스트(){
        receiver.orderFood("레드와인",5);
        receiver.orderFood("제로콜라",3);
        receiver.orderFood("샴페인",3);

        boolean onlyDrink = receiver.hasCustomerOrderedOnlyDrink();

        assertThat(onlyDrink).isEqualTo(true);
    }

    @DisplayName("음료만 주문했는지 확인할 수 있다. 음료 말고도 있다면 false")
    @Test
    void 음료_테스트2(){
        receiver.orderFood("티본스테이크",5);
        receiver.orderFood("제로콜라",3);
        receiver.orderFood("샴페인",3);

        boolean onlyDrink = receiver.hasCustomerOrderedOnlyDrink();

        assertThat(onlyDrink).isEqualTo(false);
    }
}
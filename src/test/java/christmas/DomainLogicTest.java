package christmas;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.benefit.Present;
import christmas.foodmenu.Food;
import christmas.order.OrderReceiver;
import christmas.order.OrderResult;
import christmas.service.DiscountService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DomainLogicTest {

    LocalDate localDate;
    Map<Food,Integer> orders;
    OrderReceiver receiver;
    OrderResult orderResult;

    @BeforeEach
    void 생성자주입(){
        //given
        localDate = LocalDate.of(2023,12, 15);
        orders = new HashMap<>();
        receiver = new OrderReceiver(orders);

        receiver.orderFood("시저샐러드", 3);
        receiver.orderFood("해산물파스타", 4);
        receiver.orderFood("아이스크림", 10);
        receiver.orderFood("샴페인", 3);

        orderResult = new OrderResult(orders, localDate);
    }

    /**
     *     CAESAR_SALAD("시저샐러드", 8_000, FoodType.APPETIZER) ,
     *     SEAFOOD_PASTA("해산물파스타", 35_000, FoodType.MAIN),
     *     ICE_CREAM("아이스크림", 5_000, FoodType.DESSERT),
     *     CHAMPAGNE("샴페인",25_000, FoodType.DRINK);
     */
    @Test
    void 도메인로직_테스트_할인전(){
        int orderOriginalSum = orderResult.orderOriginalSum();

        assertThat(orderOriginalSum).isEqualTo(8000 * 3 + 35000 * 4 + 5000 * 10 + 25000 * 3);
    }
    @Test
    void 도메인로직_테스트_증정품(){
        Present present = orderResult.givePresent();
        assertThat(present).isEqualTo(Present.CHAMPAGNE);
    }
    @Test
    void 도메인로직_테스트_총혜택액(){
        int benefit = orderResult.allOfBenefitAmount();
        int dDay = 1000 + 1400;
        int specialDay = 0;
        int dayDiscount = 4 * 2023;
        int present = 25000;

        assertThat(benefit).isEqualTo(dDay + specialDay + dayDiscount + present);
    }
    @Test
    void 도메인로직_테스트_뱃지지급(){
        EventBadge eventBadge = orderResult.giveEventBadge();
        assertThat(eventBadge).isEqualTo(EventBadge.TREE);
    }
    @Test
    void 도메인로직_테스트_할인후금액(){
        int totalAmountAfterDiscount = orderResult.totalAmountAfterDiscount();
        assertThat(8000 * 3 + 35000 * 4 + 5000 * 10 + 25000 * 3 - (2400 + 20230));
    }
}

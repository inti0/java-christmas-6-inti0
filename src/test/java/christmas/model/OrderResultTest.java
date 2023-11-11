package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.EventBadge;
import christmas.benefit.Present;
import christmas.benefit.discount.DiscountPolicy;
import christmas.benefit.discount.DiscountPolicyFactory;
import christmas.order.Food;
import christmas.order.OrderReceiver;
import christmas.model.OrderResult;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderResultTest {

    LocalDate localDate;
    Map<Food,Integer> orders;
    OrderReceiver receiver;
    OrderResult orderResult;
    DiscountPolicyFactory discountPolicyFactory;

    List<DiscountPolicy> discountPolicies;
    @BeforeEach
    void 생성자주입(){
        //given
        localDate = LocalDate.of(2023,12, 15);
        orders = new HashMap<>();
        receiver = new OrderReceiver(orders);
        discountPolicyFactory = new DiscountPolicyFactory(orders, localDate);
        discountPolicies = discountPolicyFactory.createDiscountPolicies();

        receiver.orderFood("시저샐러드", 3);
        receiver.orderFood("해산물파스타", 4);
        receiver.orderFood("아이스크림", 10);
        receiver.orderFood("샴페인", 3);

        orderResult = new OrderResult(orders, discountPolicies);
    }

    /**
     *     CAESAR_SALAD("시저샐러드", 8_000, FoodType.APPETIZER) ,
     *     SEAFOOD_PASTA("해산물파스타", 35_000, FoodType.MAIN),
     *     ICE_CREAM("아이스크림", 5_000, FoodType.DESSERT),
     *     CHAMPAGNE("샴페인",25_000, FoodType.DRINK);
     */
    @Test
    void 할인전_금액_테스트(){
        int orderOriginalSum = orderResult.beforeDiscountSum();

        assertThat(orderOriginalSum).isEqualTo(8000 * 3 + 35000 * 4 + 5000 * 10 + 25000 * 3);
    }
    @Test
    void 할인_금액_테스트(){
        int discount = orderResult.allOfDiscount();

        int dDay = 1000 + 1400;
        int specialDay = 0;
        int dayDiscount = 4 * 2023;

        assertThat(discount).isEqualTo(dDay + specialDay + dayDiscount);
    }

    @Test
    @DisplayName("결제액이 10000원 이하라면, 할인은 적용되지 않는다.")
    void 할인_적용_테스트(){
        orders.clear();
        receiver.orderFood("아이스크림", 1);
        int discount = orderResult.allOfDiscount();

        assertThat(discount).isEqualTo(0);
    }
    @Test
    @DisplayName("증정품은 혜택전 금액이 12만원 이상이면 주어진다.")
    void 증정품_테스트(){
        Present present = orderResult.givePresent();
        assertThat(present).isEqualTo(Present.CHAMPAGNE);
    }
    @Test
    void 총혜택액_테스트(){
        int benefit = orderResult.allOfBenefitAmount();
        int dDay = 1000 + 1400;
        int specialDay = 0;
        int dayDiscount = 4 * 2023;
        int present = 25000;

        assertThat(benefit).isEqualTo(dDay + specialDay + dayDiscount + present);
    }
    @Test
    @DisplayName("뱃지는 할인액에 따라 주어진다.")
    void 뱃지지급_테스트(){
        EventBadge eventBadge = orderResult.giveEventBadge();
        assertThat(eventBadge).isEqualTo(EventBadge.TREE);
    }
    @Test
    void 할인후금액_테스트(){
        int afterDiscount = orderResult.paymentAfterDiscount();
        assertThat(8000 * 3 + 35000 * 4 + 5000 * 10 + 25000 * 3 - (2400 + 4 * 2023)).isEqualTo(afterDiscount);
    }
}

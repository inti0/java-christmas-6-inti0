package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.EventBadge;
import christmas.domain.benefit.Present;
import christmas.domain.benefit.discount.DiscountPolicy;
import christmas.domain.benefit.discount.DiscountPolicyFactory;
import christmas.domain.order.Food;
import christmas.domain.order.OrderReceiver;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderResultTest {

    LocalDate localDate;
    Map<Food, Integer> orders;
    OrderReceiver receiver;
    OrderResult orderResult;
    DiscountPolicyFactory discountPolicyFactory;
    List<DiscountPolicy> discountPolicies;

    @BeforeEach
    void 생성자주입() {
        //given
        localDate = LocalDate.of(2023, 12, 15);
        orders = new EnumMap<Food, Integer>(Food.class);
        receiver = new OrderReceiver(orders);
        discountPolicyFactory = new DiscountPolicyFactory(localDate, orders);
        discountPolicies = discountPolicyFactory.createDiscountPolicies();

        receiver.orderFood("시저샐러드", 3);
        receiver.orderFood("해산물파스타", 4);
        receiver.orderFood("아이스크림", 10);
        receiver.orderFood("샴페인", 3);

        orderResult = new OrderResult(orders, discountPolicies);
    }

    /**
     * CAESAR_SALAD("시저샐러드", 8_000, FoodType.APPETIZER), SEAFOOD_PASTA("해산물파스타", 35_000, FoodType.MAIN),
     * ICE_CREAM("아이스크림", 5_000, FoodType.DESSERT), CHAMPAGNE("샴페인",25_000, FoodType.DRINK)
     */
    @Test
    void 할인전_금액_테스트() {
        int orderOriginalSum = orderResult.beforeDiscountSum();

        assertThat(orderOriginalSum).isEqualTo(8000 * 3 + 35000 * 4 + 5000 * 10 + 25000 * 3);
    }

    @Test
    void 할인_금액_테스트() {
        int discount = orderResult.allOfDiscount();

        int dDay = 1000 + 1400;
        int specialDay = 0;
        int dayDiscount = 4 * 2023;

        assertThat(discount).isEqualTo(dDay + specialDay + dayDiscount);
    }

    @Test
    @DisplayName("결제액이 10000원 미만이라면, 할인은 적용되지 않는다.")
    void 할인_기준_테스트() {
        orders.clear();
        receiver.orderFood("아이스크림", 1);     // 5000원
        int discount = orderResult.allOfDiscount();

        assertThat(discount).isEqualTo(0);

        orders.clear();
        receiver.orderFood("아이스크림", 2);     // 10000원
        int discount2 = orderResult.allOfDiscount();

        assertThat(discount2).isGreaterThan(0);
    }

    @Test
    void 증정품_테스트() {
        Present present = orderResult.givePresent();
        assertThat(present).isEqualTo(Present.CHAMPAGNE);
    }

    @Test
    @DisplayName("증정품은 할인 적용전 금액이 12만원 이상일 때만 주어진다.")
    void 증정품_기준_테스트() {
        orders.clear();
        receiver.orderFood("시저샐러드", 14);    // 8000 * 14 < 120000
        Present present = orderResult.givePresent();
        assertThat(present).isEqualTo(Present.NOTHING);
    }

    @Test
    @DisplayName("증정품은 할인 적용전 금액이 12만원 이상일 때만 주어진다.")
    void 증정품_기준_테스트2() {
        orders.clear();
        receiver.orderFood("시저샐러드", 15);    // 8000 * 15 = 120000
        Present present2 = orderResult.givePresent();
        assertThat(present2).isEqualTo(Present.CHAMPAGNE);

        int paymentAfterDiscount = orderResult.paymentAfterDiscount();
        assertThat(paymentAfterDiscount).isLessThan(120000);
    }

    @Test
    @DisplayName("혜택액에는 증정품의 가격이 포함된다.")
    void 총혜택액_테스트() {
        int benefit = orderResult.allOfBenefitAmount();
        int dDay = 1000 + 1400;
        int specialDay = 0;
        int dayDiscount = 4 * 2023;
        int present = 25000;

        assertThat(benefit).isEqualTo(dDay + specialDay + dayDiscount + present);
    }

    @Test
    void 뱃지지급_테스트() {
        EventBadge eventBadge = orderResult.giveEventBadge();
        assertThat(eventBadge).isEqualTo(EventBadge.SANTA);
    }

    @Test
    @DisplayName("뱃지는 결제액이 아닌 혜택액에 따라 주어진다.")
    void 뱃지지급_없음_별_테스트() {
        orders.clear();
        receiver.orderFood("제로콜라", 18);
        receiver.orderFood("크리스마스파스타", 1);
        EventBadge eventBadge = orderResult.giveEventBadge();
        assertThat(eventBadge).isEqualTo(EventBadge.NOTHING);

        orders.clear();
        receiver.orderFood("크리스마스파스타", 2);
        EventBadge eventBadge2 = orderResult.giveEventBadge();
        assertThat(eventBadge2).isEqualTo(EventBadge.STAR);
    }

    @Test
    @DisplayName("뱃지는 결제액이 아닌 혜택액에 따라 주어진다.")
    void 뱃지지급_트리_테스트() {
        orders.clear();
        receiver.orderFood("크리스마스파스타", 4);
        EventBadge eventBadge = orderResult.giveEventBadge();
        assertThat(eventBadge).isEqualTo(EventBadge.TREE);
    }

    @Test
    @DisplayName("뱃지는 혜택이 기준이므로 할인이 적더라도 샴페인을 받으면 산타를 받게 된다.")
    void 뱃지지급_기준_테스트() {
        orders.clear();
        receiver.orderFood("시저샐러드", 20);

        Present present = orderResult.givePresent();
        assertThat(present).isEqualTo(Present.CHAMPAGNE);

        int discount = orderResult.allOfDiscount();
        assertThat(discount).isLessThan(20000);

        EventBadge eventBadge = orderResult.giveEventBadge();
        assertThat(eventBadge).isEqualTo(EventBadge.SANTA);
    }

    @Test
    @DisplayName("할인 후 금액은 샴페인의 증정과는 관계 없다.")
    void 할인후금액_테스트() {
        int afterDiscount = orderResult.paymentAfterDiscount();
        assertThat(8000 * 3 + 35000 * 4 + 5000 * 10 + 25000 * 3 - (2400 + 4 * 2023)).isEqualTo(afterDiscount);
    }
}

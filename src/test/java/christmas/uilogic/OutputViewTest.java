package christmas.uilogic;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.EventBadge;
import christmas.domain.benefit.Present;
import christmas.domain.order.Food;
import christmas.model.OrderResult;
import christmas.view.OutputView;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OutputViewTest {
    OrderResult orderResult = Mockito.mock(OrderResult.class);
    OutputView outputView = new OutputView(orderResult);

    @Test
    void 주문출력_테스트() {
        Map<Food, Integer> orders = Map.of(
                Food.TAPAS, 1,
                Food.CHAMPAGNE, 2);
        Mockito.when(orderResult.getOrders()).thenReturn(orders);

        String string = outputView.toStringOrders();
        assertThat(string).contains("타파스 1개");
        assertThat(string).contains("샴페인 2개");
    }

    @Test
    void 할인전_가격_테스트() {
        Mockito.when(orderResult.beforeDiscountSum()).thenReturn(181_500);
        String string = outputView.toStringPaymentBeforeDiscount();
        assertThat(string).isEqualTo("181,500원");
    }

    @Test
    void 증정메뉴_테스트() {
        Mockito.when(orderResult.givePresent()).thenReturn(Present.CHAMPAGNE);
        String string = outputView.toStringPresent();
        assertThat(string).isEqualTo("샴페인 1개");
    }

    @Test
    void 증정메뉴_없음_테스트() {
        Mockito.when(orderResult.givePresent()).thenReturn(Present.NOTHING);
        String string = outputView.toStringPresent();
        assertThat(string).isEqualTo("없음");
    }

    @Test
    void 혜택내역_테스트() {


    }

    @Test
    void 총혜택금액_테스트() {
        Mockito.when(orderResult.allOfBenefitAmount()).thenReturn(23000);
        String string = outputView.toStringAllOfBenefitAmount();
        assertThat(string).isEqualTo("-23,000원");
    }

    @Test
    void 총혜택금액_0원_테스트() {
        Mockito.when(orderResult.allOfBenefitAmount()).thenReturn(0);
        String string = outputView.toStringAllOfBenefitAmount();
        assertThat(string).isEqualTo("0원");
    }

    @Test
    void 할인후_예상_결제금액_테스트() {
        Mockito.when(orderResult.paymentAfterDiscount()).thenReturn(500000);
        String string = outputView.toStringPaymentAfterDiscount();
        assertThat(string).isEqualTo("500,000원");
    }

    @Test
    void 이벤트배지_테스트() {
        Mockito.when(orderResult.giveEventBadge()).thenReturn(EventBadge.STAR);
        String string = outputView.toStringEventBadge();
        assertThat(string).isEqualTo("별");
    }

    @Test
    void 이벤트배지_없음_테스트() {
        Mockito.when(orderResult.giveEventBadge()).thenReturn(EventBadge.NOTHING);
        String string = outputView.toStringEventBadge();
        assertThat(string).isEqualTo("없음");
    }
}

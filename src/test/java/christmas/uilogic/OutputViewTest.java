package christmas.uilogic;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.EventBadge;
import christmas.domain.benefit.Present;
import christmas.domain.order.Food;
import christmas.model.OrderResult;
import christmas.view.OutputView;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OutputViewTest {

    Map<Food, Integer> orders = Map.of(
            Food.TAPAS, 1,
            Food.CHAMPAGNE, 2
    );
    OrderResult orderResult = Mockito.mock(OrderResult.class);
    OutputView outputView = new OutputView(orderResult);
    OutputStream captor;

    @BeforeEach
    protected final void init() {
        captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));
    }

    @BeforeEach
    void print_종속메소드_모킹() {
        //given
        Mockito.when(orderResult.orders()).thenReturn(orders);
        Mockito.when(orderResult.beforeDiscountSum()).thenReturn(181_500);
        Mockito.when(orderResult.givePresent()).thenReturn(Present.CHAMPAGNE);
        Mockito.when(orderResult.allOfBenefitAmount()).thenReturn(23000);
        Mockito.when(orderResult.paymentAfterDiscount()).thenReturn(500000);
        Mockito.when(orderResult.giveEventBadge()).thenReturn(EventBadge.STAR);
    }


    @Test
    void 주문메뉴_테스트() {
        outputView.print();

        assertThat(captor.toString()).contains("타파스 1개");
        assertThat(captor.toString()).contains("샴페인 2개");
    }

    @Test
    void 할인전_가격_테스트() {
        outputView.print();

        assertThat(captor.toString()).containsIgnoringWhitespaces("<할인 전 총주문 금액>\n181,500원");
    }

    @Test
    void 증정메뉴_테스트() {
        outputView.print();

        assertThat(captor.toString()).containsIgnoringWhitespaces("<증정 메뉴>\n샴페인 1개");
    }

    @Test
    void 증정메뉴_없음_테스트() {
        Mockito.when(orderResult.givePresent()).thenReturn(Present.NOTHING);

        outputView.print();

        assertThat(captor.toString()).containsIgnoringWhitespaces("<증정 메뉴> 없음");
    }

    @Test
    void 혜택내역_테스트() {
        //OutputViewBenefitListTest 에서 테스트
    }

    @Test
    void 총혜택금액_테스트() {
        Mockito.when(orderResult.allOfBenefitAmount()).thenReturn(23000);

        outputView.print();

        assertThat(captor.toString()).containsIgnoringWhitespaces("<총 혜택 금액> -23,000원");
    }

    @Test
    void 총혜택금액_0원_테스트() {
        Mockito.when(orderResult.allOfBenefitAmount()).thenReturn(0);

        outputView.print();

        assertThat(captor.toString()).containsIgnoringWhitespaces("<총 혜택 금액> 0원");
    }

    @Test
    void 할인후_예상_결제금액_테스트() {
        outputView.print();

        assertThat(captor.toString()).containsIgnoringWhitespaces("<할인 후 예상 결제 금액> 500,000원");
    }

    @Test
    void 이벤트배지_테스트() {
        outputView.print();

        assertThat(captor.toString()).containsIgnoringWhitespaces("<12월 이벤트 배지> 별");
    }

    @Test
    void 이벤트배지_없음_테스트() {
        Mockito.when(orderResult.giveEventBadge()).thenReturn(EventBadge.NOTHING);

        outputView.print();

        assertThat(captor.toString()).containsIgnoringWhitespaces("<12월 이벤트 배지> 없음");
    }
}

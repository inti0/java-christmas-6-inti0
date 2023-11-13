package christmas.uilogic;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.Assertions;
import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.domain.benefit.Present;
import christmas.domain.benefit.discount.DiscountPolicyFactory;
import christmas.domain.order.Food;
import christmas.model.OrderResult;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OutputViewBenefitListTest {

    LocalDate localDate = LocalDate.of(2023, 12, 26);
    Map<Food, Integer> orders = new EnumMap<Food, Integer>(Food.class);
    DiscountPolicyFactory discountPolicyFactory = new DiscountPolicyFactory(orders, localDate);
    OrderResult orderResult = new OrderResult(orders, discountPolicyFactory.createDiscountPolicies());
    OutputView outputView = new OutputView(orderResult);

    @BeforeEach
    void 저장소초기화() {
        orders.clear();
    }

    @Test
    void 혜택없음() {
        orders.put(Food.CAESAR_SALAD, 10);

        int discount = orderResult.allOfDiscount();
        Present present = orderResult.givePresent();
        String list = outputView.toStringAllOfBenefitList();

        assertThat(discount).isEqualTo(0);
        assertThat(present).isEqualTo(Present.NOTHING);
        assertThat(list).isEqualTo("없음");
    }

    @Test
    void 할인만() {
        orders.put(Food.ICE_CREAM, 3);

        int discount = orderResult.allOfDiscount();
        Present present = orderResult.givePresent();
        String list = outputView.toStringAllOfBenefitList();

        assertThat(discount).isEqualTo(2023 * 3);
        assertThat(present).isEqualTo(Present.NOTHING);
        assertThat(list).isEqualTo("요일 할인: -6,069원");
    }

    @Test
    void 증정품만() {
        orders.put(Food.BBQ_RIB, 3);

        int discount = orderResult.allOfDiscount();
        Present present = orderResult.givePresent();
        String list = outputView.toStringAllOfBenefitList();

        assertThat(discount).isEqualTo(0);
        assertThat(present).isEqualTo(Present.CHAMPAGNE);
        assertThat(list).isEqualTo("증정 이벤트: -25000원");
    }

    @Test
    void 할인_증정품_둘다() {
        orders.put(Food.BBQ_RIB, 3);
        orders.put(Food.CHOCO_CAKE, 3);

        int discount = orderResult.allOfDiscount();
        Present present = orderResult.givePresent();
        String list = outputView.toStringAllOfBenefitList();

        assertThat(discount).isEqualTo(2023 * 3);
        assertThat(present).isEqualTo(Present.CHAMPAGNE);
        assertThat(list).contains("증정 이벤트: -25000원");
        assertThat(list).contains("요일 할인: -6,069원");
    }
}

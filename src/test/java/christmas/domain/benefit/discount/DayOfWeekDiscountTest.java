package christmas.domain.benefit.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.order.Food;
import christmas.domain.order.OrderReceiver;
import christmas.service.OrderManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class DayOfWeekDiscountTest {

    Map<Food, Integer> orders;
    OrderReceiver receiver;

    @BeforeEach
    void 생성자주입() {
        orders = new EnumMap<Food, Integer>(Food.class);
        receiver = new OrderReceiver(orders);
        receiver.orderFood(Food.TAPAS, 1);
        receiver.orderFood(Food.T_BONE_STEAK, 2);       // 메인
        receiver.orderFood(Food.CHOCO_CAKE, 3);         // 디저트
        receiver.orderFood(Food.CHAMPAGNE, 4);
    }

    @ParameterizedTest
    @EnumSource(value = DayOfWeek.class, names = {"FRIDAY", "SATURDAY"})
    @DisplayName("주말(금토)엔 메인요리가 할인된다.")
    void 요일할인_주말(DayOfWeek dayOfWeek) {
        LocalDate localDate = LocalDate.now();
        DayOfWeek day = DayOfWeek.from(dayOfWeek);
        LocalDate weekend = localDate.with(day);

        DayOfWeekDiscount dayOfWeekDiscount = new DayOfWeekDiscount(weekend, orders);
        int discount = dayOfWeekDiscount.discountAmount();

        assertThat(discount).isEqualTo(2023 * 2);
    }

    @ParameterizedTest
    @EnumSource(value = DayOfWeek.class, names = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY"})
    @DisplayName("평일(금토 제외)엔 디저트가 할인된다.")
    void 요일할인_평일(DayOfWeek dayOfWeek) {
        LocalDate localDate = LocalDate.now();
        DayOfWeek day = DayOfWeek.from(dayOfWeek);
        LocalDate weekday = localDate.with(day);

        DayOfWeekDiscount dayOfWeekDiscount = new DayOfWeekDiscount(weekday, orders);
        int discount = dayOfWeekDiscount.discountAmount();

        assertThat(discount).isEqualTo(2023 * 3);
    }

    @ParameterizedTest
    @EnumSource(value = DayOfWeek.class, names = {"SUNDAY", "FRIDAY"})
    @DisplayName("요일할인을 받지 못하면 0을 반환한다.")
    void 요일할인_해당없음(DayOfWeek dayOfWeek) {
        LocalDate localDate = LocalDate.now();
        DayOfWeek day = DayOfWeek.from(dayOfWeek);
        LocalDate weekday = localDate.with(day);

        orders.clear();
        orders.put(Food.CAESAR_SALAD, 5);

        DayOfWeekDiscount dayOfWeekDiscount = new DayOfWeekDiscount(weekday, orders);
        int discount = dayOfWeekDiscount.discountAmount();

        assertThat(discount).isEqualTo(0);
    }
}

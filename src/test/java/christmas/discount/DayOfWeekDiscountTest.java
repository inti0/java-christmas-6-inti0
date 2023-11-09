package christmas.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.foodmenu.Food;
import christmas.order.OrderReceiver;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class DayOfWeekDiscountTest {

    Map<Food, Integer> orders;
    OrderReceiver receiver;
    DayOfWeekDiscount dayOfWeekDiscount;

    @BeforeEach
    void 생성자주입(){
        orders = new HashMap<>();
        receiver = new OrderReceiver(orders);
        dayOfWeekDiscount = new DayOfWeekDiscount(orders);
    }

    @ParameterizedTest
    @EnumSource(value = DayOfWeek.class, names = {"FRIDAY", "SATURDAY"})
    @DisplayName("주말(금토)엔 메인요리가 할인된다.")
    void 요일할인_1(DayOfWeek day){
        orders.put(Food.TAPAS, 1);
        orders.put(Food.T_BONE_STEAK, 2);       // 메인
        orders.put(Food.CHOCO_CAKE, 3);
        orders.put(Food.CHAMPAGNE, 4);

        int result = dayOfWeekDiscount.dayDiscountPrice(day);

        assertThat(result).isEqualTo(2023 * 2);
    }

    @ParameterizedTest
    @EnumSource(value = DayOfWeek.class, names = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY"})
    @DisplayName("평일(금토 제외)엔 디저트가 할인된다.")
    void 요일할인_2(DayOfWeek day){
        orders.put(Food.TAPAS, 1);
        orders.put(Food.T_BONE_STEAK, 2);
        orders.put(Food.CHOCO_CAKE, 3);         // 디저트
        orders.put(Food.CHAMPAGNE, 4);

        int result = dayOfWeekDiscount.dayDiscountPrice(day);

        assertThat(result).isEqualTo(2023 * 3);
    }

    @Test
    void 리시버_요일할인_종합테스트(){
        receiver.orderFood("양송이수프",4);
        receiver.orderFood("바비큐립",3);
        receiver.orderFood("아이스크림", 2);
        receiver.orderFood("레드와인",1);
        DayOfWeek weekend = DayOfWeek.FRIDAY;
        DayOfWeek weekday = DayOfWeek.SUNDAY;

        int weekendDC = dayOfWeekDiscount.dayDiscountPrice(weekend);
        int weekdayDC = dayOfWeekDiscount.dayDiscountPrice(weekday);

        assertThat(weekendDC).isEqualTo(3 * 2023);
        assertThat(weekdayDC).isEqualTo(2 * 2023);
    }
}

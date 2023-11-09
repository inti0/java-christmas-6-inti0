package christmas.foodmenu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FoodMenuTest {


    @Test
    void 푸드메뉴_테스트() {
        String menuName = "양송이수프";
        boolean isExist = OrderService.menuIsExist(String menuName);

        Assertions.assertThat(isExist).isEqualTo(true);
    }

    @Test
    void 푸드메뉴_실패테스트() {
        String menuName = "콜라";
        boolean isExist = OrderService.menuIsExist(String menuName);

        Assertions.assertThat(isExist).isEqualTo(false);
    }
}

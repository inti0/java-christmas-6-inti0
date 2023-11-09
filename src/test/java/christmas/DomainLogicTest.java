package christmas;

import christmas.foodmenu.Food;
import christmas.order.OrderReceiver;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class DomainLogicTest {

    Map<Food,Integer> orders = new HashMap<>();
    OrderReceiver receiver = new OrderReceiver(orders);

    @Test
    도메인로직_테스트(){

    }
}

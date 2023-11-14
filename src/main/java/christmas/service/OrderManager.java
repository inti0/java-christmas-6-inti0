package christmas.service;

import christmas.AppConfig;
import christmas.domain.order.Food;
import christmas.domain.order.OrderReceiver;
import java.util.EnumMap;
import java.util.List;

public class OrderManager {
    private static final String ORDER_DELIMITER = "-";
    private static final int UNIT_ORDER_SIZE = 2;
    private final List<String> menuAndAmount;

    public OrderManager(List<String> menuAndAmount) {
        this.menuAndAmount = menuAndAmount;
    }

    public OrderReceiver handleOrder() {
        OrderReceiver orderReceiver = createValidOrder();
        checkOnlyDrink(orderReceiver);
        return orderReceiver;
    }

    private OrderReceiver createValidOrder() {
        OrderReceiver orderReceiver = new OrderReceiver(new EnumMap<Food, Integer>(Food.class));

        for (String string : menuAndAmount) {
            String[] split = string.split(ORDER_DELIMITER, UNIT_ORDER_SIZE);
            validateLength(split);
            orderReceiver.orderFood(split[0], Integer.parseInt(split[1]));
        }
        return orderReceiver;
    }

    private static void validateLength(String[] split) {
        if (split.length != UNIT_ORDER_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    private static void checkOnlyDrink(OrderReceiver orderReceiver) {
        if (orderReceiver.isOrderContainOnlyDrink()) {
            throw new IllegalArgumentException();
        }
    }
}

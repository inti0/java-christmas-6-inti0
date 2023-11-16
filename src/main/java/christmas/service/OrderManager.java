package christmas.service;

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

    public OrderReceiver processOrder() {
        OrderReceiver orderReceiver = new OrderReceiver(new EnumMap<Food, Integer>(Food.class));
        receiveOrder(orderReceiver);
        checkOnlyDrink(orderReceiver);
        return orderReceiver;
    }

    private void receiveOrder(OrderReceiver orderReceiver) throws IllegalArgumentException {
        for (String string : menuAndAmount) {
            String[] split = string.split(ORDER_DELIMITER, UNIT_ORDER_SIZE);
            validateLength(split);

            Food menu = Food.findMenuByName(split[0]);
            int amount = Integer.parseInt(split[1]);

            orderReceiver.orderFood(menu, amount);
        }
    }

    private void validateLength(String[] split) {
        if (split.length != UNIT_ORDER_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    private void checkOnlyDrink(OrderReceiver orderReceiver) {
        if (orderReceiver.isOrderContainOnlyDrink()) {
            throw new IllegalArgumentException();
        }
    }
}

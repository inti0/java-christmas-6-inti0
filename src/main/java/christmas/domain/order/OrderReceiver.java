package christmas.domain.order;

import christmas.AppConstants;
import java.util.Collections;
import java.util.Map;

public class OrderReceiver {

    private static final int ORDER_AMOUNT_UNIT_MIN = 1;
    private Map<Food, Integer> orders;
    public OrderReceiver(Map<Food, Integer> orders) {
        this.orders = orders;
    }

    public void orderFood(Food menu, int amount) {
        checkAlreadyOrdered(menu);
        validate(amount);
        orders.put(menu, amount);
    }

    private void checkAlreadyOrdered(Food food) {
        if (orders.containsKey(food)) {
            throw new IllegalArgumentException();
        }
    }

    private void validate(int amount) {
        validateUnit(amount);
        validateOrderSum(amount);
    }

    private void validateUnit(int amount) {
        if (amount < ORDER_AMOUNT_UNIT_MIN) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOrderSum(int amount) {
        int total = orders.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (total + amount > AppConstants.ORDER_MAX_RANGE) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isOrderContainOnlyDrink() {
        return orders.keySet().stream()
                .allMatch(food -> food.getFoodType().equals(FoodType.DRINK));
    }

    public Map<Food, Integer> getOrders() {
        return Collections.unmodifiableMap(orders);
    }
}

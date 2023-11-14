package christmas.domain.order;

import java.util.Map;

public class OrderReceiver {
    private static final int ORDER_AMOUNT_UNIT_MIN = 1;
    private static final int ORDER_AMOUNT_SUM_MAX = 20;
    private Map<Food, Integer> orders;
    public OrderReceiver(Map<Food, Integer> orders) {
        this.orders = orders;
    }

    public void orderFood(String inputName, int amount) {
        Food menu = Food.findMenuByName(inputName);
        checkAlreadyOrdered(menu);
        validateOrderSize(amount);
        orders.put(menu, amount);
    }

    private void checkAlreadyOrdered(Food food) {
        if (orders.containsKey(food)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOrderSize(int amount) {
        validateUnit(amount);
        validateTotalOrder(amount);
    }

    private static void validateUnit(int amount) {
        if (amount < ORDER_AMOUNT_UNIT_MIN) {
            throw new IllegalArgumentException();
        }
    }

    private void validateTotalOrder(int amount) {
        int total = orders.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (total + amount > ORDER_AMOUNT_SUM_MAX) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isOrderContainOnlyDrink() {
        return orders.keySet().stream()
                .allMatch(food -> food.getFoodType().equals(FoodType.DRINK));
    }

    public Map<Food, Integer> getOrders() {
        return orders;
    }
}

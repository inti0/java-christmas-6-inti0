package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.order.Food;
import christmas.order.OrderReceiver;
import java.util.EnumMap;

public class InputView {

    private static final int FIRST_DATE = 1;
    private static final int LAST_DATE = 31;
    private static final int ORDER_MAX_RANGE = 20;
    private static final int UNIT_ORDER_SIZE = 2;
    private static final String INPUT_DELIMITER = ",";
    private static final String ORDER_DELIMITER = "-";

    public int readDate() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        while (true) {
            try {
                return parseDate();
            } catch (IllegalArgumentException exception) {
                System.out.println("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
            }
        }
    }

    private int parseDate() {
        String input = Console.readLine();
        int date = Integer.parseInt(input);

        if(date > LAST_DATE || date < FIRST_DATE) {
            throw new IllegalArgumentException();
        }

        return date;
    }

    public OrderReceiver readOrder(){
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        while (true){
            try {
                return createValidOrder();
            } catch (IllegalArgumentException exception) {
                System.out.println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            }
        }
    }

    private OrderReceiver createValidOrder() {
        OrderReceiver orderReceiver = new OrderReceiver(new EnumMap<Food, Integer>(Food.class));

        String orderLine = Console.readLine();
        String[] strings = orderLine.split(INPUT_DELIMITER, ORDER_MAX_RANGE);
        processOrder(strings, orderReceiver);

        checkOnlyDrink(orderReceiver);

        return orderReceiver;
    }
    private static void processOrder(String[] strings, OrderReceiver orderReceiver) {
        for (String menuAndAmount : strings) {
            String[] split = menuAndAmount.split(ORDER_DELIMITER, UNIT_ORDER_SIZE);
            validateLength(split);

            String menu = split[0].trim();
            int amount = Integer.parseInt(split[1].trim());

            orderReceiver.orderFood(menu, amount);
        }
    }
    private static void validateLength(String[] split) {
        if(split.length < UNIT_ORDER_SIZE){
            throw new IllegalArgumentException();
        }
    }

    private static void checkOnlyDrink(OrderReceiver orderReceiver) {
        if(orderReceiver.isOrderContainOnlyDrink()){
            throw new IllegalArgumentException();
        }
    }
}

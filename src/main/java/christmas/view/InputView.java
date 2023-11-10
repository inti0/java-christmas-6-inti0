package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.order.OrderReceiver;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class InputView {

    private static final int LAST_DATE = 31;
    private static final int FIRST_DATE = 1;
    private static final String INPUT_DELIMITER = ",";
    private static final int ORDER_MAX_RANGE = 20;
    private static final String ORDER_DELIMITER = "-";
    private static final int ORDER_SIZE = 2;


    public int readDate() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        while (true) {
            try {
                return parseDate();
            } catch (IllegalArgumentException exception) {
                System.out.println("[ERROR]");
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
                String orderLine = Console.readLine();
                String[] strings = orderLine.split(INPUT_DELIMITER, ORDER_MAX_RANGE);
                return createValidOrder(strings);
            } catch (NoSuchElementException exception){
                System.out.println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            } catch (IllegalArgumentException exception) {
                System.out.println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            }
        }
    }

    private OrderReceiver createValidOrder(String[] strings) {
        OrderReceiver orderReceiver = new OrderReceiver(new HashMap<>());

        for (String menuAndAmount : strings) {
            String[] split = menuAndAmount.split(ORDER_DELIMITER, ORDER_SIZE);
            String menu = split[0];
            int amount = Integer.parseInt(split[1]);

            orderReceiver.orderFood(menu, amount);
        }
        return orderReceiver;
    }
}

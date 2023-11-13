package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;

public class InputView {

    private static final int FIRST_DATE = 1;
    private static final int LAST_DATE = 31;
    private static final int ORDER_MAX_RANGE = 20;
    private static final String INPUT_DELIMITER = ",";
    private static final String BLANK = " ";
    private static final String EMPTY = "";

    public int readDate() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다. \n" +
                "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
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
        int date = Integer.parseInt(input.trim());

        if(date > LAST_DATE || date < FIRST_DATE) {
            throw new IllegalArgumentException();
        }

        return date;
    }

    public List<String> parseInputToOrders(){
        orderGreetMessage();
        return parseList();
    }

    private static void orderGreetMessage() {
        System.out.println("총주문 금액 10,000원 이상부터 이벤트가 적용됩니다. \n" +
                "음료만 주문 시, 주문할 수 없습니다. \n" +
                "메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다. \n" +
                "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
    }

    private List<String> parseList() {
        String orderLine = Console.readLine();
        orderLine = orderLine.replace(BLANK, EMPTY);
        String[] strings = orderLine.split(INPUT_DELIMITER, ORDER_MAX_RANGE);

        return List.of(strings);
    }
    public void OrderErrorMessage() {
        System.out.println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }
}

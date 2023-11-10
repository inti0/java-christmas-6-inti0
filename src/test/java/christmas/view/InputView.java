package christmas.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final int LAST_DATE = 31;
    private static final int FIRST_DATE = 1;

    public int readDate() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        String input = Console.readLine();
        while (true) {
            try {
                return parseDate(input);
            } catch (IllegalArgumentException exception) {
                System.out.println("에러메시지");
            }
        }
    }

    private int parseDate(String input) {
        int date = Integer.parseInt(input);
        if(date > LAST_DATE || date < FIRST_DATE) {
            throw new IllegalArgumentException();
        }
        return date;
    }

    public void order(){

    }
}

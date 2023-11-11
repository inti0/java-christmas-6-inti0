package christmas.uilogic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.Console;
import christmas.AppConfig;
import christmas.view.InputView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputViewTest {

    InputView inputView = AppConfig.inputView();
    @AfterEach
    void closeConsole() {
        Console.close();
    }
    @ParameterizedTest
    @ValueSource(strings = {"1","21","31","25"})
    void 날짜_테스트(String input){
        setIn(input);

        inputView.readDate();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1일","크리스마스","iv","0"})
    void 날짜_예외_테스트(String input){
        setIn(input);

        assertThatThrownBy(()->inputView.readDate())
                .isInstanceOf(NoSuchElementException.class);
    }
    @Test
    void 주문_테스트(){
        String allFood = "양송이수프-1,타파스-1,시저샐러드-1,티본스테이크-1,바비큐립-1,"
                + "해산물파스타-1,크리스마스파스타-1,초코케이크-1,아이스크림-1,제로콜라-1,레드와인-1,샴페인-1";
        setIn(allFood);
        inputView.readOrder();
    }

    @ParameterizedTest
    @ValueSource(strings = {"양송이수프-일","양송이수프","양송이숩-3","제로콜라,아이스크림"})
    void 주문_실패_테스트(String input){
        setIn(input);
        assertThatThrownBy(()->inputView.readDate())
                .isInstanceOf(NoSuchElementException.class);
    }
    private static void setIn(String input) {
        byte[] bytes = input.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        System.setIn(inputStream);
    }
}

package christmas.uilogic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.Console;
import christmas.AppConfig;
import christmas.view.InputView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
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
    @ValueSource(strings = {"1", "21 ", "  31 ", "25",})
    void 날짜_테스트(String input) {
        setIn(input);

        inputView.readDate();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1일", "크리스마스", "iv", "0", ""})
    void 날짜_예외_테스트(String input) {
        setIn(input);

        assertThatThrownBy(() -> inputView.readDate())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("주문입력에서 띄어쓰기는 무시되고 주문은 ,로 구분된다.")
    void 주문_테스트() {
        String allFood = " 양송이수프 - 1 , 토 마토파스타-1";
        setIn(allFood);
        List<String> strings = inputView.parseInputToList();

        assertThat(strings.get(0)).isEqualTo("양송이수프-1");
        assertThat(strings.get(1)).isEqualTo("토마토파스타-1");
    }

    private static void setIn(String input) {
        byte[] bytes = input.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        System.setIn(inputStream);
    }
}

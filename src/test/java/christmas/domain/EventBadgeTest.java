package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EventBadgeTest {

    @DisplayName("이벤트 뱃지는 5000, 10000, 20000에서 바뀐다.")
    @MethodSource("eventBadge")
    @ParameterizedTest
    void selectEventBadge3(int benefitAmount, EventBadge eventBadge) {
        EventBadge selectEventBadge = EventBadge.selectEventBadge(benefitAmount);

        assertThat(selectEventBadge).isEqualTo(eventBadge);
    }

    static Stream<Arguments> eventBadge() {
        return Stream.of(
                Arguments.of(0, EventBadge.NOTHING),
                Arguments.of(4999, EventBadge.NOTHING),
                Arguments.of(5000, EventBadge.STAR),
                Arguments.of(9999, EventBadge.STAR),
                Arguments.of(10000, EventBadge.TREE),
                Arguments.of(19999, EventBadge.TREE),
                Arguments.of(20000, EventBadge.SANTA),
                Arguments.of(30000, EventBadge.SANTA)
        );
    }
}
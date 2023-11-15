package christmas.domain.benefit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PresentTest {

    @Test
    void selectPresent() {
        Present present1 = Present.selectPresent(119000);
        Present present2 = Present.selectPresent(120000);
        Present present3 = Present.selectPresent(200000);

        assertThat(present1).isEqualTo(Present.NOTHING);
        assertThat(present2).isEqualTo(Present.CHAMPAGNE);
        assertThat(present3).isEqualTo(Present.CHAMPAGNE);
    }
}
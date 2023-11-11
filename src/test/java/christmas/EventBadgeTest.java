package christmas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EventBadgeTest {

    @Test
    void selectEventBadge() {
        EventBadge eventBadge1 = EventBadge.selectEventBadge(3000);
        EventBadge eventBadge2 = EventBadge.selectEventBadge(5000);
        EventBadge eventBadge3 = EventBadge.selectEventBadge(9000);

        assertThat(eventBadge1).isEqualTo(EventBadge.NOTHING);
        assertThat(eventBadge2).isEqualTo(EventBadge.STAR);
        assertThat(eventBadge3).isEqualTo(EventBadge.STAR);
    }

    @Test
    void selectEventBadge2() {
        EventBadge eventBadge1 = EventBadge.selectEventBadge(10000);
        EventBadge eventBadge2 = EventBadge.selectEventBadge(16000);
        EventBadge eventBadge3 = EventBadge.selectEventBadge(20000);
        EventBadge eventBadge4 = EventBadge.selectEventBadge(30000);

        assertThat(eventBadge1).isEqualTo(EventBadge.TREE);
        assertThat(eventBadge2).isEqualTo(EventBadge.TREE);
        assertThat(eventBadge3).isEqualTo(EventBadge.SANTA);
        assertThat(eventBadge4).isEqualTo(EventBadge.SANTA);
    }
}
package christmas;

import java.util.Arrays;
import java.util.Comparator;

public enum EventBadge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NOTHING("없음", 0);

    private String name;
    private int condition;

    private EventBadge(String name, int condition) {
        this.name = name;
        this.condition = condition;
    }
    public String getName() {
        return name;
    }

    static public EventBadge selectEventBadge(int discountSum) {
        return Arrays.stream(EventBadge.values())
                .filter(eventBadge -> discountSum >= eventBadge.condition)
                .min(Comparator.naturalOrder())
                .orElse(NOTHING);
    }
}

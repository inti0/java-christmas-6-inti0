package christmas;

public enum EventBadge {
    SANTA("산타", 20_000),
    STAR("별", 5_000),
    TREE("트리", 10_000),
    NOTHING("없음",0);

    public String item;
    public int condition;

    private EventBadge(String item, int condition) {
        this.item = item;
        this.condition = condition;
    }

    static public EventBadge selectEventBadge(int discountSum){
        if(discountSum >= EventBadge.SANTA.condition) {
            return EventBadge.SANTA;
        }
        if(discountSum >= EventBadge.TREE.condition) {
            return EventBadge.TREE;
        }
        if(discountSum >= EventBadge.STAR.condition) {
            return EventBadge.STAR;
        }
        return NOTHING;
    }
}

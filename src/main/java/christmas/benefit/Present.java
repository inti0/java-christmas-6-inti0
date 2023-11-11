package christmas.benefit;

public enum Present {

    CHAMPAGNE(120_000, 25_000, "샴페인"),
    NOTHING(0, 0, "없음");
    private final int condition;
    private final int price;
    private final String item;

    private Present(int condition, int price, String item) {
        this.condition = condition;
        this.price = price;
        this.item = item;
    }

    static public Present selectPresent(int originalPrice) {
        if (originalPrice >= Present.CHAMPAGNE.condition) {
            return Present.CHAMPAGNE;
        }
        return Present.NOTHING;
    }

    public int getPrice() {
        return price;
    }

    public String getItem() {
        return item;
    }
}

package christmas.domain.benefit;

public enum Present {

    CHAMPAGNE(120_000, 25_000, "샴페인", 1),
    NOTHING(-1, -1, "없음", -1);

    private final int condition;
    private final int price;
    private final String item;
    private final int amount;

    private Present(int condition, int price, String item, int amount) {
        this.condition = condition;
        this.price = price;
        this.item = item;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }
}

package christmas.benefit;

public enum Present {

    RED_WINE(120_000, 25_000);
    public int condition;
    public int price;
    Present(int condition, int price) {
        this.condition = condition;
        this.price = price;
    }
}

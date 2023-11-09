package christmas.benefit;

public enum Present {

    CHAMPAGNE(120_000, 25_000,"샴페인"),
    NOTHING(0,0,"없음");
    public int condition;
    public int price;
    String item;

    Present(int condition, int price, String item) {
        this.condition = condition;
        this.price = price;
        this.item = item;
    }

    static public Present selectPresent(int originalPrice){
        if(originalPrice >= Present.CHAMPAGNE.condition) {
            return Present.CHAMPAGNE;
        }
        return Present.NOTHING;
    }
}

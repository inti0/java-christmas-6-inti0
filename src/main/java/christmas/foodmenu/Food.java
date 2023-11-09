package christmas.foodmenu;
/**
 * <애피타이저>
 * 양송이수프(6,000), 타파스(5,500), 시저샐러드(8,000)
 *
 * <메인>
 * 티본스테이크(55,000), 바비큐립(54,000), 해산물파스타(35,000), 크리스마스파스타(25,000)
 *
 * <디저트>
 * 초코케이크(15,000), 아이스크림(5,000)
 *
 * <음료>
 * 제로콜라(3,000), 레드와인(60,000), 샴페인(25,000) *
 */

public enum Food {
    BUTTON_MUSHROOM_SOUP("양송이수프", 6_000, FoodType.APPETIZER),
    ZERO_COLA("제로콜라",3_000, FoodType.DRINK);
    public String name;
    public int price;
    public FoodType foodType;
    Food(String name, int price, FoodType foodType) {
        this.name = name;
        this.price = price;
        this.foodType = foodType;
    }
}

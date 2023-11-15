package christmas.domain.order;

import java.util.Arrays;

public enum Food {

    BUTTON_MUSHROOM_SOUP("양송이수프", 6_000, FoodType.APPETIZER),
    TAPAS("타파스", 5_500, FoodType.APPETIZER),
    CAESAR_SALAD("시저샐러드", 8_000, FoodType.APPETIZER),
    T_BONE_STEAK("티본스테이크", 55_000, FoodType.MAIN),
    BBQ_RIB("바비큐립", 54_000, FoodType.MAIN),
    SEAFOOD_PASTA("해산물파스타", 35_000, FoodType.MAIN),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000, FoodType.MAIN),
    CHOCO_CAKE("초코케이크", 15_000, FoodType.DESSERT),
    ICE_CREAM("아이스크림", 5_000, FoodType.DESSERT),
    ZERO_COLA("제로콜라", 3_000, FoodType.DRINK),
    RED_WINE("레드와인", 60_000, FoodType.DRINK),
    CHAMPAGNE("샴페인", 25_000, FoodType.DRINK);

    private String name;
    private int price;
    private FoodType foodType;

    private Food(String name, int price, FoodType foodType) {
        this.name = name;
        this.price = price;
        this.foodType = foodType;
    }

    static public Food findMenuByName(String inputName) {
        return Arrays.stream(Food.values())
                .filter(food -> food.name.equals(inputName))
                .findAny().orElseThrow(IllegalArgumentException::new);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public FoodType getFoodType() {
        return foodType;
    }
}

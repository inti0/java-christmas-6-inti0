package christmas.service;

import christmas.foodmenu.Food;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class OrderService {

    public Food findMenuBy(String inputName){
        return Arrays.stream(Food.values())
                .filter(food -> food.name.equals(inputName))
                .findAny().orElseThrow(NoSuchElementException::new);
    }
}

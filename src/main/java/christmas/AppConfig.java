package christmas;

import christmas.domain.benefit.discount.DiscountPolicyFactory;
import christmas.domain.order.Food;
import christmas.domain.order.OrderReceiver;
import christmas.model.OrderResult;
import christmas.service.OrderManager;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.time.Month;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AppConfig {

    public static final int THIS_YEAR = 2023;
    public static final int CHRISTMAS_DATE = 25;
    public static final int NO_DISCOUNT = 0;
    public static final Month THIS_MONTH = Month.DECEMBER;

    public static InputView inputView() {
        return new InputView();
    }

    public static OrderReceiver orderReceiver() {
        return new OrderReceiver(new EnumMap<Food, Integer>(Food.class));
    }

    public static OrderManager orderManger(List<String> strings) {
        return new OrderManager(strings);
    }

    public static DiscountPolicyFactory discountPolicyFactory(LocalDate bookDate, Map<Food, Integer> orders) {
        return new DiscountPolicyFactory(orders, bookDate);
    }

    public static OutputView outputView(OrderResult orderResult) {
        return new OutputView(orderResult);
    }
}

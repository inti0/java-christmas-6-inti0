package christmas.controller;

import christmas.AppConfig;
import christmas.benefit.discount.DiscountPolicy;
import christmas.benefit.discount.DiscountPolicyFactory;
import christmas.foodmenu.Food;
import christmas.model.OrderResult;
import christmas.order.OrderReceiver;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class OrderController {
    InputView inputView = AppConfig.inputView();

    public void run() {
        int date = inputView.readDate();
        LocalDate bookDate = LocalDate.of(AppConfig.THIS_YEAR, Month.DECEMBER, date);
        OrderReceiver orderReceiver = inputView.readOrder();

        Map<Food, Integer> orders = orderReceiver.getOrders();
        DiscountPolicyFactory discountPolicyFactory = new DiscountPolicyFactory(orders, bookDate);
        List<DiscountPolicy> discountPolicies = discountPolicyFactory.createDiscountPolicies();

        OrderResult orderResult = new OrderResult(orders, discountPolicies);
        OutputView outputView = new OutputView(orderResult);

        outputView.print();
    }
}

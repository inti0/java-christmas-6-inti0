package christmas.controller;

import christmas.benefit.discount.DiscountPolicy;
import christmas.benefit.discount.DiscountPolicyFactory;
import christmas.foodmenu.Food;
import christmas.order.OrderReceiver;
import christmas.model.OrderResult;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class OrderController {
    private static final int THIS_YEAR = 2023;
    InputView inputView = new InputView();

    public void run() {
        int date = inputView.readDate();
        LocalDate bookDate = LocalDate.of(THIS_YEAR, Month.DECEMBER, date);
        OrderReceiver orderReceiver = inputView.readOrder();

        Map<Food, Integer> orders = orderReceiver.getOrders();
        DiscountPolicyFactory discountPolicyFactory = new DiscountPolicyFactory(orders, bookDate);
        List<DiscountPolicy> discountPolicies = discountPolicyFactory.createDiscountPolicies();

        OrderResult orderResult = new OrderResult(orders, discountPolicies);
        OutputView outputView = new OutputView(orderResult);

        outputView.print();
    }
}

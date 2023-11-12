package christmas.controller;

import christmas.AppConfig;
import christmas.domain.benefit.discount.DiscountPolicy;
import christmas.domain.benefit.discount.DiscountPolicyFactory;
import christmas.domain.order.Food;
import christmas.model.OrderResult;
import christmas.domain.order.OrderReceiver;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class OrderController {
    private final InputView inputView;
    private OrderResult orderResult;
    private OutputView outputView;

    public OrderController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        book();
        printBill();
    }
    private void book() {
        int date = inputView.readDate();
        OrderReceiver orderReceiver = inputView.readOrder();
        LocalDate bookDate = LocalDate.of(AppConfig.THIS_YEAR, Month.DECEMBER, date);
        Map<Food, Integer> orders = orderReceiver.getOrders();

        DiscountPolicyFactory discountPolicyFactory = new DiscountPolicyFactory(orders, bookDate);
        List<DiscountPolicy> discountPolicies = discountPolicyFactory.createDiscountPolicies();

        orderResult = new OrderResult(orders, discountPolicies);
    }

    private void printBill() {
        outputView = new OutputView(orderResult);
        outputView.print();
    }
}

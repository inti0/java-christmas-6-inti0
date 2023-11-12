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
    public OrderController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        OrderResult orderResult = book();
        printBill(orderResult);
    }
    
    private OrderResult book() {
        int date = inputView.readDate();
        LocalDate bookDate = LocalDate.of(AppConfig.THIS_YEAR, Month.DECEMBER, date);

        OrderReceiver orderReceiver = inputView.readOrder();
        Map<Food, Integer> orders = orderReceiver.getOrders();

        DiscountPolicyFactory discountPolicyFactory = new DiscountPolicyFactory(orders, bookDate);
        List<DiscountPolicy> discountPolicies = discountPolicyFactory.createDiscountPolicies();

        return new OrderResult(orders, discountPolicies);
    }

    private void printBill(OrderResult orderResult) {
        OutputView outputView = new OutputView(orderResult);
        outputView.print();
    }
}

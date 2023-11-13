package christmas.controller;

import christmas.AppConfig;
import christmas.domain.benefit.discount.DiscountPolicy;
import christmas.domain.benefit.discount.DiscountPolicyFactory;
import christmas.domain.order.Food;
import christmas.domain.order.OrderReceiver;
import christmas.model.OrderResult;
import christmas.service.OrderManager;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OrderController {
    private final InputView inputView;
    public OrderController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        LocalDate bookDate = readBookDate();
        Map<Food, Integer> orders = readOrders();
        OrderResult orderResult = getResult(bookDate, orders);
        printBill(orderResult);
    }

    private LocalDate readBookDate() {
        int date = inputView.readDate();
        return LocalDate.of(AppConfig.THIS_YEAR, AppConfig.THIS_MONTH, date);
    }

    private Map<Food, Integer> readOrders() {
        while (true) {
            try {
                List<String> strings = inputView.parseInputToOrders();
                OrderManager orderManger = new OrderManager(strings);
                OrderReceiver orderReceiver = orderManger.processOrder();
                Map<Food, Integer> orders = orderReceiver.getOrders();
                return orders;
            } catch (IllegalArgumentException exception) {
                inputView.CreateOrderErrorMessage();
            }
        }

    }

    private OrderResult getResult(LocalDate bookDate, Map<Food, Integer> orders) {
        DiscountPolicyFactory discountPolicyFactory = new DiscountPolicyFactory(orders, bookDate);
        List<DiscountPolicy> discountPolicies = discountPolicyFactory.createDiscountPolicies();

        return new OrderResult(orders, discountPolicies);
    }

    private void printBill(OrderResult orderResult) {
        OutputView outputView = new OutputView(orderResult);
        outputView.print();
    }
}
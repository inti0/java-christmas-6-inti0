package christmas.controller;

import christmas.AppConstants;
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

public class EventPlanner {

    private final InputView inputView;

    public EventPlanner(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        LocalDate bookDate = readBookDate();
        Map<Food, Integer> orders = readOrder();
        OrderResult orderResult = getResult(bookDate, orders);
        printBill(orderResult);
    }

    private LocalDate readBookDate() {
        int date = inputView.readDate();
        return LocalDate.of(AppConstants.EVENT_YEAR, AppConstants.EVENT_MONTH, date);
    }

    private Map<Food, Integer> readOrder() {
        inputView.orderGreetMessage();
        while (true) {
            try {
                return retrieveOrder();
            } catch (IllegalArgumentException exception) {
                inputView.OrderErrorMessage();
            }
        }
    }

    private Map<Food, Integer> retrieveOrder() {
        List<String> strings = inputView.parseInputToList();
        OrderManager orderManger = new OrderManager(strings);
        OrderReceiver orderReceiver = orderManger.processOrder();
        Map<Food, Integer> orders = orderReceiver.getOrders();

        return orders;
    }

    private OrderResult getResult(LocalDate bookDate, Map<Food, Integer> orders) {
        DiscountPolicyFactory discountPolicyFactory = new DiscountPolicyFactory(bookDate, orders);
        List<DiscountPolicy> discountPolicies = discountPolicyFactory.createDiscountPolicies();

        return new OrderResult(orders, discountPolicies);
    }

    private void printBill(OrderResult orderResult) {
        OutputView outputView = new OutputView(orderResult);
        outputView.printAll();
    }
}
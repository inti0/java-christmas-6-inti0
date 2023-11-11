package christmas;

import christmas.controller.OrderController;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        OrderController orderController = new OrderController(AppConfig.inputView());
        orderController.run();
    }
}

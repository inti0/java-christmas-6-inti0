package christmas;

import christmas.controller.EventPlanner;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        EventPlanner eventPlanner = new EventPlanner(AppConfig.inputView());
        eventPlanner.run();
    }
}

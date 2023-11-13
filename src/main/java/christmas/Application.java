package christmas;

import christmas.controller.EventPlanner;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        EventPlanner eventPlanner = AppConfig.eventPlanner();
        eventPlanner.run();
    }
}

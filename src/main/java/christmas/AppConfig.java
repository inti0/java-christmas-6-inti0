package christmas;

import christmas.view.InputView;

public class AppConfig {

    public static final int THIS_YEAR = 2023;
    public static final int CHRISTMAS_DATE = 25;
    public static final int NO_DISCOUNT = 0;

    public static InputView inputView() {
        return new InputView();
    }
}

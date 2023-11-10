package christmas.controller;

import christmas.benefit.discount.DDayDiscount;
import christmas.benefit.discount.DayOfWeekDiscount;
import christmas.benefit.discount.DiscountPolicy;
import christmas.benefit.discount.DiscountPolicyFactory;
import christmas.benefit.discount.SpecialDayDiscount;
import christmas.foodmenu.Food;
import christmas.order.OrderReceiver;
import christmas.service.OrderResult;
import christmas.view.InputView;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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


    }

    /**
     * <주문 메뉴> Map
     * <할인 전 총주문 금액>* Map
     * <증정 메뉴>* - Map
     * <혜택 내역> - Map LocalDate
     * 크리스마스 디데이 할인: -1,200원
     * 평일 할인: -4,046원
     * 특별 할인: -1,000원
     * 증정 이벤트: -25,000원
     * <총혜택 금액>,
     * <할인 후 예상 결제 금액>
     */
}

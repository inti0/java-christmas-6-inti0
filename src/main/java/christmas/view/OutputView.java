package christmas.view;

import christmas.EventBadge;
import christmas.benefit.Present;
import christmas.model.OrderResult;
import java.util.stream.Collectors;

public class OutputView {
    private static final String NEXT_LINE = "\n";
    private static final int PRESENT_AMOUNT = 1;
    private static final int NO_DISCOUNT = 0;
    private static final String BLANK = "";
    private static final String NOTHING_FORMAT = "없음";
    private final String ORDER_FORMAT = "%s %d개";
    private final String BENEFIT_FORMAT = "-%d원";
    private final String DISCOUNT_FORMAT = "%s: -%,d원";
    private final String PAYMENT_FORMAT = "%,d원";
    private final String PRESENT_BENEFIT_FORMAT = "증정 이벤트: -%d원";
    private final OrderResult result;
    public OutputView(OrderResult result) {
        this.result = result;
    }

    public void print() {
        System.out.println(toStringOrders());
        System.out.println(toStringPaymentBeforeDiscount());
        System.out.println(toStringPresent());
        System.out.println(toStringAllOfBenefitList());
        System.out.println(toStringAllOfBenefitAmount());
        System.out.println(toStringPaymentAfterDiscount());
        System.out.println(toStringEventBadge());
    }

    public String toStringOrders() {
        System.out.println("<주문 메뉴>");
        return result.getOrders().entrySet().stream()
                .map(entry -> ORDER_FORMAT.formatted(entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining(NEXT_LINE));
    }

    public String toStringPaymentBeforeDiscount() {
        System.out.println("<할인 전 총주문 금액>");
        return PAYMENT_FORMAT.formatted(result.beforeDiscountSum());
    }

    public String toStringPresent(){
        System.out.println("<증정 메뉴>");
        Present present = result.givePresent();
        if (present.equals(Present.NOTHING)){
            return NOTHING_FORMAT;
        }
        return ORDER_FORMAT.formatted(present.getItem(), PRESENT_AMOUNT);
    }

    public String toStringAllOfDiscount(){
        if(result.allOfDiscount() == NO_DISCOUNT){
            return PAYMENT_FORMAT.formatted(NO_DISCOUNT);
        }
        return result.getDiscountPolicies().stream()
                .filter(discountPolicy -> discountPolicy.discountAmount() != NO_DISCOUNT)
                .map(discountPolicy ->
                        DISCOUNT_FORMAT.formatted(discountPolicy.discountSource(), discountPolicy.discountAmount()))
                .collect(Collectors.joining(NEXT_LINE));
    }

    public String toStringAllOfBenefitList(){
        System.out.println("<혜택 내역>");
        String allOfDiscount = toStringAllOfDiscount();
        Present present = result.givePresent();

        if(allOfDiscount.equals(BLANK) && present.equals(Present.NOTHING)){
            return NOTHING_FORMAT;
        }
        if(present.equals(Present.NOTHING)){
            return allOfDiscount;
        }
        if(allOfDiscount.equals(BLANK)) {
            return PRESENT_BENEFIT_FORMAT.formatted(present.getPrice());
        }
        return allOfDiscount + NEXT_LINE + PRESENT_BENEFIT_FORMAT.formatted(present.getPrice());
    }

    public String toStringAllOfBenefitAmount(){
        System.out.println("<총혜택 금액>");
        return BENEFIT_FORMAT.formatted(result.allOfBenefitAmount());
    }

    public String toStringPaymentAfterDiscount(){
        System.out.println("<할인 후 예상 결제 금액>");
        return PAYMENT_FORMAT.formatted(result.paymentAfterDiscount());
    }

    public String toStringEventBadge(){
        System.out.println("<12월 이벤트 배지>");
        EventBadge eventBadge = result.giveEventBadge();
        return eventBadge.item;
    }
}



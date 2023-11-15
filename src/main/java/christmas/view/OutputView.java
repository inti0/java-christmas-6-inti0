package christmas.view;

import static christmas.AppConstants.NO_DISCOUNT;

import christmas.AppConstants;
import christmas.domain.EventBadge;
import christmas.domain.benefit.Present;
import christmas.model.OrderResult;
import java.util.stream.Collectors;

public class OutputView {
    private static final int NEGATIVE_SIGN = -1;
    private static final String NEXT_LINE = "\n";
    private static final String NOTHING_FORMAT = "없음";
    private static final String DISCOUNT_FORMAT = "%s: %,d원";
    private static final String NAME_AND_AMOUNT = "%s %d개";
    private static final String PAYMENT_FORMAT = "%,d원";
    private static final String PRESENT_BENEFIT_FORMAT = "증정 이벤트: -%d원";
    private final OrderResult result;

    public OutputView(OrderResult result) {
        this.result = result;
    }

    public void print() {
        System.out.println("<주문 메뉴>");
        System.out.println(toStringOrders());
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(toStringPaymentBeforeDiscount());
        System.out.println("<증정 메뉴>");
        System.out.println(toStringPresent());
        System.out.println("<혜택 내역>");
        System.out.println(toStringAllOfBenefitList());
        System.out.println("<총혜택 금액>");
        System.out.println(toStringAllOfBenefitAmount());
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(toStringPaymentAfterDiscount());
        System.out.println("<12월 이벤트 배지>");
        System.out.println(toStringEventBadge());
    }

    private String toStringOrders() {
        return result.orders().entrySet().stream()
                .map(entry -> NAME_AND_AMOUNT.formatted(entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining(NEXT_LINE));
    }

    private String toStringPaymentBeforeDiscount() {
        return PAYMENT_FORMAT.formatted(result.beforeDiscountSum());
    }

    private String toStringPresent() {
        Present present = result.givePresent();
        if (isNoPresent()) {
            return NOTHING_FORMAT;
        }
        return NAME_AND_AMOUNT.formatted(present.getItem(), present.getAmount());
    }

    private boolean isNoPresent() {
        return result.givePresent().equals(Present.NOTHING);
    }

    private boolean isNoDiscount() {
        return result.allOfDiscount() == NO_DISCOUNT;
    }

    private String toStringAllOfBenefitList() {
        Present present = result.givePresent();
        String allOfDiscount = toStringAllOfDiscount();

        if (isNoDiscount() && isNoPresent()) {
            return NOTHING_FORMAT;
        }
        if (isNoDiscount()) {
            return PRESENT_BENEFIT_FORMAT.formatted(present.getPrice());
        }
        if (isNoPresent()) {
            return allOfDiscount;
        }
        return allOfDiscount + NEXT_LINE + PRESENT_BENEFIT_FORMAT.formatted(present.getPrice());
    }

    private String toStringAllOfDiscount() {
        if (isNoDiscount()) {
            return PAYMENT_FORMAT.formatted(AppConstants.NO_DISCOUNT);
        }
        return result.discountPolicies().stream()
                .filter(discountPolicy -> discountPolicy.discountAmount() != NO_DISCOUNT)
                .map(discountPolicy -> String.format(
                        DISCOUNT_FORMAT,
                        discountPolicy.discountSource(),
                        NEGATIVE_SIGN * discountPolicy.discountAmount()
                ))
                .collect(Collectors.joining(NEXT_LINE));
    }

    private String toStringAllOfBenefitAmount() {
        return PAYMENT_FORMAT.formatted(NEGATIVE_SIGN * result.allOfBenefitAmount());
    }

    private String toStringPaymentAfterDiscount() {
        return PAYMENT_FORMAT.formatted(result.paymentAfterDiscount());
    }

    private String toStringEventBadge() {
        EventBadge eventBadge = result.giveEventBadge();
        return eventBadge.getName();
    }
}



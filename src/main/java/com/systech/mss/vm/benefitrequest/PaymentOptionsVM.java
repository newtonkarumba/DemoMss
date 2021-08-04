package com.systech.mss.vm.benefitrequest;


import com.systech.mss.util.Ignore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentOptionsVM {
    long id;
    String paymentOption;
    double currentCreditFund;
    double amountRequested;

    @Ignore
    double totalAmount;

    @Ignore
    double amountPercentage;

    @Ignore
    String isPercentageOrAmount;


    public double getTotalAmount() {
        if (getPaymentOption().equalsIgnoreCase("totalAmount")) {
//            this.amountPercentage= (getAmountRequested()/getCurrentCreditFund())*100;
            return amountRequested;
        }
        return totalAmount;
    }

    ////
    public double getAmountPercentage() {
        if (getPaymentOption().equalsIgnoreCase("amountPercentage")) {
//            this.totalAmount= (getAmountPercentage()/100)*getCurrentCreditFund();
            return amountRequested;
        }
        return amountPercentage;
    }

    public String getIsPercentageOrAmount() {
        if (getPaymentOption().equalsIgnoreCase("totalAmount"))
            return "Total Amount";
        return "Percentage Amount";
    }

    public static PaymentOptionsVM getInstance(PaymentOptionsVM paymentOptionsVM) {
        PaymentOptionsVM v = new PaymentOptionsVM();
        v.setId(paymentOptionsVM.getId());
        v.setPaymentOption(paymentOptionsVM.getPaymentOption());
        v.setCurrentCreditFund(paymentOptionsVM.getCurrentCreditFund());
        v.setAmountRequested(paymentOptionsVM.getAmountRequested());
        v.setIsPercentageOrAmount(paymentOptionsVM.getIsPercentageOrAmount());

        if (paymentOptionsVM.getCurrentCreditFund() < paymentOptionsVM.getAmountRequested()) {
            v.setAmountPercentage(paymentOptionsVM.getAmountRequested());
            v.setTotalAmount(paymentOptionsVM.getTotalAmount());
        } else {
            if (paymentOptionsVM.getPaymentOption().equalsIgnoreCase("totalAmount")) {
                v.setTotalAmount(paymentOptionsVM.getAmountRequested());
                double x = (paymentOptionsVM.getAmountRequested() / paymentOptionsVM.getCurrentCreditFund()) * 100;
                v.setAmountPercentage(Double.isNaN(x) ? 0 : x);
            } else {
                v.setAmountPercentage(paymentOptionsVM.getAmountRequested());
                v.setTotalAmount((paymentOptionsVM.getAmountRequested() / 100) * paymentOptionsVM.getCurrentCreditFund());
            }
        }
        return v;
    }
}

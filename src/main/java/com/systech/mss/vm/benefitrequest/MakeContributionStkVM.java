package com.systech.mss.vm.benefitrequest;

import com.systech.mss.util.Ignore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MakeContributionStkVM {

    String phone;

    int amount;

    @Ignore
    String paybill;

    long memberId;

    long schemeId;

    @Ignore
    String finalPhone;

    @Ignore
    String account_reference;

    @Ignore
    String requestId;

    @Ignore
    String MerchantRequestID;

    @Ignore
    private String timestamp;
    @Ignore
    private String password;

}

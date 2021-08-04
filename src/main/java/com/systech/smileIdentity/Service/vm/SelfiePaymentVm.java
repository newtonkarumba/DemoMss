package com.systech.smileIdentity.Service.vm;

import com.systech.mss.util.Ignore;
import com.systech.smileIdentity.model.SelfieAction;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SelfiePaymentVm {
    private long userId;
    private SelfieAction action;
    private String phone;
    @Ignore
    private String paybill;
    @Ignore
    private String finalPhone;
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
    @Ignore
    private long amount=1;

}

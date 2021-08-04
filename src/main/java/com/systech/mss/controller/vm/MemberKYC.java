package com.systech.mss.controller.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberKYC {
    public String membershipNo;
    public String lastKYCConsentDate;
    @JsonProperty("KYCConsent")
    public String kYCConsent;
    public String nextKYCConsentDate;
    public String isDueForKYC;
}

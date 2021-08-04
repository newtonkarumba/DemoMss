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
public class SponsorSchemeVM {
    public long id;
    public String schemeName;
    public String sponsorId;
    public String sponsorName;
    public String sponsorProdNo;
    public String sponsorNo;
    public String sponsorProfile;
    public String billingEmail;
    public String claimsEmail;
    public String phoneNo;
    public String status;
    public String ssnitNo;
}

package com.systech.mss.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContributionEntryDTO {
    private long billMemberId;
    private long billId;
    private long billContributionBillId;
    private BigDecimal billExpectedEE;
    private BigDecimal billExpectedER;
    private BigDecimal billExpectedAvcEe;
    private BigDecimal billPenalty;
    private BigDecimal billExpectedSalary;
    private String billMembershipId;
    private String billMemberName;
    private String billSsnitNumber;
    private String billFinalPath;
    private String billDob;
    private String billException;
    private Long billSchemeId;
    private Long billSponsorId;
}

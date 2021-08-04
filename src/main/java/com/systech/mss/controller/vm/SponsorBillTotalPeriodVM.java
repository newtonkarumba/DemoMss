package com.systech.mss.controller.vm;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SponsorBillTotalPeriodVM {
    private BigDecimal amount;
    private String contributionMonthYear;
}

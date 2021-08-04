package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@ToString
@Builder
public class WhatIfAnalysisVM {
    private long schemeId;
    private long memberId;

    @Ignore
    private String avcReceiptOption;

    private int ageAtExit;
    private double returnRate;
    private double salaryEscalationRate;
    private long projectedAvc;
    private long inflationRate;
    private long targetPension;
}

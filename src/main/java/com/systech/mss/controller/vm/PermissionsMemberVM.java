package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PermissionsMemberVM implements Serializable {
    @Ignore
    boolean homeMenuActivated = true;
    @Ignore
    boolean personalInfoMenuActivated = true;
    @Ignore
    boolean contributionsMenuActivated = true;
    @Ignore
    boolean balancesMenuActivated = true;
    @Ignore
    boolean claimsMenuActivated = true;
    @Ignore
    boolean projectionsMenuActivated = true;
    @Ignore
    boolean documentsMenuActivated = true;
    @Ignore
    boolean ticketsMenuActivated = true;
    @Ignore
    boolean manageAccountMenuActivated = true;
    @Ignore
    boolean auditTrailMenuActivated = true;
    @Ignore
    boolean bankMenuActivated = true;
    @Ignore
    boolean allowMakeContributions = true;
    @Ignore
    boolean allowInitiateClaim = true;
    @Ignore
    boolean allowStkPush = true;
    @Ignore
    boolean allowRequestStatement = true;
    @Ignore
    boolean allowBenefitCalculator = true;
    @Ignore
    boolean allowWhatIfAnalysis = true;
    @Ignore
    boolean benefitsFmActivated = true;
}

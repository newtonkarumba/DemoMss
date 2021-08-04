package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionsClaimReviewerVM {
    @Ignore
    boolean homeMenuActivated = true;
    @Ignore
    boolean personalInfoMenuActivated = true;
    @Ignore
    boolean membersMenuActivated = true;
    @Ignore
    boolean claimsMenuActivated = true;
    @Ignore
    boolean stagedContributionsMenuActivated = true;
    @Ignore
    boolean billsMenuActivated = true;
    @Ignore
    boolean receiptsMenuActivated = true;
    @Ignore
    boolean dmsMenuActivated = true;
    @Ignore
    boolean ticketsMenuActivated = true;
    @Ignore
    boolean usersAccountMenuActivated = true;
    @Ignore
    boolean manageAccountMenuActivated = true;
    @Ignore
    boolean auditTrailMenuActivated = true;
    @Ignore
    boolean allowAddSingleUser = true;
    @Ignore
    boolean allowAddBatchUser = true;
    @Ignore
    boolean allowBookBill= true;
    @Ignore
    boolean allowStageContributions= true;
    @Ignore
    boolean allowApproveDocuments = true;
    @Ignore
    boolean tpfaMenuActivated = true;
    @Ignore
    boolean sponsorConfigMenuActivated = true;
    @Ignore
    boolean benefitsMenuActivated = true;
}

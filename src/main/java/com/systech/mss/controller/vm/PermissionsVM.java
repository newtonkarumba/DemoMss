package com.systech.mss.controller.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionsVM {
    String profileName;

    @Ignore
    PermissionsMemberVM memberVM;
    @Ignore
    PermissionsSponsorVM sponsorVM;
    @Ignore
    PermissionsPensionerVM pensionerVM;
    @Ignore
    PermissionsCrmVM crmVM;
    @Ignore
    PermissionsCREVM crevm;
    @Ignore
    PermissionsBillingOfficerVM permissionsBillingOfficerVM;
    @Ignore
    PermissionsClaimReviewerVM permissionsClaimReviewerVM;
    @Ignore
    PermissionsClaimsOfficerVM permissionsClaimsOfficerVM;
    @Ignore
    PermissionsClaimsAuthorizerVM permissionsClaimsAuthorizerVM;
    @Ignore
    PermissionsClaimApproverVM permissionsClaimApproverVM;
    @Ignore
    PermissionsPoVM poVM;
}

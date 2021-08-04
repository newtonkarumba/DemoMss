package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsClaimReviewerVM;
import com.systech.mss.controller.vm.PermissionsClaimsAuthorizerVM;
import com.systech.mss.domain.PermissionsClaimsAuthorizer;
import com.systech.mss.domain.PermissionsClaimsReviewer;

public interface PermissionsClaimsAuthorizerRepository {
    PermissionsClaimsAuthorizer getPermissions();

    PermissionsClaimsAuthorizer setDefault();

        PermissionsClaimsAuthorizer update(PermissionsClaimsAuthorizerVM permissionsClaimsAuthorizerVM);
}

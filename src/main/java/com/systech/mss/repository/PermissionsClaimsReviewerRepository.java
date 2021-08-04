package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsClaimReviewerVM;
import com.systech.mss.domain.PermissionsClaimsReviewer;

public interface PermissionsClaimsReviewerRepository {
    PermissionsClaimsReviewer getPermissions();

    PermissionsClaimsReviewer setDefault();

    PermissionsClaimsReviewer update(PermissionsClaimReviewerVM permissionsClaimReviewerVM);

}

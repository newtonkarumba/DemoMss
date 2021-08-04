package com.systech.mss.repository;


import com.systech.mss.controller.vm.PermissionsClaimApproverVM;
import com.systech.mss.domain.PermissionsClaimsApprover;

public interface PermissionsClaimApproverRepository {
    PermissionsClaimsApprover getPermissions();

    PermissionsClaimsApprover setDefault();

    PermissionsClaimsApprover update(PermissionsClaimApproverVM permissionsClaimApproverVM);
}

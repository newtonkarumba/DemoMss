package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsBillingOfficerVM;
import com.systech.mss.controller.vm.PermissionsClaimsOfficerVM;
import com.systech.mss.domain.PermissionsClaimsOfficer;


public interface PermissionsClaimOfficerRepository {
    PermissionsClaimsOfficer getPermissions();

    PermissionsClaimsOfficer setDefault();

    PermissionsClaimsOfficer update(PermissionsClaimsOfficerVM permissionsClaimsOfficerVM);
}

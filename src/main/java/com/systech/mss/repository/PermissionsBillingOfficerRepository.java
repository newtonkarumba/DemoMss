package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsBillingOfficerVM;
import com.systech.mss.controller.vm.PermissionsCREVM;
import com.systech.mss.domain.PermissionsBillingOfficer;
import com.systech.mss.domain.PermissionsCRE;

public interface PermissionsBillingOfficerRepository  {

    PermissionsBillingOfficer getPermissions();

    PermissionsBillingOfficer setDefault();

    PermissionsBillingOfficer update(PermissionsBillingOfficerVM permissionsBillingOfficerVM);
}

package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsPoVM;
import com.systech.mss.domain.PermissionsPrincipalOfficer;

public interface PermissionsPORepository extends AbstractRepository<PermissionsPrincipalOfficer,Long>{
    PermissionsPrincipalOfficer getPermissions();

    PermissionsPrincipalOfficer setDefault();

    PermissionsPrincipalOfficer update(PermissionsPoVM poVM);
}
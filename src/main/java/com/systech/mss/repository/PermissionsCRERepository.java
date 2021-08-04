package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsCREVM;
import com.systech.mss.domain.PermissionsCRE;

public interface PermissionsCRERepository extends AbstractRepository<PermissionsCRE,Long>{
    PermissionsCRE getPermissions();

    PermissionsCRE setDefault();

    PermissionsCRE update(PermissionsCREVM crmVM);
}

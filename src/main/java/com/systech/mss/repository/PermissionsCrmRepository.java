package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsCrmVM;
import com.systech.mss.domain.PermissionsCRM;

public interface PermissionsCrmRepository  extends AbstractRepository<PermissionsCRM,Long>{
    PermissionsCRM getPermissions();

    PermissionsCRM setDefault();

    PermissionsCRM update(PermissionsCrmVM crmVM);
}
package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsMemberVM;
import com.systech.mss.domain.PermissionsMember;

public interface PermissionsMemberRepository extends AbstractRepository<PermissionsMember,Long>{
    PermissionsMember getPermissions();

    PermissionsMember setDefault();

    PermissionsMember update(PermissionsMemberVM memberVM);
}

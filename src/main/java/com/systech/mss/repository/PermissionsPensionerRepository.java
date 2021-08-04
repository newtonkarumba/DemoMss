package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsPensionerVM;
import com.systech.mss.domain.PermissionsPensioner;

public interface PermissionsPensionerRepository extends AbstractRepository<PermissionsPensioner,Long>{
    PermissionsPensioner getPermissions();

    PermissionsPensioner setDefault();

    PermissionsPensioner update(PermissionsPensionerVM pensionerVM);
}

package com.systech.mss.repository;

import com.systech.mss.controller.vm.PermissionsSponsorVM;
import com.systech.mss.domain.PermissionsSponsor;

public interface PermissionsSponsorRepository  extends AbstractRepository<PermissionsSponsor,Long>{
    PermissionsSponsor getPermissions();

    PermissionsSponsor setDefault();

    PermissionsSponsor update(PermissionsSponsorVM sponsorVM);
}
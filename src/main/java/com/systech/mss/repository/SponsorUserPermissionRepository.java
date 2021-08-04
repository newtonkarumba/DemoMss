package com.systech.mss.repository;


import com.systech.mss.domain.Permission;


import java.util.List;

public interface SponsorUserPermissionRepository extends AbstractRepository<Permission, Long>  {
    List<Permission> getSponsorUsersPermissions();
}

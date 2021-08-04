package com.systech.mss.repository;

import com.systech.mss.domain.Admins;
import com.systech.mss.domain.User;
import com.systech.mss.repository.impl.AdminRepositoryImpl;

import java.util.List;
import java.util.Optional;

public interface AdminRepository  extends AbstractRepository<Admins,Long>{
    Optional<Admins> findOneByStaffNo(String StaffNo);
    List<Admins> getAdmins();
    List<Admins>getAdminById(long id);
}

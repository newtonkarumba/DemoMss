package com.systech.mss.service;


import com.systech.mss.domain.Admins;

import com.systech.mss.repository.AdminRepository;


import javax.inject.Inject;


public class AdminService {

    @Inject
    AdminRepository adminRepository;

    public Admins createAdmin(Admins admins){
        return  adminRepository.create(admins);

    }

}

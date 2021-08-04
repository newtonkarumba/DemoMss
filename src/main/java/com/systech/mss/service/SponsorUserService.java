package com.systech.mss.service;

import com.systech.mss.domain.SponsorUser;
import com.systech.mss.domain.Ticket;
import com.systech.mss.domain.UserSponsor;
import com.systech.mss.repository.SponsorUserRepository;

import javax.inject.Inject;

public class SponsorUserService {

    @Inject
    SponsorUserRepository sponsorUserRepository;

    public boolean checkIfSponsorUserExists(long id){
        return sponsorUserRepository.existsById(id);

    }
    public UserSponsor getSponsorUserById(long id){
        return sponsorUserRepository.find(id);
    }

    public UserSponsor editSponsorUser(UserSponsor userSponsor){
        return sponsorUserRepository.edit(userSponsor);
    }

}

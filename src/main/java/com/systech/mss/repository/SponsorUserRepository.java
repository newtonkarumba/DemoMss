package com.systech.mss.repository;


import com.systech.mss.domain.Permission;
import com.systech.mss.domain.SponsorUser;
import com.systech.mss.domain.UserSponsor;

import java.util.List;
import java.util.Optional;

public interface SponsorUserRepository extends AbstractRepository<UserSponsor, Long> {

    List<UserSponsor> getSponsorUsers();
    List<UserSponsor> getSponsorUserBySponsorId(long sponsorId);
    List<UserSponsor> getSponsorUserByName(String name);

}

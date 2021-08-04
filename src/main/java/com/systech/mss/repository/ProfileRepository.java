package com.systech.mss.repository;

import com.systech.mss.domain.Profile;

public interface ProfileRepository extends  AbstractRepository<Profile,Long> {

    Profile findByName(String name);
}

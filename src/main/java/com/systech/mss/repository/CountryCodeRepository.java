package com.systech.mss.repository;



import com.systech.mss.domain.CountryCode;

public interface CountryCodeRepository extends  AbstractRepository<CountryCode,Long>{
    CountryCode getActiveConfig();
}

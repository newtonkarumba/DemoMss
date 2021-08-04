package com.systech.smileIdentity.repository;


import com.systech.mss.repository.AbstractRepository;
import com.systech.smileIdentity.model.SelfieResults;

public interface SelfieResultsRepository extends AbstractRepository<SelfieResults,Long> {
    public SelfieResults getResultsByJobId(String jobId);
}

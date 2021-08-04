package com.systech.mss.repository;

import com.systech.mss.domain.FAQ;

import java.util.List;

public interface FaqControllerRepository extends AbstractRepository<FAQ,Long>{
    List<FAQ> getAllByProfile(long profileId);

    FAQ addEditFAQ(FAQ faq);
}

package com.systech.mss.repository;

import com.systech.mss.domain.BeneficiaryFormConfig;

public interface BeneficiaryFormConfigsRepository extends AbstractRepository<BeneficiaryFormConfig,Long>{
    BeneficiaryFormConfig getConfigs();

    BeneficiaryFormConfig setDefault();

    BeneficiaryFormConfig update(BeneficiaryFormConfig beneficiaryFormConfig);
}

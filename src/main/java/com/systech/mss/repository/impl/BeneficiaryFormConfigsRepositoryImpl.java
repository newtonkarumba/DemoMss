package com.systech.mss.repository.impl;

import com.systech.mss.domain.BeneficiaryFormConfig;
import com.systech.mss.repository.BeneficiaryFormConfigsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class BeneficiaryFormConfigsRepositoryImpl extends AbstractRepositoryImpl<BeneficiaryFormConfig, Long>  implements BeneficiaryFormConfigsRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BeneficiaryFormConfigsRepositoryImpl() {
        super(BeneficiaryFormConfig.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public BeneficiaryFormConfig getConfigs() {
        try {
            String sql = "FROM BeneficiaryFormConfig p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, BeneficiaryFormConfig.class);
            query.setMaxResults(1);
            return (BeneficiaryFormConfig) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public BeneficiaryFormConfig setDefault() {
        try {
            BeneficiaryFormConfig beneficiaryFormConfig = findAll().get(0);
            if (beneficiaryFormConfig != null) {
                remove(beneficiaryFormConfig);
            }
        }catch(Exception e){
            try {
                return create(new BeneficiaryFormConfig());
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public BeneficiaryFormConfig update(BeneficiaryFormConfig beneficiaryFormConfig) {
        return beneficiaryFormConfig;

    }
}

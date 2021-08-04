package com.systech.mss.repository.impl;

import com.systech.mss.domain.MemberFormConfigs;
import com.systech.mss.repository.MemberFormConfigsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class MemberFormConfigsRepositoryImpl extends AbstractRepositoryImpl<MemberFormConfigs, Long>  implements MemberFormConfigsRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MemberFormConfigsRepositoryImpl() {
        super(MemberFormConfigs.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public MemberFormConfigs getConfigs() {
        try {
            String sql = "FROM MemberFormConfigs p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, MemberFormConfigs.class);
            query.setMaxResults(1);
            return (MemberFormConfigs) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public MemberFormConfigs setDefault() {
        try {
            MemberFormConfigs memberFormConfigs = findAll().get(0);
            if (memberFormConfigs != null) {
                remove(memberFormConfigs);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            return create(new MemberFormConfigs());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public MemberFormConfigs update(MemberFormConfigs memberFormConfigs) {
       return edit(memberFormConfigs);

    }
}

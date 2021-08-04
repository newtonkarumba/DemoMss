package com.systech.mss.repository.impl;

import com.systech.mss.domain.Config;
import com.systech.mss.domain.SponsorConfig;
import com.systech.mss.domain.StatusConfig;
import com.systech.mss.repository.ConfigRepository;
import com.systech.mss.repository.SponsorConfigRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

public class SponsorConfigRepositoryImpl extends AbstractRepositoryImpl<SponsorConfig, Long> implements SponsorConfigRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SponsorConfigRepositoryImpl() {
        super(SponsorConfig.class);
    }


    @Override
    public SponsorConfig getActiveConfig() {
        try {
            String sql = "FROM SponsorConfig sc WHERE sc.statusConfig=:config ORDER BY sc.id DESC";
            Query query = getEntityManager().createQuery(sql, SponsorConfig.class);
            query.setParameter("config", StatusConfig.ACTIVE);
            query.setMaxResults(1);
            return (SponsorConfig) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object getSponsorActiveConfigs() {
        try {
            SponsorConfig sponsorConfig = getActiveConfig();
            if (sponsorConfig != null) {
                sponsorConfig = SponsorConfig.getSimpleConfig(sponsorConfig);
                Map<String, Object> map = new HashMap<>();
                map.put("authorizationLevel", sponsorConfig.getAuthorizationLevel());
                map.put("statusConfig ", sponsorConfig.getStatusConfig());


                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

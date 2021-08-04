package com.systech.mss.repository.impl;

import com.systech.mss.domain.Config;
import com.systech.mss.domain.StatusConfig;
import com.systech.mss.repository.ConfigRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

public class ConfigRepositoryImpl extends AbstractRepositoryImpl<Config, Long> implements ConfigRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfigRepositoryImpl() {
        super(Config.class);
    }

    @Override
    public Config getActiveConfig() {
        try {
            String sql = "FROM Config  c WHERE c.statusConfig=:config ORDER BY  c.id DESC";
            Query query = getEntityManager().createQuery(sql, Config.class);
            query.setParameter("config", StatusConfig.ACTIVE);
            query.setMaxResults(1);
            return (Config) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object getSpecificFieldsOfActiveConfigs() {
        try {
            Config config = getActiveConfig();
            if (config != null) {
                config = Config.getSimpleConfig(config);
                Map<String, Object> map = new HashMap<>();
//                map.put("isContributions",config.isContributions());
                map.put("businessName", config.getBusinessName());
                map.put("businessImage", config.getBusinessImage());
                map.put("currencyName", config.getCurrencyName());
                map.put("currencyShortName", config.getCurrencyShortName());
                map.put("registrationDeclaration", config.getRegistrationDeclaration());
                map.put("client", config.getClient().getName());
                map.put("reportServerUrl", config.getReportServerUrl());
//                map.put("isInitiateExitWithdrawal",config.isInitiateExitWithdrawal());
//                map.put("isStkPush",config.isStkPush());

                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Config> getConfig() {
//        Config config = new Config();
//        config.setCreatedDate(LocalDateTime.now());

        return findAll();
    }

    public List<Config> getConfigById(long id) {
        return findByNamedQuery("findConfigById", singletonMap("Id", id));
    }
}

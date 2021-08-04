package com.systech.mss.repository.impl;

import com.systech.mss.domain.OtpLogs;
import com.systech.mss.repository.OtpLogsRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class OtpLogsRepositoryImpl extends AbstractRepositoryImpl<OtpLogs,Long> implements OtpLogsRepository {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OtpLogsRepositoryImpl() {
        super(OtpLogs.class);
    }

    @Override
    public List<OtpLogs> findByLogin(String username) {
        try {
            Query query= getEntityManager().createNativeQuery("select * FROM otplogs l WHERE l.username=:username",OtpLogs.class);
            query.setParameter("username",username);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OtpLogs findLatestByUsername(String username) {
        try {
            Query query= getEntityManager().createNativeQuery(
                    "select * FROM otplogs l WHERE l.username=:username ORDER BY l.id DESC",
                    OtpLogs.class);
            query.setParameter("username",username);
            query.setMaxResults(1);
            return (OtpLogs) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OtpLogs findUnExpiredOtp(String login) {
        String sql="select * from otplogs l WHERE l.expiry>now() and l.username=:username ORDER BY l.id DESC";
        try {
            Query query= getEntityManager().createNativeQuery(
                    sql,
                    OtpLogs.class);
            query.setParameter("username",login);
            query.setMaxResults(1);
            return (OtpLogs) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OtpLogs findExpiredToken(int days, String username) {
        String sql="select * from otplogs l WHERE (DATEDIFF(now(),l.expiry)>=:days) AND l.username=:username  ORDER BY l.id DESC";
        try {
            Query query= getEntityManager().createNativeQuery(
                    sql,
                    OtpLogs.class);
            query.setParameter("days",days);
            query.setParameter("username",username);
            query.setMaxResults(1);
            return (OtpLogs) query.getSingleResult();
        } catch (Exception e) {
            log.info("No expired token found in {} days",days);
        }
        return null;
    }

    @Override
    public OtpLogs checkIfOtpValid(String username) {
        String sql="select * from otplogs l WHERE (l.nextCheck>NOW()) AND l.username=:username  ORDER BY l.id DESC";
        try {
            Query query= getEntityManager().createNativeQuery(
                    sql,
                    OtpLogs.class);
            query.setParameter("username",username);
            query.setMaxResults(1);
            return (OtpLogs) query.getSingleResult();
        } catch (Exception e) {
            log.info("No token found");
        }
        return null;
    }
}

package com.systech.mss.repository.impl;

import com.systech.mss.domain.Documents;
import com.systech.mss.domain.User;
import com.systech.mss.repository.DocumentRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class DocumentRepositoryImpl extends AbstractRepositoryImpl<Documents, Long> implements DocumentRepository {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    public DocumentRepositoryImpl() {
        super(Documents.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Documents> getUserUploadedDocs(User user) {
        String sql="SELECT  * FROM Documents  d WHERE d.fromUserId=:f  order by d.id DESC";
        try{
            Query query=getEntityManager().createNativeQuery(sql,Documents.class);
            query.setParameter("f",user.getId());
            query.setMaxResults(200);
            return query.getResultList();
        }catch (Exception e){}
        return new ArrayList<>();
    }

    @Override
    public List<Documents> getAllForUserOnly(User user) {
        String sql="SELECT  * FROM Documents d WHERE (d.fromUserId!=:to) AND ((d.toUserId=:to) OR (d.forProfileId=:profile) OR (d.forProfileId=:profile)) order by d.id DESC";
        try{
            Query query=getEntityManager().createNativeQuery(sql,Documents.class);
            query.setParameter("to",user.getId());
            query.setParameter("profile", user.getProfile().getId());
            query.setMaxResults(200);
            return query.getResultList();
        }catch (Exception e){}
        return new ArrayList<>();
    }

    @Override
    public List<Documents> getForPublicOnly() {
        String sql="SELECT  * FROM Documents  d WHERE (d.toUserId=-1) AND (d.forProfileId=-1) order by d.id DESC";
        try{
            Query query=getEntityManager().createNativeQuery(sql,Documents.class);
            query.setMaxResults(200);
            return query.getResultList();
        }catch (Exception e){}
        return new ArrayList<>();
    }
}

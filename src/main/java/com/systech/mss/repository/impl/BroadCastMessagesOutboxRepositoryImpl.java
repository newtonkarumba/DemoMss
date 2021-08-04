package com.systech.mss.repository.impl;

import com.systech.mss.domain.BroadCastMessagesOutbox;
import com.systech.mss.domain.User;
import com.systech.mss.repository.BroadCastMessagesOutboxRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class BroadCastMessagesOutboxRepositoryImpl
        extends AbstractRepositoryImpl<BroadCastMessagesOutbox, Long> implements BroadCastMessagesOutboxRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BroadCastMessagesOutboxRepositoryImpl() {
        super(BroadCastMessagesOutbox.class);
    }


    @Override
    public List<BroadCastMessagesOutbox> findByUser(User user) {
        try {
            Query query
                    = getEntityManager().createQuery(
                    "FROM BroadCastMessagesOutbox  b WHERE (b.messages.fromId=:id) ORDER BY b.id DESC",
                    BroadCastMessagesOutbox.class
            ).setParameter("id", user.getId());
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

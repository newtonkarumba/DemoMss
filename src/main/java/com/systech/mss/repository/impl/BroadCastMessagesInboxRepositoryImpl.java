package com.systech.mss.repository.impl;

import com.systech.mss.domain.BroadCastMessagesInbox;
import com.systech.mss.domain.User;
import com.systech.mss.repository.BroadCastMessagesInboxRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class BroadCastMessagesInboxRepositoryImpl
        extends AbstractRepositoryImpl<BroadCastMessagesInbox, Long> implements BroadCastMessagesInboxRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BroadCastMessagesInboxRepositoryImpl() {
        super(BroadCastMessagesInbox.class);
    }

    @Override
    public List<BroadCastMessagesInbox> findByUser(User user) {
        try {
            Query query
                    = getEntityManager().createQuery(
                    "FROM BroadCastMessagesInbox  b WHERE (b.messages.toId=:id) ORDER BY b.id DESC",
                    BroadCastMessagesInbox.class
            ).setParameter("id", user.getId());
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BroadCastMessagesInbox> findUnreadByUser(User user) {
        try {
            Query query
                    = getEntityManager().createQuery(
                    "FROM BroadCastMessagesInbox  b WHERE (b.messages.toId=:id) AND (b.messages.isRead=:isRead) ORDER BY b.id DESC",
                    BroadCastMessagesInbox.class
            ).setParameter("id", user.getId())
                    .setParameter("isRead", false);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

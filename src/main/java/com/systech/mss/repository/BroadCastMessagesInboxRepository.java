package com.systech.mss.repository;

import com.systech.mss.domain.BroadCastMessagesInbox;
import com.systech.mss.domain.User;

import java.util.List;

public interface BroadCastMessagesInboxRepository extends AbstractRepository<BroadCastMessagesInbox,Long>{
    List<BroadCastMessagesInbox> findByUser(User user);

    List<BroadCastMessagesInbox> findUnreadByUser(User user);
}

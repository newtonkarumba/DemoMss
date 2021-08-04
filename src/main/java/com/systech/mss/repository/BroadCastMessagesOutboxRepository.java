package com.systech.mss.repository;

import com.systech.mss.domain.BroadCastMessagesOutbox;
import com.systech.mss.domain.User;

import java.util.List;

public interface BroadCastMessagesOutboxRepository extends AbstractRepository<BroadCastMessagesOutbox,Long>{
    List<BroadCastMessagesOutbox> findByUser(User user);
}

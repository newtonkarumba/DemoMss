package com.systech.mss.repository;

import com.systech.mss.domain.Profile;
import com.systech.mss.domain.Ticket;
import com.systech.mss.domain.User;

import java.util.List;

public interface TicketRepository extends  AbstractRepository<Ticket,Long> {


    List<Ticket> filter(String dateFrom, String dateTo);

    List<Ticket> filter(Profile profile, String dateFrom, String dateTo);
}

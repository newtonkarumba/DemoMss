package com.systech.mss.repository.impl;

import com.systech.mss.domain.Profile;
import com.systech.mss.domain.Ticket;
import com.systech.mss.repository.TicketRepository;
import com.systech.mss.seurity.DateUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TicketRepositoryImpl extends AbstractRepositoryImpl<Ticket,Long> implements TicketRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketRepositoryImpl() {
        super(Ticket.class);
    }

    @Override
    public List<Ticket> filter(String dateFrom, String dateTo) {
        //2021-03-02
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateFrom = LocalDate.parse(dateFrom, dateTimeFormatter);
        LocalDate localDateTo = LocalDate.parse(dateTo, dateTimeFormatter);

        List<Ticket> tickets = em.createQuery("select ac from Ticket ac where ac.createdDate>=:dateFrom and ac.createdDate<=:dateTo  order by ac.id desc", Ticket.class)
                .setParameter("dateFrom", localDateFrom.atStartOfDay())
                .setParameter("dateTo", localDateTo.atTime(LocalTime.MAX))
                .getResultList();
        if (tickets != null) {
            for (Ticket ticket :
                    tickets) {
                ticket.setShortDate(DateUtils.shortDate(ticket.getCreatedDate()));
                ticket.setShortDateTime(DateUtils.shortDateTime(ticket.getCreatedDate()));
            }
            return tickets;
        }


        return null;
    }

    @Override
    public List<Ticket> filter(Profile profile, String dateFrom, String dateTo) {
        //2021-03-02
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateFrom = LocalDate.parse(dateFrom, dateTimeFormatter);
        LocalDate localDateTo = LocalDate.parse(dateTo, dateTimeFormatter);

        List<Ticket> tickets = em.createQuery("select ac from Ticket ac where ac.profileId=:profile and ac.createdDate>=:dateFrom and ac.createdDate<=:dateTo  order by ac.id desc", Ticket.class)
                .setParameter("profile", profile)
                .setParameter("dateFrom", localDateFrom.atStartOfDay())
                .setParameter("dateTo", localDateTo.atTime(LocalTime.MAX))
                .getResultList();
        if (tickets != null) {
            for (Ticket ticket :
                    tickets) {
                ticket.setShortDate(DateUtils.shortDate(ticket.getCreatedDate()));
                ticket.setShortDateTime(DateUtils.shortDateTime(ticket.getCreatedDate()));
            }
            return tickets;
        }


        return null;
    }
}

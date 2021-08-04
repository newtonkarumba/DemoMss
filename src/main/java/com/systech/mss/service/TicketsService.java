package com.systech.mss.service;

import com.systech.mss.config.Constants;
import com.systech.mss.controller.vm.ForwardTicketVm;
import com.systech.mss.domain.Ticket;
import com.systech.mss.domain.TicketIssues;
import com.systech.mss.domain.User;
import com.systech.mss.repository.TicketIssuesRepository;
import com.systech.mss.repository.TicketRepository;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;


public class TicketsService {
    @Inject
    private Logger logger;

    @Inject
    private TicketRepository ticketRepository;

    @Inject
    private TicketIssuesRepository ticketIssuesRepository;

    @Inject
    private UserService userService;

    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;


    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public List<Ticket> getTickets(){
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsRange(int start, int size){
        return ticketRepository.findRange(start,size);
    }

    public Ticket getTicketById(long id){
        return ticketRepository.find(id);
    }

    public boolean checkIfTicketExists(long id){
        return ticketRepository.existsById(id);

    }

    public List<Ticket> getTicketByCreatedByUserId(long userId){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> from = query.from(Ticket.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("createdBy").get("id"),
                                userId
                        )
                );
        return getEntityManager()
                .createQuery(query)
                .getResultList();
    }

    public List<Ticket> getTicketByRecipientUserId(long userId){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> from = query.from(Ticket.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("recepient").get("id"),
                                userId
                        )
                );
        return getEntityManager().createQuery(query).getResultList();
    }

    public Ticket createTicket(Ticket ticket){
        long issueId;
        try {
            issueId = ticket.getTicketIssueId().getId();
        }catch (NullPointerException exception){
            return null;
        }
        //get issue object
        TicketIssues ticketIssues = ticketIssuesRepository.find(issueId);
        ticket.setProfileById(ticketIssues.getProfileId().getId());
        //get user object
        User user =userService.getUserById(ticket.getCreatedBy().getId());
        //set sponsor id and scheme id
        // ticket.setSponsorId(user.getSponsorId());
         ticket.setSchemeId(user.getUserDetails().getSchemeId());

        return ticketRepository.create(ticket);
    }

    public Ticket editTicket(Ticket ticket){
        return ticketRepository.edit(ticket);
    }

    public Ticket closeTicket(long id){
        Ticket ticket=ticketRepository.find(id);
        if(ticket != null) {
            ticket.setClosed(true);
            return ticketRepository.edit(ticket);
        }
        return null;
    }

    public Ticket openTicket(long id){
        Ticket ticket=ticketRepository.find(id);
        if(ticket != null) {
            ticket.setClosed(false);
            return ticketRepository.edit(ticket);
        }
        return null;
    }


    public List<Ticket> setTicketExtraDetails(List<Ticket> tickets){
        for(Ticket t: tickets){
            //set owner name
            try {
                t.setOwnerName(userService.getUsersFullNameById(t.getCreatedBy().getId()));
            }
            catch (NullPointerException exception){
                t.setOwnerName("");
            }
            //set Receiver name
//            t.setReceiverName(userService.getUserNameById(t.getRecepient().getId()));
            //set status
            if (t.isClosed()) {
                t.setStatus("CLOSED");
            } else {
                t.setStatus("OPEN");
            }
            //set short date format
            t.setShortDate(t.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            //set short date time format
            t.setShortDateTime(t.getCreatedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        }
        return tickets;
    }

    public Ticket setTicketExtraDetails(Ticket ticket){
        //set owner name
        try {
            ticket.setOwnerName(userService.getUsersFullNameById(ticket.getCreatedBy().getId()));
        }
        catch (NullPointerException exception){
            ticket.setOwnerName("");
        }
        //set Receiver name
//        ticket.setReceiverName(userService.getUserNameById(ticket.getRecepient().getId()));
        //set status
        if (ticket.isClosed()) {
            ticket.setStatus("CLOSED");
        } else {
            ticket.setStatus("OPEN");
        }
        //set short date format
        ticket.setShortDate(ticket.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        //set short date time format
        ticket.setShortDateTime(ticket.getCreatedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        return ticket;
    }

    public List<Ticket> getTicketByRecipientProfile(long profileId,long sponsorId,long schemeId){

        if(sponsorId != 0 && schemeId == 0){
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
            Root<Ticket> from = query.from(Ticket.class);
            Predicate sponsorIdPredicate=criteriaBuilder.equal(
                    from.get("sponsorId"),
                    sponsorId
            );
            Predicate profileIdPredicate=criteriaBuilder.equal(
                    from.get("profileId").get("id"),
                    profileId
            );
            Predicate predicateForUserIdAndIp= criteriaBuilder.and(sponsorIdPredicate,profileIdPredicate);
            query.where(predicateForUserIdAndIp);
            return getEntityManager()
                    .createQuery(query)
                    .getResultList();
        }
        else if(sponsorId == 0 && schemeId != 0){
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
            Root<Ticket> from = query.from(Ticket.class);
            Predicate schemeIdPredicate=criteriaBuilder.equal(
                    from.get("schemeId"),
                    schemeId
            );
            Predicate profileIdPredicate=criteriaBuilder.equal(
                    from.get("profileId").get("id"),
                    profileId
            );
            Predicate predicateForUserIdAndIp= criteriaBuilder.and(schemeIdPredicate,profileIdPredicate);
            query.where(predicateForUserIdAndIp);
            return getEntityManager()
                    .createQuery(query)
                    .getResultList();
        }
        else if(sponsorId != 0 && schemeId != 0){
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
            Root<Ticket> from = query.from(Ticket.class);
            Predicate sponsorIdPredicate=criteriaBuilder.equal(
                    from.get("sponsorId"),
                    sponsorId
            );
            Predicate schemeIdPredicate=criteriaBuilder.equal(
                    from.get("schemeId"),
                    schemeId
            );
            Predicate profileIdPredicate=criteriaBuilder.equal(
                    from.get("profileId").get("id"),
                    profileId
            );
            Predicate predicateForUserIdAndIp= criteriaBuilder.and(sponsorIdPredicate,schemeIdPredicate,profileIdPredicate);
            query.where(predicateForUserIdAndIp);
            return getEntityManager()
                    .createQuery(query)
                    .getResultList();

        }else {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
            Root<Ticket> from = query.from(Ticket.class);
            query.select(from)
                    .where(
                            criteriaBuilder.equal(
                                    from.get("profileId").get("id"),
                                    profileId
                            )
                    );
            return getEntityManager()
                    .createQuery(query)
                    .getResultList();
        }
    }

    public List<Ticket> getRecentTickets(long profileId,int start, int size){
        if(size==0){
            //set default size
            size=10;
        }
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> from = query.from(Ticket.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("profileId").get("id"),
                                profileId
                        )
                );
        return getEntityManager()
                .createQuery(query)
                .setFirstResult(start)
                .setMaxResults(size)
                .getResultList();
    }

    public long getCountOfOpenSupportTicket(long profileId){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Ticket> from = query.from(Ticket.class);
        query.select(criteriaBuilder.count(from));
        Predicate profileIdPredicate=criteriaBuilder.equal(
                from.get("profileId").get("id"),
                profileId
        );
        Predicate statusPredicate=criteriaBuilder.equal(
                from.get("closed"),
                false
        );
        Predicate predicateForProfileIdAndStatus = criteriaBuilder.and(profileIdPredicate,statusPredicate);
        query.where(predicateForProfileIdAndStatus);
        return getEntityManager()
                .createQuery(query)
                .getSingleResult();
    }

    public Ticket forwardTicket(ForwardTicketVm forwardTicketVm){
        Ticket ticket=getTicketById(forwardTicketVm.getTicketId());
        if (ticket != null) {
            ticket.setProfileById(forwardTicketVm.getProfileId());
            ticket.setForwardedBy(forwardTicketVm.getUserId());
            return ticketRepository.edit(ticket);
        }
        return null;
    }

}

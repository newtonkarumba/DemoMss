package com.systech.mss.service;

import com.systech.mss.config.Constants;
import com.systech.mss.domain.Ticket;
import com.systech.mss.domain.TicketCategory;
import com.systech.mss.repository.TicketCategoryRepository;
import com.systech.mss.repository.TicketRepository;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class TicketCategoryService {
    @Inject
    private Logger logger;

    @Inject
    private TicketCategoryRepository ticketCategoryRepository;

    @Inject
    private TicketRepository ticketRepository;

    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;


    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public List<TicketCategory> getTicketCategories(){
        return ticketCategoryRepository
                .findAll();
    }

    public List<TicketCategory> getTicketCategoriesRange(int start, int size){
        return ticketCategoryRepository.findRange(start,size);
    }

    public List<Ticket> getTicketsInTicketCategory(long id){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> from = query.from(Ticket.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("ticketCategory").get("id"),
                                id
                        )
                );
        return getEntityManager().createQuery(query).getResultList();
    }
    public List<Ticket> getTicketsInTicketCategory(long id,int start, int size){
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
                                from.get("ticketCategory").get("id"),
                                id
                        )
                );
        return getEntityManager()
                .createQuery(query)
                .setFirstResult(start)
                .setMaxResults(size)
                .getResultList();
    }

    public TicketCategory getTicketCategoryById(long id){
        return ticketCategoryRepository.find(id);
    }

    public boolean checkIfTicketCategoryExists(long id){
        return ticketCategoryRepository.existsById(id);

    }

    public TicketCategory createTicketCategory(TicketCategory ticketCategory){
        return ticketCategoryRepository.create(ticketCategory);
    }

    public TicketCategory editTicketCategory(TicketCategory ticketCategory){
        return ticketCategoryRepository.edit(ticketCategory);
    }

    public TicketCategory deleteTicketCategory(long id){
        TicketCategory ticketCategory=ticketCategoryRepository.find(id);
        if(ticketCategory != null){
            ticketCategoryRepository.deleteById(id);
            return ticketCategory;
        }
        return null;
    }




}

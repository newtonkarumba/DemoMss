package com.systech.mss.service;

import com.systech.mss.config.Constants;
import com.systech.mss.domain.TicketMessage;
import com.systech.mss.repository.TicketMessageRepository;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Null;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;


public class TicketMessageService {
    @Inject
    private Logger logger;

    @Inject
    private TicketMessageRepository ticketMessageRepository;

    @Inject
    private UserService userService;

    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;


    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public List<TicketMessage> getTicketMessage(long id){
        try {

            return ticketMessageRepository
                    .findAll()
                    .stream()
                    .filter(ticketMessage -> ticketMessage.getTicket().getId()==id)
                    .collect(Collectors.toList());
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public TicketMessage getTicketMessageById(long id){
        return ticketMessageRepository.find(id);
    }

    public List<TicketMessage> getTicketMessageByUserId(long id, long userId){
        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<TicketMessage> query = criteriaBuilder.createQuery(TicketMessage.class);
            Root<TicketMessage> from = query.from(TicketMessage.class);
            query.select(from)
                    .where(
                            criteriaBuilder.equal(
                                    from.get("createdBy").get("id"),
                                    userId
                            )
                    );
            return getEntityManager()
                    .createQuery(query)
                    .getResultList()
                    .stream()
                    .filter(ticketMessage -> ticketMessage.getTicket().getId() == id)
                    .collect(Collectors.toList());
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public boolean checkIfTicketMessageExists(long id){
        return ticketMessageRepository.existsById(id);

    }

    public TicketMessage createTicketMessage(TicketMessage ticketMessage){
        return ticketMessageRepository.create(ticketMessage);
    }

    public TicketMessage editTicketMessage(TicketMessage ticketMessage){
        return ticketMessageRepository.edit(ticketMessage);
    }

    public TicketMessage deleteTicketMessage(long id){
        TicketMessage ticketMessage=ticketMessageRepository.find(id);
        if(ticketMessage != null){
            ticketMessageRepository.deleteById(id);
            return ticketMessage;
        }
        return null;
    }


    public List<TicketMessage> setTicketMessageExtraDetails(List<TicketMessage> ticketMessages){
        for(TicketMessage t: ticketMessages){
            //set owner name
            try {
                t.setOwnerName(userService.getUsersFullNameById(t.getCreatedBy().getId()));
            }
            catch (NullPointerException e){
                t.setOwnerName("");
            }
            //set short date format
            t.setShortDate(t.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            //set short date time format
            t.setShortDateTime(t.getCreatedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
            //set ticket profile name
            try {
                t.setProfileName(userService
                        .getUserById(t
                                .getCreatedBy()
                                .getId())
                        .getProfile()
                        .getName()
                );
            }
            catch (NullPointerException e){
                t.setProfileName("");
            }



        }
        return ticketMessages;
    }

    public TicketMessage setTicketMessageExtraDetails(TicketMessage ticketMessage){
        //set owner name
        try {
            ticketMessage.setOwnerName(userService.getUsersFullNameById(ticketMessage.getCreatedBy().getId()));
        }
        catch (NullPointerException e){
            ticketMessage.setOwnerName("");
        }
        //set short date format
        ticketMessage.setShortDate(ticketMessage.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        //set short date time format
        ticketMessage.setShortDateTime(ticketMessage.getCreatedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        //set ticket profile name
        try {
            ticketMessage.setProfileName(userService
                    .getUserById(ticketMessage
                            .getCreatedBy()
                            .getId())
                    .getProfile()
                    .getName()
            );
        }catch (NullPointerException e){
            ticketMessage.setProfileName("");
        }

        return ticketMessage;
    }
}

package com.systech.mss.service;

import com.systech.mss.config.Constants;
import com.systech.mss.domain.Session;
import com.systech.mss.domain.Ticket;
import com.systech.mss.repository.SessionRepository;
import com.systech.mss.vm.SessionVM;
import com.systech.mss.vm.WeekdaysEnum;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SessionService {
    @Inject
    private SessionRepository sessionRepository;
    @Inject
    private Logger logger;


    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;


    protected EntityManager getEntityManager() {
        return entityManager;
    }


    public List<Session> getSessionByUserId(long id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Session> query = criteriaBuilder.createQuery(Session.class);
        Root<Session> from = query.from(Session.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("userId"),
                                id
                        )
                );
        return getEntityManager()
                .createQuery(query)
                .getResultList();
    }

    public void logSession(long userId) {
        sessionRepository.create((Session) sessionRepository.getByUserId(userId));
    }

    public Session saveSession(Session session) {
     return  sessionRepository.create(session);

    }

    public long getActiveSessionCount(){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Session> query = criteriaBuilder.createQuery(Session.class);
        Root<Session> from = query.from(Session.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("active"),
                                true
                        )
                );
        return getEntityManager()
                .createQuery(query)
                .getResultList()
                .size();
    }
    public long getAllSessions(){
        List<Session> sessions = sessionRepository.findAll();
        long count = 0;
        String todaysDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        logger.info(todaysDate);
        for (Session session: sessions){
            String sessionDate = session.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            logger.info(sessionDate);
            if (todaysDate.equals(sessionDate)){
                count+=1;
                logger.info("match");
            }

        }
        return count;
    }
    public List<SessionVM> getWeeklySessions(){

        List<Session> sessions = sessionRepository.findAll();
        LocalDate todayDate = LocalDate.now();
        LocalDate monday,tuesday,wednesday,thursday,friday;
        String today = String.valueOf(LocalDate.now().getDayOfWeek());
        switch (today){
            case "MONDAY":
                monday=todayDate;
                tuesday=todayDate.minusDays(6);
                wednesday=todayDate.minusDays(5);
                thursday=todayDate.minusDays(4);
                friday=todayDate.minusDays(3);
                return countSessions(sessions,monday,tuesday,wednesday,thursday,friday);
            case "TUESDAY":
                monday=todayDate.minusDays(1);
                tuesday=todayDate;
                wednesday=todayDate.minusDays(6);
                thursday=todayDate.minusDays(5);
                friday=todayDate.minusDays(4);
                return countSessions(sessions,monday,tuesday,wednesday,thursday,friday);
            case "WEDNESDAY":
                monday=todayDate.minusDays(2);
                tuesday=todayDate.minusDays(1);
                wednesday=todayDate;
                thursday=todayDate.minusDays(6);
                friday=todayDate.minusDays(5);
                return countSessions(sessions,monday,tuesday,wednesday,thursday,friday);
            case "THURSDAY":
                monday=todayDate.minusDays(3);
                tuesday=todayDate.minusDays(2);
                wednesday=todayDate.minusDays(1);
                thursday=todayDate;
                friday=todayDate.minusDays(6);
                return countSessions(sessions,monday,tuesday,wednesday,thursday,friday);
            case "FRIDAY":
                monday=todayDate.minusDays(4);
                tuesday=todayDate.minusDays(3);
                wednesday=todayDate.minusDays(2);
                thursday=todayDate.minusDays(1);
                friday=todayDate;
                return countSessions(sessions,monday,tuesday,wednesday,thursday,friday);

        }
        return null;
    }

    public List<SessionVM> countSessions(List<Session> sessions,LocalDate ... days){

        int monCount=0,teuCount=0,wedCount=0,thurCount=0,friCount=0;

            for(Session session:sessions){
                LocalDate createdDate=session.getCreatedDate().toLocalDate();
                //mon count increase
                if(createdDate.equals(days[0])){
                    monCount = monCount+1;
                    logger.info("Monday Count");
                }
                //teu count increase
                if(createdDate.equals(days[1])){
                    teuCount = teuCount+1;
                    logger.info("Tuesday Count");
                }
                //wed count increase
                if(createdDate.equals(days[2])){
                   wedCount = wedCount+1;
                    logger.info("Wed Count");
                }
                //thur count increase
                if(createdDate.equals(days[3])){
                     thurCount= thurCount+1;
                    logger.info("Thu Count");
                }
                //fri count increase
                if(createdDate.equals(days[4])){
                    friCount = friCount+1;
                    logger.info("Fri Count");
                }
            }

        List<SessionVM> sessionVMS=new ArrayList<>();
        sessionVMS.add(new SessionVM(WeekdaysEnum.MONDAY,monCount));
        sessionVMS.add(new SessionVM(WeekdaysEnum.TUESDAY,teuCount));
        sessionVMS.add(new SessionVM(WeekdaysEnum.WEDNESDAY,wedCount));
        sessionVMS.add(new SessionVM(WeekdaysEnum.THURSDAY,thurCount));
        sessionVMS.add(new SessionVM(WeekdaysEnum.FRIDAY,friCount));
        if(sessionVMS.isEmpty()) {
            return null;
        }
        return sessionVMS;
    }

}

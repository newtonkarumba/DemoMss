package com.systech.mss.service;

import com.systech.mss.config.Constants;
import com.systech.mss.domain.ActivityTrail;
import com.systech.mss.domain.User;
import com.systech.mss.repository.ActivityTrailRepository;
import com.systech.mss.repository.UserRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;


public class ActivityTrailService {
    @Inject
    private ActivityTrailRepository activityTrailRepository;

    @Inject
    private UserRepository userRepository;


    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;


    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public void logActivityTrail(long userId, String msg) {
        activityTrailRepository.create(activityTrailRepository.getActivityTrail(userId, msg));
    }

    public List<ActivityTrail> getActivityTrailById(long id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ActivityTrail> query = criteriaBuilder.createQuery(ActivityTrail.class);
        Root<ActivityTrail> from = query.from(ActivityTrail.class);
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

    public List<ActivityTrail> getAll() {
//        List<ActivityTrail> activityTrails=activityTrailRepository.findRange(0,10);
        List<ActivityTrail> activityTrails = activityTrailRepository.getAllActivityLogs();
        for (ActivityTrail activityTrail : activityTrails) {
            User user = userRepository.find(activityTrail.getUserId());
            activityTrail.setShortDate(activityTrail.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            activityTrail.setShortDateTime(activityTrail.getCreatedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
            if (user != null) {
                activityTrail.setProfile(user.getProfile().getName());
                activityTrail.setUserName(user.getFirstName() + " " + user.getLastName());
            }else {
                activityTrail.setProfile("N/A");
                activityTrail.setUserName("Unknown");
            }
            activityTrail.setCreatedDate(null);
        }
        return activityTrails;
    }
}

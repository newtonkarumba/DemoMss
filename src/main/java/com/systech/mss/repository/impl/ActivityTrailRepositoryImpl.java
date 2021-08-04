package com.systech.mss.repository.impl;

import com.systech.mss.domain.ActivityTrail;
import com.systech.mss.domain.User;
import com.systech.mss.repository.ActivityTrailRepository;
import com.systech.mss.repository.UserRepository;
import com.systech.mss.service.UserService;
import com.systech.mss.seurity.DateUtils;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonMap;

public class ActivityTrailRepositoryImpl extends AbstractRepositoryImpl<ActivityTrail, Long> implements ActivityTrailRepository {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActivityTrailRepositoryImpl() {
        super(ActivityTrail.class);
    }

    @Override
    public ActivityTrail getActivityTrail(long userId, String msg) {
        ActivityTrail activityTrail = new ActivityTrail();
        activityTrail.setUserId(userId);
        activityTrail.setDescription(msg);
        activityTrail.setCreatedDate(LocalDateTime.now());
        return activityTrail;
    }

//    public ActivityTrail getActivityTrail( String msg) {
//        ActivityTrail activityTrail = new ActivityTrail();
////        activityTrail.setUserId(userId);
//        activityTrail.setUserName(User.class.getName());
//        activityTrail.setDescription(msg);
//        activityTrail.setCreatedDate(LocalDateTime.now());
//        return activityTrail;
//    }

    @Override
    public List<ActivityTrail> getByUserId(User user) {
        try {
            String sql = "FROM ActivityTrail at WHERE at.userId=:userId ORDER  BY at.id DESC";
            Query query = em.createQuery(sql, ActivityTrail.class);
            query.setParameter("userId", user.getId());
            query.setMaxResults(10);
            List<ActivityTrail> activityTrails = query.getResultList();
            if (activityTrails != null) {
                for (ActivityTrail activityTrail :
                        activityTrails) {
                    activityTrail.setShortDate(DateUtils.shortDate(activityTrail.getCreatedDate()));
                    activityTrail.setShortDateTime(DateUtils.shortDateTime(activityTrail.getCreatedDate()));
                    activityTrail.setUserName(user.getFirstName() + " " + user.getLastName());
                    activityTrail.setProfile(user.getProfile().getName());
                }
                return activityTrails;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }


    public List<ActivityTrail> getByUserId_(long userId) {
        String sql = "select (select concat(concat(coalesce(u.first_name, ''),' '),coalesce(u.last_name, '')) from users u where u.id =:userId) userName, at.* from activitytrail at where at.userId=:userId ORDER  BY at.id DESC";
        Query query = em.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.setMaxResults(100);
        List<Object[]> objects = query.getResultList();
        List<ActivityTrail> activityTrails = new ArrayList<>();
        for (Object[] at : objects) {
            Date date;
            ActivityTrail activityTrail = new ActivityTrail();
            try {
//                2021-03-08 22:25:03.000000
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = simpleDateFormat.parse(String.valueOf(at[2]));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                LocalDateTime localDateTime = LocalDateTime
                        .of(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                calendar.get(Calendar.SECOND)
                        );
                activityTrail.setShortDate(String.format("%s %s, %s", localDateTime.getMonth().name(), localDateTime.getDayOfMonth(), localDateTime.getYear()));
                activityTrail.setShortDateTime(String.format("%s %s, %s %s:%s:%s",
                        localDateTime.getMonth().name(),
                        localDateTime.getDayOfMonth(),
                        localDateTime.getYear(),
                        localDateTime.getHour(),
                        localDateTime.getMinute(),
                        localDateTime.getSecond()
                ));
                activityTrail.setCreatedDate(localDateTime);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
            activityTrail.setId(Long.parseLong(String.valueOf(at[1])));
            activityTrail.setUserId(Long.parseLong(String.valueOf(at[4])));
            activityTrail.setUserName(String.valueOf(at[0]));
            activityTrail.setDescription(String.valueOf(at[3]));

            activityTrails.add(activityTrail);
        }
        return activityTrails;
//        return findByNamedQuery("findByUserId", singletonMap("userId", userId));
    }


    @Override
    public List<ActivityTrail> getActivityOfDate(String date) {
        return findByNamedQuery("findActivityOfDate", singletonMap("dateFilter", date));
    }

    @Override
    public List<ActivityTrail> searchActivityTrail(long mssuserid, String name) {
        try {
            if (name != null) {

                name = name.toUpperCase();

                String sql = "From ActivityTrail ac where UPPER(ac.description) like :name and ac.userId=:mssuserid ORDER BY ac.id DESC ";
                Query query = em.createQuery(sql, ActivityTrail.class);
                query.setParameter("name", "%" + name + "%");
                query.setParameter("mssuserid", mssuserid);
                query.setMaxResults(10);
                List<ActivityTrail> activityTrails = query.getResultList();
                if (activityTrails != null) {
                    for (ActivityTrail activityTrail :
                            activityTrails) {
                        activityTrail.setShortDate(DateUtils.shortDate(activityTrail.getCreatedDate()));
                        activityTrail.setShortDateTime(DateUtils.shortDateTime(activityTrail.getCreatedDate()));
                        activityTrail.setUserName(userService.getUsersFullNameById(activityTrail.getUserId()));
                    }
                    return activityTrails;
                }


            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<ActivityTrail> searchActivityTrailMultiUser(long mssuserid, String name) {
        try {
            if (name != null) {

                name = name.toUpperCase();

                String sql = "From ActivityTrail ac where UPPER(ac.description) like :name ORDER BY ac.id DESC ";
                Query query = em.createQuery(sql, ActivityTrail.class);
                query.setParameter("name", "%" + name + "%");
                query.setMaxResults(10);
                List<ActivityTrail> activityTrails = query.getResultList();
                if (activityTrails != null) {
                    for (ActivityTrail activityTrail :
                            activityTrails) {
                        activityTrail.setShortDate(DateUtils.shortDate(activityTrail.getCreatedDate()));
                        activityTrail.setShortDateTime(DateUtils.shortDateTime(activityTrail.getCreatedDate()));
                        activityTrail.setUserName(userService.getUsersFullNameById(activityTrail.getUserId()));
                    }
                    return activityTrails;
                }


            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<ActivityTrail> getActivityLogOfUserOnDate(long userId, String date) {
        return findByNamedQuery("findActivityOfDate", singletonMap("dateFilter", date));
    }


    @Override
    public List<ActivityTrail> filterActivityByDate(long mssuserid, String dateFrom, String dateTo) {

        //2021-03-02
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateFrom = LocalDate.parse(dateFrom, dateTimeFormatter);
        LocalDate localDateTo = LocalDate.parse(dateTo, dateTimeFormatter);

        List<ActivityTrail> activityList = em.createQuery("select ac from ActivityTrail ac where ac.createdDate>=:dateFrom and ac.createdDate<=:dateTo and ac.userId<=:mssuserid order by ac.id desc", ActivityTrail.class)
                .setParameter("mssuserid", mssuserid)
                .setParameter("dateFrom", localDateFrom.atStartOfDay())
                .setParameter("dateTo", localDateTo.atTime(LocalTime.MAX))
                .getResultList();
        if (activityList != null) {
            for (ActivityTrail activityTrail :
                    activityList) {
                activityTrail.setShortDate(DateUtils.shortDate(activityTrail.getCreatedDate()));
                activityTrail.setShortDateTime(DateUtils.shortDateTime(activityTrail.getCreatedDate()));
                activityTrail.setUserName(userService.getUsersFullNameById(activityTrail.getUserId()));
            }
            return activityList;
        }


        return new ArrayList<>();


    }

    @Override
    public List<ActivityTrail> filterActivityByDateMultiUser(long mssuserid, String dateFrom, String dateTo) {

        //2021-03-02
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateFrom = LocalDate.parse(dateFrom, dateTimeFormatter);
        LocalDate localDateTo = LocalDate.parse(dateTo, dateTimeFormatter);

        List<ActivityTrail> activityList = em.createQuery("select ac from ActivityTrail ac where ac.createdDate>=:dateFrom and ac.createdDate<=:dateTo order by ac.id desc", ActivityTrail.class)
                .setParameter("dateFrom", localDateFrom.atStartOfDay())
                .setParameter("dateTo", localDateTo.atTime(LocalTime.MAX))
                .getResultList();
        if (activityList != null) {
            for (ActivityTrail activityTrail :
                    activityList) {
                activityTrail.setShortDate(DateUtils.shortDate(activityTrail.getCreatedDate()));
                activityTrail.setShortDateTime(DateUtils.shortDateTime(activityTrail.getCreatedDate()));
                activityTrail.setUserName(userService.getUsersFullNameById(activityTrail.getUserId()));
            }
            return activityList;
        }


        return new ArrayList<>();


    }

    @Override
    public List<ActivityTrail> getAllActivityLogs() {
        try {
//            String sql = "SELECT activitytrail.*,users.name,users.profile FROM activitytrail,users WHERE activitytrail.userId=users.id ORDER BY activitytrail.id DESC";
            String sql = "FROM ActivityTrail c ORDER BY c.id DESC";
            Query query = getEntityManager().createQuery(sql, ActivityTrail.class);
            query.setMaxResults(25);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}

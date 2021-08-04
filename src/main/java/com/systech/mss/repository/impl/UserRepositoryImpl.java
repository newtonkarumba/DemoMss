package com.systech.mss.repository.impl;

import com.systech.mss.domain.Profile;
import com.systech.mss.domain.User;
import com.systech.mss.domain.common.UserStatus;
import com.systech.mss.repository.UserRepository;
import com.systech.mss.service.ProfileService;
import com.systech.mss.seurity.DateUtils;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonMap;

public class UserRepositoryImpl extends AbstractRepositoryImpl<User, Long> implements UserRepository {
    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Inject
    Logger log;

    @Inject
    ProfileService profileService;

    @Override
    public Optional<User> findOneByLogin(String login) {
        return findSingleByNamedQuery("findUserByLogin", singletonMap("login", login));
    }


    @Override
    public Optional<User> findOneByEmail(String email) {
        return findSingleByNamedQuery("findUserByEmail", singletonMap("email", email));
    }

    @Override
    public List<User> findAllBySponsorRefNo(String sponsorRefNo) {

        return em.createNamedQuery("findUsersBySponsorRefNo", User.class).setParameter("sponsorRefNo", sponsorRefNo).getResultList();

    }

    @Override
    public User findUserBySchemeSponsorIdAndProfile(Profile profile, long schemeId, long sponsorId) {
        try {
            Query query = getEntityManager().createQuery("FROM User u WHERE  u.profile=:profile AND u.userDetails.schemeId=:schemeId AND u.userDetails.sponsorId=:sponsorId AND u.approvedByCrm=:approved", User.class);
            query.setParameter("profile", profile);
            query.setParameter("schemeId", schemeId);
            query.setParameter("sponsorId", sponsorId);
            query.setParameter("approved", true);
            query.setMaxResults(1);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            log.error("User not found");
        }
        return null;
    }


//    @Override
//    public User findUserBySchemeSAndProfile(Profile profile, long schemeId) {
//        try {
//            Query query = getEntityManager().createQuery("FROM User u WHERE  u.profile=:profile AND u.userDetails.schemeId=:schemeId AND u.approvedByCrm=:approved", User.class);
//            query.setParameter("profile", profile);
//            query.setParameter("schemeId", schemeId);
//            query.setParameter("approved", true);
//            query.setMaxResults(1);
//            return (User) query.getSingleResult();
//        } catch (Exception e) {
//            log.error("User not found");
//        }
//        return null;
//    }

    @Override
    public List<User> getUnapprovedClaimAuthorizerUsers() {
        Profile profile = profileService.getProfileByName("CLAIM AUTHORIZER");
        boolean approved = false;
        try {
            Query query = getEntityManager().createQuery("FROM User u WHERE  u.profile=:profile AND u.approvedByCrm=:approved", User.class);
            query.setParameter("profile", profile);
            query.setParameter("approved", approved);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<User> findUserBySponsorId(long sponsorId) {
        return em.createNamedQuery("findUserBySponsorId", User.class).setParameter("sponsorId", sponsorId).getResultList();
    }


    @Override
    public Optional<User> findOneByResetKey(String resetKey) {
        return findSingleByNamedQuery("findUserByResetKey", singletonMap("resetKey", resetKey));

    }

    @Override
    public Optional<User> findOneByActivationKey(String activationKey) {
        return findSingleByNamedQuery("findUserByActivationKey", singletonMap("activationKey", activationKey));

    }


    @Override
    public Optional<User> findOneById(long userId) {
        return findSingleByNamedQuery("findUserByUserId", singletonMap("id", userId));
    }

    @Override
    public List<User> findByProfile(Profile profile) {
        try {
            Query query = getEntityManager().createQuery("FROM User u WHERE  u.profile=:p", User.class);
            query.setParameter("p", profile);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findUsersBySponsorId(long sponsorId) {
        boolean approved = true;
        UserStatus userStatus = UserStatus.ACTIVE;
        try {

            Query query = getEntityManager().createQuery("FROM User u WHERE u.approvedByCrm=:approved And u.userStatus=:userStatus AND u.userDetails.sponsorId=:sponsorId", User.class);
            query.setParameter("approved", approved);
            query.setParameter("userStatus", userStatus);
            query.setParameter("sponsorId", sponsorId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

//    @Override
//    public List<User> resetAllById(long id) {
//        String passwordResetByAdmin = "user";
//
//        UserStatus userStatus = UserStatus.ACTIVE;
//        try{
//
//            Query query = getEntityManager().createQuery("FROM User u WHERE u.userDetails.userId=id");
//            query.setParameter("password", passwordResetByAdmin);
//
//        }
//
//
//        return null;
//    }


    @Override
    public List<User> filter(String dateFrom, String dateTo) {
        //2021-03-02
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateFrom = LocalDate.parse(dateFrom, dateTimeFormatter);
        LocalDate localDateTo = LocalDate.parse(dateTo, dateTimeFormatter);

        List<User> users = em.createQuery("select ac from User ac where ac.createdDate>=:dateFrom and ac.createdDate<=:dateTo  order by ac.id desc", User.class)
                .setParameter("dateFrom", localDateFrom.atStartOfDay())
                .setParameter("dateTo", localDateTo.atTime(LocalTime.MAX))
                .getResultList();
        if (users != null) {
            for (User user :
                    users) {
                user.setShortDate(DateUtils.shortDate(user.getCreatedDate()));
                user.setShortDateTime(DateUtils.shortDateTime(user.getCreatedDate()));
            }
            return users;
        }


        return null;
    }

    @Override
    public List<User> filter(Profile profile, String dateFrom, String dateTo) {
        //2021-03-02
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateFrom = LocalDate.parse(dateFrom, dateTimeFormatter);
        LocalDate localDateTo = LocalDate.parse(dateTo, dateTimeFormatter);

        List<User> users = em.createQuery("select ac from User ac where ac.profile=:profile and ac.createdDate>=:dateFrom and ac.createdDate<=:dateTo  order by ac.id desc", User.class)
                .setParameter("profile", profile)
                .setParameter("dateFrom", localDateFrom.atStartOfDay())
                .setParameter("dateTo", localDateTo.atTime(LocalTime.MAX))
                .getResultList();
        if (users != null) {
            for (User user :
                    users) {
                user.setShortDate(DateUtils.shortDate(user.getCreatedDate()));
                user.setShortDateTime(DateUtils.shortDateTime(user.getCreatedDate()));
            }
            return users;
        }


        return null;
    }

    @Override
    public List<User> getUserPrincipalOfficers(long schemeId) {
        Profile profile = profileService.getProfileByName("PRINCIPAL OFFICER");
        try {
            Query query = getEntityManager().createQuery("FROM User u WHERE  u.profile=:profile AND u.userDetails.schemeId=:schemeId", User.class);
            query.setParameter("profile", profile);
            query.setParameter("schemeId", schemeId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUserCRM(long schemeId) {
        Profile profile = profileService.getProfileByName("CRM");
        try {
            Query query = getEntityManager().createQuery("FROM User u WHERE  u.profile=:profile AND u.userDetails.schemeId=:schemeId", User.class);
            query.setParameter("profile", profile);
            query.setParameter("schemeId", schemeId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> searchUser(String searchKey) {
        try {
            Query query = getEntityManager().createQuery("FROM User u WHERE  u.firstName LIKE :searchKey " +
                            "or u.lastName LIKE :searchKey " +
                            "or u.login LIKE :searchKey " +
                            "or u.email LIKE :searchKey " +
                            "or u.userDetails.schemeId LIKE :searchKey " +
                            "or u.userDetails.cellPhone LIKE :searchKey " +
                            "or u.userDetails.nationalPenNo LIKE :searchKey " +
                            "or u.userDetails.sponsorId LIKE :searchKey " +
                            "or u.userDetails.sponsorRefNo LIKE :searchKey " +
                            "or u.userDetails.memberId LIKE :searchKey",
                    User.class);
            query.setParameter("searchKey", "%" + searchKey + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> searchUser(String searchKey, Profile profile) {
        try {
            Query query = getEntityManager().createQuery("FROM User u WHERE  ( u.firstName LIKE :searchKey " +
                            "or u.lastName LIKE :searchKey " +
                            "or u.login LIKE :searchKey " +
                            "or u.email LIKE :searchKey " +
                            "or u.userDetails.schemeId LIKE :searchKey " +
                            "or u.userDetails.cellPhone LIKE :searchKey " +
                            "or u.userDetails.nationalPenNo LIKE :searchKey " +
                            "or u.userDetails.sponsorId LIKE :searchKey " +
                            "or u.userDetails.sponsorRefNo LIKE :searchKey " +
                            "or u.userDetails.memberId LIKE :searchKey ) " +
                            "and u.profile=:profile",
                    User.class);
            query.setParameter("searchKey", "%" + searchKey + "%");
            query.setParameter("profile", profile);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> filterByScheme(Profile profile, long schemeId, long sponsorId) {
        try {
            String sql = "select * from users u where (u.profile='MEMBER') AND (u.schemeId=:schemeId)";
            if (sponsorId != -1)
                sql += " AND (u.sponsorId=:sponsorId)";
            Query query = getEntityManager().createNativeQuery(sql, User.class);
            query.setParameter("schemeId", schemeId);
            if (sponsorId != -1)
                query.setParameter("sponsorId", sponsorId);
            return query.getResultList();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return new ArrayList<>();
    }
}

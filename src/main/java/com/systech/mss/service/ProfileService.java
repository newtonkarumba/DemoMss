package com.systech.mss.service;

import com.systech.mss.config.Constants;
import com.systech.mss.domain.Profile;
import com.systech.mss.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProfileService {


    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;


    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public Profile getProfileByName(String name){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Profile> query = criteriaBuilder.createQuery(Profile.class);
        Root<Profile> from = query.from(Profile.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("name"),
                                name
                        )
                );
        List<Profile> profiles = getEntityManager().createQuery(query).getResultList();
        return !profiles.isEmpty()?profiles.get(0):null;
    }

    public long getProfileIdByName(String name){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Profile> query = criteriaBuilder.createQuery(Profile.class);
        Root<Profile> from = query.from(Profile.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("name"),
                                name
                        )
                );
        List<Profile> profiles = getEntityManager().createQuery(query).getResultList();
        if(!profiles.isEmpty()){
            return profiles.get(0).getId();
        }
        else {
            return 0;
        }
    }

    public String getProfileNameById(long id){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Profile> query = criteriaBuilder.createQuery(Profile.class);
        Root<Profile> from = query.from(Profile.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("id"),
                                id
                        )
                );
        List<Profile> profiles = getEntityManager().createQuery(query).getResultList();
        if(!profiles.isEmpty()){
            return profiles.get(0).getName();
        }
        else {
            return null;
        }
    }

    public String getSponsorNameById(long id){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("id"),
                                id
                        )
                );
        List<User> users = getEntityManager().createQuery(query).getResultList();
        if(!users.isEmpty()){
            return users.get(0).getFirstName() + " " + users.get(0).getLastName();
        }
        else {
            return null;
        }
    }

}

package org.example.todojavaee.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.example.todojavaee.model.User;
import org.example.todojavaee.utils.JPAUtils;

import java.util.List;

public class UserDao {

    public User findByKeycloakId(String ipKeycloakId) {
        User user = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            TypedQuery<User> query = em.createQuery("FROM User WHERE keycloakId = :ipKeycloakId", User.class);
            query.setParameter("ipKeycloakId", ipKeycloakId);
            List<User> userList = query.getResultList();
            if(!userList.isEmpty())
                user = userList.get(0);
        }
        return user;
    }

    public boolean createUser(User user) {
        boolean flag = false;
        EntityTransaction transaction = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(user);
            transaction.commit();
            flag = true;
        } catch (Exception e) {
            if(transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return flag;
    }

}

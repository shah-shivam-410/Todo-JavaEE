package org.example.todojavaee.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.example.todojavaee.model.Task;
import org.example.todojavaee.model.User;
import org.example.todojavaee.utils.JPAUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskJPADao {

    public List<Task> getAllTask() throws SQLException {
        List<Task> tasks = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            tasks = em.createQuery("FROM Task ORDER BY createdAt DESC, id ASC", Task.class)
                    .getResultList();
        }
        return tasks;
    }

    public List<Task> getAllTaskForUser(Integer userId) throws SQLException {
        List<Task> tasks = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            TypedQuery<Task> query = em.createQuery("FROM Task where user.id = :userId ORDER BY createdAt DESC, id ASC", Task.class);
            query.setParameter("userId", userId);
            tasks = query.getResultList();
        }
        return tasks;
    }

    public Task getTaskById(Integer id) throws SQLException {
        Task task = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            task = em.find(Task.class, id);
        }
        return task;
    }

    public boolean addTask(Task task, Integer userId) throws SQLException {
        boolean flag = false;
        EntityTransaction transaction = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            transaction = em.getTransaction();
            transaction.begin();
            User user = em.find(User.class, userId);
            task.setUser(user);
            em.persist(task);
            transaction.commit();
            flag = true;
        } catch (Exception e) {
            if(transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return flag;
    }

    public boolean updateTask(Task task, Integer userId) throws SQLException {
        boolean flag = false;
        EntityTransaction transaction = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            transaction = em.getTransaction();
            transaction.begin();
            Task existingTask = em.find(Task.class, task.getId());
            if (existingTask != null && existingTask.getUser().getId() == userId) {
                existingTask.setTitle(task.getTitle());
                existingTask.setDescription(task.getDescription());
                existingTask.setCompleted(task.getCompleted());
                em.merge(existingTask);
                transaction.commit();
                flag = true;
            }
        } catch (Exception e) {
            if(transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return flag;
    }

    public boolean deleteTask(Integer id, Integer userId) throws SQLException {
        boolean flag = false;
        EntityTransaction transaction = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            transaction = em.getTransaction();
            transaction.begin();
            Task task = em.find(Task.class, id);
            if(task != null && task.getUser().getId() == userId) {
                em.remove(task);
                transaction.commit();
                flag = true;
            }
        } catch (Exception e) {
            if(transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return flag;
    }

    public boolean toggleTask(Integer id, Integer userId) throws SQLException {
        boolean flag = false;
        EntityTransaction transaction = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            transaction = em.getTransaction();
            transaction.begin();
            Task task = em.find(Task.class, id);
            if (task != null && task.getUser().getId() == userId) {
                task.setCompleted(!task.getCompleted());
                em.merge(task);
                transaction.commit();
                flag = true;
            }
        } catch (Exception e) {
            if(transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return flag;
    }

}

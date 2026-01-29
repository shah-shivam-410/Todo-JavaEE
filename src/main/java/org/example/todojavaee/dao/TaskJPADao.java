package org.example.todojavaee.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.todojavaee.model.Task;
import org.example.todojavaee.utils.JPAUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskJPADao {

    public List<Task> getAllTask() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            tasks = em.createQuery("FROM Task ORDER BY createdAt DESC, id ASC", Task.class)
                    .getResultList();
            System.out.println("Retrieved " + tasks.size() + " tasks from database");
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

    public boolean addTask(Task task) throws SQLException {
        boolean flag = false;
        EntityTransaction transaction = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            transaction = em.getTransaction();
            transaction.begin();
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

    public boolean updateTask(Task task) throws SQLException {
        boolean flag = false;
        EntityTransaction transaction = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(task);
            transaction.commit();
            flag = true;
        } catch (Exception e) {
            if(transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return flag;
    }

    public boolean deleteTask(Integer id) throws SQLException {
        boolean flag = false;
        EntityTransaction transaction = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            transaction = em.getTransaction();
            transaction.begin();
            Task task = em.find(Task.class, id);
            if(task != null) {
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

    public boolean toggleTask(Integer id) throws SQLException {
        boolean flag = false;
        EntityTransaction transaction = null;
        try (EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();) {
            transaction = em.getTransaction();
            transaction.begin();
            Task task = em.find(Task.class, id);
            if(task != null) {
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

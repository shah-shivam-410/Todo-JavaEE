package org.example.todojavaee.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtils {

    private  static EntityManagerFactory entityManagerFactory = null;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("TodoPU");
        System.out.println("entityManagerFactory created successfully...");
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void shutdown() {
        if(entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

}

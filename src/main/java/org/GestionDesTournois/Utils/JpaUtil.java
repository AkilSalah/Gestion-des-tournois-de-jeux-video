package org.GestionDesTournois.Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static JpaUtil instance;
    private EntityManagerFactory emf;

    private JpaUtil(){
        emf = Persistence.createEntityManagerFactory("MyPersistance");
    }

    public static JpaUtil getInstance(){
        if (instance==null){
            synchronized (JpaUtil.class){
                if (instance==null){
                    instance = new JpaUtil();
                }
            }
        }
        return instance;
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

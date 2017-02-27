package com.club.control.utilidades;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

public class JPAUtil {

    private static final long serialVersionUID = 1L;
    private static JPAUtil me;
    private static EntityManagerFactory emf;

    private JPAUtil() {
        try {

            Properties props = new Properties();
            InputStream datos = this.getClass().getClassLoader().getResourceAsStream("META-INF/application.properties");
            props.load(datos);
            HashMap parametros = new HashMap();
            parametros.put("javax.persistence.jdbc.user", props.getProperty("jdbc.user"));
            parametros.put("javax.persistence.jdbc.password", props.getProperty("jdbc.pass"));
            parametros.put("javax.persistence.jdbc.driver", props.getProperty("jdbc.driver"));
            parametros.put("javax.persistence.jdbc.url", props.getProperty("jdbc.url"));
            emf = Persistence.createEntityManagerFactory("ClubSarandiPU", parametros);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }

    public static JPAUtil getInstance() {

        try {
            if (me == null) {
                me = new JPAUtil();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
        return me;
    }

    public EntityManager getEntityManager() {
        EntityManager toReturn = emf.createEntityManager();
        toReturn.getTransaction().begin();
        return toReturn;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getList(Class<T> classToCast, String jpql, Object... parameters) {
        List<T> toReturn = null;
        EntityManager em = getEntityManager();
        Query qr = em.createQuery(jpql);
        for (int i = 0; i < parameters.length; i++) {
            qr.setParameter(i + 1, parameters[i]);
        }
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public <T> T getEntity(Class<T> entityClass, Serializable pk) {
        EntityManager em = getEntityManager();
        T toReturn = em.find(entityClass, pk);
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }
}

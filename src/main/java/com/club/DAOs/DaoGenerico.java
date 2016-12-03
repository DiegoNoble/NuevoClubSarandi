/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.control.utilidades.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DaoGenerico {

    public EntityManager em;

    public DaoGenerico() {
        this.em = JPAUtil.getInstance().getEntityManager();
    }

    public void Salvar(Object obj) throws Exception {

        em.persist(obj);
        em.getTransaction().commit();
        em.close();

    }

    public void SalvarList(List list) throws Exception {
        for (Object obj : list) {
            em.persist(obj);
            em.flush();
            em.clear();
        }
        em.getTransaction().commit();
        em.close();
    }

    public void Actualizar(Object obj) throws Exception {
        em.merge(obj);
        em.getTransaction().commit();
        em.close();
    }

    public Object BuscaPorID(Class clase, int id) {
        return JPAUtil.getInstance().getEntity(clase, id);
    }

    public void elminiar(Class clase, Object obj) {
        obj = em.getReference(clase, obj);
        em.remove(obj);
        em.getTransaction().commit();
        em.close();
    }

    public void EliminarPorId(Class clase, int id) {
        em.remove(em.find(clase, id));
        em.getTransaction().commit();
        em.close();
    }

    public List BuscaTodos(Class clase) {
        return JPAUtil.getInstance().getList(clase, "from " + clase.getName() + "");
    }

    public List BuscaPor(Class clase, String campo, String variable) {
        Query qr = em.createQuery("from " + clase.getName() + " where " + campo + " like ?1");
        //qr.setParameter(1, clase.getName());
        //qr.setParameter(2, campo);
        qr.setParameter(1, "%" + variable + "%");
        List toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }
}

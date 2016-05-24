/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Categoria;
import com.club.control.utilidades.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class CategoriaDAO extends DaoGenerico {

    EntityManager em = JPAUtil.getInstance().getEntityManager();

    public List categoriasQuePagan() {
       
        List<Categoria> toRetorn = null;
        Query qr = em.createQuery("FROM Categoria AS c WHERE c.mensualidad not in(0)");
        toRetorn = qr.getResultList();

        return toRetorn;
    }
}

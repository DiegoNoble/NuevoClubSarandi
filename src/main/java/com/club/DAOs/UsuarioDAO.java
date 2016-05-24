/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Usuario;
import com.club.control.utilidades.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class UsuarioDAO extends DaoGenerico{
    
    
    public boolean isUsernameAndPasswordExists(String username, String password) {
        boolean exists = false;
        EntityManager em = JPAUtil.getInstance().getEntityManager();
        Query qr = em.createQuery("from Usuario as user where user.nombre= ?1 and user.pass = ?2");
        qr.setParameter(1, username);
        qr.setParameter(2, password);
        try {
            exists = qr.getSingleResult() != null;
        } catch (Exception e) {
            exists = false;
        }
        em.getTransaction().commit();
        em.close();
        return exists;
    }
    
    public Usuario buscaPerfilUsuario(String username, String password) {
        
        EntityManager em = JPAUtil.getInstance().getEntityManager();
        Query qr = em.createQuery("from Usuario as user where user.nombre= ?1 and user.pass = ?2");
        qr.setParameter(1, username);
        qr.setParameter(2, password);
        Usuario usuario = (Usuario) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return usuario;
    }

}

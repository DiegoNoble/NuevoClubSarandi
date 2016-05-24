/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.control.utilidades.JPAUtil;
import javax.persistence.EntityManager;

/**
 *
 * @author Diego
 */
public class FichaMedicaDAO extends DaoGenerico {
    
    private EntityManager em;
    
    public FichaMedicaDAO(){
        
        em = JPAUtil.getInstance().getEntityManager();
    }
    
}

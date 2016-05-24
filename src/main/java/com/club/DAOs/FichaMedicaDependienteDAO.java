
package com.club.DAOs;

import com.club.control.utilidades.JPAUtil;
import javax.persistence.EntityManager;

/**
 *
 * @author Diego
 */
public class FichaMedicaDependienteDAO extends DaoGenerico{
    
    private EntityManager em;
    
    public FichaMedicaDependienteDAO(){
        
        em = JPAUtil.getInstance().getEntityManager();
    }
    
}

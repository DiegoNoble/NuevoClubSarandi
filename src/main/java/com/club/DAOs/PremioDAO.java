/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.CampEconomica;
import com.club.BEANS.Numeros;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class PremioDAO extends DaoGenerico {

    public PremioDAO() {
    }

    public List BuscaTodosPorCampaña(CampEconomica campEconomica) {

        List<Numeros> toReturn = null;

        Query qr = em.createQuery("FROM Premio AS n WHERE n.campEconomica =:campEconomica ");
        qr.setParameter("campEconomica", campEconomica);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }
    
    public List BuscaPremioPorCampaña(CampEconomica campEconomica, String filtro) {

        List<Numeros> toReturn = null;

        Query qr = em.createQuery("FROM Premio AS n WHERE n.campEconomica =:campEconomica and n.descripcion like ?1");
        qr.setParameter("campEconomica", campEconomica);
        qr.setParameter(1, "%" + filtro + "%");
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

}

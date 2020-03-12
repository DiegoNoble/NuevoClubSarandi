/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.CuotaCampEconomica;
import com.club.BEANS.VentaCampEco;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class CuotaCampEconomicaDAO extends DaoGenerico {

    public CuotaCampEconomicaDAO() {
    }

    public List<CuotaCampEconomica> BuscaPorVentaCampEco(VentaCampEco ventaCampEco) {

        List toReturn = null;

        Query qr = em.createQuery("FROM CuotaCampEconomica c WHERE c.ventaCampEco =:ventaCampEco");
        qr.setParameter("ventaCampEco", ventaCampEco);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

}

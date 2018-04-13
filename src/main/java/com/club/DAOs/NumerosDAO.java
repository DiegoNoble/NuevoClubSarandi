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
public class NumerosDAO extends DaoGenerico {

    public NumerosDAO() {
    }

    public List BuscaNumeros(Integer nro1, Integer nro2, CampEconomica campEconomica) {

        List<Numeros> toReturn = null;

        Query qr = em.createQuery("FROM Numeros AS n WHERE n.nro1 =:nro1 or n.nro1 =:nro2 or n.nro2 =:nro1 or n.nro2 =:nro2 and n.campEconomica =:campEconomica order by n.nro1");
        qr.setParameter("nro1", nro1);
        qr.setParameter("nro2", nro2);
        qr.setParameter("campEconomica", campEconomica);
        toReturn = qr.getResultList();

        return toReturn;

    }
public List BuscaTodosNumerosPorCampana(CampEconomica campEconomica) {

        List<Numeros> toReturn = null;

        Query qr = em.createQuery("FROM Numeros AS n WHERE n.campEconomica =:campEconomica order by n.nro1");
        qr.setParameter("campEconomica", campEconomica);
        toReturn = qr.getResultList();

        return toReturn;

    }


    public List BuscaTodosyOrdena(CampEconomica campEconomica) {

        List<Numeros> toReturn = null;

        Query qr = em.createQuery("FROM Numeros AS n WHERE n.campEconomica =:campEconomica order by n.nro1");
        qr.setParameter("campEconomica", campEconomica);
        toReturn = qr.getResultList();

        return toReturn;

    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.FichaMedica;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class FichaMedicaDAO extends DaoGenerico {

    public List<FichaMedica> fichasEntreFechas(Date desde, Date hasta) {

        List<FichaMedica> toReturn = null;

        Query qr = em.createQuery("from FichaMedica f where f.fechaEmision between ?1 and ?2");
        qr.setParameter(1, desde);
        qr.setParameter(2, hasta);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

}

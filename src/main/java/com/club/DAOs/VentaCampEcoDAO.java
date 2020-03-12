/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.CampEconomica;
import com.club.BEANS.Cobrador;
import com.club.BEANS.VentaCampEco;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class VentaCampEcoDAO extends DaoGenerico {

    public VentaCampEcoDAO() {
    }

    public List<VentaCampEco> BuscaVentasPorCamp(CampEconomica campEconomica) {

        List toReturn = null;

        Query qr = em.createQuery("FROM VentaCampEco v WHERE v.campEconomica =:campEconomica");
        qr.setParameter("campEconomica", campEconomica);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<VentaCampEco> BuscaVentasPorCampYCobraor(CampEconomica campEconomica, Cobrador cobrador) {

        List toReturn = null;

        Query qr = em.createQuery("FROM VentaCampEco v WHERE v.campEconomica =:campEconomica and v.cobrador = :cobrador");
        qr.setParameter("campEconomica", campEconomica);
        qr.setParameter("cobrador", cobrador);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<VentaCampEco> BuscaVentasPorIdSocio(CampEconomica campEconomica, Integer idSocio) {

        List toReturn = null;

        Query qr = em.createQuery("FROM VentaCampEco v WHERE v.campEconomica =:campEconomica and v.socio.id =:idSocio");
        qr.setParameter("idSocio", idSocio);
        qr.setParameter("campEconomica", campEconomica);

        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<VentaCampEco> BuscaVentasPorNombreSocio(CampEconomica campEconomica, String nombreSocio) {

        List toReturn = null;

        Query qr = em.createQuery("FROM VentaCampEco v WHERE v.campEconomica =?1 and v.socio.nombre like ?2 ");
        qr.setParameter(1, campEconomica);
        qr.setParameter(2, "%" + nombreSocio + "%");
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<VentaCampEco> BuscaVentasPorIdSocioYCobrador(CampEconomica campEconomica, Integer idSocio, Cobrador cobrador) {

        List toReturn = null;

        Query qr = em.createQuery("FROM VentaCampEco v WHERE v.campEconomica =:campEconomica and v.socio.id =:idSocio and v.cobrador = ?3");
        qr.setParameter("idSocio", idSocio);
        qr.setParameter("campEconomica", campEconomica);
        qr.setParameter(3, cobrador);

        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<VentaCampEco> BuscaVentasPorNombreSocioyCobrador(CampEconomica campEconomica, String nombreSocio, Cobrador cobrador) {

        List toReturn = null;

        Query qr = em.createQuery("FROM VentaCampEco v WHERE v.campEconomica =?1 and v.socio.nombre like ?2 and v.cobrador = ?3 ");
        qr.setParameter(1, campEconomica);
        qr.setParameter(2, "%" + nombreSocio + "%");
        qr.setParameter(3, cobrador);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

}

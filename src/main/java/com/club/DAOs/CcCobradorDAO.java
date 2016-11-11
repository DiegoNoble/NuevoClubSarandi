/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.CcCobrador;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Mensualidades;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class CcCobradorDAO extends DaoGenerico {

    public List<CcCobrador> BuscaCcCobrador(Cobrador cobradorSeleccionado) throws Exception {

        List<CcCobrador> listMovimientos = null;
        Query qr = em.createQuery("FROM CcCobrador AS cc WHERE cc.cobrador =:cobrador");
        qr.setParameter("cobrador", cobradorSeleccionado);
        listMovimientos = qr.getResultList();

        return listMovimientos;
    }

    public List<CcCobrador> BuscaCcCobradorPorMensualidad(Mensualidades mensualidades) throws Exception {
        List<CcCobrador> listMovimientos = null;
        Query qr = em.createQuery("FROM CcCobrador AS cc WHERE cc.mensualidades =:mensualidad");
        qr.setParameter("mensualidad", mensualidades);
        listMovimientos = qr.getResultList();

        return listMovimientos;
    }

}

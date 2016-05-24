/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.CcCobrador;
import com.club.BEANS.Cobrador;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class CcCobradorDAO extends DaoGenerico {

    public List<CcCobrador> BuscaCcCobrador(Cobrador cobradorSeleccionado) throws Exception{

        List<CcCobrador> listMovimientos = null;
        Query qr = em.createQuery("FROM CcCobrador AS cc WHERE cc.cobrador =:cobrador");
        qr.setParameter("cobrador", cobradorSeleccionado);
        listMovimientos = qr.getResultList();
        
        return listMovimientos;
    }

}

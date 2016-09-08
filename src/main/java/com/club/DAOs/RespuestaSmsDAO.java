/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.RespuestaSMS;
import com.club.BEANS.Sms;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class RespuestaSmsDAO extends DaoGenerico {

    public RespuestaSmsDAO() {
    }

    public void SalvarOActualizar(Object obj) throws Exception {

        em.merge(obj);
        em.getTransaction().commit();
        em.close();

    }

    public List<RespuestaSMS> BuscarPorSMS(Sms sms) {
        Query qr = em.createQuery("from RespuestaSMS r where r.sms = ?1");
        qr.setParameter(1, sms);
        List<RespuestaSMS> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }
}

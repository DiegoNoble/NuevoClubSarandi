/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Campanasms;
import com.club.BEANS.Sms;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class SmsDAO extends DaoGenerico {

    public SmsDAO() {
    }

    public Sms BuscarPorId(int id) {
        Sms toReturn = em.find(Sms.class, id);
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public List<Sms> BuscarPorCampaña(Campanasms campanasms) {
        Query qr = em.createQuery("from Sms s join fetch s.socio where s.campanasms = ?1 order by s.respondido desc");
        qr.setParameter(1, campanasms);
        List<Sms> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public Sms BuscarCelYCampaña(String nro, Campanasms campanasms) {
        Query qr = em.createQuery("from Sms s join fetch s.socio where s.socio.celular = ?1 and s.campanasms = ?2");
        qr.setParameter(1, nro);
        qr.setParameter(2, campanasms);
        Sms toReturn = (Sms) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }
}

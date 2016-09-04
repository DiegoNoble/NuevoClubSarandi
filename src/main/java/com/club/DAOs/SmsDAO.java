/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Sms;

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

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Caja;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;


public class CajaDAO extends DaoGenerico {
    
    public List<Caja> BuscaMovimientosPorFecha(Date fecha){
        
        List toReturn = null;
        
        Query qr = em.createQuery("FROM Caja AS c WHERE c.fechaMovimiento =:fecha");
        qr.setParameter("fecha", fecha);
        toReturn = qr.getResultList();
        
        return toReturn;
        
    }

    public Double buscaSaldoAnterior(Date fecha) {

        String hql = "SELECT SUM(entrada)-SUM(salida)FROM Caja c WHERE c.fechaMovimiento < :fecha";
        Query q = em.createQuery(hql);
        q.setParameter("fecha", fecha);
        Double saldo = (Double) q.getSingleResult();
        if (saldo == null) {
            saldo = 0.0;
        }
        return saldo;

    }
    
     public Double buscaSaldoDelDia(Date fecha) {

        String hql = "SELECT SUM(entrada)-SUM(salida) FROM Caja c WHERE c.fechaMovimiento <= :fecha";
        Query q = em.createQuery(hql);
        q.setParameter("fecha", fecha);
        Double saldo = (Double) q.getSingleResult();
        return saldo;

    }

}

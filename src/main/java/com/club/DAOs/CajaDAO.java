/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Caja;
import com.club.BEANS.Rubro;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

public class CajaDAO extends DaoGenerico {

    public List<Caja> BuscaMovimientosPorFecha(Date fecha) {

        List toReturn = null;

        Query qr = em.createQuery("FROM Caja AS c WHERE c.fechaMovimiento =:fecha");
        qr.setParameter("fecha", fecha);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<Caja> BuscaTodosOrdenaPorID() {

        List toReturn = null;

        Query qr = em.createQuery("FROM Caja AS c order by id asc");
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<Caja> BuscaTodosOrdenaPorIDFyFecha(Date fecha) {

        List toReturn = null;

        Query qr = em.createQuery("FROM Caja AS c where fechaMovimiento >= :fecha order by fechaMovimiento,id asc");
        qr.setParameter("fecha", fecha);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
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
        em.getTransaction().commit();
        em.close();
        return saldo;

    }

    public Double buscaSaldoDelDia(Date fecha) {

        String hql = "SELECT SUM(entrada)-SUM(salida) FROM Caja c WHERE c.fechaMovimiento <= :fecha";
        Query q = em.createQuery(hql);
        q.setParameter("fecha", fecha);
        Double saldo = (Double) q.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return saldo;

    }

    public List<Caja> buscaMovEntreFechas(Date desde, Date hasta) {

        List toReturn = null;

        Query qr = em.createQuery("FROM Caja AS c WHERE c.fechaMovimiento between :desde and :hasta order by fechaMovimiento, id asc");
        qr.setParameter("desde", desde);
        qr.setParameter("hasta", hasta);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<Caja> BuscaMovimientosPorRubro(Rubro rubro) {

        List toReturn = null;

        Query qr = em.createQuery("FROM Caja AS c WHERE c.rubro =:rubro");
        qr.setParameter("rubro", rubro);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public Caja BuscaSaldoAnterior() {

        Caja toReturn = null;

        Query qr = em.createQuery("FROM Caja AS c WHERE c.id = (select max(id) from Caja)");
        toReturn = (Caja) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public Caja BuscaSaldoAnteriorFecha(Date fecha) {

        Caja toReturn = null;

        Query qr = em.createQuery("FROM Caja AS c WHERE c.id = (select max(id) from Caja where fechaMovimiento <:fecha and"
                + " fechaMovimiento = (select max(fechaMovimiento) from Caja where fechaMovimiento <:fecha))");
        qr.setParameter("fecha", fecha);
        toReturn = (Caja) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.CajaCampEco;
import com.club.BEANS.Rubro;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

public class CajaCampEcoDAO extends DaoGenerico {

    public List<CajaCampEco> BuscaMovimientosPorFecha(Date fecha) {

        List toReturn = null;

        Query qr = em.createQuery("FROM CajaCampEco AS c WHERE c.fechaMovimiento =:fecha");
        qr.setParameter("fecha", fecha);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();

        return toReturn;

    }

    public List<CajaCampEco> BuscaTodosOrdenaPorID() {

        List toReturn = null;

        Query qr = em.createQuery("FROM CajaCampEco AS c order by id asc");
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();

        return toReturn;

    }

    public List<CajaCampEco> BuscaTodosOrdenaPorIDFyFecha(Date fecha) {

        List toReturn = null;

        Query qr = em.createQuery("FROM CajaCampEco AS c where fechaMovimiento >= :fecha order by fechaMovimiento,id asc");
        qr.setParameter("fecha", fecha);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();

        return toReturn;

    }

    public Double buscaSaldoAnterior(Date fecha) {

        String hql = "SELECT SUM(entrada)-SUM(salida)FROM CajaCampEco c WHERE c.fechaMovimiento < :fecha";
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

        String hql = "SELECT SUM(entrada)-SUM(salida) FROM CajaCampEco c WHERE c.fechaMovimiento <= :fecha";
        Query q = em.createQuery(hql);
        q.setParameter("fecha", fecha);
        Double saldo = (Double) q.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return saldo;

    }

    public List<CajaCampEco> buscaMovEntreFechas(Date desde, Date hasta) {

        List toReturn = null;

        Query qr = em.createQuery("FROM CajaCampEco AS c WHERE c.fechaMovimiento between :desde and :hasta order by fechaMovimiento, id asc");
        qr.setParameter("desde", desde);
        qr.setParameter("hasta", hasta);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<CajaCampEco> BuscaMovimientosPorRubro(Rubro rubro) {

        List toReturn = null;

        Query qr = em.createQuery("FROM CajaCampEco AS c WHERE c.rubro =:rubro");
        qr.setParameter("rubro", rubro);
        toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public CajaCampEco BuscaSaldoAnterior() {

        CajaCampEco toReturn = null;

        Query qr = em.createQuery("FROM CajaCampEco AS c WHERE c.id = (select max(id) from CajaCampEco)");
        toReturn = (CajaCampEco) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public CajaCampEco BuscaSaldoAnteriorFecha(Date fecha) {

        CajaCampEco toReturn = null;

        Query qr = em.createQuery("FROM CajaCampEco AS c WHERE c.id = (select max(id) from CajaCampEco where fechaMovimiento <:fecha and"
                + " fechaMovimiento = (select max(fechaMovimiento) from CajaCampEco where fechaMovimiento <:fecha))");
        qr.setParameter("fecha", fecha);
        toReturn = (CajaCampEco) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }
}

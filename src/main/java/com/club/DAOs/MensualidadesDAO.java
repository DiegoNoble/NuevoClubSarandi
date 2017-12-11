/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Cobrador;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Socio;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class MensualidadesDAO extends DaoGenerico {

    public MensualidadesDAO() {

    }

    public Integer NroLanzamiento() {
        String hql = "select max(lanzamiento+1) FROM Mensualidades";
        Query q = em.createQuery(hql);
        Integer ultimoLanzamiento = (Integer) q.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return ultimoLanzamiento;

    }

    public List BuscaMensualidadesPorSocio(Socio socio) {

        List<Mensualidades> toReturn = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.socio =:socio order by m.fechaVencimiento desc");
        qr.setParameter("socio", socio);
        toReturn = qr.getResultList();

        return toReturn;

    }

    public List<Date> BuscaVencimientos() {

        List<Date> toReturn = null;

        Query qr = em.createQuery("select distinct(m.fechaVencimiento) FROM  Mensualidades m order by m.fechaVencimiento desc");
        toReturn = qr.getResultList();

        return toReturn;

    }

    /**
     *
     * @param socio Return TRUE si no existe la emisión anterior, FALSE si
     * existe
     * @return
     */
    public Boolean VerificaSiYaFueEmitida(Socio socio, Date fechaVencimiento) throws Exception {

        Boolean verifica = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.socio= :socio and m.fechaVencimiento =:fecha");
        qr.setParameter("fecha", fechaVencimiento);
        qr.setParameter("socio", socio);
        List<Mensualidades> m = qr.getResultList();
        if (m.isEmpty()) {
            verifica = true;
        } else {
            verifica = false;
        }
        em.getTransaction().commit();
        em.close();
        return verifica;
    }

    /**
     *
     * @param socio Return TRUE si tiene menos de 3 vencimientos, FALSE si tiene
     * mas o = a 3 vencimientos
     * @return
     */
    public Boolean VerificaCantidadVencimientos(Socio socio, Integer tolerancia) throws Exception {

        Boolean verifica = null;

        Query qr = em.createQuery("SELECT COUNT(*) FROM Mensualidades AS m WHERE m.socio.id =:socio AND m.pago='Pendiente de Pago'");
        qr.setParameter("socio", socio.getId());
        Long numeroVencimientos = (Long) qr.getSingleResult();

        if (numeroVencimientos > tolerancia) {
            verifica = false;
        } else if (numeroVencimientos <= tolerancia) {
            verifica = true;
        }
        em.getTransaction().commit();
        em.close();
        return verifica;

    }

    public List BuscaPorCobradorSituacionVencimiento(Cobrador cobrador, String situcion, Date desde, Date hasta) {

        List<Mensualidades> toReturn = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.cobrador =:cobrador and m.pago =:situcion and m.fechaVencimiento between :desde and :hasta");
        qr.setParameter("cobrador", cobrador);
        qr.setParameter("situcion", situcion);
        qr.setParameter("desde", desde);
        qr.setParameter("hasta", hasta);
        toReturn = qr.getResultList();

        return toReturn;

    }

    public List BuscaPendientes(Socio socio) {

        List<Mensualidades> toReturn = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.socio= :socio and m.pago='Pendiente de Pago' order by id asc");
        qr.setParameter("socio", socio);
        toReturn = qr.getResultList();

        return toReturn;

    }

    public List BuscaPorCobradorVencimiento(Cobrador cobrador, Date desde, Date hasta) {

        List<Mensualidades> toReturn = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.cobrador =:cobrador  and m.fechaVencimiento between :desde and :hasta");
        qr.setParameter("cobrador", cobrador);
        qr.setParameter("desde", desde);
        qr.setParameter("hasta", hasta);
        toReturn = qr.getResultList();

        return toReturn;

    }

    public List BuscaPorCobradorSituacionVencimiento(Cobrador cobrador, Date vencimiento, Collection situacion) {

        List<Mensualidades> toReturn = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.cobrador =:cobrador  and m.fechaVencimiento =:vencimiento "
                + "and m.pago in :situacion");
        qr.setParameter("cobrador", cobrador);
        qr.setParameter("vencimiento", vencimiento);
        qr.setParameter("situacion", situacion);
        toReturn = qr.getResultList();

        return toReturn;

    }

    public Mensualidades BuscaPorNroTalon(String nroTalon) {

        Mensualidades toReturn = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.nroTalonCobrosYa =:nroTalon");
        qr.setParameter("nroTalon", nroTalon);
        toReturn = (Mensualidades) qr.getSingleResult();

        return toReturn;

    }

    public List<Mensualidades> BuscaPorCobradorSituacion(Cobrador cobrador, String situcion) {

        List<Mensualidades> toReturn = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.socio.Cobrador =:cobrador and m.pago =:situcion");
        qr.setParameter("cobrador", cobrador);
        qr.setParameter("situcion", situcion);
        toReturn = qr.getResultList();

        return toReturn;

    }

}

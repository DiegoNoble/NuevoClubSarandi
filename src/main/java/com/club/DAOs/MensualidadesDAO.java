/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Cobrador;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Socio;
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
    public Boolean VerificaCantidadVencimientos(Socio socio) throws Exception {

        Boolean verifica = null;

        Query qr = em.createQuery("SELECT COUNT(*) FROM Mensualidades AS m WHERE m.socio.id =:socio AND m.pago='Pendiente de Pago'");
        qr.setParameter("socio", socio.getId());
        Long numeroVencimientos = (Long) qr.getSingleResult();

        if (numeroVencimientos >= 3) {
            verifica = false;
        } else if (numeroVencimientos < 3) {
            verifica = true;
        }
        em.getTransaction().commit();
        em.close();
        return verifica;

    }

    public List BuscaPorCobradorSituacionVencimiento(Cobrador cobrador, String situcion, Date vencimiento) {

        List<Mensualidades> toReturn = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.cobrador =:cobrador and m.pago =:situcion and m.fechaVencimiento =:vencimiento");
        qr.setParameter("cobrador", cobrador);
        qr.setParameter("situcion", situcion);
        qr.setParameter("vencimiento", vencimiento);
        toReturn = qr.getResultList();

        return toReturn;

    }

    public List BuscaPorCobradorSituacionVencimiento(Cobrador cobrador, Date vencimiento) {

        List<Mensualidades> toReturn = null;

        Query qr = em.createQuery("FROM Mensualidades AS m WHERE m.cobrador =:cobrador  and m.fechaVencimiento =:vencimiento");
        qr.setParameter("cobrador", cobrador);
        qr.setParameter("vencimiento", vencimiento);
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

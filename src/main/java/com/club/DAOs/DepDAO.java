/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Dependiente;
import com.club.BEANS.Socio;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class DepDAO extends DaoGenerico {

    public DepDAO() {

    }

    public List<Dependiente> FiltroInteligenteDependiente(String texto) {

        Query qr = em.createNativeQuery("select * from tbdependiente d where (select convert(d.id,char))\n"
                + "like ?1 or d.nombre like  ?2 or d.ci like ?3", Dependiente.class);
        qr.setParameter(1, "%" + texto + "%");
        qr.setParameter(2, "%" + texto + "%");
        qr.setParameter(3, "%" + texto + "%");
        List<Dependiente> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<Dependiente> BuscaPorCodigodeSocio(Socio socio) {

        Query qr = em.createQuery("from Dependiente dependiente where dependiente.socio = ?1");
        qr.setParameter(1, socio);
        List toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<Dependiente> BuscaPorCodigoLike(String ci) {

        Query qr = em.createQuery("from Dependiente d where str(d.id) like ?1");
        qr.setParameter(1, ci.toString());
        List toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List BuscaPorNombre(String name) {
        Query qr = em.createQuery("from Dependiente dp where dp.nombre like ?1");
        qr.setParameter(1, "%" + name + "%");
        List<Dependiente> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public List BuscaPorCI(String CI) {
        Query qr = em.createQuery("from Dependiente dp where dp.ci like ?1");
        qr.setParameter(1, "%" + CI + "%");
        List<Dependiente> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public Dependiente BuscaPorCodigo(String codigo) {

        Query qr = em.createQuery("from Dependiente dp where dp.id = ?1");
        qr.setParameter(1, codigo);
        Dependiente toReturn = (Dependiente) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public Dependiente BuscaPorCIIgual(String CI) {
        Dependiente toReturn;
        Query qr = em.createQuery("from Dependiente d where d.ci = ?1");
        qr.setParameter(1, CI);
        List<Dependiente> listToReturn = qr.getResultList();
        if (!listToReturn.isEmpty()) {
            toReturn = listToReturn.get(0);
        } else {
            toReturn = null;
        }

        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public List BuscaTodosMasRapido() {
        Query qr = em.createQuery("select dp.id,dp.nombre,dp.ci,dp.situacion from Dependiente dp");
        List<Dependiente> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public boolean VerificaCI(String CI) {
        boolean exists = false;
        Query qr = em.createQuery("from Socio socio where socio.ci = ?1");
        qr.setParameter(1, CI);
        try {
            exists = qr.getSingleResult() != null;
        } catch (Exception e) {
            exists = false;
        }
        em.getTransaction().commit();
        em.close();
        return exists;
    }

    public List<Dependiente> BuscaDepXHuella() {

        Query qr = em.createQuery("from Dependiente d where d.tamano is not null");
        List<Dependiente> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public Dependiente BuscaTitular(Dependiente dependiente) {

        Query qr = em.createQuery("from Dependiente d join fetch d.socio where d.id = ?1");
        qr.setParameter(1, dependiente.getId());
        Dependiente toReturn = (Dependiente) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    
}

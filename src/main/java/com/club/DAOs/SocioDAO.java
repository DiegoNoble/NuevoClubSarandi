/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Categoria;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Socio;
import java.util.List;
import javax.persistence.Query;

public class SocioDAO extends DaoGenerico {

    public SocioDAO() {

    }

    public List BuscaPorNombre(String name) {
        Query qr = em.createQuery("from Socio socio where socio.nombre like ?1");
        qr.setParameter(1, "%" + name + "%");
        List<Socio> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public List BuscaPorCI(String CI) {
        Query qr = em.createQuery("from Socio socio where socio.ci like ?1");
        qr.setParameter(1, "%" + CI + "%");
        List<Socio> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public Socio BuscaPorCIIgual(String CI) {
        Socio toReturn;
        Query qr = em.createQuery("from Socio socio where socio.ci = ?1");
        qr.setParameter(1, CI);
        List<Socio> listToReturn = qr.getResultList();
        if (!listToReturn.isEmpty()) {
            toReturn = listToReturn.get(0);
        } else {
            toReturn = null;
        }

        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public Socio BuscaPorCodigo(String codigo) {

        Query qr = em.createQuery("from Socio socio where socio.id = ?1");
        qr.setParameter(1, Integer.parseInt(codigo));
        Socio toReturn = (Socio) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public Socio BuscaPorCodigoYCobrador(String codigo, Cobrador cobrador) {

        Query qr = em.createQuery("from Socio s where s.id = ?1 and s.Cobrador = ?2");
        qr.setParameter(1, Integer.parseInt(codigo));
        qr.setParameter(2, cobrador);
        Socio toReturn = (Socio) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }
    
    public List<Socio> BuscaPorNombreYCobrador(String name, Cobrador cobrador) {

        Query qr = em.createQuery("from Socio s where s.nombre like ?1 and s.Cobrador = ?2");
        qr.setParameter(1, "%" + name + "%");
        qr.setParameter(2, cobrador);
        List<Socio> toReturn = qr.getResultList();
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

    public boolean VerificaSiTieneDependinetes(Socio socio) {
        boolean exists = false;
        Query qr = em.createQuery("from Dependiente dependiente where dependiente.socio = ?1");
        qr.setParameter(1, socio);
        try {
            exists = qr.getSingleResult() != null;
        } catch (Exception e) {
            exists = false;
        }
        em.getTransaction().commit();
        em.close();
        return exists;
    }

    public List BuscaSocioTitular(Socio socio) {

        Query qr = em.createQuery("from Dependiente dependiente where dependiente.socio = ?1");
        qr.setParameter(1, socio);
        List<Dependiente> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<Socio> BuscaSociosHabilitadosParaMensualidades(Cobrador cobrador, Categoria categoria, String idInicio, String idFin) throws Exception {

        Query qr = em.createQuery("FROM Socio AS s WHERE s.Cobrador.id = :idCobrador AND s.Categoria.id = :idCategoria "
                + "AND s.id BETWEEN :idInicio AND :idFin AND s.situacion = 'Activo' ");
        qr.setParameter("idCobrador", cobrador.getId());
        qr.setParameter("idCategoria", categoria.getId());
        qr.setParameter("idInicio", Integer.parseInt(idInicio));
        qr.setParameter("idFin", Integer.parseInt(idFin));
        List<Socio> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List<Socio> BuscaSociosxHuella() {

        Query qr = em.createQuery("from Socio s where s.tamano is not null");
        List<Socio> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List BuscaPorCategoriaSituacionConCelular(Categoria categoria, String situacion) {
        Query qr = em.createQuery("from Socio s where s.Categoria = ?1 and s.situacion = ?2 and s.celular <> ''");
        qr.setParameter(1, categoria);
        qr.setParameter(2, situacion);
        List<Socio> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }
}

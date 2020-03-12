/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Categoria;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Socio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.Tuple;

public class SocioDAO extends DaoGenerico {

    public SocioDAO() {

    }

    public List<Socio> FiltroInteligenteSocios(String texto) {

        Query qr = em.createNativeQuery("select * from tbsocio s where (select convert(s.id,char))\n"
                + "like ?1 or s.nombre like  ?2 or s.ci like ?3", Socio.class);
        qr.setParameter(1, "%" + texto + "%");
        qr.setParameter(2, "%" + texto + "%");
        qr.setParameter(3, "%" + texto + "%");
        List<Socio> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public List consultaSocios(List categorias, List situaciones) {

        Query qr = em.createNativeQuery("select s.id,s.nombre, s.situacion, c.definicion,\n"
                + "(select max(mm.fecha_pago) from tbmensualidades mm where mm.id_socio = m.id_socio)as ultimo_pago,\n"
                + " (select MAX(mmm.fecha_vencimiento) from tbmensualidades mmm where mmm.id_socio = m.id_socio and mmm.fecha_pago <> 'NULL') as ultimo_vencimiento\n"
                + "from tbsocio s\n"
                + "left join tbcategoria c on s.idcategoria = c.id\n"
                + "left join tbmensualidades m on m.id_socio = s.id\n"
                + "where s.situacion in ?2 and c.id in(?1) group by s.id order by ultimo_vencimiento desc, ultimo_pago desc,  c.definicion, s.id", Tuple.class);
        qr.setParameter(1, categorias);
        qr.setParameter(2, situaciones);
        List<Tuple> fromBD = qr.getResultList();
        List<Socio> toReturn = new ArrayList<>();
        for (Tuple tuple : fromBD) {
            Socio s = new Socio();
            s.setId((Integer) tuple.get(0));
            s.setNombre((String) tuple.get(1));
            s.setSituacion((String) tuple.get(2));
            s.setCategoria(new Categoria((String) tuple.get(3)));
            s.setUltimo_pago((Date) tuple.get(4));
            s.setVencimiento((Date) tuple.get(5));
            toReturn.add(s);
        }
        em.getTransaction().commit();
        em.close();

        return toReturn;
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
        qr.setParameter(1, "%" + CI.toString() + "%");
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

    public List<Dependiente> BuscaDependientes(Socio socio) {

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

    public List BuscaPorCategoriaSituacionConCelular(Categoria categoria, Cobrador cobrador, String situacion) {
        Query qr = em.createQuery("from Socio s where s.Categoria = ?1 and s.Cobrador = ?2 and s.situacion = ?3 and s.celular <> ''");
        qr.setParameter(1, categoria);
        qr.setParameter(2, cobrador);
        qr.setParameter(3, situacion);
        List<Socio> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public List BuscaSociosInactivosEnElPeriodo(Date inicio, Date fin) {
        Query qr = em.createQuery("select distinct(m.socio) from Mensualidades M \n"
                + "where m.fechaVencimiento between :inicio and :fin and M.pago = 'Pendiente de Pago'\n"
                + "and m.socio.situacion = 'Inactivo'");
        qr.setParameter("inicio", inicio);
        qr.setParameter("fin", fin);
        List<Socio> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

}

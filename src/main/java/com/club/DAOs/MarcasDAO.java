/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Funcionario;
import com.club.BEANS.Marcas;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
public class MarcasDAO extends DaoGenerico {

    public List<Marcas> BuscaPorFuncionario(Funcionario funcionario) {
        Query qr = em.createQuery("from Marcas m where m.funcionario = ?1");
        qr.setParameter(1, funcionario);
        List<Marcas> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public List<Marcas> BuscaPorFuncionarioAndFecha(Funcionario funcionario, Date fechaInicio, Date fechaFin) {
        Query qr = em.createQuery("from Marcas m where m.funcionario = ?1 and m.anulada = false and m.fecha between  ?2 and ?3 order by m.hora");
        qr.setParameter(1, funcionario);
        qr.setParameter(2, fechaInicio);
        qr.setParameter(3, fechaFin);
        List<Marcas> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

}

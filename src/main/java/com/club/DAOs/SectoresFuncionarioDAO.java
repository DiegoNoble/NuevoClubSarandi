/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Funcionario;
import com.club.BEANS.SectoresFuncionario;
import java.util.List;
import javax.persistence.Query;

public class SectoresFuncionarioDAO extends DaoGenerico {

    public SectoresFuncionarioDAO() {
    }

    public List<SectoresFuncionario> BuscaPorFuncionario(Funcionario funcionario) {

        Query qr = em.createQuery("from SectoresFuncionario s where s.funcionario = ?1");
        qr.setParameter(1, funcionario);
        List toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

}

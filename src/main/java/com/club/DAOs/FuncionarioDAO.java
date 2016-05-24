/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.DAOs;

import com.club.BEANS.Funcionario;
import java.util.List;
import javax.persistence.Query;

public class FuncionarioDAO extends DaoGenerico {

    public FuncionarioDAO() {

    }

    public List BuscaPorNombre(String name) {
        Query qr = em.createQuery("from Funcionario f where f.nombre like ?1");
        qr.setParameter(1, "%" + name + "%");
        List<Funcionario> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public List BuscaPorCI(String CI) {
        Query qr = em.createQuery("from Funcionario funcionario where funcionario.ci like ?1");
        qr.setParameter(1, "%" + CI + "%");
        List<Funcionario> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public Funcionario BuscaPorCIIgual(String CI) {
        Funcionario toReturn ;
        Query qr = em.createQuery("from Funcionario funcionario where funcionario.ci = ?1");
        qr.setParameter(1, CI);
        List<Funcionario> listToReturn = qr.getResultList();
        if (!listToReturn.isEmpty()) {
            toReturn = listToReturn.get(0);
        }else{
            toReturn = null;
        }

        em.getTransaction().commit();
        em.close();
        return toReturn;
    }

    public Funcionario BuscaPorCodigo(String codigo) {

        Query qr = em.createQuery("from Funcionario funcionario where funcionario.id = ?1");
        qr.setParameter(1, Integer.parseInt(codigo));
        Funcionario toReturn = (Funcionario) qr.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }

    public boolean VerificaCI(String CI) {
        boolean exists = false;
        Query qr = em.createQuery("from Funcionario funcionario where funcionario.ci = ?1");
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

  
    
    public List<Funcionario> BuscaFuncionariosxHuella() {

        Query qr = em.createQuery("from Funcionario s where s.tamano is not null");
        List<Funcionario> toReturn = qr.getResultList();
        em.getTransaction().commit();
        em.close();
        return toReturn;

    }
}

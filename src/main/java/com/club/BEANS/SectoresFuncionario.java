/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.BEANS;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "sectores_funcionario")

public class SectoresFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "SECTOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sectores sector;
    @JoinColumn(name = "FUNCIONARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Funcionario funcionario;
    @Column(name = "procentageSector")
    private Double procentageSector;

    public SectoresFuncionario() {
    }

    public SectoresFuncionario(Sectores sector, Funcionario funcionario, Double procentageSector) {
        this.sector = sector;
        this.funcionario = funcionario;
        this.procentageSector = procentageSector;
    }

    
    public SectoresFuncionario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sectores getSector() {
        return sector;
    }

    public void setSector(Sectores sector) {
        this.sector = sector;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Double getProcentageSector() {
        return procentageSector;
    }

    public void setProcentageSector(Double procentageSector) {
        this.procentageSector = procentageSector;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SectoresFuncionario other = (SectoresFuncionario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.BEANS;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "numeros")

public class Numeros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer nro1;
    private Integer nro2;
    private Boolean disponible;

    @JoinColumn(name = "campeconomica_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private CampEconomica campEconomica;

    public Numeros() {
    }

    public Numeros(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNro1() {
        return nro1;
    }

    public void setNro1(Integer nro1) {
        this.nro1 = nro1;
    }

    public Integer getNro2() {
        return nro2;
    }

    public void setNro2(Integer nro2) {
        this.nro2 = nro2;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public CampEconomica getCampEconomica() {
        return campEconomica;
    }

    public void setCampEconomica(CampEconomica campEconomica) {
        this.campEconomica = campEconomica;
    }

    

}

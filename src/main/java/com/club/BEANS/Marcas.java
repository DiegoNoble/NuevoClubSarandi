/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.BEANS;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Nullable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "tbmarcas")

public class Marcas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "hora")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date hora;

    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @ManyToOne(optional = false)
    private Funcionario funcionario;

    @Enumerated(EnumType.STRING)
    private TipoMarca tipoMarca;

    @Column(name = "origen", nullable = false, length = 10)
    private String origen = "Reloj";
    @Column(name = "anulada", nullable = false)
    private Boolean anulada = false;

    public Marcas() {
    }

    public Marcas(Integer id, Date hora, Date fecha, Funcionario funcionario) {
        this.id = id;
        this.hora = hora;
        this.fecha = fecha;
        this.funcionario = funcionario;
    }

    public Marcas(Date hora, Date fecha, Funcionario funcionario, TipoMarca tipoMarca) {
        this.hora = hora;
        this.fecha = fecha;
        this.funcionario = funcionario;
        this.tipoMarca = tipoMarca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public TipoMarca getTipoMarca() {
        return tipoMarca;
    }

    public void setTipoMarca(TipoMarca tipoMarca) {
        this.tipoMarca = tipoMarca;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Boolean getAnulada() {
        return anulada;
    }

    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }

}

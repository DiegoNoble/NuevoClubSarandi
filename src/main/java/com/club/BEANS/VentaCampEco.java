/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.BEANS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "venta_camp_economica")

public class VentaCampEco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date fechaVenta;

    @JoinColumn(name = "id_socio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Socio socio;

    @JoinColumn(name = "id_cobrador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cobrador cobrador;

    @JoinColumn(name = "id_numeros", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Numeros numeros;

    @JoinColumn(name = "campeconomica_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CampEconomica campEconomica;

    private Integer cantidadCuotas;
    private Double valor;

    public VentaCampEco() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Cobrador getCobrador() {
        return cobrador;
    }

    public void setCobrador(Cobrador cobrador) {
        this.cobrador = cobrador;
    }

    public Numeros getNumeros() {
        return numeros;
    }

    public void setNumeros(Numeros numeros) {
        this.numeros = numeros;
    }

    public Integer getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(Integer cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valorCuota) {
        this.valor = valorCuota;
    }

    public CampEconomica getCampEconomica() {
        return campEconomica;
    }

    public void setCampEconomica(CampEconomica campEconomica) {
        this.campEconomica = campEconomica;
    }

}

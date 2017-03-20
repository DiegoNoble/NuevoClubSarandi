/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.BEANS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "cuotas_camp_economica")

public class CuotaCampEconomica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double valor;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;

    private Boolean pago;

    @JoinColumn(name = "id_venta_camp_eco", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private VentaCampEco ventaCampEco;

    private Boolean enviado = false;
    private String situacionTalonCobrosYa;
    @Column(name = "nro_talon")
    private String nroTalonCobrosYa;
    @Column(name = "id_secreto")
    private String idSecreto;
    @Column(name = "url_pdf")
    private String urlPDF;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaHoraTransaccionCobrosYa;
    private Integer medioPagoId;
    private String medioPago;
    private int nroCuota;

    public CuotaCampEconomica() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public VentaCampEco getVentaCampEco() {
        return ventaCampEco;
    }

    public void setVentaCampEco(VentaCampEco ventaCampEco) {
        this.ventaCampEco = ventaCampEco;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public String getSituacionTalonCobrosYa() {
        return situacionTalonCobrosYa;
    }

    public void setSituacionTalonCobrosYa(String situacionTalonCobrosYa) {
        this.situacionTalonCobrosYa = situacionTalonCobrosYa;
    }

    public String getNroTalonCobrosYa() {
        return nroTalonCobrosYa;
    }

    public void setNroTalonCobrosYa(String nroTalonCobrosYa) {
        this.nroTalonCobrosYa = nroTalonCobrosYa;
    }

    public String getIdSecreto() {
        return idSecreto;
    }

    public void setIdSecreto(String idSecreto) {
        this.idSecreto = idSecreto;
    }

    public String getUrlPDF() {
        return urlPDF;
    }

    public void setUrlPDF(String urlPDF) {
        this.urlPDF = urlPDF;
    }

    public Date getFechaHoraTransaccionCobrosYa() {
        return fechaHoraTransaccionCobrosYa;
    }

    public void setFechaHoraTransaccionCobrosYa(Date fechaHoraTransaccionCobrosYa) {
        this.fechaHoraTransaccionCobrosYa = fechaHoraTransaccionCobrosYa;
    }

    public Integer getMedioPagoId() {
        return medioPagoId;
    }

    public void setMedioPagoId(Integer medioPagoId) {
        this.medioPagoId = medioPagoId;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public int getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(int nroCuota) {
        this.nroCuota = nroCuota;
    }

    
}

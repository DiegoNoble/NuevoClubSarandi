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
@Table(name = "tbmensualidades")

public class Mensualidades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "VALOR")
    private Double valor;
    @Column(name = "FECHA_VENCIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Column(name = "FECHA_PAGO")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "PAGO")
    private String pago;
    @Basic(optional = false)
    @Column(name = "LANZAMIENTO")
    private int lanzamiento;
    @JoinColumn(name = "ID_SOCIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Socio socio;
    @JoinColumn(name = "ID_COBRADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cobrador cobrador;
    private Boolean enviaTalonCobrosYa = false;
    private String situacionTalonCobrosYa;
    @Column(name = "nro_talon")
    private String nroTalonCobrosYa;
    @Column(name = "id_secreto")
    private String idSecreto;
    @Column(name = "url_pdf")
    private String urlPDF;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaHoraTransaccionCobrosYa;

    public Mensualidades() {
    }

    public Mensualidades(Integer id) {
        this.id = id;
    }

    public Mensualidades(Integer id, Double valor, Date fechaVencimiento, Date fechaPago, String pago, int lanzamiento, Socio tbsocio, Cobrador tbcobrador) {
        this.id = id;
        this.valor = valor;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaPago = fechaPago;
        this.pago = pago;
        this.lanzamiento = lanzamiento;
        this.socio = tbsocio;
        this.cobrador = tbcobrador;
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

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public int getLanzamiento() {
        return lanzamiento;
    }

    public void setLanzamiento(int lanzamiento) {
        this.lanzamiento = lanzamiento;
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

    public Boolean getEnviaTalonCobrosYa() {
        return enviaTalonCobrosYa;
    }

    public void setEnviaTalonCobrosYa(Boolean enviaTalonCobrosYa) {
        this.enviaTalonCobrosYa = enviaTalonCobrosYa;
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
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Mensualidades other = (Mensualidades) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
  
}

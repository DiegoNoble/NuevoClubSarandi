/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.BEANS;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
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
 * @author dnoble
 */
@Entity
@Table(name = "sms")

public class Sms implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "fechahoraemision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraemision;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "campanasms_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Campanasms campanasms;
    @JoinColumn(name = "socio_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Socio socio;
    private Boolean respondido;

    public Sms() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Campanasms getCampanasms() {
        return campanasms;
    }

    public void setCampanasms(Campanasms campanasms) {
        this.campanasms = campanasms;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Date getFechahoraemision() {
        return fechahoraemision;
    }

    public void setFechahoraemision(Date fechahoraemision) {
        this.fechahoraemision = fechahoraemision;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getRespondido() {
        return respondido;
    }

    public void setRespondido(Boolean respondido) {
        this.respondido = respondido;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Sms other = (Sms) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}

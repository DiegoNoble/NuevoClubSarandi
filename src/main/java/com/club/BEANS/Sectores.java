/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.BEANS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "tbsectores")

public class Sectores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE_SECTOR")
    private String nombreSector;
    @Column(name = "cod_ref")
    private String codRef;


    public Sectores() {
    }

    public Sectores(Integer id) {
        this.id = id;
    }

    public Sectores(Integer id, String nombreSector) {
        this.id = id;
        this.nombreSector = nombreSector;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreSector() {
        return nombreSector;
    }

    public void setNombreSector(String nombreSector) {
        this.nombreSector = nombreSector;
    }

    public String getCodRef() {
        return codRef;
    }

    public void setCodRef(String codRef) {
        this.codRef = codRef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sectores)) {
            return false;
        }
        Sectores other = (Sectores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreSector;
    }
    
}

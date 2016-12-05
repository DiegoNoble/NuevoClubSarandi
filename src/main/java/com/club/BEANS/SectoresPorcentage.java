/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.BEANS;

import java.io.Serializable;

/**
 *
 * @author Diego
 */
public class SectoresPorcentage implements Serializable {

    private static final long serialVersionUID = 1L;
    private Sectores sector;
    private Double porcentage;

    public SectoresPorcentage() {
    }

    public SectoresPorcentage(Sectores sector, Double porcentage) {
        this.sector = sector;
        this.porcentage = porcentage;
    }

    public Sectores getSector() {
        return sector;
    }

    public void setSector(Sectores sector) {
        this.sector = sector;
    }

    public Double getPorcentage() {
        return porcentage;
    }

    public void setPorcentage(Double porcentage) {
        this.porcentage = porcentage;
    }

}

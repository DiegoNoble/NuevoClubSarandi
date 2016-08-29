/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.BEANS;

/**
 *
 * @author Diego Noble
 */
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
@Table(name = "parametros")

public class Parametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "url_base_datos_remota")
    private String urlBaseDatosRemota;
    @Column(name = "usuario_base_remota")
    private String usuarioBD;
    @Column(name = "psw_base_remota")
    private String pswBD;
    @Column(name = "url_post_cobrosya")
    private String urlPostCobrosYa;
    @Column(name = "token_cobrosya")
    private String tokenCobrosYa;
    private String casilla_email;
    private String psw_email;
    private String usuario_SMS;
    private String psw_SMS;
    
    

    public Parametros() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlBaseDatosRemota() {
        return urlBaseDatosRemota;
    }

    public void setUrlBaseDatosRemota(String urlBaseDatosRemota) {
        this.urlBaseDatosRemota = urlBaseDatosRemota;
    }

    public String getUsuarioBD() {
        return usuarioBD;
    }

    public void setUsuarioBD(String usuarioBD) {
        this.usuarioBD = usuarioBD;
    }

    public String getPswBD() {
        return pswBD;
    }

    public void setPswBD(String pswBD) {
        this.pswBD = pswBD;
    }

    public String getUrlPostCobrosYa() {
        return urlPostCobrosYa;
    }

    public void setUrlPostCobrosYa(String urlPostCobrosYa) {
        this.urlPostCobrosYa = urlPostCobrosYa;
    }

    public String getTokenCobrosYa() {
        return tokenCobrosYa;
    }

    public void setTokenCobrosYa(String tokenCobrosYa) {
        this.tokenCobrosYa = tokenCobrosYa;
    }

    public String getCasilla_email() {
        return casilla_email;
    }

    public void setCasilla_email(String casilla_email) {
        this.casilla_email = casilla_email;
    }

    public String getPsw_email() {
        return psw_email;
    }

    public void setPsw_email(String psw_email) {
        this.psw_email = psw_email;
    }

    public String getUsuario_SMS() {
        return usuario_SMS;
    }

    public void setUsuario_SMS(String usuario_SMS) {
        this.usuario_SMS = usuario_SMS;
    }

    public String getPsw_SMS() {
        return psw_SMS;
    }

    public void setPsw_SMS(String psw_SMS) {
        this.psw_SMS = psw_SMS;
    }

   

}

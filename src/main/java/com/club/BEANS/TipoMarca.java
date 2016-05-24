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
public enum TipoMarca {
    ENTRADA("ENTRADA"), SALIDA("SALIDA");
    
     private final String string;

    private TipoMarca(String name) {
        string = name;
    }

    @Override
    public String toString() {
        return string;
    }
}

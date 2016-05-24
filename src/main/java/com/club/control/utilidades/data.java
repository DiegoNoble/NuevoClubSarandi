package com.club.control.utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class data {

    public String mes, mes_del_ano, dia, ano, dia_semana, hora;
    SimpleDateFormat horaformatada = new SimpleDateFormat("HH:mm:ss");

    public void leeHora() {

        Date horaActual = new Date();
        hora = horaformatada.format(horaActual);

    }

    public void le_data() {

        Date data = new Date();
        mes = "" + data.getMonth();// empieza contando del 0
        mes_del_ano = "" + data.getMonth();
        dia = "" + data.getDate();
        ano = "" + (1900 + data.getYear()); //los computadores cuentan los a~nos a partir del 1900
        dia_semana = "" + data.getDay();

        switch (data.getDay()) {
            case 0:
                dia_semana = "Domingo";
                break;
            case 1:
                dia_semana = "Lunes";
                break;
            case 2:
                dia_semana = "Martes";
                break;
            case 3:
                dia_semana = "Miercoles";
                break;
            case 4:
                dia_semana = "Jueves";
                break;
            case 5:
                dia_semana = "Viernes";
                break;
            case 6:
                dia_semana = "Sabado";
                break;
        }

        switch (data.getMonth()) {

            case 0:
                mes_del_ano = "Enero";
                break;
            case 1:
                mes_del_ano = "Febrero";
                break;
            case 2:
                mes_del_ano = "Marzo";
                break;
            case 3:
                mes_del_ano = "Abril";
                break;
            case 4:
                mes_del_ano = "Mayo";
                break;
            case 5:
                mes_del_ano = "Junio";
                break;
            case 6:
                mes_del_ano = "Julio";
                break;
            case 7:
                mes_del_ano = "Agosto";
                break;
            case 8:
                mes_del_ano = "Septiembre";
                break;
            case 9:
                mes_del_ano = "Octubre";
                break;
            case 10:
                mes_del_ano = "Noviembre";
                break;
            case 11:
                mes_del_ano = "Diciembre";
                break;

        }

        switch (data.getMonth()) {

            case 0:
                mes = "01";
                break;
            case 1:
                mes = "02";
                break;
            case 2:
                mes = "03";
                break;
            case 3:
                mes = "04";
                break;
            case 4:
                mes = "05";
                break;
            case 5:
                mes = "06";
                break;
            case 6:
                mes = "07";
                break;
            case 7:
                mes = "08";
                break;
            case 8:
                mes = "09";
                break;
            case 9:
                mes = "10";
                break;
            case 10:
                mes = "11";
                break;
            case 11:
                mes = "12";
                break;
        }
        /*switch (data.getDay()) {
        case 1:
        dia = "01";
        break;
        case 2:
        dia = "02";
        break;
        case 3:
        dia = "03";
        break;
        case 4:
        dia = "04";
        break;
        case 5:
        dia = "05";
        break;
        case 6:
        dia = "06";
        break;
        case 7:
        dia = "07";
        break;
        case 8:
        dia = "08";
        break;
        case 9:
        dia = "09";
        break;
        case 10:
        dia = "10";
        break;
        case 11:
        dia = "11";
        break;
        case 12:
        dia = "12";
        break;
        case 13:
        dia = "13";
        break;
        case 14:
        dia = "14";
        break;
        case 15:
        dia = "15";
        break;
        case 16:
        dia = "16";
        break;
        case 17:
        dia = "17";
        break;
        case 18:
        dia = "18";
        break;
        case 19:
        dia = "19";
        break;
        case 20:
        dia = "20";
        break;







        }*/

    }

    public static String getDataTelaToBD(String x) { //Este funcion modifica la fecha ingresada en el formato BR a formato USA compatible con el BD
        String dia, mes, ano = "";
        String data = "";
        dia = x.substring(0, 2);
        mes = x.substring(3, 5);
        ano = x.substring(6, 10);
        data = ano + "-" + mes + "-" + dia;
        return data;
    }
}

package com.club.control.acceso;

import java.sql.*;
import javax.swing.*;

public class conexion {

    final private String driver = "com.mysql.jdbc.Driver";
    final private String url = "jdbc:mysql://localhost/club_sarandi";
    final private String usuario = "root";
    final private String pass = "dieguito";
    private Connection conexion;
    public Statement statement;
    public ResultSet resultSet;

    public boolean conecta() {

        boolean result = true;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, pass);
            //JOptionPane.showMessageDialog(null, "contecto");
        } catch (ClassNotFoundException Driver) {
            JOptionPane.showMessageDialog(null, "Driver no localizado" + Driver);
            result = false;
        } catch (SQLException Fonte) {
            JOptionPane.showMessageDialog(null, "Error en la conexion" + Fonte);
            result = false;
        }
        return result;
    }

    public void desconecta() {

        boolean result = true;
        try {
            conexion.close();
            JOptionPane.showMessageDialog(null, "Banco cerrado");
        } catch (SQLException cierra) {
            JOptionPane.showMessageDialog(null, "No fue posible la desconexion" + cierra);
            result = false;
        }
    }

    public void ejecutaSQL(String sql) {
        try {
            statement = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sql);
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado" + sqlex + "El SQl pasado fue" + sql);
        }
    }
}

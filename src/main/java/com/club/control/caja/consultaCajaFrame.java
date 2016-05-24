package com.club.control.caja;

import com.club.control.acceso.conexion;
import com.club.control.utilidades.Constantes;
import com.club.control.utilidades.data;
import java.io.InputStream;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class consultaCajaFrame extends javax.swing.JInternalFrame {

    conexion conexionCaja, conexionRubro, conexionSectores;
    conexion conexionInforme = new conexion();
    Constantes constanteFormatoCamposFecha;

    public consultaCajaFrame() {

        initComponents();

        conexionCaja = new conexion();
        conexionCaja.conecta();
        conexionCaja.ejecutaSQL("select * from tbcaja");

        constanteFormatoCamposFecha = new Constantes();
        constanteFormatoCamposFecha.formataciondeCamposFecha(ftxtFechaFinal);
        constanteFormatoCamposFecha.formataciondeCamposFecha(ftxtFechaInicio);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaactual = formato.format(new Date());
        ftxtFechaInicio.setText(fechaactual);
        ftxtFechaFinal.setText(fechaactual);

        muestraContenidoTabla();

    }

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void muestraContenidoTabla() {

        conexionCaja.ejecutaSQL("Select * from ((tbcaja INNER JOIN tbrubros ON tbcaja.rubro = tbrubros.id) "
                + "INNER JOIN tbsectores ON tbcaja.sector = tbsectores.id) "
                + "where tbcaja.fecha_movimiento between'" + data.getDataTelaToBD(ftxtFechaInicio.getText()) + "' "
                + "and '" + data.getDataTelaToBD(ftxtFechaFinal.getText()) + "' order by tbcaja.fecha_movimiento");

        TableCellRenderer centerRenderer = new CenterRenderer();
        TableColumn column = tblCaja.getColumnModel().getColumn(0); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column1 = tblCaja.getColumnModel().getColumn(1);
        TableColumn column2 = tblCaja.getColumnModel().getColumn(2);
        TableColumn column3 = tblCaja.getColumnModel().getColumn(3);
        TableColumn column4 = tblCaja.getColumnModel().getColumn(4);
        TableColumn column5 = tblCaja.getColumnModel().getColumn(5);
        TableColumn column6 = tblCaja.getColumnModel().getColumn(6);

        column.setCellRenderer(centerRenderer);
        column1.setCellRenderer(centerRenderer);
        column2.setCellRenderer(centerRenderer);
        column3.setCellRenderer(centerRenderer);
        column4.setCellRenderer(centerRenderer);
        column5.setCellRenderer(centerRenderer);
        column6.setCellRenderer(centerRenderer);

        DefaultTableModel modelo = (DefaultTableModel) tblCaja.getModel();
        modelo.setNumRows(0);

        tblCaja.getColumn("Código").setPreferredWidth(5); //------> Ajusta el tamaño de las columnas
        tblCaja.getColumn("Fecha del Movimiento").setPreferredWidth(100);
        tblCaja.getColumn("Rubro").setPreferredWidth(150);
        tblCaja.getColumn("Concepto").setPreferredWidth(150);
        tblCaja.getColumn("Entrada $").setPreferredWidth(15);
        tblCaja.getColumn("Salida $").setPreferredWidth(15);
        try {
            while (conexionCaja.resultSet.next()) {

                Object[] linha = new Object[7];
                linha[0] = conexionCaja.resultSet.getInt("id");
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaVencimiento = conexionCaja.resultSet.getDate("fecha_movimiento");
                if (fechaVencimiento != null) {
                    linha[1] = formato.format(fechaVencimiento);
                }

                linha[2] = conexionCaja.resultSet.getString("nombre_rubro");
                linha[3] = conexionCaja.resultSet.getString("nombre_sector");
                linha[4] = conexionCaja.resultSet.getString("Concepto");
                linha[5] = conexionCaja.resultSet.getDouble("Entrada");
                linha[6] = conexionCaja.resultSet.getDouble("Salida");

                modelo.addRow(linha);

            }

            conexionCaja.resultSet.first();

            conexionCaja.ejecutaSQL("select sum(entrada) from tbcaja where fecha_movimiento "
                    + "between '" + data.getDataTelaToBD(ftxtFechaInicio.getText()) + "' "
                    + "and '" + data.getDataTelaToBD(ftxtFechaFinal.getText()) + "'");
            conexionCaja.resultSet.next();
            String totalEntradas = conexionCaja.resultSet.getString("sum(entrada)");
            txtEntrada1.setText(totalEntradas);

            conexionCaja.ejecutaSQL("select sum(salida) from tbcaja where fecha_movimiento "
                    + "between '" + data.getDataTelaToBD(ftxtFechaInicio.getText()) + "' "
                    + "and '" + data.getDataTelaToBD(ftxtFechaFinal.getText()) + "'");
            conexionCaja.resultSet.next();
            String totalSalidas = conexionCaja.resultSet.getString("sum(salida)");
            txtSalida.setText(totalSalidas);

            conexionCaja.ejecutaSQL("select sum(entrada)-sum(salida) from tbcaja where fecha_movimiento "
                    + "between '" + data.getDataTelaToBD(ftxtFechaInicio.getText()) + "' "
                    + "and '" + data.getDataTelaToBD(ftxtFechaFinal.getText()) + "'");
            conexionCaja.resultSet.next();
            String resumenTotal = conexionCaja.resultSet.getString(1);
            txtTotal.setText(resumenTotal);

        } catch (SQLException error) {

            JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado" + error);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaja = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        ftxtFechaInicio = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        ftxtFechaFinal = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCaja1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSalida = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtEntrada1 = new javax.swing.JTextField();
        btnInformeResumen = new javax.swing.JButton();
        btnInformeGastos = new javax.swing.JButton();
        btnInformeIngresos = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consulta Movimientos Caja"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscar, gridBagConstraints);

        tblCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Fecha del Movimiento", "Rubro", "Sector", "Concepto", "Entrada $", "Salida $"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCaja);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jLabel9.setText("al"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel9, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(ftxtFechaInicio, gridBagConstraints);

        jLabel17.setText("Ingrese el período del filtro"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel17, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(ftxtFechaFinal, gridBagConstraints);

        tblCaja1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Fecha del Movimiento", "Rubro", "Sector", "Concepto", "Entrada $", "Salida $"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblCaja1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel2.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel5, gridBagConstraints);

        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Total Entrada $:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel2, gridBagConstraints);

        txtSalida.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSalida, gridBagConstraints);

        jLabel14.setText("Total Salida $:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel14, gridBagConstraints);

        jLabel6.setText("Resultado del Pedíodo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel6, gridBagConstraints);

        txtTotal.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtTotal, gridBagConstraints);

        txtEntrada1.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtEntrada1, gridBagConstraints);

        jTabbedPane1.addTab("Resumen del día o Período", jPanel4);

        btnInformeResumen.setText("Generar informe con resumen de movimientos");
        btnInformeResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeResumenActionPerformed(evt);
            }
        });

        btnInformeGastos.setText("Generar informe de gastos agrupados por Rubros");
        btnInformeGastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeGastosActionPerformed(evt);
            }
        });

        btnInformeIngresos.setText("Generar informe de Ingresos agrupados por Rubros");
        btnInformeIngresos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeIngresosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(125, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInformeGastos)
                    .addComponent(btnInformeResumen)
                    .addComponent(btnInformeIngresos))
                .addGap(65, 65, 65)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnInformeGastos, btnInformeResumen});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnInformeResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnInformeGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInformeIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        muestraContenidoTabla();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnInformeResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeResumenActionPerformed

        String fecha1 = ftxtFechaInicio.getText();
        String fecha2 = ftxtFechaFinal.getText();

        try {

            HashMap parametros = new HashMap();
            parametros.clear();
            
            conexionCaja.ejecutaSQL("select sum(entrada)-sum(salida) from tbcaja where fecha_movimiento < '"+data.getDataTelaToBD(ftxtFechaInicio.getText())+"'");
            conexionCaja.resultSet.next();
            double saldoAnterior = conexionCaja.resultSet.getDouble(1);
            
            conexionCaja.ejecutaSQL("select sum(entrada)-sum(salida) from tbcaja where fecha_movimiento <= '"+data.getDataTelaToBD(ftxtFechaFinal.getText())+"'");
            conexionCaja.resultSet.next();
            double saldoFinal = conexionCaja.resultSet.getDouble(1);

            parametros.put("saldo_anterior", saldoAnterior);
            parametros.put("saldo_final", saldoFinal);
            parametros.put("fecha1", fecha1);
            parametros.put("fecha2", fecha2);

            conexionInforme.conecta();
            conexionInforme.ejecutaSQL("SELECT tbcaja.`ID` AS tbcaja_ID, tbcaja.`CONCEPTO` AS tbcaja_CONCEPTO, tbcaja.`FECHA_MOVIMIENTO` AS tbcaja_FECHA_MOVIMIENTO,"
                    + "tbcaja.`ENTRADA` AS tbcaja_ENTRADA, tbcaja.`SALIDA` AS tbcaja_SALIDA,tbsectores.`NOMBRE_SECTOR` AS tbsectores_NOMBRE_SECTOR, "
                    + "tbrubros.`NOMBRE_RUBRO` AS tbrubros_NOMBRE_RUBRO FROM `tbsectores` tbsectores INNER JOIN `tbcaja` tbcaja ON tbsectores.`ID` = tbcaja.`SECTOR` "
                    + "INNER JOIN `tbrubros` tbrubros ON tbcaja.`RUBRO` = tbrubros.`ID` "
                    + "where fecha_movimiento between '" + data.getDataTelaToBD(ftxtFechaInicio.getText()) + "' "
                    + "and '" + data.getDataTelaToBD(ftxtFechaFinal.getText()) + "'");

            JRResultSetDataSource jrRS = new JRResultSetDataSource(conexionInforme.resultSet);
            InputStream resource = getClass().getResourceAsStream("/com/club/control/caja/informeCaja.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, jrRS);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException ex) {
            Logger.getLogger(consultaCajaFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(consultaCajaFrame.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnInformeResumenActionPerformed

    private void btnInformeGastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeGastosActionPerformed

        String fecha1 = ftxtFechaInicio.getText();
        String fecha2 = ftxtFechaFinal.getText();
        try {
            conexionInforme.conecta();
            conexionInforme.ejecutaSQL("SELECT sum(salida), tbcaja.`rubro`, tbcaja.`sector`, "
                    + "tbrubros.`ID` AS tbrubros_ID, tbrubros.`NOMBRE_RUBRO` AS tbrubros_NOMBRE_RUBRO, "
                    + "tbsectores.`ID` AS tbsectores_ID, tbsectores.`NOMBRE_SECTOR` AS tbsectores_NOMBRE_SECTOR "
                    + "FROM `tbrubros` tbrubros INNER JOIN `tbcaja` tbcaja ON tbrubros.`ID` = tbcaja.`rubro` "
                    + "INNER JOIN `tbsectores` tbsectores ON tbcaja.`sector` = tbsectores.`ID` "
                    + "WHERE (salida!= 0) and (fecha_movimiento between '" + data.getDataTelaToBD(ftxtFechaInicio.getText()) + "' "
                    + "and '" + data.getDataTelaToBD(ftxtFechaFinal.getText()) + "') GROUP BY rubro,sector");

            HashMap parametros = new HashMap();
            parametros.clear();
            parametros.put("fecha1", fecha1);
            parametros.put("fecha2", fecha2);

            JRResultSetDataSource jrRS = new JRResultSetDataSource(conexionInforme.resultSet);
            InputStream resource = getClass().getResourceAsStream("/com/club/control/caja/gastosPorRubros.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, jrRS);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            Logger.getLogger(consultaCajaFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnInformeGastosActionPerformed

    private void btnInformeIngresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeIngresosActionPerformed

        String fecha1 = ftxtFechaInicio.getText();
        String fecha2 = ftxtFechaFinal.getText();
        try {
            conexionInforme.conecta();
            conexionInforme.ejecutaSQL("SELECT sum(entrada), tbcaja.`rubro`, tbcaja.`sector`, "
                    + "tbrubros.`ID` AS tbrubros_ID, tbrubros.`NOMBRE_RUBRO` AS tbrubros_NOMBRE_RUBRO, "
                    + "tbsectores.`ID` AS tbsectores_ID, tbsectores.`NOMBRE_SECTOR` AS tbsectores_NOMBRE_SECTOR "
                    + "FROM `tbrubros` tbrubros INNER JOIN `tbcaja` tbcaja ON tbrubros.`ID` = tbcaja.`rubro` "
                    + "INNER JOIN `tbsectores` tbsectores ON tbcaja.`sector` = tbsectores.`ID` "
                    + "WHERE (entrada!= 0) and (fecha_movimiento between '" + data.getDataTelaToBD(ftxtFechaInicio.getText()) + "' "
                    + "and '" + data.getDataTelaToBD(ftxtFechaFinal.getText()) + "') GROUP BY rubro,sector");

            HashMap parametros = new HashMap();
            parametros.clear();
            parametros.put("fecha1", fecha1);
            parametros.put("fecha2", fecha2);

            JRResultSetDataSource jrRS = new JRResultSetDataSource(conexionInforme.resultSet);
            InputStream resource = getClass().getResourceAsStream("/com/club/control/caja/ingresosPorRubros.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, jrRS);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            Logger.getLogger(consultaCajaFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnInformeIngresosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnInformeGastos;
    private javax.swing.JButton btnInformeIngresos;
    private javax.swing.JButton btnInformeResumen;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFormattedTextField ftxtFechaFinal;
    private javax.swing.JFormattedTextField ftxtFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblCaja;
    private javax.swing.JTable tblCaja1;
    private javax.swing.JTextField txtEntrada1;
    private javax.swing.JTextField txtSalida;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}

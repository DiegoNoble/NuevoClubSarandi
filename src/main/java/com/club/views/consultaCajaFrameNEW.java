package com.club.views;

import com.club.BEANS.Caja;
import com.club.DAOs.CajaDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.modelos.CajaTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class consultaCajaFrameNEW extends javax.swing.JInternalFrame {

    CajaDAO cajaDAO;
    List<Caja> listMovCaja;
    CajaTableModel modelo;

    public consultaCajaFrameNEW() {

        initComponents();
        dpDesde.setDate(new Date());
        dpHasta.setDate(new Date());

        listMovCaja = new ArrayList<>();
        cajaDAO = new CajaDAO();
        
        defineModelo();
        muestraContenidoTabla();

    }

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    private void defineModelo() {
        try {
            ((DefaultTableCellRenderer) tblCaja.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            listMovCaja = new ArrayList<>();
            modelo = new CajaTableModel(listMovCaja);
            tblCaja.setModel(modelo);
            tblCaja.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
            int[] anchos = {50, 100, 80, 300, 20, 20, 20};

            for (int i = 0; i < tblCaja.getColumnCount(); i++) {

                tblCaja.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

            }
        } catch (Exception error) {

            JOptionPane.showMessageDialog(null, "No fue posible ejecutar la consulta" + error);
            error.printStackTrace();
        }
    }

    private void muestraContenidoTabla() {
        try {
            listMovCaja.clear();
            listMovCaja.addAll(cajaDAO.buscaMovEntreFechas(dpDesde.getDate(), dpHasta.getDate()));
            modelo.fireTableDataChanged();

            Double totalEntrada = 0.0;
            Double totalSalida = 0.0;
            for (Caja movCaja : listMovCaja) {
                if (movCaja.getEntrada() == null) {
                    movCaja.setEntrada(0.0);
                } else if (movCaja.getSalida() == null) {
                    movCaja.setSalida(0.0);
                }

                totalEntrada = totalEntrada + movCaja.getEntrada();
                totalSalida = totalSalida + movCaja.getSalida();

            }
            Double resultado = totalEntrada - totalSalida;
            txtEntrada1.setText(totalEntrada.toString());
            txtSalida.setText(totalSalida.toString());
            txtTotal.setText(resultado.toString());

        } catch (Exception error) {

            JOptionPane.showMessageDialog(null, "No fue posible ejecutar la consulta" + error);
            error.printStackTrace();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaja = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        dpDesde = new org.jdesktop.swingx.JXDatePicker();
        dpHasta = new org.jdesktop.swingx.JXDatePicker();
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
        btnInformeResumen1 = new org.jasper.viewer.components.JasperRunnerButton();
        btnInformeGastos1 = new org.jasper.viewer.components.JasperRunnerButton();
        btnInformeIngresos1 = new org.jasper.viewer.components.JasperRunnerButton();

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

        tblCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblCaja);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(btnBuscar, gridBagConstraints);

        jLabel9.setText("al"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jLabel9, gridBagConstraints);

        jLabel17.setText("Ingrese el período del filtro"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jLabel17, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(dpDesde, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(dpHasta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        jPanel2.add(jPanel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel5, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jTabbedPane1, gridBagConstraints);

        btnInformeResumen1.setText("Generar informe con resumen de movimientos");
        btnInformeResumen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeResumen1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnInformeResumen1, gridBagConstraints);

        btnInformeGastos1.setText("Generar informe de gastos agrupados por Rubros");
        btnInformeGastos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeGastos1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnInformeGastos1, gridBagConstraints);

        btnInformeIngresos1.setText("Generar informe de Ingresos agrupados por Rubros");
        btnInformeIngresos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeIngresos1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnInformeIngresos1, gridBagConstraints);

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

    private void btnInformeResumen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeResumen1ActionPerformed
        HashMap parametros = new HashMap();
        parametros.clear();

        Double saldoAnterior = cajaDAO.buscaSaldoAnterior(dpDesde.getDate());
        Double saldoFinal = cajaDAO.buscaSaldoDelDia(dpDesde.getDate());
        parametros.put("saldo_anterior", saldoAnterior);
        parametros.put("saldo_final", saldoFinal);
        parametros.put("fecha1", dpDesde.getDate());
        parametros.put("fecha2", dpHasta.getDate());

        btnInformeResumen1.setReportParameters(parametros);
        btnInformeResumen1.setReportURL("/Reportes/informeCaja_NEW.jasper");


    }//GEN-LAST:event_btnInformeResumen1ActionPerformed

    private void btnInformeGastos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeGastos1ActionPerformed

        HashMap parametros = new HashMap();
        parametros.clear();
        parametros.put("fecha1", dpDesde.getDate());
        parametros.put("fecha2", dpHasta.getDate());

        btnInformeGastos1.setReportParameters(parametros);
        btnInformeGastos1.setReportURL("/Reportes/gastosPorRubros.jasper");

    }//GEN-LAST:event_btnInformeGastos1ActionPerformed

    private void btnInformeIngresos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeIngresos1ActionPerformed

        HashMap parametros = new HashMap();
        parametros.clear();
        parametros.put("fecha1", dpDesde.getDate());
        parametros.put("fecha2", dpHasta.getDate());

        btnInformeIngresos1.setReportParameters(parametros);
        btnInformeIngresos1.setReportURL("/Reportes/ingresosPorRubros.jasper");

    }//GEN-LAST:event_btnInformeIngresos1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private org.jasper.viewer.components.JasperRunnerButton btnInformeGastos1;
    private org.jasper.viewer.components.JasperRunnerButton btnInformeIngresos1;
    private org.jasper.viewer.components.JasperRunnerButton btnInformeResumen1;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.jdesktop.swingx.JXDatePicker dpDesde;
    private org.jdesktop.swingx.JXDatePicker dpHasta;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblCaja;
    private javax.swing.JTextField txtEntrada1;
    private javax.swing.JTextField txtSalida;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}

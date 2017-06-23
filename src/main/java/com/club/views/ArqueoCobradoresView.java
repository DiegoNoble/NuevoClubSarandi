package com.club.views;

import com.club.BEANS.CcCobrador;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Socio;
import com.club.DAOs.CcCobradorDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.MensualidadesAnuladasDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.Renderers.TableRendererColor;
import com.club.Renderers.TableRendererColorSituacion;
import com.club.control.utilidades.LeeProperties;
import com.club.tableModels.MensualidadesTableModel;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ArqueoCobradoresView extends javax.swing.JInternalFrame {

    MensualidadesDAO mensualidadesDAO;
    CobradorDAO cobradorDAO;

    MensualidadesAnuladasDAO mensualidadesAnuladasDAO;
    CcCobradorDAO ccCobradorDAO;
    Socio socioSeleccionado;
    CcCobrador credito;
    MensualidadesTableModel tblModelMensualidades;
    ListSelectionModel listModelSocios;
    ListSelectionModel listModelMensualidades;
    Mensualidades mensualidadSeleccionada;
    List<Mensualidades> listMensualidades;
    LeeProperties props = new LeeProperties();

    public ArqueoCobradoresView() {

        initComponents();
        listMensualidades = new ArrayList<>();
        //dpVencimiento.setFormats("15/MM/yyyy");
        dpHasta.setDate(new Date());
        completaCombos();
        defineModelo();
        buscarRecibos();
        Calendar venc = Calendar.getInstance();
        venc.set(Calendar.DAY_OF_MONTH, 15);
        dpDesde.setDate(venc.getTime());
        dpHasta.setDate(venc.getTime());

    }

    void completaCombos() {
        cobradorDAO = new CobradorDAO();
        List<Cobrador> cobradores = cobradorDAO.BuscaTodos(Cobrador.class);
        for (Cobrador cobrador : cobradores) {
            cbCobrador.addItem(cobrador);
        }

    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblMensualidades.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModelMensualidades = new MensualidadesTableModel(listMensualidades);
        tblMensualidades.setModel(tblModelMensualidades);
        tblMensualidades.getColumn("Vencimiento").setCellRenderer(new MeDateCellRenderer());
        //tblMensualidades.getColumn("Pago").setCellRenderer(new MeDateCellRenderer());
        tblMensualidades.getColumn("Pago").setCellRenderer(new TableRendererColor(5));
        int[] anchos = {1, 170, 5, 20, 10, 150, 20, 20, 5};

        for (int i = 0; i < tblMensualidades.getColumnCount(); i++) {

            tblMensualidades.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        tblMensualidades.getColumn("Situación").setMaxWidth(0);
        tblMensualidades.getColumn("Situación").setMinWidth(0);
        tblMensualidades.getColumn("Situación").setPreferredWidth(0);
        tblMensualidades.getColumn("Situación").setWidth(0);
        
        

    }

    private void buscarRecibos() {
        mensualidadesDAO = new MensualidadesDAO();
        listMensualidades.clear();

        if (cbSituacion.getSelectedItem().toString().equals("Todos")) {
            listMensualidades.addAll(mensualidadesDAO.BuscaPorCobradorVencimiento((Cobrador) cbCobrador.getSelectedItem(), dpDesde.getDate(), dpHasta.getDate()));
        } else {

            listMensualidades.addAll(mensualidadesDAO.BuscaPorCobradorSituacionVencimiento((Cobrador) cbCobrador.getSelectedItem(),
                    cbSituacion.getSelectedItem().toString(), dpDesde.getDate(), dpHasta.getDate()));
        }
        tblModelMensualidades.fireTableDataChanged();
        Integer cantTotal = 0;
        Integer cantPagos = 0;
        Integer cantPendientes = 0;
        Double total = 0.0;
        Double pagos = 0.0;
        Double pendientes = 0.0;
        for (Mensualidades mensualidad : listMensualidades) {
            cantTotal = cantTotal + 1;
            total = total + mensualidad.getValor();
            if (mensualidad.getPago().equals("Pago")) {
                cantPagos = cantPagos + 1;
                pagos = pagos + mensualidad.getValor();
            } else {
                cantPendientes = cantPendientes + 1;
                pendientes = pendientes + mensualidad.getValor();
            }
        }
        ftxtCant.setValue(cantTotal);
        ftxtCantPagos.setValue(cantPagos);
        ftxtCantPendientes.setValue(cantPendientes);
        ftxtValorPagos.setValue(pagos);
        ftxtValorPendientes.setValue(pendientes);
        ftxtValor.setValue(total);
    }

    
    public void informeRecibosPendientes() {
        try {
            HashMap parametros = new HashMap();
            parametros.clear();
            parametros.put("Cobrador", ((Cobrador)cbCobrador.getSelectedItem()).getId());
            
            Connection conexion = DriverManager.getConnection(props.getUrl(), props.getUsr(), props.getPsw());

            InputStream resource = getClass().getClassLoader().getResourceAsStream("Reportes/recibos_pendientes_por_cobrador.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, conexion);
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! "+e,"Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        cbSituacion = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cbCobrador = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        dpHasta = new org.jdesktop.swingx.JXDatePicker();
        jLabel8 = new javax.swing.JLabel();
        dpDesde = new org.jdesktop.swingx.JXDatePicker();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMensualidades = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        ftxtValor = new javax.swing.JFormattedTextField();
        jasperRunnerButton1 = new org.jasper.viewer.components.JasperRunnerButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ftxtValorPagos = new javax.swing.JFormattedTextField();
        ftxtValorPendientes = new javax.swing.JFormattedTextField();
        ftxtCantPendientes = new javax.swing.JFormattedTextField();
        ftxtCantPagos = new javax.swing.JFormattedTextField();
        ftxtCant = new javax.swing.JFormattedTextField();
        btnBuscar1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Situación recibo"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscar, gridBagConstraints);

        cbSituacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pendiente de Pago", "Pago", "Todos" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel2.add(cbSituacion, gridBagConstraints);

        jLabel4.setText("hasta"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel2.add(cbCobrador, gridBagConstraints);

        jLabel5.setText("Cobrador"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpHasta, gridBagConstraints);

        jLabel8.setText("Vencimiento desde"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpDesde, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consulta de Mensualidades"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        tblMensualidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblMensualidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMensualidadesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMensualidades);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel6.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel6, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel2.setText("Pagos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel2, gridBagConstraints);

        ftxtValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$ #,##0.00"))));
        ftxtValor.setEnabled(false);
        ftxtValor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(ftxtValor, gridBagConstraints);

        jasperRunnerButton1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jasperRunnerButton1.setForeground(new java.awt.Color(204, 0, 51));
        jasperRunnerButton1.setText("Imprimir listado");
        jasperRunnerButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jasperRunnerButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jasperRunnerButton1, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel6.setText("Importe Total ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel7.setText("Pendientes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel7, gridBagConstraints);

        ftxtValorPagos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$ #,##0.00"))));
        ftxtValorPagos.setEnabled(false);
        ftxtValorPagos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(ftxtValorPagos, gridBagConstraints);

        ftxtValorPendientes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$ #,##0.00"))));
        ftxtValorPendientes.setEnabled(false);
        ftxtValorPendientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(ftxtValorPendientes, gridBagConstraints);

        ftxtCantPendientes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftxtCantPendientes.setEnabled(false);
        ftxtCantPendientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(ftxtCantPendientes, gridBagConstraints);

        ftxtCantPagos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftxtCantPagos.setEnabled(false);
        ftxtCantPagos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(ftxtCantPagos, gridBagConstraints);

        ftxtCant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftxtCant.setEnabled(false);
        ftxtCant.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(ftxtCant, gridBagConstraints);

        btnBuscar1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBuscar1.setText("Informe recibos pendientes"); // NOI18N
        btnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel5.add(btnBuscar1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel5, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        buscarRecibos();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblMensualidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMensualidadesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMensualidadesMouseClicked

    private void jasperRunnerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jasperRunnerButton1ActionPerformed

        HashMap parametros = new HashMap();
        parametros.put("desde", dpDesde.getDate());
        parametros.put("hasta", dpHasta.getDate());
        List situaciones = new ArrayList();
        if (cbSituacion.getSelectedItem().toString().equals("Todos")) {
            situaciones.add("Pago");
            situaciones.add("Pendiente de Pago");
        } else {
            situaciones.add(cbSituacion.getSelectedItem());
        }
        parametros.put("situaciones", situaciones);
        parametros.put("cobrador", ((Cobrador) cbCobrador.getSelectedItem()).getId());

        jasperRunnerButton1.setDatabaseDriver(props.getDriver());
        jasperRunnerButton1.setDatabasePassword(props.getPsw());
        jasperRunnerButton1.setDatabaseURL(props.getUrl());
        jasperRunnerButton1.setDatabaseUser(props.getUsr());
        jasperRunnerButton1.setReportParameters(parametros);
        jasperRunnerButton1.setReportURL("/Reportes/SociosMensualidades.jasper");

    }//GEN-LAST:event_jasperRunnerButton1ActionPerformed

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed

         informeRecibosPendientes();
    }//GEN-LAST:event_btnBuscar1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscar1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCobrador;
    private javax.swing.JComboBox cbSituacion;
    private org.jdesktop.swingx.JXDatePicker dpDesde;
    private org.jdesktop.swingx.JXDatePicker dpHasta;
    private javax.swing.JFormattedTextField ftxtCant;
    private javax.swing.JFormattedTextField ftxtCantPagos;
    private javax.swing.JFormattedTextField ftxtCantPendientes;
    private javax.swing.JFormattedTextField ftxtValor;
    private javax.swing.JFormattedTextField ftxtValorPagos;
    private javax.swing.JFormattedTextField ftxtValorPendientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jasper.viewer.components.JasperRunnerButton jasperRunnerButton1;
    private javax.swing.JTable tblMensualidades;
    // End of variables declaration//GEN-END:variables
}

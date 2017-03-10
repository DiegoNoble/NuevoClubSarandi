package com.club.views;

import com.club.BEANS.CcCobrador;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Parametros;
import com.club.BEANS.Socio;
import com.club.DAOs.CategoriaDAO;
import com.club.DAOs.CcCobradorDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.DepDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.ParametrosDAO;
import com.club.DAOs.SocioDAO;
import com.club.tableModels.SociosInactivosTableModel;
import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class EmiteMensualidadesInactivos extends javax.swing.JInternalFrame {

    private MensualidadesDAO mensualidadesDAO;
    private SocioDAO socioDAO;
    private ParametrosDAO parametrosDAO;
    private DepDAO depDAO;
    private CategoriaDAO categoriaDAO;
    private CobradorDAO cobradorDAO;
    private CcCobradorDAO ccCobradorDAO;
    private Mensualidades mensualidad;
    private CcCobrador debito;
    private List<Socio> listSocios;
    String fecha;
    SimpleDateFormat formato;
    Parametros param;
    ListSelectionModel listModelSocios;
    SociosInactivosTableModel tblModel;
    Socio socioSeleccionado;
    JDesktopPane desktopPane;

    public EmiteMensualidadesInactivos(JDesktopPane desktopPane) {

        initComponents();
        this.desktopPane = desktopPane;
        parametrosDAO = new ParametrosDAO();
        param = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);
        rellentaComboBox();
        formato = new SimpleDateFormat("15/MM/yyyy");
        dpInicio.setFormats(formato);
        dpFin.setFormats(formato);
        DefineModeloTbl();
    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblSocio.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listSocios = new ArrayList<>();
        tblModel = new SociosInactivosTableModel(listSocios);
        tblSocio.setModel(tblModel);
        int[] anchos = {5, 15, 100, 50, 50, 50, 50};

        for (int i = 0; i < tblSocio.getColumnCount(); i++) {

            tblSocio.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        listModelSocios = tblSocio.getSelectionModel();
        listModelSocios.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblSocio.getSelectedRow() != -1) {

                        // socioSeleccionado = listSocios.get(tblSocio.getSelectedRow());
                    }
                }
            }
        });

    }

    private void rellentaComboBox() {

        try {

            cobradorDAO = new CobradorDAO();
            List<Cobrador> listCobradores = cobradorDAO.BuscaTodos(Cobrador.class);
            cbCobrador.removeAllItems();
            for (Cobrador cobrador : listCobradores) {
                cbCobrador.addItem(cobrador);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar cobradores y categorias", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void guardaLog(int emision, Cobrador cob) throws IOException {

        File archivo = new File("C:/ClubSarandi/logs");
        if (!archivo.exists()) {
            archivo.mkdirs();
        }
        BufferedWriter bw;

        bw = new BufferedWriter(new FileWriter("C:/ClubSarandi/logs/Emisión socios inactivos" + emision + " " + cob.toString() + ".txt"));
        bw.write(txtLog.getText());
        bw.close();
    }

    void guardaLogError() {

        try {
            File archivo = new File("C:/ClubSarandi/logs");
            if (!archivo.exists()) {
                archivo.mkdirs();
            }
            BufferedWriter bw;

            bw = new BufferedWriter(new FileWriter("C:/ClubSarandi/logs/Error emisión.txt"));
            bw.write(txtLog.getText());
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(EmiteMensualidadesInactivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void EmisionMensualidades(List<Socio> sociosSeleccionados, Cobrador nuevoCobrador) throws Exception {

        mensualidadesDAO = new MensualidadesDAO();
        int emision = mensualidadesDAO.NroLanzamiento();
        int contadorRecibos = 0;
        txtLog.append("\n Emisión nro: " + emision);
        txtLog.append("\n------------------------");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
        for (Socio socio : sociosSeleccionados) {
            txtLog.append("\n Emisión socio " + socio.toString());
            txtLog.setCaretPosition(txtLog.getDocument().getLength());
            mensualidadesDAO = new MensualidadesDAO();
            List<Mensualidades> pendientes = mensualidadesDAO.BuscaPendientes(socio);
            if (!pendientes.isEmpty()) {
                txtLog.append("\n Socio posee recibos pendientes: " + socio.getId());
                txtLog.setCaretPosition(txtLog.getDocument().getLength());
                for (Mensualidades pendiente : pendientes) {
                    txtLog.append("\n Recibo " + pendiente.getId());
                    txtLog.append("\n Vencimiento " + pendiente.getFechaVencimiento());
                    txtLog.append("\n Cobrador actualizado, OK!");
                    mensualidadesDAO = new MensualidadesDAO();
                    pendiente.setLanzamiento(emision);
                    pendiente.setCobrador(nuevoCobrador);
                    mensualidadesDAO.Actualizar(pendiente);
                }
                Mensualidades ultima = pendientes.get(pendientes.size() - 1);
                Calendar ultimoVencimientoEmitido = Calendar.getInstance();
                ultimoVencimientoEmitido.setTime(ultima.getFechaVencimiento());
                Calendar hoy = Calendar.getInstance();
                hoy.set(Calendar.DAY_OF_MONTH, 15);

                ultimoVencimientoEmitido.add(Calendar.MONTH, 1);
                
                for (Calendar i = ultimoVencimientoEmitido; i.before(hoy); i.add(Calendar.MONTH, 1)) {
                    //txtLog.append("\n" + i.getTime());

                    mensualidad = new Mensualidades();
                    mensualidad.setCobrador(nuevoCobrador);
                    mensualidad.setLanzamiento(emision);
                    mensualidad.setPago("Pendiente de Pago");
                    mensualidad.setSocio(socio);
                    mensualidad.setValor(ultima.getValor());
                    mensualidad.setFechaVencimiento(ultimoVencimientoEmitido.getTime());

                    mensualidadesDAO = new MensualidadesDAO();
                    mensualidadesDAO.Salvar(mensualidad);
                    txtLog.append("\n Recibo nro.: " + mensualidad.getId() + ", vencimiento: " + mensualidad.getFechaVencimiento());
                    contadorRecibos = contadorRecibos + 1;
                }
            }
            socioDAO = new SocioDAO();
            socio.setCobrador(nuevoCobrador);
            socioDAO.Actualizar(socio);
            txtLog.append("\n------------------------");
        }

        txtLog.append("\n Emisión finalizada, se generaron " + contadorRecibos + " recibos!");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
        guardaLog(emision, nuevoCobrador);

        try {

            Properties props = new Properties();
            InputStream datos = this.getClass().getClassLoader().getResourceAsStream("META-INF/application.properties");
            props.load(datos);
            Connection conexion = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.user"), props.getProperty("jdbc.pass"));

            InputStream resource = getClass().getClassLoader().getResourceAsStream("Reportes/recibos.jasper");
            HashMap parametros = new HashMap();
            parametros.clear();
            parametros.put("Msj", txtAreaMsj.getText());
            parametros.put("emision", emision);
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, conexion);
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dateChooserDialog1 = new datechooser.beans.DateChooserDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtAreaMsj = new java.awt.TextArea();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbCobrador = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSocio = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        dpInicio = new org.jdesktop.swingx.JXDatePicker();
        dpFin = new org.jdesktop.swingx.JXDatePicker();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Generar Recibos de Inactivos");
        setPreferredSize(new java.awt.Dimension(900, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMinimumSize(new java.awt.Dimension(529, 340));
        jPanel1.setPreferredSize(new java.awt.Dimension(607, 300));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("Mensaje para imprimir en los recibos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtAreaMsj, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel16.setText("Cobrador"); // NOI18N

        cbCobrador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Seleccionar cobrador", jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jTabbedPane1, gridBagConstraints);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Generar recibos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jButton1, gridBagConstraints);

        tblSocio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSocio.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(tblSocio);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jScrollPane2, gridBagConstraints);

        jButton2.setText("Consultar mensualidades socio seleccionado");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jButton2, gridBagConstraints);

        jTabbedPane3.addTab("", jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jTabbedPane3, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Período"));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("hasta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel3.setText("Desde");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        btnBuscar.setText("Buscar");
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpInicio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(dpFin, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        txtLog.setColumns(20);
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel7.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel7, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    txtLog.setText("");
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh'h'mm'm'ss");
                    String ahora = format.format(date);
                    txtLog.append("\n Inicio " + ahora);
                    //txtLog.setCaretPosition(txtLog.getDocument().getLength());
                    List<Socio> ListSociosSeleccionados = new ArrayList<>();
                    int[] indexSocios = tblSocio.getSelectedRows();
                    for (int c : indexSocios) {
                        Socio Socio = listSocios.get(c);

                        ListSociosSeleccionados.add(Socio);

                    }
                    EmisionMensualidades(ListSociosSeleccionados, (Cobrador) cbCobrador.getSelectedItem());

                } catch (Exception e) {
                    e.printStackTrace();
                    txtLog.append("\n Error al generar mensualidades!");
                    txtLog.append(e.toString());
                    txtLog.setCaretPosition(txtLog.getDocument().getLength());
                    guardaLogError();
                }
            }
        }).start();

        //GeneraRecibos generaRecibos = new GeneraRecibos(txtLog);
        //generaRecibos.execute();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        listSocios.clear();
        socioDAO = new SocioDAO();
        listSocios.addAll(socioDAO.BuscaSociosInactivosEnElPeriodo(dpInicio.getDate(), dpFin.getDate()));
        tblModel.fireTableDataChanged();


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        socioSeleccionado = listSocios.get(tblSocio.getSelectedRow());

        ConsultaMensualidades mensualidades = new ConsultaMensualidades(socioSeleccionado);
        desktopPane.add(mensualidades);
        mensualidades.setVisible(true);
        mensualidades.toFront();
        //centralizaVentanas(mensualidades);
        try {
            mensualidades.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox cbCobrador;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private org.jdesktop.swingx.JXDatePicker dpFin;
    private org.jdesktop.swingx.JXDatePicker dpInicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable tblSocio;
    private java.awt.TextArea txtAreaMsj;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables

}

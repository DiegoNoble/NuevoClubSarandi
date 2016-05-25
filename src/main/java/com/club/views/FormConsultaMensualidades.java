package com.club.views;

import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.CcCobrador;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.MensualidadesAnuladas;
import com.club.BEANS.Socio;
import com.club.DAOs.CcCobradorDAO;
import com.club.DAOs.MensualidadesAnuladasDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.SocioDAO;
import com.club.Renderers.TableRendererColorSituacion;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FormConsultaMensualidades extends javax.swing.JInternalFrame {

    MensualidadesDAO mensualidadesDAO;
    SocioDAO socioDAO;
    MensualidadesAnuladasDAO mensualidadesAnuladasDAO;
    CcCobradorDAO ccCobradorDAO;
    Socio socioSeleccionado;
    CcCobrador credito;
    DefaultTableModel tblModelSocio;
    DefaultTableModel tblModelMensualidades;
    ListSelectionModel listModelSocios;
    ListSelectionModel listModelMensualidades;
    Mensualidades mensualidadSeleccionada;
    List<Socio> listSocios;
    List<Mensualidades> listMensualidades;

    public FormConsultaMensualidades() {

        initComponents();
        defineModelo();
        muestraContenidoTabla();

    }

    private void muestraContenidoTabla() {

        if (rbCodSocio.isSelected()) {
            socioDAO = new SocioDAO();
            socioSeleccionado = socioDAO.BuscaPorCodigo(txtFiltro.getText());
            listSocios = new ArrayList<Socio>();
            listSocios.add(socioSeleccionado);

        } else if (rbNombre.isSelected()) {

            socioDAO = new SocioDAO();
            listSocios = new ArrayList<Socio>();
            listSocios = socioDAO.BuscaPorNombre(txtFiltro.getText());

        } else {
            socioDAO = new SocioDAO();
            listSocios = new ArrayList<Socio>();
            listSocios = socioDAO.BuscaTodos(Socio.class);

        }

        tblModelSocio.setNumRows(0);
        listMensualidades = new ArrayList<Mensualidades>();
        tblModelMensualidades.setNumRows(0);

        for (Socio socio : listSocios) {
            tblModelSocio.addRow(new Object[]{
                socio.getId(),
                socio.getNombre(),
                socio.getCi(),
                socio.getFechaingreso(),
                socio.getFechanacimiento(),
                socio.getCategoria().getDefinicion(),
                socio.getSituacion()
            });

        }
    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblSocio.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModelSocio = (DefaultTableModel) tblSocio.getModel();

        listModelSocios = tblSocio.getSelectionModel();
        listModelSocios.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblSocio.getSelectedRow() != -1) {

                        socioSeleccionado = listSocios.get(tblSocio.getSelectedRow());
                    }
                    muestraMensualidades();
                }
            }
        });

        ((DefaultTableCellRenderer) tblMensualidades.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModelMensualidades = (DefaultTableModel) tblMensualidades.getModel();
        tblMensualidades.getColumn("Estado").setCellRenderer(new TableRendererColorSituacion(4));
        listModelMensualidades = tblMensualidades.getSelectionModel();
        listModelMensualidades.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblMensualidades.getSelectedRow() != -1) {

                        mensualidadSeleccionada = listMensualidades.get(tblMensualidades.getSelectedRow());
                        btnAnularRecibo.setEnabled(true);
                    }

                }
            }
        });

    }

    private void muestraMensualidades() {

        mensualidadesDAO = new MensualidadesDAO();
        listMensualidades = mensualidadesDAO.BuscaMensualidadesPorSocio(socioSeleccionado);

        tblModelMensualidades.setNumRows(0);
        for (Mensualidades mensualidades : listMensualidades) {
            tblModelMensualidades.addRow(new Object[]{
                mensualidades.getId(),
                mensualidades.getFechaVencimiento(),
                mensualidades.getFechaPago(),
                mensualidades.getCobrador().getNombre(),
                mensualidades.getPago(),
                mensualidades.getValor()
            });

        }
    }

    private void ReimprimeRecibo(String Msj, Integer emision) {

        try {

        HashMap parametros = new HashMap();
        parametros.clear();
        parametros.put("Msj", Msj);
        parametros.put("Recibo", emision);
        btnCarneSocio.setReportParameters(parametros);
        btnCarneSocio.setReportURL("/Reportes/recibos.jasper");

    }
    catch (Exception ex ) {
            ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al generar reporte", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void AnulaRecibo() {

        int confirmación = JOptionPane.showConfirmDialog(null, "Confirma la anulación de la Mensualidad Seleccionada?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmación == JOptionPane.YES_OPTION) {
            try {
                String motivo = JOptionPane.showInputDialog(null, "Motivo?");

                mensualidadesAnuladasDAO = new MensualidadesAnuladasDAO();
                MensualidadesAnuladas mensualidadesAnlada = new MensualidadesAnuladas();
                mensualidadesAnlada.setCobrador(mensualidadSeleccionada.getCobrador());
                mensualidadesAnlada.setFechaPago(mensualidadSeleccionada.getFechaPago());
                mensualidadesAnlada.setFechaVencimiento(mensualidadSeleccionada.getFechaVencimiento());
                mensualidadesAnlada.setLanzamiento(mensualidadSeleccionada.getLanzamiento());
                mensualidadesAnlada.setPago(mensualidadSeleccionada.getPago());
                mensualidadesAnlada.setSocio(mensualidadSeleccionada.getSocio());
                mensualidadesAnlada.setValor(mensualidadSeleccionada.getValor());
                mensualidadesAnlada.setFechaAnulacion(new Date());
                mensualidadesAnlada.setMotivo(motivo);
                mensualidadesAnuladasDAO.Salvar(mensualidadesAnlada);

                credito = new CcCobrador();
                credito.setCobrador(mensualidadSeleccionada.getCobrador());
                credito.setFechaMovimiento(new Date());
                credito.setDescripcion("Anulación Recibo Nro: " + mensualidadSeleccionada.getId());
                credito.setDebito(0.0);
                credito.setCredito(mensualidadSeleccionada.getValor());
                ccCobradorDAO = new CcCobradorDAO();
                ccCobradorDAO.Salvar(credito);

                mensualidadesDAO = new MensualidadesDAO();
                mensualidadesDAO

.EliminarPorId(Mensualidades.class  

    , mensualidadSeleccionada.getId());

    JOptionPane.showMessageDialog (

    null, "Mensualidad anulada correctamente");
    muestraMensualidades();
}


catch (Exception ex) {
                Logger.getLogger(FormConsultaMensualidades.class  

.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        rbNombre = new javax.swing.JRadioButton();
        rbCodSocio = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSocio = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMensualidades = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btnAnularRecibo = new javax.swing.JButton();
        btnCarneSocio = new org.jasper.viewer.components.JasperRunnerButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Busqueda por:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtFiltro, gridBagConstraints);

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscar, gridBagConstraints);

        buttonGroup1.add(rbNombre);
        rbNombre.setText("Nombre");
        jPanel4.add(rbNombre);

        buttonGroup1.add(rbCodSocio);
        rbCodSocio.setText("Código de Socio");
        jPanel4.add(rbCodSocio);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel4, gridBagConstraints);

        tblSocio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "C.I.", "Fecha Ingreso", "Fecha Nacimiento", "Categoria", "Situación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
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
        tblSocio.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblSocio);
        if (tblSocio.getColumnModel().getColumnCount() > 0) {
            tblSocio.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblSocio.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblSocio.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblSocio.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblSocio.getColumnModel().getColumn(3).setCellRenderer(new MyDateCellRenderer());
            tblSocio.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblSocio.getColumnModel().getColumn(4).setCellRenderer(new MyDateCellRenderer());
            tblSocio.getColumnModel().getColumn(5).setPreferredWidth(40);
            tblSocio.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(6).setPreferredWidth(30);
            tblSocio.getColumnModel().getColumn(6).setCellRenderer(new MyDefaultCellRenderer());
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        tblMensualidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. Recibo", "Fecha de Vencimiento", "Fecha de Pago", "Cobrador", "Estado", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMensualidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMensualidadesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMensualidades);
        if (tblMensualidades.getColumnModel().getColumnCount() > 0) {
            tblMensualidades.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblMensualidades.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
            tblMensualidades.getColumnModel().getColumn(1).setPreferredWidth(40);
            tblMensualidades.getColumnModel().getColumn(1).setCellRenderer(new MyDateCellRenderer());
            tblMensualidades.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblMensualidades.getColumnModel().getColumn(2).setCellRenderer(new MyDateCellRenderer());
            tblMensualidades.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblMensualidades.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
            tblMensualidades.getColumnModel().getColumn(4).setPreferredWidth(30);
            tblMensualidades.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
            tblMensualidades.getColumnModel().getColumn(5).setPreferredWidth(30);
            tblMensualidades.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel6.add(jScrollPane2, gridBagConstraints);

        jTabbedPane1.addTab("Mensualidades", jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnAnularRecibo.setText("Anular Recibo");
        btnAnularRecibo.setEnabled(false);
        btnAnularRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularReciboActionPerformed(evt);
            }
        });
        jPanel5.add(btnAnularRecibo);

        btnCarneSocio.setText("Re-Impreción de Recibo");
        btnCarneSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarneSocioActionPerformed(evt);
            }
        });
        jPanel5.add(btnCarneSocio);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel5, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        muestraContenidoTabla();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblMensualidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMensualidadesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMensualidadesMouseClicked

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        muestraContenidoTabla();

    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnAnularReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularReciboActionPerformed

        AnulaRecibo();

    }//GEN-LAST:event_btnAnularReciboActionPerformed

    private void btnCarneSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarneSocioActionPerformed

        String Msj = JOptionPane.showInputDialog(null, "Digite el mensaje a imprimir en el recibo");
        ReimprimeRecibo(Msj, mensualidadSeleccionada.getId());

    }//GEN-LAST:event_btnCarneSocioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnularRecibo;
    private javax.swing.JButton btnBuscar;
    private org.jasper.viewer.components.JasperRunnerButton btnCarneSocio;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbCodSocio;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblMensualidades;
    private javax.swing.JTable tblSocio;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}

package com.club.views;

import com.club.BEANS.Caja;
import com.club.BEANS.Rubro;
import com.club.BEANS.Sectores;
import com.club.BEANS.SectoresFuncionario;
import com.club.BEANS.SectoresPorcentage;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.RubroDAO;
import com.club.DAOs.SectorDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.control.utilidades.ImprimiRecibo;
import com.club.modelos.CajaTableModel;
import com.club.modelos.SectoresCajaTableModel;
import com.club.modelos.SectoresFuncionarioTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

public class cajaFrameNEW extends javax.swing.JInternalFrame {

    CajaTableModel tblModelCaja;
    CajaDAO cajaDAO;
    RubroDAO rubroDAO;
    SectorDAO sectorDAO;
    List<Caja> listMovimientosCaja;
    Caja movimiento;
    String dataSeleccionada;
    String nombreUsuario;
    ListSelectionModel listModel;
    SectoresCajaTableModel tblModelSectoresCaja;
    ListSelectionModel listModelSectores;
    private List<SectoresPorcentage> listSectoresCaja;
    private List<SectoresPorcentage> listSectoresCajaToRemove;
    private List<SectoresPorcentage> sectoresSeleccionados;
    int cifras = (int) Math.pow(10, 2);

    public cajaFrameNEW(String nombreUsuario) {
        initComponents();

        dataPiker.setFormats("dd/MM/yyyy");
        dataPiker.setDate(new Date());
        actualizaComboBox();
        AutoCompleteDecorator.decorate(cbRubro);
        AutoCompleteDecorator.decorate(cbTIpo);
        this.nombreUsuario = nombreUsuario;
        defineModelo();
        configTblSectores();
        muestraMovimientos();

    }

    public void actualizaComboBox() {

        try {

            rubroDAO = new RubroDAO();
            List<Rubro> listRubros = rubroDAO.BuscaTodos(Rubro.class);
            for (Rubro rubro : listRubros) {
                cbRubro.addItem(rubro);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar comboboxes: " + ex);
            ex.printStackTrace();
        }
    }

    private void defineModelo() {

        try {
            ((DefaultTableCellRenderer) tblCaja.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            listMovimientosCaja = new ArrayList<>();
            tblModelCaja = new CajaTableModel(listMovimientosCaja);
            tblCaja.setModel(tblModelCaja);
            tblCaja.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
            int[] anchos = {50, 100, 80, 300, 20, 20, 20};

            for (int i = 0; i < tblCaja.getColumnCount(); i++) {

                tblCaja.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

            }

            listModel = tblCaja.getSelectionModel();
            listModel.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {

                        if (tblCaja.getSelectedRow() != -1) {
                            btnReimprime.setEnabled(true);
                        } else {
                            btnReimprime.setEnabled(false);
                        }

                    }
                }
            });
        } catch (Exception error) {
            error.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error" + error, "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void muestraMovimientos() {
        try {
            listMovimientosCaja.clear();
            cajaDAO = new CajaDAO();
            listMovimientosCaja.addAll(cajaDAO.BuscaMovimientosPorFecha(dataPiker.getDate()));
            tblModelCaja.fireTableDataChanged();

            cajaDAO = new CajaDAO();
            Double saldoAnterior = cajaDAO.buscaSaldoAnterior(dataPiker.getDate());
            txtSaldoAnterior.setValue(saldoAnterior);

            cajaDAO = new CajaDAO();
            Double saldoDelDia = cajaDAO.buscaSaldoDelDia(dataPiker.getDate());
            txtSaldoDelDia.setValue(saldoDelDia);

        } catch (Exception error) {
            error.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error" + error, "Error", JOptionPane.ERROR_MESSAGE);
            error.printStackTrace();

        }
    }

    Double buscaSaldoAnterior() {
        Double saldoAnterior = 0.0;
        cajaDAO = new CajaDAO();
        saldoAnterior = cajaDAO.BuscaSaldoAnterior().getSaldo();
        return saldoAnterior;
    }

    void configTblSectores() {
        ((DefaultTableCellRenderer) tblSectores.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listSectoresCaja = new ArrayList<>();
        listSectoresCajaToRemove = new ArrayList<>();
        sectoresSeleccionados = new ArrayList<>();
        tblModelSectoresCaja = new SectoresCajaTableModel(listSectoresCaja);
        tblSectores.setModel(tblModelSectoresCaja);

        tblSectores.setRowHeight(15);
        //tblSectores.removeColumn(tblSectores.getColumn("Inmueble"));
        tblSectores.getColumn("Sector").setCellEditor(new ComboBoxCellEditor(new cajaFrameNEW.ComboSectores()));

        listModelSectores = tblSectores.getSelectionModel();
        listModelSectores.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblSectores.getSelectedRow() != -1) {

                    btnEliminarSector.setEnabled(true);
                } else {
                    btnEliminarSector.setEnabled(false);
                }
            }
        });

    }

    void agregarNuevoSector() {

        tblModelSectoresCaja.agregar(new SectoresPorcentage(new Sectores(), new Double(0.0)));

        for (SectoresPorcentage sectores : listSectoresCaja) {
            Double procentage = 100.00 / listSectoresCaja.size();
            sectores.setPorcentage(Math.rint((procentage * cifras) / cifras));
        }
        tblModelSectoresCaja.fireTableDataChanged();
    }

    void elminarSectorSeleccionado() {

        SectoresPorcentage sectoresToRemove = listSectoresCaja.get(tblSectores.getSelectedRow());
        listSectoresCaja.remove(sectoresToRemove);
        listSectoresCajaToRemove.add(sectoresToRemove);
        tblModelSectoresCaja.fireTableDataChanged();
        for (SectoresPorcentage sectores : listSectoresCaja) {
            Double procentage = 100.00 / listSectoresCaja.size();
            sectores.setPorcentage(Math.rint((procentage * cifras) / cifras));
        }
        tblModelSectoresCaja.fireTableDataChanged();
    }

    private class ComboSectores extends JComboBox<Object> {

        public ComboSectores() {
            AutoCompleteDecorator.decorate(this);
            SectorDAO sectorDAO = new SectorDAO();
            List<Sectores> sectores = sectorDAO.BuscaTodos(Sectores.class);
            for (Sectores sector : sectores) {
                this.addItem(sector);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        txtConcepto = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbRubro = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbTIpo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        dataPiker = new org.jdesktop.swingx.JXDatePicker();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSectores = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        btnAgregarSector = new javax.swing.JButton();
        btnEliminarSector = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnRegistrar = new javax.swing.JButton();
        btnReimprime = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtSaldoAnterior = new javax.swing.JFormattedTextField();
        txtSaldoDelDia = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaja = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtConcepto, gridBagConstraints);

        jLabel1.setText("Concepto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Rubro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        cbRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRubroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbRubro, gridBagConstraints);

        jLabel3.setText("Tipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel3, gridBagConstraints);

        cbTIpo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbTIpo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entrada", "Salida" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTIpo, gridBagConstraints);

        jLabel5.setText("Importe total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel5, gridBagConstraints);

        txtValor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtValor, gridBagConstraints);

        jLabel8.setText("Fecha:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel8, gridBagConstraints);

        dataPiker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataPikerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dataPiker, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Distribución Sectores"));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        tblSectores.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        tblSectores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblSectores);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel7.add(jScrollPane4, gridBagConstraints);

        btnAgregarSector.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        btnAgregarSector.setText("Agregar Sector");
        btnAgregarSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarSectorActionPerformed(evt);
            }
        });
        jPanel8.add(btnAgregarSector);

        btnEliminarSector.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        btnEliminarSector.setText("Eliminar Sector");
        btnEliminarSector.setEnabled(false);
        btnEliminarSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarSectorActionPerformed(evt);
            }
        });
        jPanel8.add(btnEliminarSector);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel7.add(jPanel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(jPanel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 400));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Movimientos de Caja"); // NOI18N
        jPanel2.add(jLabel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnRegistrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrar.setText("Registrar el Movimiento");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnRegistrar, gridBagConstraints);

        btnReimprime.setText("Reimprimir recibo");
        btnReimprime.setEnabled(false);
        btnReimprime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReimprimeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnReimprime, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel7.setText("Anterior $ :"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel7, gridBagConstraints);

        jLabel14.setText("Del Día $ :"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel14, gridBagConstraints);

        txtSaldoAnterior.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$ #,##0.00"))));
        txtSaldoAnterior.setEnabled(false);
        txtSaldoAnterior.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 130;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoAnterior, gridBagConstraints);

        txtSaldoDelDia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$ #,##0.00"))));
        txtSaldoDelDia.setEnabled(false);
        txtSaldoDelDia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 130;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoDelDia, gridBagConstraints);

        jTabbedPane1.addTab("Saldos $", jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jTabbedPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jPanel6, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed

        try {
            Double importe = Double.valueOf(txtValor.getText());
            if (listSectoresCaja.isEmpty()) {
                throw new Exception("Debe asignar al menos 1 sector al 100%");
            }
            if (cbTIpo.getSelectedItem().equals("Entrada")) {
                for (SectoresPorcentage sector : listSectoresCaja) {

                    movimiento = new Caja();
                    movimiento.setConcepto(txtConcepto.getText());
                    movimiento.setRubro((Rubro) cbRubro.getSelectedItem());
                    movimiento.setFechaMovimiento(new Date());
                    movimiento.setSectores(sector.getSector());

                    movimiento.setEntrada((importe * sector.getPorcentage()) / 100);
                    movimiento.setUsuario(nombreUsuario);
                    movimiento.setSalida(0.0);
                    movimiento.setSaldo(buscaSaldoAnterior() + movimiento.getEntrada());

                    cajaDAO = new CajaDAO();
                    cajaDAO.Salvar(movimiento);

                    JOptionPane.showMessageDialog(null, "Movimiento registrado correctamente!");
                    new ImprimiRecibo().imprimieRecibo(movimiento);

                }

            } else if (cbTIpo.getSelectedItem().equals("Salida")) {

                for (SectoresPorcentage sector : listSectoresCaja) {

                    movimiento = new Caja();
                    movimiento.setConcepto(txtConcepto.getText());
                    movimiento.setRubro((Rubro) cbRubro.getSelectedItem());
                    movimiento.setFechaMovimiento(new Date());
                    movimiento.setSectores(sector.getSector());
                    movimiento.setEntrada(0.0);
                    movimiento.setUsuario(nombreUsuario);
                    movimiento.setSalida((importe * sector.getPorcentage()) / 100);
                    movimiento.setSaldo(buscaSaldoAnterior() - movimiento.getSalida());

                    cajaDAO = new CajaDAO();
                    cajaDAO.Salvar(movimiento);
                    JOptionPane.showMessageDialog(null, "Movimiento registrado correctamente!");
                    new ImprimiRecibo().imprimieRecibo(movimiento);
                }

            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error " + error);
            error.printStackTrace();
        }

        txtConcepto.setText("");
        txtValor.setText("");
        listSectoresCaja.clear();
        tblModelSectoresCaja.fireTableDataChanged();
        muestraMovimientos();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void dataPikerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataPikerActionPerformed

        muestraMovimientos();

    }//GEN-LAST:event_dataPikerActionPerformed

    private void txtValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyReleased
        txtValor.setText(txtValor.getText().replaceAll("[^0-9 | ^.]", ""));

    }//GEN-LAST:event_txtValorKeyReleased

    private void cbRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRubroActionPerformed
        Rubro rubroSeleccionado = (Rubro) cbRubro.getSelectedItem();
        if (rubroSeleccionado.getValorFijo() == true) {
            txtValor.setEditable(false);
            txtValor.setText(rubroSeleccionado.getValor().toString());

        } else {
            txtValor.setEditable(true);

        }

    }//GEN-LAST:event_cbRubroActionPerformed

    private void btnReimprimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReimprimeActionPerformed

        Caja movSeleccionado = (Caja) cajaDAO.BuscaPorID(Caja.class, (Integer) tblModelCaja.getValueAt(tblCaja.getSelectedRow(), 0));
        new ImprimiRecibo()
                .imprimieRecibo(movSeleccionado);

    }//GEN-LAST:event_btnReimprimeActionPerformed

    private void btnAgregarSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarSectorActionPerformed
        agregarNuevoSector();
    }//GEN-LAST:event_btnAgregarSectorActionPerformed

    private void btnEliminarSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarSectorActionPerformed
        elminarSectorSeleccionado();
    }//GEN-LAST:event_btnEliminarSectorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarSector;
    private javax.swing.JButton btnEliminarSector;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnReimprime;
    private javax.swing.JComboBox cbRubro;
    private javax.swing.JComboBox cbTIpo;
    private org.jdesktop.swingx.JXDatePicker dataPiker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblCaja;
    public javax.swing.JTable tblSectores;
    private javax.swing.JFormattedTextField txtConcepto;
    private javax.swing.JFormattedTextField txtSaldoAnterior;
    private javax.swing.JFormattedTextField txtSaldoDelDia;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}

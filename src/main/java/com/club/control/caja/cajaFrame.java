package com.club.control.caja;

import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.Caja;
import com.club.BEANS.Rubro;
import com.club.BEANS.Sectores;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.RubroDAO;
import com.club.DAOs.SectorDAO;
import com.club.control.acceso.conexion;
import com.club.control.sectores.sectoresFrame;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class cajaFrame extends javax.swing.JInternalFrame {

    DefaultTableModel tblModelMensualidades;
    CajaDAO cajaDAO;
    RubroDAO rubroDAO;
    SectorDAO sectorDAO;
    List<Caja> listMovimientosCaja;
    Caja movimiento;
    String dataSeleccionada;

    public cajaFrame() {
        initComponents();

        btnCancelar.setVisible(false);

        dataPiker.setFormats("dd/MM/yyyy");
        dataPiker.setDate(new Date());
        AutoCompleteDecorator.decorate(cbRubro);

        actualizaComboBox();
        muestraContenidoTabla();

    }

    public void actualizaComboBox() {

        try {

            rubroDAO = new RubroDAO();
            List<Rubro> listRubros = rubroDAO.BuscaTodos(Rubro.class);
            for (Rubro rubro : listRubros) {
                cbRubro.addItem(rubro);
            }

            sectorDAO = new SectorDAO();
            List<Sectores> listSectores = sectorDAO.BuscaTodos(Sectores.class);
            for (Sectores sectores : listSectores) {
                cbSectores.addItem(sectores);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar comboboxes: " + ex);
        }
    }

    private void muestraContenidoTabla() {

        try {
            SimpleDateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
            Date data = dataPiker.getDate();

            dataSeleccionada = formatoData.format(data);

            ((DefaultTableCellRenderer) tblCaja.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            tblModelMensualidades = (DefaultTableModel) tblCaja.getModel();
            tblModelMensualidades.setNumRows(0);

            cajaDAO = new CajaDAO();
            listMovimientosCaja = cajaDAO.BuscaMovimientosPorFecha(dataPiker.getDate());
            for (Caja caja : listMovimientosCaja) {
                tblModelMensualidades.addRow(new Object[]{
                    caja.getId(),
                    caja.getFechaMovimiento(),
                    caja.getRubro().getNombreRubro(),
                    caja.getSectores().getNombreSector(),
                    caja.getConcepto(),
                    caja.getEntrada(),
                    caja.getSalida()
                });
            }

            cajaDAO = new CajaDAO();
            Double saldoAnterior = cajaDAO.buscaSaldoAnterior(dataPiker.getDate());
            txtSaldoAnterior.setText(String.valueOf(saldoAnterior));

            cajaDAO = new CajaDAO();
            Double saldoDelDia = cajaDAO.buscaSaldoDelDia(dataPiker.getDate());
            txtSaldoDia.setText(String.valueOf(saldoDelDia));

        } catch (Exception error) {

            JOptionPane.showMessageDialog(null, "Error" + error, "Error", JOptionPane.ERROR_MESSAGE);
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
        jLabel6 = new javax.swing.JLabel();
        cbSectores = new javax.swing.JComboBox();
        btnSector = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        dataPiker = new org.jdesktop.swingx.JXDatePicker();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnRegistrar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaja = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSaldoDia = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtSaldoAnterior = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
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
        gridBagConstraints.gridy = 3;
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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbRubro, gridBagConstraints);

        jLabel3.setText("Tipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel3, gridBagConstraints);

        cbTIpo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entrada", "Salida" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTIpo, gridBagConstraints);

        jLabel5.setText("Valor en $U:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel5, gridBagConstraints);

        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtValor, gridBagConstraints);

        jLabel6.setText("Sector");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbSectores, gridBagConstraints);

        btnSector.setText("Nuevo Sector");
        btnSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSectorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 1;
        jPanel1.add(btnSector, gridBagConstraints);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        jPanel1.add(btnCancelar, gridBagConstraints);

        jLabel8.setText("Fecha:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel8, gridBagConstraints);

        dataPiker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataPikerActionPerformed(evt);
            }
        });
        jPanel1.add(dataPiker, new java.awt.GridBagConstraints());

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 400));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Movimientos de Caja"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addContainerGap(339, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnRegistrar.setText("Registrar el Movimiento");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(347, 347, 347)
                .addComponent(btnRegistrar)
                .addContainerGap(326, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegistrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Fecha", "Rubro", "Sector", "Concepto", "Entrada $", "Salida $"
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
        if (tblCaja.getColumnModel().getColumnCount() > 0) {
            tblCaja.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblCaja.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
            tblCaja.getColumnModel().getColumn(1).setPreferredWidth(40);
            tblCaja.getColumnModel().getColumn(1).setCellRenderer(new MyDateCellRenderer());
            tblCaja.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblCaja.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
            tblCaja.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblCaja.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
            tblCaja.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblCaja.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
            tblCaja.getColumnModel().getColumn(5).setPreferredWidth(20);
            tblCaja.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
            tblCaja.getColumnModel().getColumn(6).setPreferredWidth(20);
            tblCaja.getColumnModel().getColumn(6).setCellRenderer(new MyDefaultCellRenderer());
        }

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

        txtSaldoDia.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoDia, gridBagConstraints);

        jLabel14.setText("Del Día $ :"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel14, gridBagConstraints);

        txtSaldoAnterior.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaldoAnterior, gridBagConstraints);

        jTabbedPane1.addTab("Saldos $", jPanel5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(656, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed

        if (cbTIpo.getSelectedItem().equals("Entrada")) {

            try {

                movimiento = new Caja();
                movimiento.setConcepto(txtConcepto.getText());
                movimiento.setRubro((Rubro) cbRubro.getSelectedItem());
                movimiento.setFechaMovimiento(new Date());
                movimiento.setSectores((Sectores) cbSectores.getSelectedItem());
                movimiento.setEntrada(Double.valueOf(txtValor.getText()));
                movimiento.setSalida(0.0);

                cajaDAO = new CajaDAO();
                cajaDAO.Salvar(movimiento);

                JOptionPane.showMessageDialog(null, "Movimiento registrado correctamente!");

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Error al salvar en BD " + error, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (cbTIpo.getSelectedItem().equals("Salida")) {

            try {
                movimiento = new Caja();
                movimiento.setConcepto(txtConcepto.getText());
                movimiento.setRubro((Rubro) cbRubro.getSelectedItem());
                movimiento.setFechaMovimiento(new Date());
                movimiento.setSectores((Sectores) cbSectores.getSelectedItem());
                movimiento.setEntrada(0.0);
                movimiento.setSalida(Double.valueOf(txtValor.getText()));

                cajaDAO = new CajaDAO();
                cajaDAO.Salvar(movimiento);
                JOptionPane.showMessageDialog(null, "Movimiento registrado correctamente!");

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado " + error);
            }

        }

        txtConcepto.setText("");
        txtValor.setText("");

        muestraContenidoTabla();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSectorActionPerformed

        sectoresFrame sectores = new sectoresFrame();
        sectores.setVisible(true);
        this.getDesktopPane().add(sectores);
        sectores.toFront();


    }//GEN-LAST:event_btnSectorActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        btnCancelar.setVisible(false);
        cbRubro.requestFocus();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void dataPikerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataPikerActionPerformed

        muestraContenidoTabla();

    }//GEN-LAST:event_dataPikerActionPerformed

    private void txtValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyReleased
        txtValor.setText(txtValor.getText().replaceAll("[^0-9 | ^.]", ""));

    }//GEN-LAST:event_txtValorKeyReleased

    private void cbRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRubroActionPerformed
        Rubro rubroSeleccionado = (Rubro) cbRubro.getSelectedItem();
        if (rubroSeleccionado.getValorFijo() == true) {
            txtValor.setText(rubroSeleccionado.getValor().toString());
            txtValor.setEditable(false);

        } else {
            txtValor.setEditable(true);
        }

    }//GEN-LAST:event_cbRubroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnSector;
    private javax.swing.JComboBox cbRubro;
    private javax.swing.JComboBox cbSectores;
    private javax.swing.JComboBox cbTIpo;
    private org.jdesktop.swingx.JXDatePicker dataPiker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblCaja;
    private javax.swing.JFormattedTextField txtConcepto;
    private javax.swing.JTextField txtSaldoAnterior;
    private javax.swing.JTextField txtSaldoDia;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}

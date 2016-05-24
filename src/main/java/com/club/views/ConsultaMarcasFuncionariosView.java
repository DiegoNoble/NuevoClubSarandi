package com.club.views;

import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.Renderers.MyTimeCellRenderer;
import com.club.BEANS.Funcionario;
import com.club.BEANS.Marcas;
import com.club.DAOs.FuncionarioDAO;
import com.club.DAOs.MarcasDAO;
import com.club.huellas.BioMini;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public final class ConsultaMarcasFuncionariosView extends javax.swing.JInternalFrame {

    private FuncionarioDAO funcionarioDAO;
    private MarcasDAO marcasDAO;
    private List<Funcionario> listFuncionarios;
    private List<Marcas> listMarcas;
    private Funcionario funcionario;
    private DefaultTableModel tblModelFuncionarios;
    private DefaultTableModel tblModelMarcas;
    private ListSelectionModel listModelFuncionarios;
    private ImageIcon foto;
    private Icon icono;
    private Funcionario funcionarioSeleccionado;
    private HashMap parametros;
    BioMini bioMini;

    public ConsultaMarcasFuncionariosView() {
        initComponents();

        parametros = new HashMap();
        DefineModeloTbl();
        buscaTodosLosRegistros();
        muestraContenidoTbl();

    }

    private void buscaTodosLosRegistros() {
        funcionarioDAO = new FuncionarioDAO();
        listFuncionarios = funcionarioDAO.BuscaTodos(Funcionario.class);

    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblFuncionario.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModelFuncionarios = (DefaultTableModel) tblFuncionario.getModel();

        ((DefaultTableCellRenderer) tblMarcas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModelMarcas = (DefaultTableModel) tblMarcas.getModel();

        listModelFuncionarios = tblFuncionario.getSelectionModel();
        listModelFuncionarios.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblFuncionario.getSelectedRow() != -1) {

                        funcionarioSeleccionado = listFuncionarios.get(tblFuncionario.getSelectedRow());
                    }
                    muestraDetalles();
                }
            }
        });

    }

    private void muestraContenidoTbl() {

        try {

            tblModelFuncionarios.setNumRows(0);
            for (Funcionario funcionario1 : listFuncionarios) {
                tblModelFuncionarios.addRow(new Object[]{
                    funcionario1.getId(),
                    funcionario1.getNombre(),
                    funcionario1.getCi(),
                    funcionario1.getFechaingreso(),
                    funcionario1.getFechaegreso(),
                    funcionario1.getSituacion()});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Funcionarios no encontrado");
            e.printStackTrace();
        }
    }

    private void muestraDetalles() {

        limpiaCampos();

        if (tblFuncionario.getSelectedRow() != -1) {

            foto = new ImageIcon(funcionarioSeleccionado.getFoto());
            icono = new ImageIcon(foto.getImage().getScaledInstance(jlblFoto.getWidth(), jlblFoto.getHeight(), Image.SCALE_DEFAULT));
            jlblFoto.setIcon(icono);

            marcasDAO = new MarcasDAO();
            listMarcas = marcasDAO.BuscaPorFuncionarioAndFecha(funcionarioSeleccionado, dpInicio.getDate(), dpFin.getDate());
            tblModelMarcas.setNumRows(0);
            for (Marcas marca : listMarcas) {
                tblModelMarcas.addRow(new Object[]{
                    marca.getFecha(),
                    marca.getHora(),
                    marca.getTipoMarca()});
            }
        }
    }

    private void filtros() {

        if (rbCodigo.isSelected()) {
            funcionario = new Funcionario();
            funcionario = (Funcionario) funcionarioDAO.BuscaPorID(Funcionario.class, Integer.parseInt(txtFiltro.getText()));

            listFuncionarios.clear();
            listFuncionarios.add(funcionario);
            muestraContenidoTbl();

        } else if (rbCI.isSelected()) {
            funcionarioDAO = new FuncionarioDAO();
            listFuncionarios = funcionarioDAO.BuscaPorCI(txtFiltro.getText());
            muestraContenidoTbl();

        } else if (rbNombre.isSelected()) {
            funcionarioDAO = new FuncionarioDAO();
            listFuncionarios = funcionarioDAO.BuscaPorNombre(txtFiltro.getText());
            muestraContenidoTbl();

        }

    }

    private void limpiaCampos() {

        jlblFoto.setIcon(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        rbCI = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbCodigo = new javax.swing.JRadioButton();
        txtFiltro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        dpFin = new org.jdesktop.swingx.JXDatePicker();
        dpInicio = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jlblFoto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFuncionario = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMarcas = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(900, 700));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Marcajes funcionarios"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        buttonGroup1.add(rbCI);
        rbCI.setText("Cédula");

        buttonGroup1.add(rbNombre);
        rbNombre.setSelected(true);
        rbNombre.setText("Nombre");

        buttonGroup1.add(rbCodigo);
        rbCodigo.setText("Código");

        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });

        jLabel3.setText("Filtro por Nombre"); // NOI18N

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        dpFin.setDate(new Date());
        dpFin.setFormats("dd/MM/yyyy");
        dpFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpFinActionPerformed(evt);
            }
        });

        dpInicio.setDate(new Date());
        dpInicio.setFormats("dd/MM/yyyy");
        dpInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpInicioActionPerformed(evt);
            }
        });

        jLabel2.setText("Fecha inicio");

        jLabel4.setText("Fecha fin");

        jlblFoto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jlblFoto.setPreferredSize(new java.awt.Dimension(3, 4));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel2)))
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(rbCodigo))
                    .addComponent(dpInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(rbNombre))
                    .addComponent(jLabel4))
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(rbCI))
                    .addComponent(dpFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnBuscar)
                .addGap(10, 10, 10)
                .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel2))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(rbCodigo)
                .addGap(49, 49, 49)
                .addComponent(dpInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(rbNombre)
                .addGap(53, 53, 53)
                .addComponent(jLabel4))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(rbCI)
                .addGap(49, 49, 49)
                .addComponent(dpFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(btnBuscar))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel7, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 14))); // NOI18N
        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblFuncionario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "C.I.", "Fecha Ingreso", "Fecha Egreso", "Situación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tblFuncionario.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblFuncionario);
        if (tblFuncionario.getColumnModel().getColumnCount() > 0) {
            tblFuncionario.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblFuncionario.getColumnModel().getColumn(0).setHeaderValue("Código");
            tblFuncionario.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
            tblFuncionario.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblFuncionario.getColumnModel().getColumn(1).setHeaderValue("Nombre");
            tblFuncionario.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
            tblFuncionario.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblFuncionario.getColumnModel().getColumn(2).setHeaderValue("C.I.");
            tblFuncionario.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
            tblFuncionario.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblFuncionario.getColumnModel().getColumn(3).setCellRenderer(new MyDateCellRenderer());
            tblFuncionario.getColumnModel().getColumn(4).setPreferredWidth(60);
            tblFuncionario.getColumnModel().getColumn(4).setCellRenderer(new MyTimeCellRenderer());
            tblFuncionario.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Marcajes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 14))); // NOI18N
        jPanel9.setLayout(new java.awt.GridBagLayout());

        tblMarcas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tblMarcas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Día", "Hora", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMarcas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblMarcas);
        if (tblMarcas.getColumnModel().getColumnCount() > 0) {
            tblMarcas.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblMarcas.getColumnModel().getColumn(0).setCellRenderer(new MyDateCellRenderer());
            tblMarcas.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblMarcas.getColumnModel().getColumn(1).setCellRenderer(new MyTimeCellRenderer());
            tblMarcas.getColumnModel().getColumn(2).setPreferredWidth(40);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel9.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel9, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        filtros();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        filtros();

    }//GEN-LAST:event_txtFiltroActionPerformed

    private void dpInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpInicioActionPerformed
        muestraDetalles();
    }//GEN-LAST:event_dpInicioActionPerformed

    private void dpFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpFinActionPerformed
        muestraDetalles();
    }//GEN-LAST:event_dpFinActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.jdesktop.swingx.JXDatePicker dpFin;
    private org.jdesktop.swingx.JXDatePicker dpInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jlblFoto;
    private javax.swing.JRadioButton rbCI;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblFuncionario;
    private javax.swing.JTable tblMarcas;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}

package com.club.views;

import com.club.BEANS.Categoria;
import com.club.BEANS.Socio;
import com.club.DAOs.SectorDAO;
import com.club.DAOs.SocioDAO;
import com.club.Renderers.MeDateMesAnoCellRenderer;
import com.club.control.utilidades.ExportarDatosExcel;
import com.club.tableModels.CategoriaTableModel;
import com.club.tableModels.FiltroSocioTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class FiltroSocios extends javax.swing.JInternalFrame {

    private SocioDAO SocioDAO;
    private List<Socio> listSocioes;
    private FiltroSocioTableModel tblModelSocios;
    private ListSelectionModel listModelSocioes;
    private Socio SocioSelecionado;
    private SectorDAO CategoriaDAO;
    private List<Categoria> listCategoriaes;
    private CategoriaTableModel tblModelCategoria;
    private ListSelectionModel listModelCategoriaes;
    private Categoria sectorSelecionado;
    private List CategoriaSeleccionados;

    public FiltroSocios() {
        initComponents();
        DefineModeloTbl();
        DefineModeloTblCategoria();
        buscaTodosLosCategoria();
        accionesBotones();

    }

    private void buscaTodosLosCategoria() {
        listCategoriaes.clear();
        CategoriaDAO = new SectorDAO();
        listCategoriaes.addAll(CategoriaDAO.BuscaTodos(Categoria.class));
        tblModelSocios.fireTableDataChanged();
    }

    private void DefineModeloTblCategoria() {

        ((DefaultTableCellRenderer) tblCategoria.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listCategoriaes = new ArrayList<>();
        tblModelCategoria = new CategoriaTableModel(listCategoriaes);
        tblCategoria.setModel(tblModelCategoria);

        int[] anchos = {60, 400};

        for (int i = 0; i < tblCategoria.getColumnCount(); i++) {

            tblCategoria.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        listModelCategoriaes = tblCategoria.getSelectionModel();
        listModelCategoriaes.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblCategoria.getSelectedRow() != -1) {

                        CategoriaSeleccionados = new ArrayList();
                        int[] selectedRows = tblCategoria.getSelectedRows();
                        for (int selectedRow : selectedRows) {
                            CategoriaSeleccionados.add(listCategoriaes.get(selectedRow).getId());

                        }

                    }
                }
            }
        });

    }

    void accionesBotones() {
        btnExcelPesos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    ExportarDatosExcel exportar = new ExportarDatosExcel(tblSocio, "Cuenta paciente");
                    exportar.exportarJTableExcel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al exportar datos " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void buscaTodosLosRegistros(List situaciones) {
        listSocioes.clear();
        SocioDAO = new SocioDAO();
        listSocioes.addAll(SocioDAO.consultaSocios(CategoriaSeleccionados, situaciones));
        tblModelSocios.fireTableDataChanged();
    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblSocio.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listSocioes = new ArrayList<>();
        tblModelSocios = new FiltroSocioTableModel(listSocioes);
        tblSocio.setModel(tblModelSocios);
        tblSocio.getColumn("Vencimiento").setCellRenderer(new MeDateMesAnoCellRenderer());

        int[] anchos = {10, 40, 400, 40, 40, 40, 40};

        for (int i = 0; i < tblSocio.getColumnCount(); i++) {

            tblSocio.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        listModelSocioes = tblSocio.getSelectionModel();
        listModelSocioes.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblSocio.getSelectedRow() != -1) {

                        SocioSelecionado = listSocioes.get(tblSocio.getSelectedRow());
                    }
                }
            }
        });

    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSocio = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        rbActivoa = new javax.swing.JRadioButton();
        rbInactivos = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategoria = new javax.swing.JTable();
        rbRenuncia = new javax.swing.JRadioButton();
        btnExcelPesos = new botones.BotonExcel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1024, 600));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Socios"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblSocio.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSocio.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblSocio);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscar, gridBagConstraints);

        rbActivoa.setText("Activos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel2.add(rbActivoa, gridBagConstraints);

        rbInactivos.setText("Inactivos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel2.add(rbInactivos, gridBagConstraints);

        tblCategoria.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCategoria.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(tblCategoria);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 150;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jScrollPane2, gridBagConstraints);

        rbRenuncia.setText("Renuncia");
        rbRenuncia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRenunciaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel2.add(rbRenuncia, gridBagConstraints);

        btnExcelPesos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnExcelPesos.setPreferredSize(new java.awt.Dimension(80, 100));
        btnExcelPesos.setText("Exportar datos Excel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnExcelPesos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        List situaciones = new ArrayList();
        if (rbActivoa.isSelected()) {
            situaciones.add("Activo");
        }
        if (rbInactivos.isSelected()) {
            situaciones.add("Inactivo");
        }
        if (rbRenuncia.isSelected()) {
            situaciones.add("Renuncia");
        }

        buscaTodosLosRegistros(situaciones);

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void rbRenunciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRenunciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbRenunciaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    public botones.BotonExcel btnExcelPesos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbActivoa;
    private javax.swing.JRadioButton rbInactivos;
    private javax.swing.JRadioButton rbRenuncia;
    private javax.swing.JTable tblCategoria;
    private javax.swing.JTable tblSocio;
    // End of variables declaration//GEN-END:variables
}

package com.club.views;

import com.club.BEANS.Categoria;
import com.club.BEANS.Dependiente;
import com.club.DAOs.DepDAO;
import com.club.DAOs.SectorDAO;
import com.club.Renderers.MeDateMesAnoCellRenderer;
import com.club.control.utilidades.ExportarDatosExcel;
import com.club.tableModels.CategoriaTableModel;
import com.club.tableModels.FiltroDependientesTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Tuple;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class FiltroDependientes extends javax.swing.JInternalFrame {

    private DepDAO DependienteDAO;
    private List<Tuple> listDependientees;
    private FiltroDependientesTableModel tblModelDependientes;
    private ListSelectionModel listModelDependientees;
    private Dependiente DependienteSelecionado;
    private SectorDAO CategoriaDAO;
    private List<Categoria> listCategoriaes;
    private CategoriaTableModel tblModelCategoria;
    private ListSelectionModel listModelCategoriaes;
    private Categoria sectorSelecionado;
    private List CategoriaSeleccionados;

    public FiltroDependientes() {
        initComponents();
        DefineModeloTbl();
        DefineModeloTblCategoria();
        buscaTodosLosCategoria();
        accionesBotones();
        for (int i = 1; i < 101; i++) {
            cbEdad.addItem(i);
            
        }
    }

    private void buscaTodosLosCategoria() {
        listCategoriaes.clear();
        CategoriaDAO = new SectorDAO();
        listCategoriaes.addAll(CategoriaDAO.BuscaTodos(Categoria.class));
        tblModelDependientes.fireTableDataChanged();
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
                    ExportarDatosExcel exportar = new ExportarDatosExcel(tblDep, "Cuenta paciente");
                    exportar.exportarJTableExcel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al exportar datos " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void buscaTodosLosRegistros(List parentezco, Integer edad, List categorias) {
        listDependientees.clear();
        DependienteDAO = new DepDAO();
        listDependientees.addAll(DependienteDAO.consultaDependientes(parentezco, edad, categorias));
        tblModelDependientes.fireTableDataChanged();
    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblDep.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listDependientees = new ArrayList<>();
        tblModelDependientes = new FiltroDependientesTableModel(listDependientees);
        tblDep.setModel(tblModelDependientes);

        int[] anchos = {10, 30, 250, 60, 30, 30, 250, 40};

        for (int i = 0; i < tblDep.getColumnCount(); i++) {

            tblDep.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        listModelDependientees = tblDep.getSelectionModel();
        listModelDependientees.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblDep.getSelectedRow() != -1) {

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
        tblDep = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategoria = new javax.swing.JTable();
        btnExcelPesos = new botones.BotonExcel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listParentezco = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        cbEdad = new javax.swing.JComboBox<>();

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
        jLabel1.setText("Dependientes"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblDep.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDep.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblDep);

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

        btnExcelPesos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnExcelPesos.setPreferredSize(new java.awt.Dimension(80, 100));
        btnExcelPesos.setText("Exportar datos Excel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnExcelPesos, gridBagConstraints);

        listParentezco.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Hijo/a", "Cónyuge", "Varios" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listParentezco);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jScrollPane3, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Edad mayor a:"));

        cbEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEdadActionPerformed(evt);
            }
        });
        jPanel3.add(cbEdad);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jPanel3, gridBagConstraints);

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
        listParentezco.getSelectedValuesList();

        buscaTodosLosRegistros(listParentezco.getSelectedValuesList(), (Integer) cbEdad.getSelectedItem(), CategoriaSeleccionados);

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void cbEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEdadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEdadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    public botones.BotonExcel btnExcelPesos;
    private javax.swing.JComboBox<Integer> cbEdad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listParentezco;
    private javax.swing.JTable tblCategoria;
    private javax.swing.JTable tblDep;
    // End of variables declaration//GEN-END:variables
}

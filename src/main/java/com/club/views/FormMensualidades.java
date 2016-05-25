package com.club.views;

import com.club.BEANS.Categoria;
import com.club.BEANS.CcCobrador;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Socio;
import com.club.DAOs.CategoriaDAO;
import com.club.DAOs.CcCobradorDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.DepDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.SocioDAO;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FormMensualidades extends javax.swing.JInternalFrame {

    private MensualidadesDAO mensualidadesDAO;
    private SocioDAO socioDAO;
    private DepDAO depDAO;
    private CategoriaDAO categoriaDAO;
    private CobradorDAO cobradorDAO;
    private CcCobradorDAO ccCobradorDAO;
    private Mensualidades mensualidad;
    private CcCobrador debito;
    private List<Socio> listSocios;
    String fecha;
    private HashMap parametros;
    SimpleDateFormat formato;

    public FormMensualidades() {

        initComponents();

        rellentaComboBox();
        formato = new SimpleDateFormat("15/MM/yyyy");
        dp.setDateFormat(formato);
    }

    private void rellentaComboBox() {

        try {
            categoriaDAO = new CategoriaDAO();
            List<Categoria> listCategorias = categoriaDAO.categoriasQuePagan();
            cbCategoria.removeAllItems();
            for (Categoria categoria : listCategorias) {
                cbCategoria.addItem(categoria);
            }

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

    public boolean verificaCampos() {
        boolean verificacion = true;

        if (txtInicio.getText().equals("")) {
            verificacion = false;
        } else if (txtFin.getText().equals("")) {
            verificacion = false;
        }
        return verificacion;

    }

    private void registraEnCcCobrador(Cobrador cobrador, Mensualidades mensualidades) {
        try {
            debito = new CcCobrador();
            debito.setCobrador(cobrador);
            debito.setFechaMovimiento(new Date());
            debito.setDescripcion("Recibo Nro: " + mensualidades.getId());
            debito.setDebito(mensualidades.getValor());
            debito.setCredito(0.0);
            ccCobradorDAO = new CcCobradorDAO();
            ccCobradorDAO.Salvar(debito);
        } catch (Exception ex) {
            Logger.getLogger(FormMensualidades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void EmisionMensualidades(String inicio, String fin) {

        mensualidadesDAO = new MensualidadesDAO();
        int emision = mensualidadesDAO.NroLanzamiento();

        try {
            socioDAO = new SocioDAO();
            listSocios = socioDAO.BuscaSociosHabilitadosParaMensualidades((Cobrador) cbCobrador.getSelectedItem(), (Categoria) cbCategoria.getSelectedItem(),
                    txtInicio.getText(), txtFin.getText());

            fecha = dp.getText();
            SimpleDateFormat formatoBD = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaMensualidad = formatoBD.parse(fecha);

            for (Socio socio : listSocios) {

                mensualidadesDAO = new MensualidadesDAO();
                if (mensualidadesDAO.VerificaSiYaFueEmitida(socio, fechaMensualidad) == true) {

                    mensualidadesDAO = new MensualidadesDAO();
                    if (mensualidadesDAO.VerificaCantidadVencimientos(socio) == true) {

                        Cobrador cobrador = socio.getCobrador();
                        Double valorMensualidad = socio.getCategoria().getMensualidad();

                        mensualidad = new Mensualidades();
                        mensualidad.setCobrador(cobrador);
                        mensualidad.setLanzamiento(emision);
                        mensualidad.setPago("Pendiente de Pago");
                        mensualidad.setSocio(socio);
                        mensualidad.setValor(valorMensualidad);
                        mensualidad.setFechaVencimiento(fechaMensualidad);

                        mensualidadesDAO = new MensualidadesDAO();
                        mensualidadesDAO.Salvar(mensualidad);

                        registraEnCcCobrador(cobrador, mensualidad);

                    } else {
                        //Inactiva Socio y sus dependientes caso tenga más de 3 vencimientos pendientes
                        socio.setSituacion("Inactivo");
                        socioDAO = new SocioDAO();
                        socioDAO.Actualizar(socio);

                        depDAO = new DepDAO();
                        List<Dependiente> listDependientes = depDAO.BuscaPorCodigodeSocio(socio);
                        for (Dependiente dep : listDependientes) {
                            dep.setSituacion("Inactivo");
                            depDAO = new DepDAO();
                            depDAO.Actualizar(dep);
                        }

                    }
                }

            }
            JOptionPane.showMessageDialog(null, "Mensualidades registradas");
            HashMap parametros = new HashMap();
            parametros.clear();
            parametros.put("Msj", txtAreaMsj.getText());
            parametros.put("emision", emision);
            btnRecibos.setReportParameters(parametros);
            btnRecibos.setReportURL("/Reportes/recibos.jasper");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar mensualidades " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void EmisionIndividual(String idSocio) {

        /*
         InputStream resource = getClass().getClassLoader().getResourceAsStream("Reportes/recibos.jasper");
         parametros = new HashMap();
         parametros.put("Msj", "preuba");
         parametros.put("emision", 6194);
         JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
         JasperViewer reporte = new JasperViewer(jasperPrint, false);
         reporte.setVisible(true);

         reporte.toFront();

         */ mensualidadesDAO = new MensualidadesDAO();
        int emision = mensualidadesDAO.NroLanzamiento();

        try {
            socioDAO = new SocioDAO();
            Socio socioIndividual = socioDAO.BuscaPorCodigo(idSocio);
            if (socioIndividual.getSituacion().equals("Inactivo") || (socioIndividual.getSituacion().equals("Renuncia"))) {
                JOptionPane.showMessageDialog(null, "El Status del socio no permite generar Mensualidades", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                fecha = dp.getText();
                SimpleDateFormat formatoBD = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaMensualidad = formatoBD.parse(fecha);

                mensualidadesDAO = new MensualidadesDAO();
                if (mensualidadesDAO.VerificaSiYaFueEmitida(socioIndividual, fechaMensualidad) == true) {

                    mensualidadesDAO = new MensualidadesDAO();

                    if (mensualidadesDAO.VerificaCantidadVencimientos(socioIndividual) == true) {

                        Cobrador cobrador = socioIndividual.getCobrador();
                        Double valorMensualidad = socioIndividual.getCategoria().getMensualidad();

                        mensualidad = new Mensualidades();
                        mensualidad.setCobrador(cobrador);
                        mensualidad.setLanzamiento(emision);
                        mensualidad.setPago("Pendiente de Pago");
                        mensualidad.setSocio(socioIndividual);
                        mensualidad.setValor(valorMensualidad);
                        mensualidad.setFechaVencimiento(fechaMensualidad);

                        mensualidadesDAO = new MensualidadesDAO();
                        mensualidadesDAO.Salvar(mensualidad);

                        registraEnCcCobrador(cobrador, mensualidad);

                    } else {
                        //Inactiva Socio y sus dependientes caso tenga más de 3 vencimientos pendientes
                        socioIndividual.setSituacion("Inactivo");
                        socioDAO = new SocioDAO();
                        socioDAO.Actualizar(socioIndividual);

                        depDAO = new DepDAO();
                        List<Dependiente> listDependientes = depDAO.BuscaPorCodigodeSocio(socioIndividual);
                        for (Dependiente dep : listDependientes) {
                            dep.setSituacion("Inactivo");
                            depDAO = new DepDAO();
                            depDAO.Actualizar(dep);

                        }

                        JOptionPane.showMessageDialog(null, "Socio y dependientes inactivado por acumulación de Mensualidades Pendientes",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    JOptionPane.showMessageDialog(null, "Mensualidades registradas");

                    parametros = new HashMap();
                    parametros.put("Msj", txtAreaMsj.getText());
                    parametros.put("emision", emision);
                    btnReciboIndividual.setReportParameters(parametros);
                    btnReciboIndividual.setReportURL("/Reportes/recibos.jasper");

                } else {
                    JOptionPane.showMessageDialog(null, "Ya existe una emisión con la misma fecha vencimiento indicada", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar mensualidades " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooserDialog1 = new datechooser.beans.DateChooserDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAreaMsj = new java.awt.TextArea();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        LblCodigoCobrador1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtInicio = new javax.swing.JTextField();
        txtFin = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbCobrador = new javax.swing.JComboBox();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox();
        btnRecibos = new org.jasper.viewer.components.JasperRunnerButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        LblCodigoCobrador2 = new javax.swing.JLabel();
        txtUnSocio = new javax.swing.JTextField();
        btnReciboIndividual = new org.jasper.viewer.components.JasperRunnerButton();
        dp = new datechooser.beans.DateChooserCombo();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Generar Recibos de Mensualidades");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMinimumSize(new java.awt.Dimension(529, 340));
        jPanel1.setPreferredSize(new java.awt.Dimension(607, 300));

        jLabel3.setText("Fecha de Vencimiento:");

        jLabel5.setText("Mensaje para imprimir en los recibos:");

        LblCodigoCobrador1.setText("Primero"); // NOI18N

        jLabel17.setText("Último"); // NOI18N

        txtInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInicioKeyReleased(evt);
            }
        });

        txtFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFinKeyReleased(evt);
            }
        });

        jLabel16.setText("Cobrador"); // NOI18N

        cbCobrador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
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
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Seleccionar cobrador", jPanel3);

        jLabel19.setText("Categoría"); // NOI18N

        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Seleccionar categoría", jPanel4);

        btnRecibos.setText("Generar Recíbo");
        btnRecibos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecibosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(LblCodigoCobrador1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(btnRecibos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblCodigoCobrador1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRecibos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Un grupo de Sócios", jPanel5);

        LblCodigoCobrador2.setText("Sócio:"); // NOI18N

        txtUnSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnSocioKeyReleased(evt);
            }
        });

        btnReciboIndividual.setText("Generar Recíbo");
        btnReciboIndividual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReciboIndividualActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(LblCodigoCobrador2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUnSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReciboIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblCodigoCobrador2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUnSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReciboIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Un solo Sócio", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAreaMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAreaMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Mensualidades"); // NOI18N
        jPanel2.add(jLabel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUnSocioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnSocioKeyReleased

        txtUnSocio.setText(txtUnSocio.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_txtUnSocioKeyReleased

    private void txtInicioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInicioKeyReleased

        txtInicio.setText(txtInicio.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_txtInicioKeyReleased

    private void txtFinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFinKeyReleased

        txtFin.setText(txtFin.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_txtFinKeyReleased

    private void btnReciboIndividualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReciboIndividualActionPerformed

        if (txtUnSocio.getText().equals("")) {

            JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos");

        } else {
            EmisionIndividual(txtUnSocio.getText());
        }
    }//GEN-LAST:event_btnReciboIndividualActionPerformed

    private void btnRecibosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecibosActionPerformed
        if (verificaCampos() == false) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos necessarios!", "Error!", JOptionPane.ERROR_MESSAGE);

        } else {
            EmisionMensualidades(txtInicio.getText(), txtFin.getText());
        }
    }//GEN-LAST:event_btnRecibosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblCodigoCobrador1;
    private javax.swing.JLabel LblCodigoCobrador2;
    private org.jasper.viewer.components.JasperRunnerButton btnReciboIndividual;
    private org.jasper.viewer.components.JasperRunnerButton btnRecibos;
    private javax.swing.JComboBox cbCategoria;
    private javax.swing.JComboBox cbCobrador;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private datechooser.beans.DateChooserCombo dp;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private java.awt.TextArea txtAreaMsj;
    private javax.swing.JTextField txtFin;
    private javax.swing.JTextField txtInicio;
    private javax.swing.JTextField txtUnSocio;
    // End of variables declaration//GEN-END:variables
}

package com.club.views;

import com.club.BEANS.Caja;
import com.club.BEANS.Categoria;
import com.club.BEANS.CcCobrador;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Parametros;
import com.club.BEANS.Rubro;
import com.club.BEANS.Sectores;
import com.club.BEANS.Socio;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.CategoriaDAO;
import com.club.DAOs.CcCobradorDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.DepDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.ParametrosDAO;
import com.club.DAOs.SectorDAO;
import com.club.DAOs.SocioDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class EmiteMensualidades extends javax.swing.JInternalFrame {

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

    public EmiteMensualidades() {

        initComponents();
        parametrosDAO = new ParametrosDAO();
        param = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);
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

            AutoCompleteDecorator.decorate(cbSectores);
            SectorDAO sectorDAO = new SectorDAO();
            List<Sectores> listSectores = sectorDAO.BuscaTodos(Sectores.class);
            for (Sectores sector : listSectores) {
                cbSectores.addItem(sector);
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
            Logger.getLogger(EmiteMensualidades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void guardaLog(int emision, Cobrador cob, Categoria cat) throws IOException {

        File archivo = new File("C:/ClubSarandi/logs");
        if (!archivo.exists()) {
            archivo.mkdirs();
        }
        BufferedWriter bw;

        bw = new BufferedWriter(new FileWriter("C:/ClubSarandi/logs/Emisión" + emision + " " + cob.toString() + " " + cat.toString() + ".txt"));
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
            Logger.getLogger(EmiteMensualidades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void EmisionMensualidades(String inicio, String fin, Cobrador cobrador, Categoria categoria, JTextArea txtLog) throws Exception {

        mensualidadesDAO = new MensualidadesDAO();
        int emision = mensualidadesDAO.NroLanzamiento();
        int contadorRecibos = 0;
        txtLog.append("\n Emisión nro: " + emision);
        //txtLog.setCaretPosition(txtLog.getDocument().getLength());

        socioDAO = new SocioDAO();
        listSocios = socioDAO.BuscaSociosHabilitadosParaMensualidades(cobrador, categoria,
                inicio, fin);
        txtLog.append("\n Cobrador: " + cobrador.toString());
        txtLog.append("\n Categoría: " + categoria.toString());
        txtLog.append("\n Se procesaran " + listSocios.size() + " socios");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());

        fecha = dp.getText();
        SimpleDateFormat formatoBD = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaMensualidad = formatoBD.parse(fecha);
        txtLog.append("\n Fecha vencimientos: " + fecha);

        for (Socio socio : listSocios) {
            txtLog.append("\n -------------------------");
            mensualidadesDAO = new MensualidadesDAO();
            if (mensualidadesDAO.VerificaSiYaFueEmitida(socio, fechaMensualidad) == true) {

                mensualidadesDAO = new MensualidadesDAO();

                if (mensualidadesDAO.VerificaCantidadVencimientos(socio, param.getToleranciaRecibosPenientes()) == true) {

                    //Cobrador cobrador = socio.getCobrador();
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
                    txtLog.append("\n Recibo nro.: " + mensualidad.getId() + ", socio: " + socio.toString() + " generado correctamente");
                    contadorRecibos = contadorRecibos + 1;
                    //txtLog.setCaretPosition(txtLog.getDocument().getLength());

                } else {
                    //Inactiva Socio y sus dependientes caso tenga más de 3 vencimientos pendientes
                    socio.setSituacion("Inactivo");
                    socioDAO = new SocioDAO();
                    socioDAO.Actualizar(socio);

                    txtLog.append("\n Socio: " + socio.toString() + " posee " + param.getToleranciaRecibosPenientes() + " o mas recibos pendientes!");
                    txtLog.append("\n Pasa a situación INACTIVA!");
                    //txtLog.setCaretPosition(txtLog.getDocument().getLength());

                    depDAO = new DepDAO();
                    List<Dependiente> listDependientes = depDAO.BuscaPorCodigodeSocio(socio);
                    for (Dependiente dep : listDependientes) {
                        dep.setSituacion("Inactivo");
                        depDAO = new DepDAO();
                        depDAO.Actualizar(dep);
                        txtLog.append("\n Dependiente: " + dep.toString() + " pasa a situación INACTIVA!");
                        //txtLog.setCaretPosition(txtLog.getDocument().getLength());
                    }

                }
            }

        }
        txtLog.append("\n Emisión finalizada, se generarron " + contadorRecibos + " recibos!");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
        guardaLog(emision, cobrador, categoria);

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

    private Mensualidades EmisionMensualidad(String idSocio, JTextArea txtLog) throws Exception {

        Mensualidades mensualidadPaga = null;
        mensualidadesDAO = new MensualidadesDAO();
        int emision = mensualidadesDAO.NroLanzamiento();
        int contadorRecibos = 0;
        txtLog.append("\n Emisión nro: " + emision);
        //txtLog.setCaretPosition(txtLog.getDocument().getLength());
        listSocios = new ArrayList<>();
        socioDAO = new SocioDAO();
        Socio s = socioDAO.BuscaPorCodigo(idSocio);
        listSocios.add(s);
        txtLog.append("\n Cobrador: " + s.getCobrador().toString());
        txtLog.append("\n Categoría: " + s.getCategoria().toString());
        txtLog.append("\n Se procesaran " + listSocios.size() + " socios");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());

        fecha = dp.getText();
        SimpleDateFormat formatoBD = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaMensualidad = formatoBD.parse(fecha);
        txtLog.append("\n Fecha vencimientos: " + fecha);

        for (Socio socio : listSocios) {
            txtLog.append("\n -------------------------");
            mensualidadesDAO = new MensualidadesDAO();
            if (mensualidadesDAO.VerificaSiYaFueEmitida(socio, fechaMensualidad) == true) {

                mensualidadesDAO = new MensualidadesDAO();

                if (mensualidadesDAO.VerificaCantidadVencimientos(socio, param.getToleranciaRecibosPenientes()) == true) {

                    //Cobrador cobrador = socio.getCobrador();
                    Double valorMensualidad = socio.getCategoria().getMensualidad();

                    mensualidad = new Mensualidades();
                    mensualidad.setCobrador(socio.getCobrador());
                    mensualidad.setLanzamiento(emision);
                    mensualidad.setPago("Pendiente de Pago");
                    mensualidad.setSocio(socio);
                    mensualidad.setValor(valorMensualidad);
                    mensualidad.setFechaVencimiento(fechaMensualidad);

                    mensualidadesDAO = new MensualidadesDAO();
                    mensualidadesDAO.Salvar(mensualidad);
                    mensualidadPaga = mensualidad;

                    registraEnCcCobrador(socio.getCobrador(), mensualidad);
                    txtLog.append("\n Recibo nro.: " + mensualidad.getId() + ", socio: " + socio.toString() + " generado correctamente");
                    contadorRecibos = contadorRecibos + 1;
                    //txtLog.setCaretPosition(txtLog.getDocument().getLength());

                } else {
                    //Inactiva Socio y sus dependientes caso tenga más de 3 vencimientos pendientes
                    socio.setSituacion("Inactivo");
                    socioDAO = new SocioDAO();
                    socioDAO.Actualizar(socio);

                    txtLog.append("\n Socio: " + socio.toString() + " posee " + param.getToleranciaRecibosPenientes() + " o mas recibos pendientes!");
                    txtLog.append("\n Pasa a situación INACTIVA!");
                    //txtLog.setCaretPosition(txtLog.getDocument().getLength());

                    depDAO = new DepDAO();
                    List<Dependiente> listDependientes = depDAO.BuscaPorCodigodeSocio(socio);
                    for (Dependiente dep : listDependientes) {
                        dep.setSituacion("Inactivo");
                        depDAO = new DepDAO();
                        depDAO.Actualizar(dep);
                        txtLog.append("\n Dependiente: " + dep.toString() + " pasa a situación INACTIVA!");
                        //txtLog.setCaretPosition(txtLog.getDocument().getLength());
                    }

                }
            }

        }
        txtLog.append("\n Emisión finalizada, se generarron " + contadorRecibos + " recibos!");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
        guardaLog(emision, s.getCobrador(), s.getCategoria());

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
        return mensualidadPaga;

    }

    private void registraPago(Mensualidades mensualidadeEmitida) {

        try {

            mensualidadesDAO = new MensualidadesDAO();

            mensualidadeEmitida.setFechaPago(new Date());
            mensualidadeEmitida.setPago("Pago");

            mensualidadesDAO = new MensualidadesDAO();
            mensualidadesDAO.Actualizar(mensualidadeEmitida);
            registraCreditoCuentaCobrador(mensualidadeEmitida);

            Caja pago = new Caja();

            pago.setConcepto("Cobro mensualidad socio '" + mensualidadeEmitida.getSocio().getNombre() + "'");
            pago.setRubro(new Rubro(1));
            pago.setFechaMovimiento(new Date());
            pago.setEntrada(mensualidadeEmitida.getValor());
            pago.setSalida(0.0);
            pago.setSaldo(buscaSaldoAnterior() + pago.getEntrada());
            pago.setSectores((Sectores) cbSectores.getSelectedItem());

            CajaDAO cajaDAO = new CajaDAO();
            cajaDAO.Salvar(pago);

            JOptionPane.showMessageDialog(null, "Pago registrado");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar el SQL deseado : " + ex + "");
            ex.printStackTrace();
        }

    }

    private void registraCreditoCuentaCobrador(Mensualidades pago) {
        try {
            CcCobrador credito = new CcCobrador();
            credito.setCobrador(pago.getCobrador());
            credito.setCredito(pago.getValor());
            credito.setDebito(0.0);
            credito.setDescripcion("Recibo Nro: " + pago.getId());
            credito.setFechaMovimiento(new Date());

            ccCobradorDAO = new CcCobradorDAO();
            ccCobradorDAO.Salvar(credito);
        } catch (Exception ex) {
            Logger.getLogger(RegistrarPagosMensualidades.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    Double buscaSaldoAnterior() {
        Double saldoAnterior = 0.0;
        CajaDAO cajaDAO = new CajaDAO();
        saldoAnterior = cajaDAO.BuscaSaldoAnterior().getSaldo();
        return saldoAnterior;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

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
        jButton1 = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        LblCodigoCobrador2 = new javax.swing.JLabel();
        txtUnSocio = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbSectores = new javax.swing.JComboBox();
        dp = new datechooser.beans.DateChooserCombo();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Generar Recibos de Mensualidades");
        setPreferredSize(new java.awt.Dimension(900, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMinimumSize(new java.awt.Dimension(529, 340));
        jPanel1.setPreferredSize(new java.awt.Dimension(607, 300));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Fecha de Vencimiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 12, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel5.setText("Mensaje para imprimir en los recibos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtAreaMsj, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        LblCodigoCobrador1.setText("Primero"); // NOI18N
        jPanel5.add(LblCodigoCobrador1, new java.awt.GridBagConstraints());

        jLabel17.setText("Último"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel17, gridBagConstraints);

        txtInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInicioKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtInicio, gridBagConstraints);

        txtFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFinKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtFin, gridBagConstraints);

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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel5.add(jTabbedPane1, gridBagConstraints);

        jLabel19.setText("Categoría"); // NOI18N

        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Seleccionar categoría", jPanel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel5.add(jTabbedPane2, gridBagConstraints);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Generar recibos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel5.add(jButton1, gridBagConstraints);

        jTabbedPane3.addTab("Un grupo de Sócios", jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jTabbedPane3, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        LblCodigoCobrador2.setText("Sócio:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel6.add(LblCodigoCobrador2, gridBagConstraints);

        txtUnSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnSocioKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtUnSocio, gridBagConstraints);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Generar Recíbo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jButton2, gridBagConstraints);

        jLabel4.setText("Sectores");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel6.add(jLabel4, gridBagConstraints);

        cbSectores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSectoresActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(cbSectores, gridBagConstraints);

        jTabbedPane4.addTab("Un solo Sócio", jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jTabbedPane4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dp, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel7, gridBagConstraints);

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
                    if (verificaCampos() == false) {
                        txtLog.append("\n Error: Complete todos los campos necessarios!");
                        txtLog.setCaretPosition(txtLog.getDocument().getLength());
                    } else {
                        EmisionMensualidades(txtInicio.getText(), txtFin.getText(), (Cobrador) cbCobrador.getSelectedItem(), (Categoria) cbCategoria.getSelectedItem(), txtLog);
                    }

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    txtLog.setText("");
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh'h'mm'm'ss");
                    String ahora = format.format(date);
                    txtLog.append("\n Inicio " + ahora);
                    txtLog.append("\n Atención los recibos que se generen individualmente automáticamente se registraran como pagos e ingresaran en caja");
                    //txtLog.setCaretPosition(txtLog.getDocument().getLength());

                    if (txtUnSocio.getText().equals("")) {
                        txtLog.append("\n Debe rellenar todos los campos");
                        txtLog.setCaretPosition(txtLog.getDocument().getLength());
                    } else {

                        registraPago(EmisionMensualidad(txtUnSocio.getText(), txtLog));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    txtLog.append("\n Error al generar mensualidades!");
                    txtLog.append(e.toString());
                    txtLog.setCaretPosition(txtLog.getDocument().getLength());
                    guardaLogError();
                }
            }
        }
        ).start();


    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbSectoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSectoresActionPerformed

    }//GEN-LAST:event_cbSectoresActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblCodigoCobrador1;
    private javax.swing.JLabel LblCodigoCobrador2;
    private javax.swing.JComboBox cbCategoria;
    private javax.swing.JComboBox cbCobrador;
    private javax.swing.JComboBox cbSectores;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private datechooser.beans.DateChooserCombo dp;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private java.awt.TextArea txtAreaMsj;
    private javax.swing.JTextField txtFin;
    private javax.swing.JTextField txtInicio;
    private javax.swing.JTextArea txtLog;
    private javax.swing.JTextField txtUnSocio;
    // End of variables declaration//GEN-END:variables

}

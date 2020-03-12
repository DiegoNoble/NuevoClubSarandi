package com.club.views;

import com.club.BEANS.Cobrador;
import com.club.BEANS.Parametros;
import com.club.BEANS.Rubro;
import com.club.BEANS.Sectores;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.ParametrosDAO;
import com.club.DAOs.RubroDAO;
import com.club.DAOs.SectorDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class ParametrosView extends javax.swing.JInternalFrame {

    private ParametrosDAO parametrosDAO;
    private Parametros parametros;

    public ParametrosView() {
        initComponents();
        parametrosDAO = new ParametrosDAO();
        parametros = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);
        inicio();

    }

    private void inicio() {
        comboCobradores();
        comboRubros();
        comboSectores();
        txtEmail.setText(parametros.getCasilla_email());
        txtPswEmail.setText(parametros.getPsw_email());
        txtPswSms.setText(parametros.getPsw_SMS());
        txtTokenCobrosYa.setText(parametros.getTokenCobrosYa());
        txtUrlPost.setText(parametros.getUrlPostCobrosYa());
        txtEmailPadron.setText(parametros.getEmailPadron());
        txtUsuarioSMS.setText(parametros.getUsuario_SMS());
        cbCobrosYa.setSelectedItem(parametros.getCobradorCobrosYa());
        txtApiUrlCrear.setText(parametros.getApiUrlCrear());
        txtUrlConsultaCobranzas.setText(parametros.getUrlConsultaCobranzasCobrosYa());
        cbRubro.setSelectedItem(parametros.getRubroPagoCuotasCampEco());
        cbSector.setSelectedItem(parametros.getSectorCampEco());
        cbSectorFicha.setSelectedItem(parametros.getSectorFicha());
        
        txtMysqlPath.setText(parametros.getMySql_Path());
        txtNombreBasedeDatos.setText(parametros.getNombreBasesDatos());

        txtValorFicha.setText(parametros.getValorFicha().toString());
        txtValorFichaBecados.setText(parametros.getValorFichaBecados().toString());
        
        txtComisionFicha.setText(parametros.getComisionMedicoFicha().toString());
        txtVencimientoFicha.setText(parametros.getVencimientoFicha().toString());
        cbRubroFichaMedica.setSelectedItem(parametros.getRubroFichaMedica());
        cbRubroPagoDoctor.setSelectedItem(parametros.getRubroPagoDoctor());

        txttoleranciaRecibosPendientes.setText(parametros.getToleranciaRecibosPenientes().toString());
    }

    void comboCobradores() {
        cbCobrosYa.removeAllItems();
        CobradorDAO cobradorDAO = new CobradorDAO();
        List<Cobrador> cobradores = cobradorDAO.BuscaTodos(Cobrador.class);
        for (Cobrador cobrador : cobradores) {
            cbCobrosYa.addItem(cobrador);
            
        }
    }

    void comboSectores() {
        cbSector.removeAllItems();
        SectorDAO sectorDAO = new SectorDAO();
        List<Sectores> sectores = sectorDAO.BuscaTodos(Sectores.class);
        for (Sectores sector : sectores) {
            cbSector.addItem(sector);
            cbSectorFicha.addItem(sector);
        }
    }

    public void comboRubros() {

        try {

            RubroDAO rubroDAO = new RubroDAO();
            List<Rubro> listRubros = rubroDAO.BuscaTodos(Rubro.class);
            for (Rubro rubro : listRubros) {
                cbRubro.addItem(rubro);
                cbRubroFichaMedica.addItem(rubro);
                cbRubroPagoDoctor.addItem(rubro);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar comboboxes: " + ex);
            ex.printStackTrace();
        }
    }

    private void habilitaCampos() {

        txtMysqlPath.setEnabled(true);
        txtNombreBasedeDatos.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPswEmail.setEnabled(true);
        txtPswSms.setEnabled(true);
        txtTokenCobrosYa.setEnabled(true);
        txtUrlPost.setEnabled(true);
        txtUrlConsultaCobranzas.setEnabled(true);
        txtApiUrlCrear.setEnabled(true);
        txtUsuarioSMS.setEnabled(true);
        txtEmailPadron.setEnabled(true);
        txtValorFicha.setEnabled(true);
        txtValorFichaBecados.setEnabled(true);
        txtComisionFicha.setEnabled(true);
        txtVencimientoFicha.setEnabled(true);
        txttoleranciaRecibosPendientes.setEnabled(true);
        cbCobrosYa.setEnabled(true);
        cbRubro.setEnabled(true);
        cbRubroFichaMedica.setEnabled(true);
        cbRubroPagoDoctor.setEnabled(true);
        cbSector.setEnabled(true);
        cbSectorFicha.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnEditar.setEnabled(false);

    }

    private void deshabilitaCampos() {
        txtMysqlPath.setEnabled(false);
        txtNombreBasedeDatos.setEnabled(false);
        cbRubro.setEnabled(false);
        txtEmail.setEnabled(false);
        txtPswEmail.setEnabled(false);
        txtPswSms.setEnabled(false);
        txtComisionFicha.setEnabled(false);
        txtTokenCobrosYa.setEnabled(false);
        txtUrlPost.setEnabled(false);
        txtUrlConsultaCobranzas.setEnabled(false);
        txtApiUrlCrear.setEnabled(false);
        txtUsuarioSMS.setEnabled(false);
        txtEmailPadron.setEnabled(false);
        txtValorFicha.setEnabled(false);
        txtValorFichaBecados.setEnabled(false);
        txtVencimientoFicha.setEnabled(false);
        txttoleranciaRecibosPendientes.setEnabled(false);
        cbCobrosYa.setEnabled(false);
        cbRubroFichaMedica.setEnabled(true);
        cbRubroPagoDoctor.setEnabled(true);
        cbSector.setEnabled(false);
        cbSectorFicha.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnEditar.setEnabled(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtTokenCobrosYa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUsuarioSMS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUrlPost = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPswEmail = new javax.swing.JTextField();
        txtPswSms = new javax.swing.JTextField();
        cbCobrosYa = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtApiUrlCrear = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtEmailPadron = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtUrlConsultaCobranzas = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txttoleranciaRecibosPendientes = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbRubro = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        txtNombreBasedeDatos = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtMysqlPath = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbSector = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        txtValorFicha = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtVencimientoFicha = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbRubroFichaMedica = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cbRubroPagoDoctor = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        txtComisionFicha = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cbSectorFicha = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        txtValorFichaBecados = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(900, 450));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Parametros"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        txtTokenCobrosYa.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtTokenCobrosYa, gridBagConstraints);

        jLabel2.setText("Casilla email remitente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel2, gridBagConstraints);

        txtUsuarioSMS.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtUsuarioSMS, gridBagConstraints);

        jLabel5.setText("Usuario SMS Masivos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel5, gridBagConstraints);

        txtUrlPost.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtUrlPost, gridBagConstraints);

        jLabel7.setText("Token CobrosYa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel9.setText("Si socio no tiene mail, envia a:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel9, gridBagConstraints);

        txtEmail.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtEmail, gridBagConstraints);

        jLabel10.setText("Psw Email remitente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel10, gridBagConstraints);

        jLabel11.setText("Psw SMS Masivos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel11, gridBagConstraints);

        txtPswEmail.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtPswEmail, gridBagConstraints);

        txtPswSms.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtPswSms, gridBagConstraints);

        cbCobrosYa.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbCobrosYa, gridBagConstraints);

        jLabel12.setText("Cobrador CobrosYa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel12, gridBagConstraints);

        jLabel13.setText("URL consulta Cobranzas CobrosYA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel13, gridBagConstraints);

        txtApiUrlCrear.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtApiUrlCrear, gridBagConstraints);

        jLabel14.setText("API URL CobrosYA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel14, gridBagConstraints);

        txtEmailPadron.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtEmailPadron, gridBagConstraints);

        jLabel15.setText("URL Post CobrosYA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel15, gridBagConstraints);

        txtUrlConsultaCobranzas.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtUrlConsultaCobranzas, gridBagConstraints);

        jLabel16.setText("Tolerancia recibos pendientes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel16, gridBagConstraints);

        txttoleranciaRecibosPendientes.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txttoleranciaRecibosPendientes, gridBagConstraints);

        jLabel3.setText("Sector Ficha Médica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        cbRubro.setEnabled(false);
        cbRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRubroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbRubro, gridBagConstraints);

        jLabel17.setText("Vencimiento ficha médica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel17, gridBagConstraints);

        txtNombreBasedeDatos.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNombreBasedeDatos, gridBagConstraints);

        jLabel18.setText("Comisión Ficha médica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel18, gridBagConstraints);

        txtMysqlPath.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtMysqlPath, gridBagConstraints);

        jLabel4.setText("Rubro ficha medica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel4, gridBagConstraints);

        cbSector.setEnabled(false);
        cbSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSectorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbSector, gridBagConstraints);

        jLabel19.setText("MySql Path (termina en /)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel19, gridBagConstraints);

        txtValorFicha.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtValorFicha, gridBagConstraints);

        jLabel20.setText("Nombre base de datos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel20, gridBagConstraints);

        txtVencimientoFicha.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtVencimientoFicha, gridBagConstraints);

        jLabel6.setText("Rubro pago doctores");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel6, gridBagConstraints);

        cbRubroFichaMedica.setEnabled(false);
        cbRubroFichaMedica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRubroFichaMedicaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbRubroFichaMedica, gridBagConstraints);

        jLabel8.setText("Rubro pago cuotas campaña economica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel8, gridBagConstraints);

        cbRubroPagoDoctor.setEnabled(false);
        cbRubroPagoDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRubroPagoDoctorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbRubroPagoDoctor, gridBagConstraints);

        jLabel21.setText("Valor Ficha becados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel21, gridBagConstraints);

        txtComisionFicha.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtComisionFicha, gridBagConstraints);

        jLabel22.setText("Sector campaña economica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel22, gridBagConstraints);

        cbSectorFicha.setEnabled(false);
        cbSectorFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSectorFichaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbSectorFicha, gridBagConstraints);

        jLabel23.setText("Valor Ficha médica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel23, gridBagConstraints);

        txtValorFichaBecados.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtValorFichaBecados, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(btnEditar, gridBagConstraints);

        btnGuardar.setText("Salvar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel6.add(btnGuardar, new java.awt.GridBagConstraints());

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(btnCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel6, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jasperRunnerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FichaMedicaActionPerformed


    }//GEN-LAST:event_FichaMedicaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        habilitaCampos();

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        try {
            deshabilitaCampos();

            parametros.setCasilla_email(txtEmail.getText());

            parametros.setPsw_SMS(txtPswSms.getText());
            parametros.setPsw_email(txtPswEmail.getText());
            parametros.setTokenCobrosYa(txtTokenCobrosYa.getText());
            parametros.setUrlPostCobrosYa(txtUrlPost.getText());
            parametros.setUrlConsultaCobranzasCobrosYa(txtUrlConsultaCobranzas.getText());
            parametros.setUsuario_SMS(txtUsuarioSMS.getText());
            parametros.setApiUrlCrear(txtApiUrlCrear.getText());
            parametros.setEmailPadron(txtEmailPadron.getText());
            parametros.setCobradorCobrosYa((Cobrador) cbCobrosYa.getSelectedItem());
            parametros.setToleranciaRecibosPenientes(Integer.parseInt(txttoleranciaRecibosPendientes.getText()));
            parametros.setRubroPagoCuotasCampEco((Rubro) cbRubro.getSelectedItem());
            parametros.setMySql_Path(txtMysqlPath.getText());
            parametros.setNombreBasesDatos(txtNombreBasedeDatos.getText());
            parametros.setSectorCampEco((Sectores) cbSector.getSelectedItem());
            parametros.setSectorFicha((Sectores) cbSectorFicha.getSelectedItem());

            parametros.setValorFicha(Double.valueOf(txtValorFicha.getText()));
            parametros.setValorFichaBecados(Double.valueOf(txtValorFichaBecados.getText()));
            parametros.setComisionMedicoFicha(Double.valueOf(txtComisionFicha.getText()));
            parametros.setVencimientoFicha(Integer.valueOf(txtVencimientoFicha.getText()));

            parametros.setRubroFichaMedica((Rubro) cbRubroFichaMedica.getSelectedItem());
            parametros.setRubroPagoDoctor((Rubro) cbRubroPagoDoctor.getSelectedItem());

            parametrosDAO = new ParametrosDAO();
            parametrosDAO.Actualizar(parametros);
        } catch (Exception ex) {
            Logger.getLogger(ParametrosView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        deshabilitaCampos();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRubroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbRubroActionPerformed

    private void cbSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSectorActionPerformed

    private void cbRubroFichaMedicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRubroFichaMedicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbRubroFichaMedicaActionPerformed

    private void cbRubroPagoDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRubroPagoDoctorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbRubroPagoDoctorActionPerformed

    private void cbSectorFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSectorFichaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSectorFichaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cbCobrosYa;
    private javax.swing.JComboBox cbRubro;
    private javax.swing.JComboBox cbRubroFichaMedica;
    private javax.swing.JComboBox cbRubroPagoDoctor;
    private javax.swing.JComboBox cbSector;
    private javax.swing.JComboBox cbSectorFicha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField txtApiUrlCrear;
    private javax.swing.JTextField txtComisionFicha;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmailPadron;
    private javax.swing.JTextField txtMysqlPath;
    private javax.swing.JTextField txtNombreBasedeDatos;
    private javax.swing.JTextField txtPswEmail;
    private javax.swing.JTextField txtPswSms;
    private javax.swing.JTextField txtTokenCobrosYa;
    private javax.swing.JTextField txtUrlConsultaCobranzas;
    private javax.swing.JTextField txtUrlPost;
    private javax.swing.JTextField txtUsuarioSMS;
    private javax.swing.JTextField txtValorFicha;
    private javax.swing.JTextField txtValorFichaBecados;
    private javax.swing.JTextField txtVencimientoFicha;
    private javax.swing.JTextField txttoleranciaRecibosPendientes;
    // End of variables declaration//GEN-END:variables
}

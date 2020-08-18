package com.club.views;

import com.club.BEANS.Usuario;
import com.club.control.utilidades.data;
import com.club.smsmasivos.SMSMasivosController;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import com.club.smsmasivos.RecibeRespuestasSMSController;

public class Principal extends javax.swing.JFrame {

    data muestraData;
    data muestraHora;
    Usuario usuario;

    public Principal(Usuario usuario) {

        initComponents();
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
//jLabel1.setVisible(false);
        this.usuario = usuario;
        jlbNombre.setText(usuario.getNombre());
        jlbPerfil.setText(usuario.getPerfil());
        muestraData = new data();
        muestraData.le_data();
        lblFecha.setText(muestraData.dia + "," + muestraData.dia_semana + " de " + muestraData.mes_del_ano + " de " + muestraData.ano);

        if (jlbPerfil.getText().equals("Usuario")) {

            mnuRegistros.setEnabled(false);
            mnuHabilitacionMedica.setEnabled(false);
            mnuMensualidades.setEnabled(false);
            mnuItemUsuarios.setEnabled(false);
            //   mniItemMovimientosCaja.setEnabled(false);
        }

        if (jlbPerfil.getText().equals("Operador")) {

            mnuItemUsuarios.setEnabled(false);

        }

        if (jlbPerfil.getText().equals("Gerencia")) {

            mnuItemUsuarios.setEnabled(false);
        }

        if (jlbPerfil.getText().equals("Portero")) {

            mnuItemUsuarios.setVisible(false);
            mnuRegistros.setVisible(false);
            mnuHabilitacionMedica.setVisible(false);
            mnuMensualidades.setVisible(false);
            mnuArqueo.setVisible(false);
            mnuConsultas.setVisible(false);
            mnuControlPresencia.setVisible(false);
            mnuHabilitacionMedica.setVisible(false);
            mnuCampanaSMS.setVisible(false);
            mnuCampanaEco.setVisible(false);
            btnCaja.setVisible(false);
            btndependientes.setVisible(false);
            btnMensualidades.setVisible(false);
            btnSocio.setVisible(false);
        }

        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1024, 750);             //Ajusta tamanho do form principal
        setLocationRelativeTo(null);   //Centraliza o Form principal no meio da tela

    }

    public Principal() {

        initComponents();

        muestraData = new data();
        muestraData.le_data();
        lblFecha.setText(muestraData.dia + "," + muestraData.dia_semana + " de " + muestraData.mes_del_ano + " de " + muestraData.ano);

        if (jlbPerfil.getText().equals("Usuario")) {

            mnuRegistros.setEnabled(false);
            mnuHabilitacionMedica.setEnabled(false);
            mnuMensualidades.setEnabled(false);
            mnuItemUsuarios.setEnabled(false);
            // mniItemMovimientosCaja.setEnabled(false);
        }

        if (jlbPerfil.getText().equals("Operador")) {

            mnuItemUsuarios.setEnabled(false);

        }

        if (jlbPerfil.getText().equals("Gerencia")) {

            mnuItemUsuarios.setEnabled(false);
        }

        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1024, 750);             //Ajusta tamanho do form principal
        setLocationRelativeTo(null);   //Centraliza o Form principal no meio da tela

    }

    private void centralizaVentanas(JInternalFrame internalFrame) {
        internalFrame.setLocation((this.getWidth() - internalFrame.getWidth()) / 2,
                (this.getHeight() - internalFrame.getHeight()) / 2);
    }

    private static void setLogs() throws IOException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh'h'mm'm'ss");
        String agora = format.format(date);

        File out = new File("logs/System.out");
        File err = new File("logs/System.err");
        if (!out.exists()) {
            out.mkdirs();
        }
        if (!err.exists()) {
            err.mkdirs();
        }

        System.setOut(
                new PrintStream(
                        new FileOutputStream("logs/System.out/" + agora + ".txt", true)));

        System.setErr(
                new PrintStream(
                        new FileOutputStream("logs/System.err/" + agora + ".txt", true)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnSocio = new javax.swing.JButton();
        btndependientes = new javax.swing.JButton();
        btnMensualidades = new javax.swing.JButton();
        btnCaja = new javax.swing.JButton();
        btnMarcajes = new javax.swing.JButton();
        btnTitulares = new javax.swing.JButton();
        btnRegistroDependientes = new javax.swing.JButton();
        btnRegistroDependientes1 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        lblFecha = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        jlbNombre = new javax.swing.JLabel();
        jlbPerfil = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuSistema = new javax.swing.JMenu();
        mnuItemUsuarios = new javax.swing.JMenuItem();
        mnuItemUsuarios1 = new javax.swing.JMenuItem();
        mnuItemSalir = new javax.swing.JMenuItem();
        mnuItemUsuarios2 = new javax.swing.JMenuItem();
        mnuItemUsuarios3 = new javax.swing.JMenuItem();
        mnuRegistros = new javax.swing.JMenu();
        mnuItemSocios = new javax.swing.JMenuItem();
        mnuItemDependientes = new javax.swing.JMenuItem();
        mnuItemCobrador = new javax.swing.JMenuItem();
        mnuItemCategoria = new javax.swing.JMenuItem();
        mnuItemCategoria1 = new javax.swing.JMenuItem();
        mnuItemCategoria3 = new javax.swing.JMenuItem();
        mnuItemCategoria2 = new javax.swing.JMenuItem();
        mnuItemCaja1 = new javax.swing.JMenuItem();
        mnuItemCaja2 = new javax.swing.JMenuItem();
        mnuConsultas = new javax.swing.JMenu();
        mnuItemConsultaSocio2 = new javax.swing.JMenuItem();
        mnuitemConsultaDependientes = new javax.swing.JMenuItem();
        mnuitemConsultaMensualidades = new javax.swing.JMenuItem();
        mnuItemConsultaSocio1 = new javax.swing.JMenuItem();
        mnuItemConsultaSocio3 = new javax.swing.JMenuItem();
        mniItemMovimientosCaja1 = new javax.swing.JMenuItem();
        mnuItemConsultaSocio4 = new javax.swing.JMenuItem();
        mnuItemConsultaSocio5 = new javax.swing.JMenuItem();
        mnuMensualidades = new javax.swing.JMenu();
        mnuItemRecibos = new javax.swing.JMenuItem();
        mnuItemPagos = new javax.swing.JMenuItem();
        mnuItemRecibos1 = new javax.swing.JMenuItem();
        mnuItemRecibos2 = new javax.swing.JMenuItem();
        mnuItemRecibos3 = new javax.swing.JMenuItem();
        mnuItemRecibos4 = new javax.swing.JMenuItem();
        mnuArqueo = new javax.swing.JMenu();
        mnuItemArqueo = new javax.swing.JMenuItem();
        mnuHabilitacionMedica = new javax.swing.JMenu();
        mnuItemHabilitacionTitulares = new javax.swing.JMenuItem();
        mnuItemHabilitacionDependientes = new javax.swing.JMenuItem();
        mnuItemHabilitacionTitulares1 = new javax.swing.JMenuItem();
        mnuControlPresencia = new javax.swing.JMenu();
        mnuItemArqueo1 = new javax.swing.JMenuItem();
        mnuItemMarcajes = new javax.swing.JMenuItem();
        mnuItemArqueo2 = new javax.swing.JMenuItem();
        mnuCampanaSMS = new javax.swing.JMenu();
        mnuItemArqueo3 = new javax.swing.JMenuItem();
        mnuItemArqueo4 = new javax.swing.JMenuItem();
        mnuItemArqueo5 = new javax.swing.JMenuItem();
        mnuCampanaEco = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        mnuItemCaja3 = new javax.swing.JMenuItem();
        btnAsignarPremio = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuItemArqueo6 = new javax.swing.JMenuItem();
        mnuItemArqueo7 = new javax.swing.JMenuItem();
        mnuItemCaja4 = new javax.swing.JMenuItem();
        mniItemMovimientosCaja3 = new javax.swing.JMenuItem();
        mniItemMovimientosCaja4 = new javax.swing.JMenuItem();
        mnuItemArqueo8 = new javax.swing.JMenuItem();
        mnuItemArqueo9 = new javax.swing.JMenuItem();
        mnuAyuda = new javax.swing.JMenu();
        mnuItemSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sis. Control de Clubes -- DNSoft .");
        setBackground(new java.awt.Color(255, 255, 255));

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Escudo.jpg"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(1024, 750));
        jDesktopPane1.add(jLabel1);
        jLabel1.setBounds(280, 0, 840, 660);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSocio.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnSocio.setText("Consultar Socios");
        btnSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSocioActionPerformed(evt);
            }
        });

        btndependientes.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btndependientes.setText("Consultar Dependientes");
        btndependientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndependientesActionPerformed(evt);
            }
        });

        btnMensualidades.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnMensualidades.setText("Consultar Mensualidades");
        btnMensualidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMensualidadesActionPerformed(evt);
            }
        });

        btnCaja.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCaja.setText("Movimientos de Caja");
        btnCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaActionPerformed(evt);
            }
        });

        btnMarcajes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnMarcajes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/reloj.png"))); // NOI18N
        btnMarcajes.setText("Reloj personal");
        btnMarcajes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMarcajes.setIconTextGap(-3);
        btnMarcajes.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnMarcajes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMarcajes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcajesActionPerformed(evt);
            }
        });

        btnTitulares.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnTitulares.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/registro1.png"))); // NOI18N
        btnTitulares.setText("Socios TITULARES");
        btnTitulares.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTitulares.setIconTextGap(-3);
        btnTitulares.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnTitulares.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTitulares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTitularesActionPerformed(evt);
            }
        });

        btnRegistroDependientes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnRegistroDependientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/registro1.png"))); // NOI18N
        btnRegistroDependientes.setText("Socios DEPENDIENTES");
        btnRegistroDependientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegistroDependientes.setIconTextGap(-3);
        btnRegistroDependientes.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnRegistroDependientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegistroDependientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroDependientesActionPerformed(evt);
            }
        });

        btnRegistroDependientes1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnRegistroDependientes1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/socios.png"))); // NOI18N
        btnRegistroDependientes1.setText("Consultar ingresos");
        btnRegistroDependientes1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegistroDependientes1.setIconTextGap(-3);
        btnRegistroDependientes1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnRegistroDependientes1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegistroDependientes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroDependientes1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegistroDependientes1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnSocio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btndependientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCaja, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMensualidades, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btnTitulares, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistroDependientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnMarcajes, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 71, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btndependientes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMensualidades, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMarcajes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRegistroDependientes1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTitulares)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRegistroDependientes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDesktopPane1.add(jPanel1);
        jPanel1.setBounds(0, 0, 270, 630);

        jToolBar1.setRollover(true);

        lblFecha.setText("Data");

        lblHora.setText("Hora");

        jlbNombre.setText("Nombre");

        jlbPerfil.setText("Perfil");

        jLabel2.setText("Usuario:");

        jLabel3.setText("Perfil:");

        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        mnuSistema.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuSistema.setText("Sistema"); // NOI18N
        mnuSistema.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        mnuItemUsuarios.setText("Usuarios");
        mnuItemUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemUsuariosActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemUsuarios);

        mnuItemUsuarios1.setText("Parametros");
        mnuItemUsuarios1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemUsuarios1ActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemUsuarios1);

        mnuItemSalir.setText("Salir"); // NOI18N
        mnuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSalirActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemSalir);

        mnuItemUsuarios2.setText(" Act. Saldo Caja");
        mnuItemUsuarios2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemUsuarios2ActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemUsuarios2);

        mnuItemUsuarios3.setText("Backup BD");
        mnuItemUsuarios3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemUsuarios3ActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemUsuarios3);

        jMenuBar1.add(mnuSistema);

        mnuRegistros.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuRegistros.setText("Registros"); // NOI18N
        mnuRegistros.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        mnuItemSocios.setText("Socios"); // NOI18N
        mnuItemSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSociosActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemSocios);

        mnuItemDependientes.setText("Dependientes"); // NOI18N
        mnuItemDependientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemDependientesActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemDependientes);

        mnuItemCobrador.setText("Cobradores"); // NOI18N
        mnuItemCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCobradorActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemCobrador);

        mnuItemCategoria.setText("Categorías"); // NOI18N
        mnuItemCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCategoriaActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemCategoria);

        mnuItemCategoria1.setText("Rubros"); // NOI18N
        mnuItemCategoria1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCategoria1ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemCategoria1);

        mnuItemCategoria3.setText("Unificar Rubros"); // NOI18N
        mnuItemCategoria3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCategoria3ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemCategoria3);

        mnuItemCategoria2.setText("Sectores"); // NOI18N
        mnuItemCategoria2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCategoria2ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemCategoria2);

        mnuItemCaja1.setText("Movimientos de Caja");
        mnuItemCaja1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCaja1ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemCaja1);

        mnuItemCaja2.setText("Pago Sueldos");
        mnuItemCaja2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCaja2ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemCaja2);

        jMenuBar1.add(mnuRegistros);

        mnuConsultas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuConsultas.setText("Consultas");
        mnuConsultas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        mnuItemConsultaSocio2.setText("Socios");
        mnuItemConsultaSocio2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaSocio2ActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuItemConsultaSocio2);

        mnuitemConsultaDependientes.setText("Dependientes");
        mnuitemConsultaDependientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuitemConsultaDependientesActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuitemConsultaDependientes);

        mnuitemConsultaMensualidades.setText("Mensualidades");
        mnuitemConsultaMensualidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuitemConsultaMensualidadesActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuitemConsultaMensualidades);

        mnuItemConsultaSocio1.setText("Prueba de Huella");
        mnuItemConsultaSocio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaSocio1ActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuItemConsultaSocio1);

        mnuItemConsultaSocio3.setText("Socios por Huella digital");
        mnuItemConsultaSocio3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaSocio3ActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuItemConsultaSocio3);

        mniItemMovimientosCaja1.setText("Movimientos de Caja");
        mniItemMovimientosCaja1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniItemMovimientosCaja1ActionPerformed(evt);
            }
        });
        mnuConsultas.add(mniItemMovimientosCaja1);

        mnuItemConsultaSocio4.setText("Filtros avanzados de Socios");
        mnuItemConsultaSocio4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaSocio4ActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuItemConsultaSocio4);

        mnuItemConsultaSocio5.setText("Filtros avanzados de Dependientes");
        mnuItemConsultaSocio5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaSocio5ActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuItemConsultaSocio5);

        jMenuBar1.add(mnuConsultas);

        mnuMensualidades.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuMensualidades.setText("Mensualidades");
        mnuMensualidades.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        mnuItemRecibos.setText("Generar Recibos");
        mnuItemRecibos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRecibosActionPerformed(evt);
            }
        });
        mnuMensualidades.add(mnuItemRecibos);

        mnuItemPagos.setText("Registrar Pagos");
        mnuItemPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemPagosActionPerformed(evt);
            }
        });
        mnuMensualidades.add(mnuItemPagos);

        mnuItemRecibos1.setText("Listados");
        mnuItemRecibos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRecibos1ActionPerformed(evt);
            }
        });
        mnuMensualidades.add(mnuItemRecibos1);

        mnuItemRecibos2.setText("Generar talones Cobros Ya");
        mnuItemRecibos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRecibos2ActionPerformed(evt);
            }
        });
        mnuMensualidades.add(mnuItemRecibos2);

        mnuItemRecibos3.setText("Actualizar talones CobrosYa");
        mnuItemRecibos3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRecibos3ActionPerformed(evt);
            }
        });
        mnuMensualidades.add(mnuItemRecibos3);

        mnuItemRecibos4.setText("Generar Recibos Inactivos");
        mnuItemRecibos4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRecibos4ActionPerformed(evt);
            }
        });
        mnuMensualidades.add(mnuItemRecibos4);

        jMenuBar1.add(mnuMensualidades);

        mnuArqueo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuArqueo.setText("Arqueo de Cobradores");
        mnuArqueo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        mnuItemArqueo.setText("Arqueos");
        mnuItemArqueo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueoActionPerformed(evt);
            }
        });
        mnuArqueo.add(mnuItemArqueo);

        jMenuBar1.add(mnuArqueo);

        mnuHabilitacionMedica.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuHabilitacionMedica.setText("Habilitación Medica");
        mnuHabilitacionMedica.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        mnuItemHabilitacionTitulares.setText("Emición de Habilitación Titulares");
        mnuItemHabilitacionTitulares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemHabilitacionTitularesActionPerformed(evt);
            }
        });
        mnuHabilitacionMedica.add(mnuItemHabilitacionTitulares);

        mnuItemHabilitacionDependientes.setText("Emición de Habilitación Dependientes");
        mnuItemHabilitacionDependientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemHabilitacionDependientesActionPerformed(evt);
            }
        });
        mnuHabilitacionMedica.add(mnuItemHabilitacionDependientes);

        mnuItemHabilitacionTitulares1.setText("Registra exámen medico Titulares");
        mnuItemHabilitacionTitulares1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemHabilitacionTitulares1ActionPerformed(evt);
            }
        });
        mnuHabilitacionMedica.add(mnuItemHabilitacionTitulares1);

        jMenuBar1.add(mnuHabilitacionMedica);

        mnuControlPresencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuControlPresencia.setText("Control de presencia");
        mnuControlPresencia.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        mnuItemArqueo1.setText(" Funcionarios");
        mnuItemArqueo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueo1ActionPerformed(evt);
            }
        });
        mnuControlPresencia.add(mnuItemArqueo1);

        mnuItemMarcajes.setText("Marcajes x Huella");
        mnuItemMarcajes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemMarcajesActionPerformed(evt);
            }
        });
        mnuControlPresencia.add(mnuItemMarcajes);

        mnuItemArqueo2.setText("Control de marcajes ");
        mnuItemArqueo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueo2ActionPerformed(evt);
            }
        });
        mnuControlPresencia.add(mnuItemArqueo2);

        jMenuBar1.add(mnuControlPresencia);

        mnuCampanaSMS.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuCampanaSMS.setText("Campañas comunicación SMS");
        mnuCampanaSMS.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        mnuItemArqueo3.setText("Nueva campaña");
        mnuItemArqueo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueo3ActionPerformed(evt);
            }
        });
        mnuCampanaSMS.add(mnuItemArqueo3);

        mnuItemArqueo4.setText("Recibe respuestas SMS");
        mnuItemArqueo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueo4ActionPerformed(evt);
            }
        });
        mnuCampanaSMS.add(mnuItemArqueo4);

        mnuItemArqueo5.setText("Consulta campañas");
        mnuItemArqueo5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueo5ActionPerformed(evt);
            }
        });
        mnuCampanaSMS.add(mnuItemArqueo5);

        jMenuBar1.add(mnuCampanaSMS);

        mnuCampanaEco.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuCampanaEco.setText("Campaña económica");
        mnuCampanaEco.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jMenu1.setText("Caja");

        mnuItemCaja3.setText("Movimientos de caja");
        mnuItemCaja3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCaja3ActionPerformed(evt);
            }
        });
        jMenu1.add(mnuItemCaja3);

        btnAsignarPremio.setText("Consulta movimientos");
        btnAsignarPremio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarPremioActionPerformed(evt);
            }
        });
        jMenu1.add(btnAsignarPremio);

        mnuCampanaEco.add(jMenu1);

        jMenu2.setText("Campañas económicas");

        mnuItemArqueo6.setText("Campañas");
        mnuItemArqueo6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueo6ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuItemArqueo6);

        mnuItemArqueo7.setText("Números");
        mnuItemArqueo7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueo7ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuItemArqueo7);

        mnuItemCaja4.setText("Premios");
        mnuItemCaja4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCaja4ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuItemCaja4);

        mniItemMovimientosCaja3.setText("Asignar premios");
        mniItemMovimientosCaja3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniItemMovimientosCaja3ActionPerformed(evt);
            }
        });
        jMenu2.add(mniItemMovimientosCaja3);

        mniItemMovimientosCaja4.setText("Consulta premios entregados");
        mniItemMovimientosCaja4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniItemMovimientosCaja4ActionPerformed(evt);
            }
        });
        jMenu2.add(mniItemMovimientosCaja4);

        mnuCampanaEco.add(jMenu2);

        mnuItemArqueo8.setText("Ventas camp. Eco.");
        mnuItemArqueo8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueo8ActionPerformed(evt);
            }
        });
        mnuCampanaEco.add(mnuItemArqueo8);

        mnuItemArqueo9.setText("Consulta ventas | Registra pagos");
        mnuItemArqueo9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemArqueo9ActionPerformed(evt);
            }
        });
        mnuCampanaEco.add(mnuItemArqueo9);

        jMenuBar1.add(mnuCampanaEco);

        mnuAyuda.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda.setText("Ayuda"); // NOI18N
        mnuAyuda.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        mnuItemSobre.setText("Sobre"); // NOI18N
        mnuItemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSobreActionPerformed(evt);
            }
        });
        mnuAyuda.add(mnuItemSobre);

        jMenuBar1.add(mnuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jlbNombre)
                .addGap(76, 76, 76)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlbPerfil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 469, Short.MAX_VALUE)
                .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1125, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHora)
                        .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbNombre)
                        .addComponent(jlbPerfil)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuItemSalirActionPerformed

    private void mnuItemSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSobreActionPerformed


    }//GEN-LAST:event_mnuItemSobreActionPerformed

    private void mnuItemSociosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSociosActionPerformed

        SocioFrameCompleto socio = new SocioFrameCompleto();
        jDesktopPane1.add(socio);
        socio.setVisible(true);
        try {
            socio.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_mnuItemSociosActionPerformed

    private void mnuItemCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCobradorActionPerformed

        cobradorView cobrador = new cobradorView();
        jDesktopPane1.add(cobrador);
        cobrador.setVisible(true);
        centralizaVentanas(cobrador);
    }//GEN-LAST:event_mnuItemCobradorActionPerformed

    private void mnuItemDependientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemDependientesActionPerformed

        DependienteFrameCompleto dependiente = new DependienteFrameCompleto();
        jDesktopPane1.add(dependiente);
        dependiente.setVisible(true);
        try {
            dependiente.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mnuItemDependientesActionPerformed

    private void mnuItemCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCategoriaActionPerformed

        CategoriaFrame categoria = new CategoriaFrame();
        jDesktopPane1.add(categoria);
        categoria.setVisible(true);
        centralizaVentanas(categoria);

    }//GEN-LAST:event_mnuItemCategoriaActionPerformed

    private void timer1OnTime(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timer1OnTime

        muestraData.leeHora();
        lblHora.setText("La hora actual es: " + muestraData.hora);

    }//GEN-LAST:event_timer1OnTime

    private void mnuitemConsultaDependientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuitemConsultaDependientesActionPerformed

        DependienteFrameConsulta dependiente = new DependienteFrameConsulta();
        jDesktopPane1.add(dependiente);
        dependiente.setVisible(true);
        //try {
        //  dependiente.setMaximum(true);
        centralizaVentanas(dependiente);
        //} catch (PropertyVetoException ex) {
        //  Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        // }

    }//GEN-LAST:event_mnuitemConsultaDependientesActionPerformed

    private void mnuItemRecibosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRecibosActionPerformed

        EmiteMensualidades mensualidades = new EmiteMensualidades();
        jDesktopPane1.add(mensualidades);

        mensualidades.setSize(jDesktopPane1.getSize());
        centralizaVentanas(mensualidades);
        mensualidades.setVisible(true);
        centralizaVentanas(mensualidades);

    }//GEN-LAST:event_mnuItemRecibosActionPerformed

    private void mnuItemPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemPagosActionPerformed

        RegistrarPagosMensualidades pagos = new RegistrarPagosMensualidades();
        jDesktopPane1.add(pagos);
        pagos.setVisible(true);
        centralizaVentanas(pagos);
    }//GEN-LAST:event_mnuItemPagosActionPerformed

    private void mnuitemConsultaMensualidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuitemConsultaMensualidadesActionPerformed

        ConsultaMensualidades mensualidades = new ConsultaMensualidades();
        jDesktopPane1.add(mensualidades);
        mensualidades.setVisible(true);
        centralizaVentanas(mensualidades);
        try {
            mensualidades.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_mnuitemConsultaMensualidadesActionPerformed

    private void mnuItemHabilitacionTitularesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemHabilitacionTitularesActionPerformed

        HabilitacionMedica habilitacion = new HabilitacionMedica(usuario);
        jDesktopPane1.add(habilitacion);
        habilitacion.setVisible(true);
        centralizaVentanas(habilitacion);
       
    }//GEN-LAST:event_mnuItemHabilitacionTitularesActionPerformed

    private void mnuItemUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemUsuariosActionPerformed

        registroUsuarios usuarios = new registroUsuarios();
        jDesktopPane1.add(usuarios);
        usuarios.setVisible(true);
        centralizaVentanas(usuarios);
    }//GEN-LAST:event_mnuItemUsuariosActionPerformed

    private void mnuItemHabilitacionDependientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemHabilitacionDependientesActionPerformed

        HabilitacionMedicaDependientes habilitacion = new HabilitacionMedicaDependientes(usuario);
        jDesktopPane1.add(habilitacion);
        habilitacion.setVisible(true);
        centralizaVentanas(habilitacion);
       

    }//GEN-LAST:event_mnuItemHabilitacionDependientesActionPerformed

    private void btnCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaActionPerformed


    }//GEN-LAST:event_btnCajaActionPerformed

    private void btnMensualidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMensualidadesActionPerformed

        ConsultaMensualidades mensualidades = new ConsultaMensualidades();
        jDesktopPane1.add(mensualidades);
        mensualidades.setVisible(true);
        centralizaVentanas(mensualidades);
        try {
            mensualidades.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMensualidadesActionPerformed

    private void btndependientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndependientesActionPerformed

        DependienteFrameConsulta dependiente = new DependienteFrameConsulta();
        jDesktopPane1.add(dependiente);
        dependiente.setVisible(true);
        // try {
        //dependiente.setMaximum(true);
        centralizaVentanas(dependiente);
        //} catch (PropertyVetoException ex) {
        //  Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }//GEN-LAST:event_btndependientesActionPerformed

    private void btnSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSocioActionPerformed

        SocioFrameCompleto socio = new SocioFrameCompleto(true);
        jDesktopPane1.add(socio);
        socio.setVisible(true);
        try {
            socio.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSocioActionPerformed

    private void mnuItemArqueoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueoActionPerformed

        FormArqueos formArqueo = new FormArqueos();
        jDesktopPane1.add(formArqueo);
        formArqueo.setVisible(true);

    }//GEN-LAST:event_mnuItemArqueoActionPerformed

    private void mnuItemConsultaSocio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaSocio1ActionPerformed

        ConsultaSocioxHuella huella = new ConsultaSocioxHuella();
        jDesktopPane1.add(huella);
        huella.setVisible(true);

    }//GEN-LAST:event_mnuItemConsultaSocio1ActionPerformed

    private void mnuItemConsultaSocio2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaSocio2ActionPerformed
        SocioFrameCompleto socio = new SocioFrameCompleto(true);
        jDesktopPane1.add(socio);
        socio.setVisible(true);
        try {
            socio.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mnuItemConsultaSocio2ActionPerformed

    private void mnuItemConsultaSocio3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaSocio3ActionPerformed
        ControlDeAccesos mensualidades = new ControlDeAccesos();
        jDesktopPane1.add(mensualidades);
        mensualidades.setVisible(true);
        centralizaVentanas(mensualidades);
        try {
            mensualidades.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mnuItemConsultaSocio3ActionPerformed

    private void mnuItemArqueo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueo1ActionPerformed

        FuncionarioView funcionarios = new FuncionarioView();
        jDesktopPane1.add(funcionarios);
        funcionarios.setVisible(true);
        try {
            funcionarios.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mnuItemArqueo1ActionPerformed

    private void mnuItemMarcajesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemMarcajesActionPerformed
        MarcarjeFuncionarioxHuella huella = new MarcarjeFuncionarioxHuella();
        jDesktopPane1.add(huella);
        huella.setVisible(true);
    }//GEN-LAST:event_mnuItemMarcajesActionPerformed

    private void mnuItemArqueo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueo2ActionPerformed
        ConsultaMarcasFuncionariosView consultaMarcajes = new ConsultaMarcasFuncionariosView();
        jDesktopPane1.add(consultaMarcajes);
        consultaMarcajes.setVisible(true);
        try {
            consultaMarcajes.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mnuItemArqueo2ActionPerformed

    private void btnMarcajesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcajesActionPerformed
        MarcarjeFuncionarioxHuella huella = new MarcarjeFuncionarioxHuella();
        jDesktopPane1.add(huella);
        huella.setVisible(true);
    }//GEN-LAST:event_btnMarcajesActionPerformed

    private void btnTitularesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTitularesActionPerformed

        SocioFrameCompleto socio = new SocioFrameCompleto();
        jDesktopPane1.add(socio);
        socio.setVisible(true);
        try {
            socio.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnTitularesActionPerformed

    private void btnRegistroDependientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroDependientesActionPerformed
        DependienteFrameCompleto dependiente = new DependienteFrameCompleto();
        jDesktopPane1.add(dependiente);
        dependiente.setVisible(true);
        try {
            dependiente.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistroDependientesActionPerformed

    private void btnRegistroDependientes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroDependientes1ActionPerformed
        ControlDeAccesos mensualidades = new ControlDeAccesos();
        jDesktopPane1.add(mensualidades);
        mensualidades.setVisible(true);
        centralizaVentanas(mensualidades);
        try {
            //mensualidades.setMaximum(true);
            //centralizaVentanas(socio);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistroDependientes1ActionPerformed

    private void mnuItemCategoria1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCategoria1ActionPerformed
        RubrosView rubros = new RubrosView();
        jDesktopPane1.add(rubros);
        rubros.setVisible(true);
        centralizaVentanas(rubros);
    }//GEN-LAST:event_mnuItemCategoria1ActionPerformed

    private void mnuItemCategoria2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCategoria2ActionPerformed
        sectoresFrame sectores = new sectoresFrame();
        jDesktopPane1.add(sectores);
        sectores.setVisible(true);
        centralizaVentanas(sectores);

    }//GEN-LAST:event_mnuItemCategoria2ActionPerformed

    private void mnuItemCategoria3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCategoria3ActionPerformed
        UnificarRubrosView unificarRubros = new UnificarRubrosView();
        jDesktopPane1.add(unificarRubros);
        unificarRubros.setVisible(true);
        centralizaVentanas(unificarRubros);
    }//GEN-LAST:event_mnuItemCategoria3ActionPerformed

    private void mnuItemRecibos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRecibos1ActionPerformed

        ArqueoCobradoresView arqueoCobradores = new ArqueoCobradoresView();
        jDesktopPane1.add(arqueoCobradores);
        arqueoCobradores.setVisible(true);
        centralizaVentanas(arqueoCobradores);
    }//GEN-LAST:event_mnuItemRecibos1ActionPerformed

    private void mnuItemArqueo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueo3ActionPerformed
        SMSMasivosView smsView = new SMSMasivosView();
        SMSMasivosController controller = new SMSMasivosController(smsView, jDesktopPane1);
        controller.go();
    }//GEN-LAST:event_mnuItemArqueo3ActionPerformed

    private void mnuItemArqueo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueo4ActionPerformed
        RecibeRespuestasSMSView consulta = new RecibeRespuestasSMSView();
        RecibeRespuestasSMSController controller = new RecibeRespuestasSMSController(consulta, jDesktopPane1);
        controller.go();
    }//GEN-LAST:event_mnuItemArqueo4ActionPerformed

    private void mnuItemUsuarios1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemUsuarios1ActionPerformed

        ParametrosView parametrosView = new ParametrosView();
        jDesktopPane1.add(parametrosView);
        parametrosView.setVisible(true);
        parametrosView.toFront();
    }//GEN-LAST:event_mnuItemUsuarios1ActionPerformed

    private void mnuItemArqueo5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueo5ActionPerformed
        ConsultaRespuestasCamapanSMSView camapanSMSView = new ConsultaRespuestasCamapanSMSView();
        jDesktopPane1.add(camapanSMSView);
        camapanSMSView.setVisible(true);
        camapanSMSView.toFront();
    }//GEN-LAST:event_mnuItemArqueo5ActionPerformed

    private void mnuItemRecibos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRecibos2ActionPerformed
        GeneraTalonCobrosYa cobrosYa = new GeneraTalonCobrosYa();
        jDesktopPane1.add(cobrosYa);
        cobrosYa.setVisible(true);
        cobrosYa.toFront();
    }//GEN-LAST:event_mnuItemRecibos2ActionPerformed

    private void mnuItemRecibos3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRecibos3ActionPerformed
        ConsultaActualizaTalonCobrosYa actualizaTalonCobrosYa = new ConsultaActualizaTalonCobrosYa();
        jDesktopPane1.add(actualizaTalonCobrosYa);
        actualizaTalonCobrosYa.setVisible(true);
        actualizaTalonCobrosYa.toFront();

    }//GEN-LAST:event_mnuItemRecibos3ActionPerformed

    private void mnuItemCaja1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCaja1ActionPerformed

        cajaView caja = new cajaView(usuario.getNombre());
        jDesktopPane1.add(caja);
        caja.setVisible(true);
        centralizaVentanas(caja);

    }//GEN-LAST:event_mnuItemCaja1ActionPerformed

    private void mnuItemUsuarios2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemUsuarios2ActionPerformed
   
    
        
        /*try {
            CajaDAO cajaDAO = new CajaDAO();
            List<Caja> BuscaTodosOrdenaPorID = cajaDAO.BuscaTodosOrdenaPorID();
            Double saldo = 0.0;
            for (Caja mov : BuscaTodosOrdenaPorID) {
                saldo = saldo + (mov.getEntrada() - mov.getSalida());
                mov.setSaldo(saldo);
                cajaDAO = new CajaDAO();

                cajaDAO.Actualizar(mov);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }*/
        ActualizaSaldos as = new ActualizaSaldos(null, true);
        as.setVisible(true);
        as.toFront();
        
        
    }//GEN-LAST:event_mnuItemUsuarios2ActionPerformed

    private void mniItemMovimientosCaja1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniItemMovimientosCaja1ActionPerformed
        consultaCajaViewNEW consultaCaja = new consultaCajaViewNEW();
        jDesktopPane1.add(consultaCaja);
        consultaCaja.setVisible(true);
        centralizaVentanas(consultaCaja);
    }//GEN-LAST:event_mniItemMovimientosCaja1ActionPerformed

    private void mnuItemCaja2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCaja2ActionPerformed
        PagoSueldosView pagoSueldos = new PagoSueldosView(usuario.toString());
        jDesktopPane1.add(pagoSueldos);
        pagoSueldos.setVisible(true);
        centralizaVentanas(pagoSueldos);
    }//GEN-LAST:event_mnuItemCaja2ActionPerformed

    private void mnuItemRecibos4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRecibos4ActionPerformed

        EmiteMensualidadesInactivos mensualidades = new EmiteMensualidadesInactivos(jDesktopPane1);
        jDesktopPane1.add(mensualidades);

        mensualidades.setSize(jDesktopPane1.getSize());
        centralizaVentanas(mensualidades);
        mensualidades.setVisible(true);
        centralizaVentanas(mensualidades);
    }//GEN-LAST:event_mnuItemRecibos4ActionPerformed

    private void mnuItemArqueo6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueo6ActionPerformed
        CampañaEconomicaView campañaEconomicaView = new CampañaEconomicaView();
        jDesktopPane1.add(campañaEconomicaView);
        campañaEconomicaView.setVisible(true);
        centralizaVentanas(campañaEconomicaView);
    }//GEN-LAST:event_mnuItemArqueo6ActionPerformed

    private void mnuItemArqueo7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueo7ActionPerformed
        NumerosView numerosView = new NumerosView();
        jDesktopPane1.add(numerosView);
        numerosView.setVisible(true);
        centralizaVentanas(numerosView);
    }//GEN-LAST:event_mnuItemArqueo7ActionPerformed

    private void mnuItemArqueo8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueo8ActionPerformed
        VentasCampEcoView ventasCampEcoView = new VentasCampEcoView();
        jDesktopPane1.add(ventasCampEcoView);
        ventasCampEcoView.setSize(jDesktopPane1.getWidth(), 500);
        ventasCampEcoView.setVisible(true);
        centralizaVentanas(ventasCampEcoView);
    }//GEN-LAST:event_mnuItemArqueo8ActionPerformed

    private void mnuItemArqueo9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemArqueo9ActionPerformed

        ConsultaVentasCampEcoView consultaVentasCampEcoView = new ConsultaVentasCampEcoView(jDesktopPane1);
        jDesktopPane1.add(consultaVentasCampEcoView);
        //consultaVentasCampEcoView.setSize(jDesktopPane1.getWidth(), 500);
        consultaVentasCampEcoView.setVisible(true);
        centralizaVentanas(consultaVentasCampEcoView);


    }//GEN-LAST:event_mnuItemArqueo9ActionPerformed

    private void mnuItemUsuarios3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemUsuarios3ActionPerformed
        BackUpsView logBackup = new BackUpsView();
        logBackup.setVisible(true);
        logBackup.toFront();
    }//GEN-LAST:event_mnuItemUsuarios3ActionPerformed

    private void btnAsignarPremioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarPremioActionPerformed
        consultaCajaCampEcoFrameNEW consultaCaja = new consultaCajaCampEcoFrameNEW();
        jDesktopPane1.add(consultaCaja);
        consultaCaja.setVisible(true);
        centralizaVentanas(consultaCaja);
    }//GEN-LAST:event_btnAsignarPremioActionPerformed

    private void mnuItemCaja3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCaja3ActionPerformed
        cajaCampEcoView caja = new cajaCampEcoView(usuario.getNombre());
        jDesktopPane1.add(caja);
        caja.setVisible(true);
        centralizaVentanas(caja);
    }//GEN-LAST:event_mnuItemCaja3ActionPerformed

    private void mnuItemCaja4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCaja4ActionPerformed

        PremiosView premios = new PremiosView();
        jDesktopPane1.add(premios);
        premios.setVisible(true);
        centralizaVentanas(premios);

    }//GEN-LAST:event_mnuItemCaja4ActionPerformed

    private void mniItemMovimientosCaja3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniItemMovimientosCaja3ActionPerformed
        AsignarPremiosCampEcoView asignarPremiosCampEcoView = new AsignarPremiosCampEcoView();
        jDesktopPane1.add(asignarPremiosCampEcoView);
        asignarPremiosCampEcoView.setVisible(true);
        centralizaVentanas(asignarPremiosCampEcoView);
    }//GEN-LAST:event_mniItemMovimientosCaja3ActionPerformed

    private void mniItemMovimientosCaja4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniItemMovimientosCaja4ActionPerformed
        EntregadePremiosView entregadePremiosView = new EntregadePremiosView();
        jDesktopPane1.add(entregadePremiosView);
        entregadePremiosView.setVisible(true);
        centralizaVentanas(entregadePremiosView);
    }//GEN-LAST:event_mniItemMovimientosCaja4ActionPerformed

    private void mnuItemHabilitacionTitulares1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemHabilitacionTitulares1ActionPerformed
        ConsultaFichasTitularesView consultaFichasTitularesView = new ConsultaFichasTitularesView(usuario);
        jDesktopPane1.add(consultaFichasTitularesView);
        consultaFichasTitularesView.setVisible(true);
        centralizaVentanas(consultaFichasTitularesView);
    }//GEN-LAST:event_mnuItemHabilitacionTitulares1ActionPerformed

    private void mnuItemConsultaSocio4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaSocio4ActionPerformed

        FiltroSocios filtroSocios = new FiltroSocios();
        jDesktopPane1.add(filtroSocios);
        filtroSocios.setVisible(true);
        centralizaVentanas(filtroSocios);


    }//GEN-LAST:event_mnuItemConsultaSocio4ActionPerformed

    private void mnuItemConsultaSocio5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaSocio5ActionPerformed
        FiltroDependientes filtroDependientes = new FiltroDependientes();
        jDesktopPane1.add(filtroDependientes);
        filtroDependientes.setVisible(true);
        centralizaVentanas(filtroDependientes);
        
    }//GEN-LAST:event_mnuItemConsultaSocio5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnAsignarPremio;
    private javax.swing.JButton btnCaja;
    private javax.swing.JButton btnMarcajes;
    private javax.swing.JButton btnMensualidades;
    private javax.swing.JButton btnRegistroDependientes;
    private javax.swing.JButton btnRegistroDependientes1;
    private javax.swing.JButton btnSocio;
    private javax.swing.JButton btnTitulares;
    private javax.swing.JButton btndependientes;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JLabel jlbPerfil;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHora;
    private javax.swing.JMenuItem mniItemMovimientosCaja1;
    private javax.swing.JMenuItem mniItemMovimientosCaja3;
    private javax.swing.JMenuItem mniItemMovimientosCaja4;
    private javax.swing.JMenu mnuArqueo;
    private javax.swing.JMenu mnuAyuda;
    private javax.swing.JMenu mnuCampanaEco;
    private javax.swing.JMenu mnuCampanaSMS;
    private javax.swing.JMenu mnuConsultas;
    private javax.swing.JMenu mnuControlPresencia;
    private javax.swing.JMenu mnuHabilitacionMedica;
    private javax.swing.JMenuItem mnuItemArqueo;
    private javax.swing.JMenuItem mnuItemArqueo1;
    private javax.swing.JMenuItem mnuItemArqueo2;
    private javax.swing.JMenuItem mnuItemArqueo3;
    private javax.swing.JMenuItem mnuItemArqueo4;
    private javax.swing.JMenuItem mnuItemArqueo5;
    private javax.swing.JMenuItem mnuItemArqueo6;
    private javax.swing.JMenuItem mnuItemArqueo7;
    private javax.swing.JMenuItem mnuItemArqueo8;
    private javax.swing.JMenuItem mnuItemArqueo9;
    private javax.swing.JMenuItem mnuItemCaja1;
    private javax.swing.JMenuItem mnuItemCaja2;
    private javax.swing.JMenuItem mnuItemCaja3;
    private javax.swing.JMenuItem mnuItemCaja4;
    private javax.swing.JMenuItem mnuItemCategoria;
    private javax.swing.JMenuItem mnuItemCategoria1;
    private javax.swing.JMenuItem mnuItemCategoria2;
    private javax.swing.JMenuItem mnuItemCategoria3;
    private javax.swing.JMenuItem mnuItemCobrador;
    private javax.swing.JMenuItem mnuItemConsultaSocio1;
    private javax.swing.JMenuItem mnuItemConsultaSocio2;
    private javax.swing.JMenuItem mnuItemConsultaSocio3;
    private javax.swing.JMenuItem mnuItemConsultaSocio4;
    private javax.swing.JMenuItem mnuItemConsultaSocio5;
    private javax.swing.JMenuItem mnuItemDependientes;
    private javax.swing.JMenuItem mnuItemHabilitacionDependientes;
    private javax.swing.JMenuItem mnuItemHabilitacionTitulares;
    private javax.swing.JMenuItem mnuItemHabilitacionTitulares1;
    private javax.swing.JMenuItem mnuItemMarcajes;
    private javax.swing.JMenuItem mnuItemPagos;
    private javax.swing.JMenuItem mnuItemRecibos;
    private javax.swing.JMenuItem mnuItemRecibos1;
    private javax.swing.JMenuItem mnuItemRecibos2;
    private javax.swing.JMenuItem mnuItemRecibos3;
    private javax.swing.JMenuItem mnuItemRecibos4;
    private javax.swing.JMenuItem mnuItemSalir;
    private javax.swing.JMenuItem mnuItemSobre;
    private javax.swing.JMenuItem mnuItemSocios;
    private javax.swing.JMenuItem mnuItemUsuarios;
    private javax.swing.JMenuItem mnuItemUsuarios1;
    private javax.swing.JMenuItem mnuItemUsuarios2;
    private javax.swing.JMenuItem mnuItemUsuarios3;
    private javax.swing.JMenu mnuMensualidades;
    private javax.swing.JMenu mnuRegistros;
    private javax.swing.JMenu mnuSistema;
    private javax.swing.JMenuItem mnuitemConsultaDependientes;
    private javax.swing.JMenuItem mnuitemConsultaMensualidades;
    // End of variables declaration//GEN-END:variables
}

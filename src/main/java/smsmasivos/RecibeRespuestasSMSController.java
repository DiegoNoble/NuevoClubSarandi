package smsmasivos;

import Utilidades.AjustaColumnas;
import com.club.BEANS.Campanasms;
import com.club.BEANS.Parametros;
import com.club.DAOs.CampanaSmsDAO;
import com.club.DAOs.ParametrosDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.tableModels.CampanaTableModel;
import com.club.views.RecibeRespuestasSMSView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public class RecibeRespuestasSMSController implements ActionListener {

    public RecibeRespuestasSMSView view;
    CampanaTableModel tableModel;
    List<Campanasms> listCampanasms;
    CampanaSmsDAO campanaSmsDAO;
    ListSelectionModel listModel;
    JDesktopPane desktopPane;
    Campanasms campanaSeleccionada;

    public RecibeRespuestasSMSController(RecibeRespuestasSMSView view, JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
        this.view = view;
        inicia();
    }

    private void inicia() {
        this.view.btnActualizarRespuestas.setActionCommand("btnActualizarRespuestas");
        this.view.btnActualizarRespuestas.addActionListener(this);

        TableModel();
    }

    public void go() {
        try {
            desktopPane.add(view);
            view.setMaximum(true);
            this.view.setVisible(true);
            this.view.toFront();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(RecibeRespuestasSMSController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tblCampañas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCampanasms = new ArrayList<Campanasms>();
        campanaSmsDAO = new CampanaSmsDAO();
        listCampanasms.addAll(campanaSmsDAO.BuscaTodos(Campanasms.class));

        tableModel = new CampanaTableModel(listCampanasms);
        view.tblCampañas.setModel(tableModel);

        int[] anchosCampañas = {20, 50, 400};
        new AjustaColumnas().ajustar(view.tblCampañas, anchosCampañas);

        view.tblCampañas.getColumn("Fecha creación").setCellRenderer(new MeDateCellRenderer());

        listModel = view.tblCampañas.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblCampañas.getSelectedRow() != -1) {
                    campanaSeleccionada = listCampanasms.get(view.tblCampañas.getSelectedRow());
                    view.btnActualizarRespuestas.setEnabled(true);
                } else {
                    view.btnActualizarRespuestas.setEnabled(false);
                }
            }
        });

    }

    public void actualizaTbl() {
        listCampanasms.clear();
        campanaSmsDAO = new CampanaSmsDAO();
        listCampanasms.addAll(campanaSmsDAO.BuscaTodos(Campanasms.class));
        tableModel.fireTableDataChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "btnActualizarRespuestas":
                ParametrosDAO parametrosDAO = new ParametrosDAO();
                Parametros parametros = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);
                ThreadRecibeRespuesta envia = new ThreadRecibeRespuesta(view.txtLog, view, campanaSeleccionada, parametros, view.chSoloNoLeidos.isSelected(), view.chMarcarComoLeído.isSelected());
                envia.execute();
                break;

            default:
                throw new AssertionError();
        }
    }

}

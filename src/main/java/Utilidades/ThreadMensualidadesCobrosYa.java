/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.club.BEANS.Mensualidades;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author Diego Noble
 */
public class ThreadMensualidadesCobrosYa extends SwingWorker<Void, Void> {

    private final JTextArea txtStatus;
    List<Mensualidades> listMensualidades;
    String status;
    int tamanoLista;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public ThreadMensualidadesCobrosYa(JTextArea txtStatus, List listMensualidades) {
        this.txtStatus = txtStatus;
        this.listMensualidades = listMensualidades;
        tamanoLista = listMensualidades.size();
    }

    @Override
    protected Void doInBackground() throws Exception {

        this.txtStatus.append("Procesando mensualidades pendientes de Cobros Ya, aguarde un momento...\n");

        for (Mensualidades m : listMensualidades) {
            this.txtStatus.append("\n");
            this.txtStatus.append("Socio: " + m.getSocio() + ", Cuota social Nro.: " + m.getId() + ", Vencimiento " + formato.format(m.getFechaVencimiento()));
            this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());

        }

        return null;
    }

    @Override
    protected void done() {
        this.txtStatus.append("\n");
        this.txtStatus.append("\n Listo!");
        this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());
    }

}

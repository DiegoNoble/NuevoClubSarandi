/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.club.BEANS.Parametros;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Diego Noble
 */
public class EnviarEmail {

    Parametros parametros;
    Message mensaje;
    String modelo_email;
    String destinatario;

    public EnviarEmail(Parametros parametros, String UrlCobrosYa, String destinatario) {
        this.parametros = parametros;
        this.destinatario = destinatario;
        int cantidad = UrlCobrosYa.length();
        String codigoCobrosYa = UrlCobrosYa.substring(cantidad - 40, cantidad);

        this.modelo_email = "<table style=\"background-color:#f3f3f3;padding:0 15px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "    <tbody>\n"
                + "        <tr>\n"
                + "            <td style=\"height:25px;padding:0;margin:0\" width=\"12%\"/>\n"
                + "            <td style=\"height:25px;padding:0;margin:0\" width=\"76%\"/>\n"
                + "            <td style=\"height:25px;padding:0;margin:0\" width=\"12%\"/>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td width=\"12%\"/>\n"
                + "            <td width=\"76%\">\n"
                + "                <span class=\"HOEnZb\">\n"
                + "                    <font color=\"#888888\">\n"
                + "                    </font>\n"
                + "                </span>\n"
                + "                <table style=\"font-family:'Segoe UI',Helvetica,Arial,sans-serif;font-size:11px;color:#666666\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "                    <tbody>\n"
                + "                        <tr>\n"
                + "                            <td style=\"font-family:'Segoe UI',Helvetica,Arial,sans-serif;font-size:12px;color:rgb(51,51,51);font-weight:bold;padding:15px 0 0\">\n"
                + "                                Te informamos que tienes un pago para Club Sarandí Universitario.\n"
                + "                            </td>\n"
                + "                        </tr>\n"
                + "                        <tr>\n"
                + "                            <td style=\"font-family:'Segoe UI',Helvetica,Arial,sans-serif;font-size:12px;color:#666;padding:20px 0\">\n"
                + "                                Hacé click en el botón naranja para acceder, ver los detalles y elegir como pagar. Ahí podés descargar tu <b>talón</b> para pagar en las <b>Redes de Cobranza </b> o pagar <b>online</b>.\n"
                + "                            </td>\n"
                + "                        </tr>\n"
                + "                        <tr>\n"
                + "                            <td style=\"height:50px\" align=\"center\">\n"
                + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"380\">\n"
                + "                                    <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"background:#ff5d30;height:30px\" align=\"center\" valign=\"middle\">\n"
                + "                                                <a href=\"https://api.cobrosya.com/mostrador/cobrar?t=" + codigoCobrosYa + "\" style=\"color:#fff;font-family:'Segoe UI',Helvetica,Arial,sans-serif;font-size:15px;text-decoration:none\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=es-419&amp;q=https://api.cobrosya.com/mostrador/cobrar?t%" + codigoCobrosYa + "&amp;source=gmail&amp;ust=1472416032276000&amp;usg=AFQjCNH9Yj8yW_1EWT79VgtHGte2hTEW1w\">HACÉ CLIC AQUÍ PARA ELEGIR COMO PAGAR</a>\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </tbody>\n"
                + "                                </table>\n"
                + "                            </td>\n"
                + "                        </tr>\n"
                + "                        <tr>\n"
                + "                            <td style=\"padding:20px 0 7px;font-family:'Segoe UI',Helvetica,Arial,sans-serif;font-size:11px;color:#666666\" align=\"center\"><b>El pago puede ser efectuado a través de:</b></td>\n"
                + "                        </tr>\n"
                + "                        <tr>\n"
                + "                            <td>\n"
                + "                                <table style=\"border-top:1px solid #e3e3e3;padding:8px 0;font-size:11px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "                                    <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td align=\"center\">\n"
                + "                                                <img class=\"CToWUd\" src=\"https://ci5.googleusercontent.com/proxy/0KR7sa6RAe2ljNHUdxi8L88zkMXkTYB_9kZnaEe5EmUmtJFrcvUUvFdwoPMbjSq3ky3g1ABLIgmPCbNiGKgtiNdnNONYa4WSP8uc7XKeWkILVR-W5RKZ=s0-d-e1-ft#https://api.cobrosya.com/themes/cobrosya/images/mail/redpagos.png\" alt=\"Redpagos\" style=\"text-align:center;color:#333;border:none\" width=\"70\">\n"
                + "                                                <img class=\"CToWUd\" src=\"https://ci6.googleusercontent.com/proxy/XQHT_fNQm4G8_mpfX41FvpUSSTDeenemOSsQqRLOJRz-Ss2x0JbXJ7MFu1u36r55DFcI4cHy0dgAhErs2M3Ma-Y4-mDD3Bq9SlT2mYo7iEt99LrFIAk=s0-d-e1-ft#https://api.cobrosya.com/themes/cobrosya/images/mail/paganza.png\" alt=\"Paganza\" style=\"text-align:center;color:#333;border:none\" width=\"70\">\n"
                + "                                                <img class=\"CToWUd\" src=\"https://ci4.googleusercontent.com/proxy/tpVdwqTwhW_QHDsPXcmdBuRzwKoZf0dKDr56DnUGuHSF1T0eSE-xitcLGthSBsrCadT_P57dtE48EY35Xe_lRozyBHK2PlK8DGczuMPTrPhz8iNlTQ=s0-d-e1-ft#https://api.cobrosya.com/themes/cobrosya/images/mail/banred.png\" alt=\"Banred\" style=\"text-align:center;color:#333;border:none\" width=\"70\">\n"
                + "                                                <img class=\"CToWUd\" src=\"https://ci6.googleusercontent.com/proxy/pkyL7tppu_LKglOS6pifcoMpGCfQY-CC5QI-6rqRUD75wy_AMK_cmYub3QU1U9cA-Lc25IgQ4wbe-FMD6NldKSIKzQJABKmCkMknjgNuW9h9Jj4=s0-d-e1-ft#https://api.cobrosya.com/themes/cobrosya/images/mail/brou.png\" alt=\"Banred\" style=\"text-align:center;color:#333;border:none\" width=\"70\">\n"
                + "                                                <img class=\"CToWUd\" src=\"https://ci3.googleusercontent.com/proxy/qY9f02w9q5jCKN65S8TJGNkigN4NGUTRWLBM9pomrtSgXHZOr_iIiYe6suI34MR5zL9Mly_7zbNYwG8AI8OsKwl_Ow-ypIceddddblxNXKp33FRLJzr-Mg=s0-d-e1-ft#https://api.cobrosya.com/themes/cobrosya/images/mail/santander.png\" alt=\"Banred\" style=\"text-align:center;color:#333;border:none\" width=\"70\">\n"
                + "                                                <img class=\"CToWUd\" src=\"https://ci3.googleusercontent.com/proxy/pQDZ1ifHh8ZqFpfQXEzo5cLVCibBl13Y5YG_uv_qNYb8UdndNhGfSbI3BIOaB3b98GLVE4UISYm5beaNEx27B2diFDHWeSdSbM1iZfZJZby2DlI=s0-d-e1-ft#https://api.cobrosya.com/themes/cobrosya/images/mail/bbva.png\" alt=\"Banred\" style=\"text-align:center;color:#333;border:none\" width=\"70\">\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </tbody>\n"
                + "                                </table>\n"
                + "                            </td>\n"
                + "                        </tr>\n"
                + "                        <tr>\n"
                + "                            <td style=\"font-family:'Segoe UI',Helvetica,Arial,sans-serif;font-size:11px;color:#666;text-align:justify;padding:20px 0;border-top:1px solid #e3e3e3\">\n"
                + "                                La familia está en Sarandí!<span class=\"HOEnZb\">\n"
                + "                                    <font color=\"#888888\">\n"
                + "                                    <br>\n"
                + "                                    <br>\n"
                + "                                    -- <br>\n"
                + "                                    Dr. Anolles 533 - Tel 4622 3478<br>\n"
                + "                                    Rivera<br>\n"
                + "                                    <a href=\"http://www.sarandiuniversitario.com\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=es-419&amp;q=http://www.sarandiuniversitario.com&amp;source=gmail&amp;ust=1472416032277000&amp;usg=AFQjCNGouUw3xQraWD7twXoXd_g-axewaw\">www.sarandiuniversitario.com</a>\n"
                + "                                    </font>\n"
                + "                                </span>\n"
                + "                            </td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </td>\n"
                + "            <td width=\"12%\"/>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td width=\"12%\"/>\n"
                + "            <td width=\"76%\"/>\n"
                + "            <td style=\"height:32px\" width=\"12%\"/>\n"
                + "        </tr>\n"
                + "    </tbody>\n"
                + "</table>";
    }

    public Boolean enviaMail() {
        Boolean retorno;
        try {

            final String username = parametros.getCasilla_email();
            final String password = parametros.getPsw_email();

            Properties props = new Properties();
            /*props.put("mail.smtp.auth", "true");
             props.put("mail.smtp.starttls.enable", "true");
             //props.put("mail.smtp.ssl.trust", "correo.saltohotelcasino.com");
             props.put("mail.smtp.host", "smtp.gmail.com");
             props.put("mail.smtp.port", "587");*/

            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                mensaje = new MimeMessage(session);

                mensaje.setFrom(new InternetAddress(username));

                //criando a Multipart
                Multipart multipart = new MimeMultipart();

                //criando a primeira parte da mensagem
                MimeBodyPart attachment0 = new MimeBodyPart();
                //configurando o htmlMessage com o mime type
                attachment0.setContent(modelo_email, "text/html; charset=UTF-8");
                //adicionando na multipart
                multipart.addBodyPart(attachment0);
                mensaje.setContent(multipart);

                //mensaje.setText("Prueba envio email GMAIL");
                mensaje.setSubject("Talón de Pago Sarandí Universitario");

                //String destinatario = parametros.getNotificamails();
                mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
                Transport.send(mensaje);

                retorno = true;

            } catch (MessagingException e) {
                retorno = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            retorno = false;
        }
        return retorno;
    }

}

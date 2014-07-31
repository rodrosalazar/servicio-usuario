package ec.gob.senescyt.commons.email;

import ec.gob.senescyt.commons.configuracion.ConfiguracionEmail;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class DespachadorEmail {

    private ConfiguracionEmail configuracionEmail;

    public DespachadorEmail(ConfiguracionEmail configuracionEmail) {
        this.configuracionEmail = configuracionEmail;
    }

    public String enviarEmail(String emailDestinatario, String nombreDestinatario, String asunto, String mensaje) throws EmailException {
        HtmlEmail correoElectronico = new HtmlEmail();
        correoElectronico.setHostName(configuracionEmail.getServidor());
        correoElectronico.setSmtpPort(configuracionEmail.getPuerto());
        correoElectronico.setDebug(configuracionEmail.isDebugModeActivo());
        correoElectronico.setSSLOnConnect(configuracionEmail.isSSLRequerido());
        correoElectronico.setAuthenticator(new DefaultAuthenticator(configuracionEmail.getNombreUsuario(), configuracionEmail.getClave()));
        correoElectronico.setFrom(configuracionEmail.getCorreoRemitente(), configuracionEmail.getNombreRemitente());
        correoElectronico.addTo(emailDestinatario, nombreDestinatario);
        correoElectronico.setSubject(asunto);
        correoElectronico.setHtmlMsg(mensaje);

        return correoElectronico.send();
    }
}

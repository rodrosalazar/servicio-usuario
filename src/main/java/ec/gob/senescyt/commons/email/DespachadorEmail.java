package ec.gob.senescyt.commons.email;

import ec.gob.senescyt.commons.configuracion.ConfiguracionEmail;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class DespachadorEmail {

    private ConstructorDeContenidoDeEmail constructorDeContenidoDeEmail;
    private ConfiguracionEmail configuracionEmail;

    public DespachadorEmail(ConstructorDeContenidoDeEmail constructorDeContenidoDeEmail, ConfiguracionEmail configuracionEmail) {
        this.constructorDeContenidoDeEmail = constructorDeContenidoDeEmail;
        this.configuracionEmail = configuracionEmail;
    }

    public String enviarEmailNotificacionUsuarioCreado(String emailUsuarioCreado, String userName, String nombresCompletosUsuario) throws EmailException {
        HtmlEmail correoElectronico = new HtmlEmail();
        correoElectronico.setHostName(configuracionEmail.getServidor());
        correoElectronico.setSmtpPort(configuracionEmail.getPuerto());
        correoElectronico.setDebug(configuracionEmail.isDebugModeActivo());
        correoElectronico.setSSLOnConnect(configuracionEmail.isSSLRequerido());
        correoElectronico.setAuthenticator(new DefaultAuthenticator(configuracionEmail.getNombreUsuario(), configuracionEmail.getClave()));
        correoElectronico.setFrom(configuracionEmail.getCorreoRemitente(), configuracionEmail.getNombreRemitente());
        correoElectronico.addTo(emailUsuarioCreado, nombresCompletosUsuario);
        correoElectronico.setSubject("Creación de usuario para el sistema SNIESE");
        correoElectronico.setHtmlMsg(constructorDeContenidoDeEmail.construirEmailNotificacionUsuarioCreado(nombresCompletosUsuario, userName));

        return correoElectronico.send();
    }
}

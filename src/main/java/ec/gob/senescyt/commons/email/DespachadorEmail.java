package ec.gob.senescyt.commons.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class DespachadorEmail {

    private ConstructorDeContenidoDeEmail constructorDeContenidoDeEmail;

    public DespachadorEmail(ConstructorDeContenidoDeEmail constructorDeContenidoDeEmail) {
        this.constructorDeContenidoDeEmail = constructorDeContenidoDeEmail;
    }

    public String enviarEmailNotificacionUsuarioCreado(String emailUsuarioCreado, String userName, String nombresCompletosUsuario) throws EmailException {
        HtmlEmail correoElectronico = new HtmlEmail();
        correoElectronico.setHostName("smtp.gmail.com");
        correoElectronico.setSmtpPort(465);
        correoElectronico.setDebug(true);
        correoElectronico.setSSLOnConnect(true);
        correoElectronico.setAuthenticator(new DefaultAuthenticator("seneteam@gmail.com", "seneteam123"));
        correoElectronico.setFrom("seneteam@gmail.com", "Sniese");
        correoElectronico.addTo(emailUsuarioCreado, nombresCompletosUsuario);
        correoElectronico.setSubject("Creación de usuario para el sistema SNIESE");
        correoElectronico.setHtmlMsg(constructorDeContenidoDeEmail.construirEmailNotificacionUsuarioCreado(nombresCompletosUsuario, userName));

        return correoElectronico.send();
    }
}

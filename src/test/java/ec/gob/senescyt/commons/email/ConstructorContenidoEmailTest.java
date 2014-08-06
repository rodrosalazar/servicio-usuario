package ec.gob.senescyt.commons.email;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class ConstructorContenidoEmailTest {

    @Test
    public void debeAgragarElTokenAlEmail() {
        ConstructorContenidoEmail constructorContenidoEmail = new ConstructorContenidoEmail();
        String idToken = "indiferente";
        String dominio = "http://sniese.gob.ec/usuarios/contrasenia/";
        String urlConToken = dominio + idToken;
        String direccionEsperada = ("href='" + dominio).concat(idToken);
        String nombreUsuario = "Nombre Usuario";
        String nombreDestinatario = "Nombre Destinario";
        String cadenaEmail = constructorContenidoEmail.construirEmailNotificacionUsuarioCreado(nombreDestinatario, nombreUsuario, urlConToken);

        assertThat(cadenaEmail, CoreMatchers.containsString(direccionEsperada));
    }
}
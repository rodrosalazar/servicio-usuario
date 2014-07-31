package ec.gob.senescyt.commons.email;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class ConstructorDeContenidoDeEmailTest {

    @Test
    public void debeAgragarElTokenAlEmail() {
        String idToken = "indiferente";
        String direccionEsperada = "href='http://sniese.gob.ec/usuarios/contrasenia/".concat(idToken);
        String nombreUsuario = "Nombre Usuario";
        String nombreDestinatario = "Nombre Destinario";
        String cadenaEmail = ConstructorDeContenidoDeEmail.construirEmailNotificacionUsuarioCreado(nombreDestinatario, nombreUsuario, idToken);

        assertThat(cadenaEmail, CoreMatchers.containsString(direccionEsperada));
    }
}
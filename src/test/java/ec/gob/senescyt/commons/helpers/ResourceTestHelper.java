package ec.gob.senescyt.commons.helpers;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.configuracion.ConfiguracionEmail;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;

import static org.mockito.Mockito.when;

public class ResourceTestHelper {

    public static void assertErrorMessage(ClientResponse response, String expectedErrorMessage) {
        String errorMessage = response.getEntity(String.class);
        MatcherAssert.assertThat(errorMessage, CoreMatchers.containsString(expectedErrorMessage));
    }

    public static void mockConfiguracionMail(ConfiguracionEmail configuracionEmail){
        when(configuracionEmail.getServidor()).thenReturn("puertoTest");
        when(configuracionEmail.getPuerto()).thenReturn(123);
        when(configuracionEmail.getNombreUsuario()).thenReturn("usuarioTest");
        when(configuracionEmail.getClave()).thenReturn("claveTest");
        when(configuracionEmail.getCorreoRemitente()).thenReturn("test@test.com");
        when(configuracionEmail.getNombreRemitente()).thenReturn("remitenteTest");
    }
}
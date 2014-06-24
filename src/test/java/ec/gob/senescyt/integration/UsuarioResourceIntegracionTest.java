package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.UsuarioApplication;
import ec.gob.senescyt.usuario.UsuarioConfiguration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UsuarioResourceIntegracionTest {

    private static final String CONFIGURACION = "test-integracion.yml";
    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    private static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void debeVerificarNumeroDeCedulaCorrecto() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("cedula","1718642174")
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void debeVerificarNumeroDeCedulaIncorrecto() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("cedula","1111111111")
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(400));
        assertThat(response.getEntity(String.class), is("Debe ingresar una cédula válida"));
    }

}

package ec.gob.senescyt.integration;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.UsuarioApplication;
import ec.gob.senescyt.usuario.UsuarioConfiguration;
import ec.gob.senescyt.usuario.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Usuario;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.validation.ConstraintViolations;
import org.fest.assertions.api.Assertions;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UsuarioResourceIntegracionTest {

    private static final String CONFIGURACION = "test-integracion.yml";
    private static final String numeroQuipuxValido = "SENESCYT-DFAPO-2014-65946-MI";

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

    @Test
    public void debeVerificarQueLaFechaDeFinDeVigenciaNoPuedeSerMenorALaFechaActual() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConFechaDeVigenciaInvalida());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeLanzarErrorCuandoEmailDeUsuarioEsInvalido() throws Exception {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConEmailInvalido());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeCrearUnNuevoUsuarioCuandoEsValido(){
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido());

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void noDebeCrearUnNuevoUsuarioCuandoElNombreDeUsuarioYaExiste() {

    }

    @Test
    public void noDebeCrearUnNuevoUsuarioCuandoLaCedulaYaExiste() {

    }
}

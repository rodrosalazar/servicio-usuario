package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.UsuarioApplication;
import ec.gob.senescyt.usuario.UsuarioConfiguration;
import ec.gob.senescyt.usuario.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UsuarioResourceIntegracionTest {

    private static final String CONFIGURACION = "test-integracion.yml";

    private SessionFactory sessionFactory;

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    private static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() {
        sessionFactory = ((UsuarioApplication) RULE.getApplication()).getSessionFactory();
        ManagedSessionContext.bind(sessionFactory.openSession());
        UsuarioDAO usuarioDAO = new UsuarioDAO(sessionFactory);
        usuarioDAO.limpiar();
    }

    @After
    public void tearDown() {
        ManagedSessionContext.unbind(sessionFactory);
    }

    @Test
    public void debeVerificarNumeroDeCedulaCorrecto() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("cedula", "1718642174")
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void debeVerificarNumeroDeCedulaIncorrecto() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("cedula", "1111111111")
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(400));
        assertThat(response.getEntity(String.class), is("identificacion.numeroIdentificacion Cedula no valida *="));
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
    public void debeCrearUnNuevoUsuarioCuandoEsValido() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido());

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void debeIndicarQueUnNombreDeUsuarioYaHaSidoRegistrado() throws Exception {
        Client client = new Client();

        ClientResponse responseInsertUsuario = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido());

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseValidacion = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("nombreUsuario",UsuarioBuilder.usuarioValido().getNombreUsuario())
                .get(ClientResponse.class);

        assertThat(responseValidacion.getStatus(), is(400));
        assertThat(responseValidacion.getEntity(String.class), is("nombreUsuario Nombre de usuario ya esta registrado *="));

    }

    @Test
    public void debeIndicarQueUnNombreDeUsuarioNoSeEncuentraRegistrado() throws Exception {
        Client client = new Client();

        ClientResponse responseValidacion = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("nombreUsuario", UsuarioBuilder.usuarioValido().getNombreUsuario())
                .get(ClientResponse.class);

        assertThat(responseValidacion.getStatus(), is(200));

    }

    @Test
    public void debeIndicarQueUnNumeroDeIdentificacionYaHaSidoRegistrado() throws Exception {
        Client client = new Client();

        ClientResponse responseInsertUsuario = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido());

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseValidacion = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("numeroIdentificacion", UsuarioBuilder.usuarioValido().getIdentificacion().getNumeroIdentificacion())
                .get(ClientResponse.class);

        assertThat(responseValidacion.getStatus(), is(400));
        assertThat(responseValidacion.getEntity(String.class), is("identificacion.numeroIdentificacion Numero de Identificacion ya esta registrado *="));

    }

    @Test
    public void debeIndicarQueUnNumeroDeIdentificacionNoSeEncuentraRegistrado() throws Exception {
        Client client = new Client();

        ClientResponse responseValidacion = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("numeroIdentificacion", UsuarioBuilder.usuarioValido().getIdentificacion().getNumeroIdentificacion())
                .get(ClientResponse.class);

        assertThat(responseValidacion.getStatus(), is(200));

    }

    @Test
    public void debeValidarQueNombreDeUsuarioNoSeRepitaCuandoSeGuardaUsuario() throws Exception {
        Client client = new Client();

        ClientResponse responseInsertUsuario = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido1718642174UsuarioSenescyt());

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseUsuarioRepetido = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido1804068953UsuarioSenescyt());

        assertThat(responseUsuarioRepetido.getStatus(), is(400));
        assertThat(responseUsuarioRepetido.getEntity(String.class), is("nombreUsuario Nombre de usuario ya esta registrado *="));
    }


    @Test
    public void debeValidarQueNumeroDeIdentificacionNoSeRepitaCuandoSeGuardaUsuario() throws Exception {
        Client client = new Client();

        ClientResponse responseInsertUsuario = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido1718642174UsuarioSenescyt());

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseUsuarioRepetido = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido1718642174UsuarioAdmin());

        assertThat(responseUsuarioRepetido.getStatus(), is(400));
        assertThat(responseUsuarioRepetido.getEntity(String.class), is("identificacion.numeroIdentificacion Numero de Identificacion ya esta registrado *="));
    }


}

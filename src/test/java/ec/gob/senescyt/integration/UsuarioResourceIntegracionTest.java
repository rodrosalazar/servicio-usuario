package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.commons.builders.PerfilBuilder;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.commons.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Usuario;
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

    private LectorArchivoDePropiedades lectorArchivoDePropiedades;
    private MensajeErrorBuilder mensajeErrorBuilder;
    private UsuarioDAO usuarioDAO;

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));
    private Perfil perfil1;
    private Client client;
    private Perfil perfilGuardado;

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
        usuarioDAO = new UsuarioDAO(sessionFactory);
        perfil1 = PerfilBuilder.nuevoPerfil().generar();
        lectorArchivoDePropiedades = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());
        mensajeErrorBuilder = new MensajeErrorBuilder(lectorArchivoDePropiedades);
        client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/perfiles", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, perfil1);

        assertThat(response.getStatus(), is(201));
        perfilGuardado = response.getEntity(Perfil.class);
    }

    @After
    public void tearDown() {
        usuarioDAO.limpiar();
        ManagedSessionContext.unbind(sessionFactory);
    }

    @Test
    public void debeVerificarNumeroDeCedulaCorrecto() {

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("cedula", "1718642174")
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void debeVerificarNumeroDeCedulaIncorrecto() {

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("cedula", "1111111111")
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(400));
        assertThat(response.getEntity(String.class),
                is(mensajeErrorBuilder.mensajeNumeroIdentificacionInvalido()));
    }

    @Test
    public void debeVerificarQueLaFechaDeFinDeVigenciaNoPuedeSerMenorALaFechaActual() {

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConFechaDeVigenciaInvalida());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeLanzarErrorCuandoEmailDeUsuarioEsInvalido() throws Exception {

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConEmailInvalido());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeCrearUnNuevoUsuarioCuandoEsValido() {

        Usuario usuarioValido = UsuarioBuilder.usuarioValido(perfilGuardado);
        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioValido);

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void debeIndicarQueUnNombreDeUsuarioYaHaSidoRegistrado() throws Exception {

        ClientResponse responseInsertUsuario = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido(perfilGuardado));

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseValidacion = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("nombreUsuario",UsuarioBuilder.usuarioValido().getNombreUsuario())
                .get(ClientResponse.class);

        assertThat(responseValidacion.getStatus(), is(400));
        assertThat(responseValidacion.getEntity(String.class), is(mensajeErrorBuilder.mensajeNombreDeUsuarioYaHaSidoRegistrado()));
    }

    @Test
    public void debeIndicarQueUnNombreDeUsuarioNoSeEncuentraRegistrado() throws Exception {

        ClientResponse responseValidacion = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("nombreUsuario", UsuarioBuilder.usuarioValido().getNombreUsuario())
                .get(ClientResponse.class);

        assertThat(responseValidacion.getStatus(), is(200));
    }

    @Test
    public void debeIndicarQueUnNumeroDeIdentificacionYaHaSidoRegistrado() throws Exception {

        ClientResponse responseInsertUsuario = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido(perfilGuardado));

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseValidacion = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("numeroIdentificacion", UsuarioBuilder.usuarioValido().getIdentificacion().getNumeroIdentificacion())
                .get(ClientResponse.class);

        assertThat(responseValidacion.getStatus(), is(400));
        assertThat(responseValidacion.getEntity(String.class), is(mensajeErrorBuilder.mensajeNumeroIdentificacionYaHaSidoRegistrado()));
    }

    @Test
    public void debeIndicarQueUnNumeroDeIdentificacionNoSeEncuentraRegistrado() throws Exception {

        ClientResponse responseValidacion = client.resource(
                String.format("http://localhost:%d/usuario/validacion", RULE.getLocalPort()))
                .queryParam("numeroIdentificacion", UsuarioBuilder.usuarioValido().getIdentificacion().getNumeroIdentificacion())
                .get(ClientResponse.class);

        assertThat(responseValidacion.getStatus(), is(200));
    }

    @Test
    public void debeValidarQueNombreDeUsuarioNoSeRepitaCuandoSeGuardaUsuario() throws Exception {

        ClientResponse responseInsertUsuario = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido1718642174UsuarioSenescyt(perfilGuardado));

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseUsuarioRepetido = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido1804068953UsuarioSenescyt());

        assertThat(responseUsuarioRepetido.getStatus(), is(400));
        assertThat(responseUsuarioRepetido.getEntity(String.class), is(mensajeErrorBuilder.mensajeNombreDeUsuarioYaHaSidoRegistrado()));
    }

    @Test
    public void debeValidarQueNumeroDeIdentificacionNoSeRepitaCuandoSeGuardaUsuario() throws Exception {

        ClientResponse responseInsertUsuario = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido1718642174UsuarioSenescyt(perfilGuardado));

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseUsuarioRepetido = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido1718642174UsuarioAdmin());

        assertThat(responseUsuarioRepetido.getStatus(), is(400));
        assertThat(responseUsuarioRepetido.getEntity(String.class), is(mensajeErrorBuilder.mensajeNumeroIdentificacionYaHaSidoRegistrado()));
    }

    @Test
    public void debeDevolverUnMensajeDeErrorCuandoElNumeroDeIdentificacionEsMayorDe20() {
        Usuario usuarioCon21Digitos = UsuarioBuilder.usuarioConIdentificacionDeMasDe20Digitos();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioCon21Digitos);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeDevolverUnMensajeDeErrorCuandoElNumeroDePasaporteEstaVacio() {
        Usuario usuarioCon21Digitos = UsuarioBuilder.usuarioConPasaporteVacio();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/usuario", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioCon21Digitos);

        assertThat(response.getStatus(), is(400));
    }
}

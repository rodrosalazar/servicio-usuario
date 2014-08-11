package ec.gob.senescyt.usuario.autenticacion;

import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.exceptions.LoginIncorrectoMapper;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.resources.IdentificacionResource;
import ec.gob.senescyt.usuario.services.ServicioCredencial;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IdentificacionResourceTest {

    private static ServicioCredencial servicioCredencial = Mockito.mock(ServicioCredencial.class);
    private static IdentificacionResource identificacionResource = new IdentificacionResource(servicioCredencial);

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(identificacionResource)
            .addProvider(ValidacionExceptionMapper.class)
            .addProvider(LoginIncorrectoMapper.class)
            .build();

    private Client client;

    @Before
    public void setUp() {
        client = RESOURCES.client();
    }

    @After
    public void tearDown() {
        Mockito.reset(servicioCredencial);
    }

    @Test
    public void debeVerificarQueElUsuarioYLaContraseniaSonCorrectos() {

        String nombreUsuario = "nombreUsuario";
        String contrasenia = "contrasenia";
        String tokenDeInicioDeSesion = "token_de_inicio_de_sesion";
        CredencialLogin credencialLogin = new CredencialLogin(nombreUsuario, contrasenia);

        when(servicioCredencial.obtenerTokenDeInicioDeSesion(Mockito.refEq(credencialLogin))).thenReturn(Optional.fromNullable(tokenDeInicioDeSesion));

        ClientResponse response = hacerPost(credencialLogin);

        assertThat(response.getStatus(), Is.is(201));
        verify(servicioCredencial).obtenerTokenDeInicioDeSesion(Matchers.refEq(credencialLogin));
    }

    @Test
    public void debeVerificarQueUnaCredencialNoEsValida() {
        String nombreUsuario = "nombreUsuario";
        String contrasenia = "contrasenia";
        CredencialLogin credencialLoginInvalida = new CredencialLogin(nombreUsuario, contrasenia);

        when(servicioCredencial.obtenerTokenDeInicioDeSesion(Matchers.refEq(credencialLoginInvalida))).thenReturn(Optional.absent());

        ClientResponse response = hacerPost(credencialLoginInvalida);

        assertThat(response.getStatus(), Is.is(401));
        String mensajeErrorEsperado = "Credenciales Incorrectas";
        assertErrorMessage(response, mensajeErrorEsperado);
    }

    @Test
    public void debeRetornarErrorCuandoElNombreDeUsuarioEsNulo() {
        String nombreUsuario = null;
        String contrasenia = "constrasenia indiferente";

        CredencialLogin credencialLoginInvalida = new CredencialLogin(nombreUsuario, contrasenia);

        ClientResponse response = hacerPost(credencialLoginInvalida);

        assertThat(response.getStatus(), Is.is(400));
        assertErrorMessage(response, "nombreUsuario El campo es obligatorio");
        Mockito.verifyZeroInteractions(servicioCredencial);
    }

    @Test
    public void debeRetornarErrorCuandoElNombreDeUsuarioEstaVacio() {
        String nombreUsuario = "";
        String contrasenia = "constrasenia indiferente";

        CredencialLogin credencialLoginInvalida = new CredencialLogin(nombreUsuario, contrasenia);

        ClientResponse response = hacerPost(credencialLoginInvalida);

        assertThat(response.getStatus(), Is.is(400));
        assertErrorMessage(response, "nombreUsuario El campo es obligatorio");
        Mockito.verifyZeroInteractions(servicioCredencial);
    }

    @Test
    public void debeRetornarErrorCuandoElContraseniaEsNula() {
        String nombreUsuario = "usuario indiferente";
        String contrasenia = null;

        CredencialLogin credencialLoginInvalida = new CredencialLogin(nombreUsuario, contrasenia);

        ClientResponse response = hacerPost(credencialLoginInvalida);

        assertThat(response.getStatus(), Is.is(400));
        assertErrorMessage(response, "contrasenia El campo es obligatorio");
        Mockito.verifyZeroInteractions(servicioCredencial);
    }

    @Test
    public void debeRetornarErrorCuandoElContraseniaEstaVacia() {
        String nombreUsuario = "usuario indiferente";
        String contrasenia = "";

        CredencialLogin credencialLoginInvalida = new CredencialLogin(nombreUsuario, contrasenia);

        ClientResponse response = hacerPost(credencialLoginInvalida);

        assertThat(response.getStatus(), Is.is(400));
        assertErrorMessage(response, "contrasenia El campo es obligatorio");
        Mockito.verifyZeroInteractions(servicioCredencial);
    }

    private ClientResponse hacerPost(CredencialLogin credencialLogin) {
        return client.resource("/identificacion")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialLogin);
    }
}

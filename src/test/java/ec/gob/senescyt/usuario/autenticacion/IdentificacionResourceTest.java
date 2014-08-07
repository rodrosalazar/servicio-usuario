package ec.gob.senescyt.usuario.autenticacion;

import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.helpers.ResourceTestHelper;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.exceptions.LoginIncorrectoMapper;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.resources.IdentificacionResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.any;

public class IdentificacionResourceTest {

    private static final String RUTA_IDENTIFICACION = "/identificacion";
    private static final String INDIFERENTE = "indiferente";
    private static final String CAMPO_OBLIGATORIO = "El campo es obligatorio";
    private static CredencialDAO credencialDAO = Mockito.mock(CredencialDAO.class);
    private static IdentificacionResource identificacionResource = new IdentificacionResource(credencialDAO);

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(identificacionResource)
            .addProvider(ValidacionExceptionMapper.class)
            .addProvider(LoginIncorrectoMapper.class)
            .build();

    private Client client = RESOURCES.client();

    @After
    public void tearDown() {
        Mockito.reset(credencialDAO);
    }

    @Test
    public void debeDevolverTokenConLasCredencialesCorrectas() {
        String password = "Clave456";
        String token = "ASD123123asdasd";
        String username = "username";

        Credencial credencialesUsuario = new Credencial(username, password);

        Mockito.when(credencialDAO.validar(any(Credencial.class))).thenReturn(Optional.of(token));

        ClientResponse response = client.resource(RUTA_IDENTIFICACION)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesUsuario);

        Mockito.verify(credencialDAO).validar(any(Credencial.class));
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void debeDevolverErrorCuandoContraseniaEsNula() {
        Credencial credencialesConContraseniaNula = new Credencial(INDIFERENTE, null);
        ClientResponse response = client.resource(RUTA_IDENTIFICACION)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesConContraseniaNula);

        Mockito.verifyZeroInteractions(credencialDAO);
        assertThat(response.getStatus(), is(400));
        ResourceTestHelper.assertErrorMessage(response, CAMPO_OBLIGATORIO);
    }

    @Test
    public void debeDevolverErrorCuandoContraseniaEstaVacia() {
        Credencial credencialesConContraseniaVacia = new Credencial(INDIFERENTE, "");
        ClientResponse response = client.resource(RUTA_IDENTIFICACION)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesConContraseniaVacia);

        Mockito.verifyZeroInteractions(credencialDAO);
        assertThat(response.getStatus(), is(400));
        ResourceTestHelper.assertErrorMessage(response, CAMPO_OBLIGATORIO);
    }

    @Test
    public void debeDevolverErrorCuandoNombreDeUsuarioEsNulo() {
        Credencial credencialesConContraseniaVacia = new Credencial(null, INDIFERENTE);
        ClientResponse response = client.resource(RUTA_IDENTIFICACION)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesConContraseniaVacia);

        Mockito.verifyZeroInteractions(credencialDAO);
        assertThat(response.getStatus(), is(400));
        ResourceTestHelper.assertErrorMessage(response, CAMPO_OBLIGATORIO);
    }

    @Test
    public void debeDevolverErrorCuandoNombreDeUsuarioEstaVacio() {
        Credencial credencialesConContraseniaVacia = new Credencial("", INDIFERENTE);
        ClientResponse response = client.resource(RUTA_IDENTIFICACION)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesConContraseniaVacia);

        Mockito.verifyZeroInteractions(credencialDAO);
        assertThat(response.getStatus(), is(400));
        ResourceTestHelper.assertErrorMessage(response, CAMPO_OBLIGATORIO);
    }

    @Test
    public void debeDevolverNoAutorizadoCuandoCredencialesSonIncorrectas() {
        Credencial credencialesIncorectas = new Credencial("incorrecta", "Incorr3cta");
        Mockito.when(credencialDAO.validar(any(Credencial.class))).thenReturn(Optional.absent());

        ClientResponse response = client.resource(RUTA_IDENTIFICACION)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesIncorectas);

        Mockito.verify(credencialDAO).validar(any(Credencial.class));
        assertThat(response.getStatus(), is(401));
        ResourceTestHelper.assertErrorMessage(response, "Credenciales Incorrectas");
    }
}

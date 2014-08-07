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

import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class IdentificacionResourceTest {

    private static CredencialDAO credencialDAO = mock(CredencialDAO.class);
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
        reset(credencialDAO);
    }

    @Test
    public void debeDevolverTokenConLasCredencialesCorrectas() {
        String password = "Clave456";
        String token = "ASD123123asdasd";
        String username = "username";

        Credencial credencialesUsuario = new Credencial(username, password);

        when(credencialDAO.validar(any(Credencial.class))).thenReturn(Optional.of(token));

        ClientResponse response = client.resource("/identificacion")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesUsuario);

        verify(credencialDAO).validar(any(Credencial.class));
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void debeDevolverErrorCuandoContraseniaEsNula() {
        Credencial credencialesConContraseniaNula = new Credencial("indiferente", null);
        ClientResponse response = client.resource("/identificacion")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesConContraseniaNula);

        verifyZeroInteractions(credencialDAO);
        assertThat(response.getStatus(), is(400));
        ResourceTestHelper.assertErrorMessage(response, "El campo es obligatorio");
    }

    @Test
    public void debeDevolverErrorCuandoContraseniaEstaVacia() {
        Credencial credencialesConContraseniaVacia = new Credencial("indiferente", "");
        ClientResponse response = client.resource("/identificacion")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesConContraseniaVacia);

        verifyZeroInteractions(credencialDAO);
        assertThat(response.getStatus(), is(400));
        ResourceTestHelper.assertErrorMessage(response, "El campo es obligatorio");
    }

    @Test
    public void debeDevolverErrorCuandoNombreDeUsuarioEsNulo() {
        Credencial credencialesConContraseniaVacia = new Credencial(null, "indiferente");
        ClientResponse response = client.resource("/identificacion")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesConContraseniaVacia);

        verifyZeroInteractions(credencialDAO);
        assertThat(response.getStatus(), is(400));
        ResourceTestHelper.assertErrorMessage(response, "El campo es obligatorio");
    }

    @Test
    public void debeDevolverErrorCuandoNombreDeUsuarioEstaVacio() {
        Credencial credencialesConContraseniaVacia = new Credencial("", "indiferente");
        ClientResponse response = client.resource("/identificacion")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesConContraseniaVacia);

        verifyZeroInteractions(credencialDAO);
        assertThat(response.getStatus(), is(400));
        ResourceTestHelper.assertErrorMessage(response, "El campo es obligatorio");
    }

    @Test
    public void debeDevolverNoAutorizadoCuandoCredencialesSonIncorrectas() {
        Credencial credencialesIncorectas = new Credencial("incorrecta", "Incorr3cta");
        when(credencialDAO.validar(any(Credencial.class))).thenReturn(Optional.absent());

        ClientResponse response = client.resource("/identificacion")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialesIncorectas);

        verify(credencialDAO).validar(any(Credencial.class));
        assertThat(response.getStatus(), is(401));
        ResourceTestHelper.assertErrorMessage(response, "Credenciales Incorrectas");
    }
}

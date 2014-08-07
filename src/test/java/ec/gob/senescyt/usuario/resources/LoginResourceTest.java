package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.services.ServicioCredencial;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

public class LoginResourceTest {

    private static CredencialDAO credencialDAO = Mockito.mock(CredencialDAO.class);
    private static ServicioCredencial servicioCredencial = Mockito.mock(ServicioCredencial.class);
    private static LoginResource loginResource = new LoginResource(credencialDAO, servicioCredencial);

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(loginResource)
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;

    @Before
    public void setUp() {
        client = RESOURCES.client();
    }

    @Test
    public void debeVerificarExistenciaDeUsuario() {
        String nombreUsuario = "nombreUsuario";
        String contrasenia = "contrasenia";
        String hash = "hash-indiferente-bla-bla";
        CredencialLogin credencialLogin = new CredencialLogin(nombreUsuario, contrasenia);

        Credencial credencial = new Credencial(nombreUsuario, hash);

        Mockito.when(servicioCredencial.convertirACredencialDesdeLogin(any(CredencialLogin.class))).thenReturn(credencial);
        Mockito.when(credencialDAO.obtenerPorNombreUsuario(nombreUsuario)).thenReturn(credencial);

        ClientResponse response = client.resource("/login")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencialLogin);

        assertThat(response.getStatus(), is(201));
        verify(servicioCredencial).convertirACredencialDesdeLogin(any(CredencialLogin.class));
        verify(credencialDAO).obtenerPorNombreUsuario(nombreUsuario);
    }
}

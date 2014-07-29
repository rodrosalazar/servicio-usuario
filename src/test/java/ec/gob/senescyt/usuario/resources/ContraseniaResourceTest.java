package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.commons.exceptions.NotFoundExceptionMapper;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class ContraseniaResourceTest {

    public static final int OK_STATUS_CODE = Response.Status.OK.getStatusCode();
    private static TokenDAO tokenDAO = mock(TokenDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ContraseniaResource(tokenDAO))
            .addProvider(NotFoundExceptionMapper.class)
            .build();
    private String tokenInvalido;
    private String tokenValido;

    @Before
    public void setUp() throws Exception {
        reset(tokenDAO);
        tokenInvalido = UUID.randomUUID().toString();
        tokenValido = UUID.randomUUID().toString();
    }

    @Test
    public void debeDevolverRecursoNoEncontradoCuandoTokenNoEsValido() {
        when(tokenDAO.buscar(tokenInvalido)).thenThrow(new NotFoundException("Error"));

        ClientResponse response = resources.client().resource("/contrasenia/" + tokenInvalido)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
    }

    @Test
    public void debeDevolverElIdDelUsuarioCuandoElTokenEsValido() {
        when(tokenDAO.buscar(tokenValido)).thenReturn(new Token(tokenValido, UsuarioBuilder.usuarioValido()));
        ClientResponse response = resources.client().resource("/contrasenia/" + tokenValido)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        long idUsuarioEsperado = 0;
        assertThat(response.getStatus(), is(OK_STATUS_CODE));
        Token token = response.getEntity(Token.class);
        assertThat(token.getUsuario().getId(), is(idUsuarioEsperado));
    }

    @Test
    public void debeDevolverElNombreDeUsuarioCuandoElTokenEsValido() {
        when(tokenDAO.buscar(tokenValido)).thenReturn(new Token(tokenValido, UsuarioBuilder.usuarioValido()));
        ClientResponse response = resources.client().resource("/contrasenia/" + tokenValido)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        String nombreUsuarioEsperado = "usuarioSenescyt";
        assertThat(response.getStatus(), is(OK_STATUS_CODE));
        Token token = response.getEntity(Token.class);
        assertThat(token.getUsuario().getNombreUsuario(), is(nombreUsuarioEsperado));
    }
}
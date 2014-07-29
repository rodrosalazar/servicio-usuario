package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.commons.exceptions.NotFoundExceptionMapper;
import ec.gob.senescyt.usuario.core.TokenUsuario;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.TokenUsuarioDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContraseniaResourceTest {

    private static TokenUsuarioDAO tokenUsuarioDAO = mock(TokenUsuarioDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ContraseniaResource(tokenUsuarioDAO))
            .addProvider(NotFoundExceptionMapper.class)
            .build();
    private String tokenInvalido;
    private String tokenValido;

    @Before
    public void setUp() throws Exception {
        tokenInvalido = RandomStringUtils.randomAlphanumeric(16);
        tokenValido = RandomStringUtils.randomAlphanumeric(16);
    }

    @Test
    public void debeDevolverRecursoNoEncontradoCuandoTokenNoEsValido() {
        when(tokenUsuarioDAO.buscar(tokenInvalido)).thenThrow(new NotFoundException("Error"));

        ClientResponse response = resources.client().resource("/contrasenia/" + tokenInvalido)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
    }

    @Test
    public void debeDevolverElIdDelUsuarioCuandoElTokenEsValido() {
        when(tokenUsuarioDAO.buscar(tokenValido)).thenReturn(new TokenUsuario(tokenValido, UsuarioBuilder.usuarioValido()));
        ClientResponse response = resources.client().resource("/contrasenia/" + tokenValido)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        long idUsuarioEsperado = 0;
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        TokenUsuario tokenUsuario = response.getEntity(TokenUsuario.class);
        Usuario usuario = tokenUsuario.getUsuario();
        assertThat(usuario.getId(), is(idUsuarioEsperado));
    }

    @Test
    public void debeDevolverElNombreDeUsuarioCuandoElTokenEsValido() {
        when(tokenUsuarioDAO.buscar(tokenValido)).thenReturn(new TokenUsuario(tokenValido, UsuarioBuilder.usuarioValido()));
        ClientResponse response = resources.client().resource("/contrasenia/" + tokenValido)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        String nombreUsuarioEsperado = "usuarioSenescyt";
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getEntity(TokenUsuario.class).getUsuario().getNombreUsuario(), is(nombreUsuarioEsperado));
    }
}
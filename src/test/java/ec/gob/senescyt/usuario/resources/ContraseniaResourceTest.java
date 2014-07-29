package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.exceptions.NotFoundExceptionMapper;
import ec.gob.senescyt.usuario.dao.TokenDAO;
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
        tokenInvalido = RandomStringUtils.randomAlphanumeric(16);
        tokenValido = RandomStringUtils.randomAlphanumeric(16);

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
        ClientResponse response = resources.client().resource("/contrasenia/" + tokenValido)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Integer idUsuarioEsperado = 1;
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getEntity(Integer.class), is(idUsuarioEsperado));
    }
}
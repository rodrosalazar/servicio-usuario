package ec.gob.senescyt.biblioteca.resource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.dao.ArbolDAO;
import ec.gob.senescyt.commons.builders.ArbolBuilder;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;

public class ArbolResourceTest {

    private static ArbolDAO arbolDAO = Mockito.mock(ArbolDAO.class);
    private static ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new ArbolResource(arbolDAO, constructorRespuestas))
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;

    @Before
    public void setUp() {
        Mockito.reset(arbolDAO);
        client = RESOURCES.client();
        Arbol arbol = ArbolBuilder.nuevoArbol().generar();
        Mockito.when(arbolDAO.obtenerPorId(any())).thenReturn(arbol);
    }

    @Test
    public void debeObtenerTodosLosArboles() {
        ClientResponse response = client.resource("/arboles")
                .get(ClientResponse.class);

        Mockito.verify(arbolDAO).obtenerTodos();
        assertThat(response.getStatus(), is(200));
        Map arboles = response.getEntity(Map.class);
        MatcherAssert.assertThat(arboles.isEmpty(), is(not(true)));
    }
}

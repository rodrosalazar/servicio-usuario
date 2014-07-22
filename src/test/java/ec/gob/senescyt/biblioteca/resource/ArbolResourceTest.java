package ec.gob.senescyt.biblioteca.resource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.dao.ArbolDAO;
import ec.gob.senescyt.commons.builders.ArbolBuilder;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ArbolResourceTest {

    private ArbolResource arbolResource;
    private static ArbolDAO arbolDAO = mock(ArbolDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ArbolResource(arbolDAO))
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;
    private Arbol arbol;

    @Before
    public void setUp() throws Exception {

        reset(arbolDAO);
        client = resources.client();
        arbol = ArbolBuilder.nuevoArbol().generar();
        when(arbolDAO.obtenerPorId(any())).thenReturn(arbol);
    }

    @Test
    public void debeObtenerArbolDeNormativas() throws Exception {

        ClientResponse response = client.resource("/arboles/" + arbol.getId())
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));

        Arbol responseEntity = response.getEntity(Arbol.class);
        assertThat(responseEntity.getId(), is(arbol.getId()));
        assertThat(responseEntity.getNivelesArbol().size(), is(arbol.getNivelesArbol().size()));
    }
}

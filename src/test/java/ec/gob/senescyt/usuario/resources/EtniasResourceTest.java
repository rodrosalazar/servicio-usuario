package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Etnia;
import ec.gob.senescyt.usuario.dao.EtniaDAO;
import ec.gob.senescyt.usuario.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.usuario.resources.builders.ConstructorRespuestas;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class EtniasResourceTest {

    public static final String ID_ETNIA = "99";
    public static final String NOMBRE_ETNIA = "ETNIA_TEST";

    private EtniaResource etniaResource;
    private EtniaDAO etniaDAO = mock(EtniaDAO.class);
    private ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();

    @Before
    public void setUp() {
        etniaResource = new EtniaResource(etniaDAO, constructorRespuestas);
    }

    @Test
    public void debeObtenerTodosLosPaises() throws Exception {
        Etnia etnia = new Etnia(ID_ETNIA, NOMBRE_ETNIA);
        when(etniaDAO.obtenerTodos()).thenReturn(newArrayList(etnia));

        Response response = etniaResource.obtenerTodos();

        verify(etniaDAO).obtenerTodos();
        assertThat(response.getStatus(), is(200));
        assertThat(((HashMap<String, List>) response.getEntity()).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_ETNIAS.getNombre()).size(), is(not(0)));
    }
}


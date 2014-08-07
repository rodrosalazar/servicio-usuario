package ec.gob.senescyt.titulos.resources;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.Etnia;
import ec.gob.senescyt.titulos.dao.EtniaDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EtniasResourceTest {

    public static final String ID_ETNIA = "99";
    public static final String NOMBRE_ETNIA = "ETNIA_TEST";

    private EtniaResource etniaResource;
    private EtniaDAO etniaDAO = Mockito.mock(EtniaDAO.class);
    private ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();

    @Before
    public void setUp() {
        etniaResource = new EtniaResource(etniaDAO, constructorRespuestas);
    }

    @Test
    public void debeObtenerTodosLosPaises() {
        Etnia etnia = new Etnia(ID_ETNIA, NOMBRE_ETNIA);
        Mockito.when(etniaDAO.obtenerTodos()).thenReturn(newArrayList(etnia));

        Response response = etniaResource.obtenerTodos();

        Mockito.verify(etniaDAO).obtenerTodos();
        assertThat(response.getStatus(), is(200));
        assertThat(((HashMap<String, List>) response.getEntity()).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_ETNIAS.getNombre()).size(), is(not(0)));
    }
}


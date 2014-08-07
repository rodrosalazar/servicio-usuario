package ec.gob.senescyt.titulos.resources;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.Pais;
import ec.gob.senescyt.titulos.dao.PaisDAO;
import ec.gob.senescyt.usuario.resources.PaisResource;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaisResourceTest {

    public static final String ID_PAIS = "999999";
    public static final String NOMBRE_PAIS = "Honduras";

    private PaisResource paisResource;
    private PaisDAO paisDAO = mock(PaisDAO.class);
    private ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();

    @Before
    public void setUp() {
        paisResource = new PaisResource(paisDAO, constructorRespuestas);
    }

    @Test
    public void debeObtenerTodosLosPaises() {
        Pais pais = new Pais(ID_PAIS, NOMBRE_PAIS);
        when(paisDAO.obtenerTodos()).thenReturn(newArrayList(pais));

        Response response = paisResource.obtenerTodos();

        verify(paisDAO).obtenerTodos();
        assertThat(response.getStatus(), is(200));
        assertThat(((HashMap<String, List>) response.getEntity()).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PAISES.getNombre()).size(), is(not(0)));
    }
}


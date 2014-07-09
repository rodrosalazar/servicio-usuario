package ec.gob.senescyt.usuario.resources.pais;

import ec.gob.senescyt.usuario.core.pais.Pais;
import ec.gob.senescyt.usuario.dao.pais.PaisDAO;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PaisResourceTest {

    public static final String ID_PAIS = "999999";
    public static final String NOMBRE_PAIS = "Honduras";

    private PaisResource paisResource;
    private PaisDAO paisDAO = mock(PaisDAO.class);

    @Before
    public void setUp() {
        paisResource = new PaisResource(paisDAO);
    }

    @Test
    public void debeObtenerTodosLosPaises() throws Exception {
        Pais pais = new Pais(ID_PAIS, NOMBRE_PAIS);
        when(paisDAO.obtenerTodos()).thenReturn(newArrayList(pais));

        Response response = paisResource.obtenerTodos();

        verify(paisDAO).obtenerTodos();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((List<Pais>) response.getEntity())).isNotEmpty();
    }
}


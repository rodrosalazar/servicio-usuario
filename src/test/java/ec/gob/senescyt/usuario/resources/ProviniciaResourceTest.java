package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Provincia;
import ec.gob.senescyt.usuario.dao.ProvinciaDAO;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProviniciaResourceTest {

    public static final String ID_PROVINCIA = "1";
    public static final String NOMBRE_PROVINCIA = "Azuay";

    private ProvinciaResource provinciaResource;
    private ProvinciaDAO provinciaDAO = mock(ProvinciaDAO.class);

    @Before
    public void setUp() throws Exception {
        provinciaResource = new ProvinciaResource(provinciaDAO);
    }

    @Test
    public void debeObtenerTodasLasProvincias() throws Exception {
        Provincia provincia = new Provincia(ID_PROVINCIA, NOMBRE_PROVINCIA);
        when(provinciaDAO.obtenerTodos()).thenReturn(newArrayList(provincia));

        Response response = provinciaResource.obtenerTodos();

        verify(provinciaDAO).obtenerTodos();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((List<Provincia>) response.getEntity())).isNotEmpty();
    }
}

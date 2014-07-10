package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.dao.ClasificacionDAO;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ClasificacionResourceTest {
    public static final String ID_CLASIFICACION = "001";
    public static final String NOMBRE_CLASIFICACION = "CINE-UNESCO 1997";
    private ClasificacionResource clasificacionResource;
    private ClasificacionDAO clasificacionDAO = mock(ClasificacionDAO.class);

    @Before
    public void setUp() {
        clasificacionResource = new ClasificacionResource(clasificacionDAO);
    }

    @Test
    public void debeObtenerElListadoDeAreasYSubareasParaCine() throws Exception {
        Clasificacion clasificacion = new Clasificacion(ID_CLASIFICACION, NOMBRE_CLASIFICACION, null);
        when(clasificacionDAO.obtenerClasificacion("001")).thenReturn(clasificacion);

        Response response = clasificacionResource.obtenerClasificacion("1997");

        verify(clasificacionDAO).obtenerClasificacion("001");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((Clasificacion) response.getEntity())).isNotNull();
    }
}

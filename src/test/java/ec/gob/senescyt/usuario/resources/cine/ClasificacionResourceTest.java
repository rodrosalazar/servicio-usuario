package ec.gob.senescyt.usuario.resources.cine;

import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.dao.cine.ClasificacionDAO;
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
    public void debeObtenerElListadoDeAreasYSubareasParaCine1997() throws Exception {
        Clasificacion clasificacion = new Clasificacion(ID_CLASIFICACION, NOMBRE_CLASIFICACION, null);
        when(clasificacionDAO.obtenerClasificacion()).thenReturn(clasificacion);

        Response response = clasificacionResource.obtenerClasificacion();

        verify(clasificacionDAO).obtenerClasificacion();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((Clasificacion) response.getEntity())).isNotNull();
    }
}

package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.dao.Cine1997DAO;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class Cine1997ResourceTest {
    public static final String ID_CLASIFICACION = "001";
    public static final String NOMBRE_CLASIFICACION = "CINE-UNESCO 1997";
    private Cine1997Resource cine1997Resource;
    private Cine1997DAO cine1997DAO = mock(Cine1997DAO.class);

    @Before
    public void setUp() {
        cine1997Resource = new Cine1997Resource(cine1997DAO);
    }

    @Test
    public void debeObtenerElListadoDeAreasYSubareasParaCine1997() throws Exception {
        Clasificacion clasificacion = new Clasificacion(ID_CLASIFICACION, NOMBRE_CLASIFICACION, null);
        when(cine1997DAO.obtenerClasificacion()).thenReturn(clasificacion);

        Response response = cine1997Resource.obtenerClasificacion();

        verify(cine1997DAO).obtenerClasificacion();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((Clasificacion) response.getEntity())).isNotNull();
    }
}

package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class InstitucionResourceTest {
    public static final long ID_INSTITUCION = 1l;
    public static final String NOMBRE_INSTITUCION = "ESPE";
    public static final long REGIMEN_ID = 1L;
    public static final String NOMBRE_REGIMEN = "REGIMENTEST";
    public static final long ESTADO_ID = 1L;
    public static final String NOMBRE_ESTADO = "ESTADO";
    public static final long CATEGORIA_ID = 1L;
    public static final String NOMBRE_CATEGORIA = "CATEGORIA";

    private InstitucionResource institucionResource;
    private InstitucionDAO institucionDAO = mock(InstitucionDAO.class);

    @Before
    public void setUp() {
        institucionResource = new InstitucionResource(institucionDAO);
    }

    @Test
    public void debeObtenerTodasLasInstituciones() throws Exception {
        Institucion institucion = new Institucion(ID_INSTITUCION, NOMBRE_INSTITUCION, REGIMEN_ID, NOMBRE_REGIMEN, ESTADO_ID, NOMBRE_ESTADO, CATEGORIA_ID, NOMBRE_CATEGORIA);
        when(institucionDAO.obtenerTodas()).thenReturn(newArrayList(institucion));

        Response response = institucionResource.obtenerTodas();

        verify(institucionDAO).obtenerTodas();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(( (List<Institucion>) response.getEntity())).isNotEmpty();
    }
}

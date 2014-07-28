package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
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

    private static InstitucionDAO institucionDAO = mock(InstitucionDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new InstitucionResource(institucionDAO))
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;

    @Before
    public void setUp() {
        client = resources.client();
    }

    @Test
    public void debeObtenerTodasLasInstituciones() throws Exception {
//        Institucion institucion = new Institucion(ID_INSTITUCION, NOMBRE_INSTITUCION, REGIMEN_ID, NOMBRE_REGIMEN, ESTADO_ID, NOMBRE_ESTADO, CATEGORIA_ID, NOMBRE_CATEGORIA);
//        when(institucionDAO.obtenerTodas()).thenReturn(newArrayList(institucion));
//
//        ClientResponse response = client.resource("/instituciones")
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .get(ClientResponse.class);
//
//        verify(institucionDAO).obtenerTodas();
//        assertThat(response.getStatus()).isEqualTo(200);
//        HashMap instituciones =
//
//        assertThat(( (List<Institucion>) response.getEntity())).isNotEmpty();
    }

    @Test
    public void debeObtenerUniversidadesDeConvenio() throws Exception {


    }
}

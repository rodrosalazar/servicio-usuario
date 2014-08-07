package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.UniversidadExtranjera;
import ec.gob.senescyt.titulos.core.UniversidadExtranjeraDAO;
import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InstitucionResourceTest {
    public static final long ID_INSTITUCION = 1l;
    public static final String NOMBRE_INSTITUCION = "ESPE";
    public static final long REGIMEN_ID = 1L;
    public static final String NOMBRE_REGIMEN = "REGIMENTEST";
    public static final long ESTADO_ID = 1L;
    public static final String NOMBRE_ESTADO = "ESTADO";
    public static final long CATEGORIA_ID = 1L;
    public static final String NOMBRE_CATEGORIA = "CATEGORIA";

    private static InstitucionDAO institucionDAO = Mockito.mock(InstitucionDAO.class);
    private static ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();
    private static UniversidadExtranjeraDAO universidadExtranjeraDAO = Mockito.mock(UniversidadExtranjeraDAO.class);

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new InstitucionResource(institucionDAO, constructorRespuestas, universidadExtranjeraDAO))
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;

    @Before
    public void setUp() {
        client = RESOURCES.client();
    }

    @Test
    public void debeObtenerTodasLasInstituciones() {
        Institucion institucion = new Institucion(ID_INSTITUCION, NOMBRE_INSTITUCION, REGIMEN_ID, NOMBRE_REGIMEN, ESTADO_ID, NOMBRE_ESTADO, CATEGORIA_ID, NOMBRE_CATEGORIA);
        Mockito.when(institucionDAO.obtenerTodas()).thenReturn(newArrayList(institucion));

        ClientResponse response = client.resource("/instituciones")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Mockito.verify(institucionDAO).obtenerTodas();
        assertThat(response.getStatus(), is(200));
        List instituciones = response.getEntity(List.class);
        assertThat(instituciones.isEmpty(), is(not(true)));

    }

    @Test
    public void debeObtenerUniversidadesDeConvenio() {
        String id = "4001";
        String nombre = "UNIVERSIDAD CONVENIO";
        String codigoTipo = "00008";
        String codigoPais = "FI";

        UniversidadExtranjera universidadExtranjeraConvenio = new UniversidadExtranjera(id, nombre, codigoTipo, codigoPais);
        Mockito.when(universidadExtranjeraDAO.obtenerUniversidadesConvenio()).thenReturn(newArrayList(universidadExtranjeraConvenio));

        ClientResponse response = client.resource("/instituciones/convenio")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Mockito.verify(universidadExtranjeraDAO).obtenerUniversidadesConvenio();
        assertThat(response.getStatus(), is(200));
        Map universidadesConvenio = response.getEntity(Map.class);
        assertThat(universidadesConvenio.isEmpty(), is(not(true)));
    }

    @Test
    public void debeObtenerUniversidadesDeListado() {
        String id = "4001";
        String nombre = "UNIVERSIDAD LISTADO";
        String codigoTipo = "00006";
        String codigoPais = "FI";

        UniversidadExtranjera universidadExtranjeraListado = new UniversidadExtranjera(id, nombre, codigoTipo, codigoPais);
        Mockito.when(universidadExtranjeraDAO.obtenerUniversidadesListado()).thenReturn(newArrayList(universidadExtranjeraListado));

        ClientResponse response = client.resource("/instituciones/listado")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Mockito.verify(universidadExtranjeraDAO).obtenerUniversidadesListado();
        assertThat(response.getStatus(), is(200));
        Map universidadesListado = response.getEntity(Map.class);
        assertThat(universidadesListado.isEmpty(), is(not(true)));
    }
}

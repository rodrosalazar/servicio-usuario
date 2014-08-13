package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import ec.gob.senescyt.ayudantes.AyudantePerfil;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@SuppressWarnings("PMD.TooManyStaticImports")
public class PerfilResourceTest {

    public static final String PERFILES_URL = "/perfiles";
    public static final String PERFILES_RESOURCE_URL = "/perfiles/1";
    private static PerfilDAO perfilDAO = mock(PerfilDAO.class);

    private final AyudantePerfil ayudantePerfil = new AyudantePerfil();
    private Perfil perfil;
    
    

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
                                                                            .addResource(new PerfilResource(perfilDAO))
                                                                            .addProvider(ValidacionExceptionMapper.class)
                                                                            .build();

    private Client client;
    
    @Before
    public void setUp() {
        client = RESOURCES.client();
    }

    @After
    public void tearDown() {
        reset(perfilDAO);
    }

    @Test
    public void debeCrear() {
        perfil = ayudantePerfil.construirConPermisos(new Permiso());
        ClientResponse response = getClient(PERFILES_URL)
                                        .post(ClientResponse.class, perfil);
        assertThat(response.getStatus(), is(201));
        Perfil encontrarEntidad = response.getEntity(Perfil.class);
        assertThat(encontrarEntidad.getNombre(), is(perfil.getNombre()));
        verify(perfilDAO, times(1)).guardar(any(Perfil.class));
    }

    private WebResource.Builder getClient(String url) {
        return client.resource(url)
                             .header("Content-Type", MediaType.APPLICATION_JSON);
    }

    @Test
    public void debeNoCrearCuandoNoValido() {
        perfil = ayudantePerfil.construir();
        ClientResponse response = getClient(PERFILES_URL)
                .post(ClientResponse.class, perfil);
        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void debeModificar() {
        perfil = ayudantePerfil.construirConPermisos(new Permiso());
        when(perfilDAO.encontrarPorId(1L)).thenReturn(perfil);
        ClientResponse response = getClient(PERFILES_URL)
                .put(ClientResponse.class, perfil);
        assertThat(response.getStatus(), is(200));
        Perfil encontrarEntidad = response.getEntity(Perfil.class);
        assertThat(encontrarEntidad.getNombre(), is(perfil.getNombre()));
        verify(perfilDAO, times(1)).guardar(any(Perfil.class));
    }

    @Test
    public void debeNoModificarCuandoNoValido() {
        perfil = ayudantePerfil.construir();
        ClientResponse response = getClient(PERFILES_URL)
                .put(ClientResponse.class, perfil);
        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void debeObtener() {
        perfil = ayudantePerfil.construir();
        when(perfilDAO.encontrarPorId(1L)).thenReturn(perfil);
        ClientResponse response = getClient(PERFILES_RESOURCE_URL)
                .get(ClientResponse.class);
        assertThat(response.getStatus(), is(200));
        Perfil encontrarEntidad = response.getEntity(Perfil.class);
        assertThat(encontrarEntidad.getNombre(), is(perfil.getNombre()));
    }

    @Test
    public void debeNoObtenerCuandoNoHayObjecto() {
        ClientResponse response = getClient(PERFILES_RESOURCE_URL)
                .get(ClientResponse.class);
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void debeObtenerTodos() {
        perfil = ayudantePerfil.construir();
        List<Perfil> perfilList = newArrayList(perfil);
        when(perfilDAO.obtenerTodos()).thenReturn(perfilList);
        ClientResponse response = getClient(PERFILES_URL)
                .get(ClientResponse.class);
        assertThat(response.getStatus(), is(200));
        List<LinkedHashMap> encontrarEntidad = response.getEntity(List.class);
        assertThat(encontrarEntidad.size(), is(1));
        assertThat(encontrarEntidad.get(0).get("nombre"), is(perfil.getNombre()));
    }

    @Test
    public void debeEliminar() {
        perfil = ayudantePerfil.construirConPermisos(new Permiso());
        long id = 1L;
        when(perfilDAO.encontrarPorId(id)).thenReturn(perfil);
        ClientResponse response = getClient(PERFILES_RESOURCE_URL)
                .delete(ClientResponse.class);
        assertThat(response.getStatus(), is(200));
        verify(perfilDAO, times(1)).eliminar(id);
    }

}
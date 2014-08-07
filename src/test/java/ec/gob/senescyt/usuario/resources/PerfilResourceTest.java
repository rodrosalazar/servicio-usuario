package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PerfilBuilder;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.usuario.core.Acceso;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PerfilResourceTest {

    private static PerfilDAO perfilDAO = mock(PerfilDAO.class);
    private static ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new PerfilResource(perfilDAO, constructorRespuestas))
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
    public void noPuedoCrearPerfilSinNombre() {
        Perfil perfil = PerfilBuilder.nuevoPerfil()
                .con(p -> p.nombre = null)
                .generar();

        ClientResponse response = client.resource("/perfiles")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, perfil);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void noPuedoCrearPerfilConNombreDeMasDe100Caracteres() {
        Perfil perfil = PerfilBuilder.nuevoPerfil()
                .con(p -> p.nombre = RandomStringUtils.random(101))
                .generar();

        ClientResponse response = client.resource("/perfiles")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, perfil);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void noDebeCrearUnPerfilSinPermisos() {
        Perfil perfilSinPermisos = PerfilBuilder.nuevoPerfil()
                .con(p -> p.permisos = null)
                .generar();

        ClientResponse response = client.resource("/perfiles")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, perfilSinPermisos);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void noDebeCrearUnPerfilConPermisosSinModulo() {
        Permiso permiso = new Permiso(null, 1l, newArrayList(Acceso.LEER));
        Perfil perfilSinPermisos = PerfilBuilder.nuevoPerfil()
                .con(p -> p.permisos = newArrayList(permiso))
                .generar();

        ClientResponse response = client.resource("/perfiles")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, perfilSinPermisos);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void noDebeCrearUnPerfilConPermisosSinFuncion() {
        Permiso permiso = new Permiso(1l, null, newArrayList(Acceso.LEER));
        Perfil perfilSinPermisos = PerfilBuilder.nuevoPerfil()
                .con(p -> p.permisos = newArrayList(permiso))
                .generar();

        ClientResponse response = client.resource("/perfiles")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, perfilSinPermisos);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void noDebeCrearUnPerfilSinAlMenosUnAcceso() {
        long moduloId = 1l;
        long funcionId = 2l;

        Permiso permisoSinAcceso = new Permiso(moduloId, funcionId, null);
        List<Permiso> permisos = newArrayList(permisoSinAcceso);

        Perfil perfilSinAccesos = PerfilBuilder.nuevoPerfil()
                .con(p -> p.permisos = permisos)
                .generar();

        ClientResponse response = client.resource("/perfiles")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, perfilSinAccesos);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void debeCrearUnNuevoPerfilConNombreYPermisos() {
        Perfil perfil = PerfilBuilder.nuevoPerfil()
                .generar();

        ClientResponse response = client.resource("/perfiles")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, perfil);

        verify(perfilDAO).guardar(any(Perfil.class));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void debeObtenerTodosLosPerfiles() {
        Perfil perfil = PerfilBuilder.nuevoPerfil().generar();
        when(perfilDAO.obtenerTodos()).thenReturn(newArrayList(perfil));

        ClientResponse response = client.resource("/perfiles")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        verify(perfilDAO).obtenerTodos();
        assertThat(response.getStatus()).isEqualTo(200);
        Map<String, List<Perfil>> resultado = response.getEntity(Map.class);
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get("perfiles").size()).isEqualTo(1);
    }
}
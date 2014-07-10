package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.builders.PerfilBuilder;
import ec.gob.senescyt.usuario.core.Acceso;
import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class PerfilResourceTest {

    private PerfilDAO perfilDAO;
    private PerfilResource perfilResource;

    @Before
    public void setUp() throws Exception {
        perfilDAO = mock(PerfilDAO.class);
        perfilResource = new PerfilResource(perfilDAO);
    }

    @Test
    public void noPuedoCrearPerfilSinNombre() {
        Perfil perfil = new Perfil(null, null);

        Response response = perfilResource.crearPerfil(perfil);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void noDebeCrearUnPerfilSinPermisos() {
        Perfil perfilSinPermisos = new Perfil("indiferente", null);

        Response response = perfilResource.crearPerfil(perfilSinPermisos);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void noDebeCrearUnPerfilSinAlMenosUnAcceso() {
        long modduloId = 1l;
        long funcionId = 2l;
        Permiso permisoSinAcceso = new Permiso(modduloId, funcionId, null);
        List<Permiso> permisos = newArrayList(permisoSinAcceso);
        Perfil perfilSinAccesos = new Perfil("indiferente", permisos);

        Response response = perfilResource.crearPerfil(perfilSinAccesos);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(perfilDAO);
    }

    @Test
    public void debeCrearUnNuevoPerfilConNombreYPermisos() {
        long moduloId = 3l;
        long funcionId = 1l;
        List<Acceso> accesos = newArrayList(Acceso.LEER, Acceso.MODIFICAR, Acceso.ELIMINAR, Acceso.CREAR);
        Permiso permiso = new Permiso(moduloId, funcionId, accesos);
        Perfil perfil = new Perfil("Perfil 1", newArrayList(permiso));

        Response response = perfilResource.crearPerfil(perfil);

        verify(perfilDAO).guardar(eq(perfil));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void debeObtenerTodosLosPerfiles() throws Exception {
        Perfil perfil = PerfilBuilder.nuevoPerfil().generar();
        when(perfilDAO.obtenerTodos()).thenReturn(newArrayList(perfil));

        Response response = perfilResource.obtenerTodos();

        verify(perfilDAO).obtenerTodos();
        assertThat(response.getStatus()).isEqualTo(200);
        Map<String, List<Perfil>> resultado = (Map<String, List<Perfil>>) response.getEntity();
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get("perfiles").size()).isEqualTo(1);
    }
}
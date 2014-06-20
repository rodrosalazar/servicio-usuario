package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Acceso;
import ec.gob.senescyt.usuario.core.Funcion;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
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

        verifyZeroInteractions(perfilDAO);
        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void debeCrearUnNuevoPerfilConNombreYPermisos() {
        Funcion funcion = new Funcion("funcionA", newArrayList(Acceso.LEER, Acceso.MODIFICAR, Acceso.ELIMINAR, Acceso.CREAR));
        Permiso permiso = new Permiso("modulo101", newArrayList(funcion));
        Perfil perfil = new Perfil("Perfil 1", newArrayList(permiso));

        Response response = perfilResource.crearPerfil(perfil);

        verify(perfilDAO).guardar(any(Perfil.class));
        assertThat(response.getStatus()).isEqualTo(201);
    }
}
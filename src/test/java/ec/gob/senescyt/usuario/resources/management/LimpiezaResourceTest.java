package ec.gob.senescyt.usuario.resources.management;


import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.utils.Hasher;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LimpiezaResourceTest {

    @Test
    public void debeLimpiarLaBaseDeDatos() {
        UsuarioDAO usuarioDAO = Mockito.mock(UsuarioDAO.class);
        PerfilDAO perfilDAO = Mockito.mock(PerfilDAO.class);
        InstitucionDAO institucionDAO = Mockito.mock(InstitucionDAO.class);
        CredencialDAO credencialDAO = Mockito.mock(CredencialDAO.class);
        Hasher hasher = Mockito.mock(Hasher.class);
        LimpiezaResource limpiezaResource = new LimpiezaResource(usuarioDAO, perfilDAO, institucionDAO, credencialDAO, hasher);

        Institucion institucion = new Institucion(1, "institucionTest", 1, "regimenTest", 1, "estadoTest", 1, "categoriaTest");

        Mockito.when(institucionDAO.obtenerTodas()).thenReturn(newArrayList(institucion));


        Response response = limpiezaResource.inicializarDatosParaPruebas();
        CredencialLogin credencial = (CredencialLogin) response.getEntity();

        Mockito.verify(credencialDAO).guardar(Matchers.any(Credencial.class));
        Mockito.verify(usuarioDAO).guardar(Matchers.any(Usuario.class));
        Mockito.verify(usuarioDAO).limpiar();
        Mockito.verify(perfilDAO).limpiar();
        assertThat(response.getStatus(), is(200));
        assertThat(credencial.getNombreUsuario().isEmpty(), is(not(true)));
        assertThat(credencial.getContrasenia().isEmpty(), is(not(true)));
    }
}

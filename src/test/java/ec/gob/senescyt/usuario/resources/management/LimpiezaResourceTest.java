package ec.gob.senescyt.usuario.resources.management;


import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LimpiezaResourceTest {

    @Test
    public void debeLimpiarLaBaseDeDatos() {
        UsuarioDAO usuarioDAO = mock(UsuarioDAO.class);
        PerfilDAO perfilDAO = mock(PerfilDAO.class);
        LimpiezaResource limpiezaResource = new LimpiezaResource(usuarioDAO, perfilDAO);

        Response response = limpiezaResource.limpiar();

        verify(usuarioDAO).limpiar();
        verify(perfilDAO).limpiar();
        assertThat(response.getStatus()).isEqualTo(204);
    }
}

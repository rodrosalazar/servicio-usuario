package ec.gob.senescyt.usuario.resources.management;


import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LimpiezaResourceTest {

    @Test
    public void debeLimpiarLaBaseDeDatos() {
        UsuarioDAO usuarioDAO = mock(UsuarioDAO.class);
        LimpiezaResource limpiezaResource = new LimpiezaResource(usuarioDAO);

        Response response = limpiezaResource.limpiar();

        verify(usuarioDAO).limpiar();
        assertThat(response.getStatus()).isEqualTo(204);
    }
}

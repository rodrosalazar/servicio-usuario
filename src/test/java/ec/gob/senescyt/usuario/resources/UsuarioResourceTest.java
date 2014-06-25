package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UsuarioResourceTest {
    private UsuarioResource usuarioResource;
    private UsuarioDAO usuarioDAO;

    @Before
    public void init() {
        usuarioDAO = mock(UsuarioDAO.class);
        usuarioResource = new UsuarioResource(usuarioDAO);
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaValida() {
        String cedulaValida = "1718642174";

        Response response = usuarioResource.verificarCedula(cedulaValida);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaInvalida() {
        String cedulaInvalida = "1111111111";

        Response response = usuarioResource.verificarCedula(cedulaInvalida);

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void debeGuardarUsuario() {
        long idInstitucion = 1l;

        Usuario usuario = new Usuario(TipoDocumentoEnum.CEDULA, "1718642174", "Nelson", "Alberto", "Jumbo", "Hidalgo",
                "njumbo@thoughtworks.com", "123456", new Date(), idInstitucion, "nombreUsuario");

        Response response = usuarioResource.crearUsuario(usuario);

        verify(usuarioDAO).guardar(eq(usuario));
        assertThat(response.getStatus()).isEqualTo(201);
    }
}

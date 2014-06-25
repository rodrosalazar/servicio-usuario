package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.core.Nombre;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

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
    public void debeIndicarCuandoUnEmailInstitucionalEsInvalido(){
        String emailInvalido = "emailInvalido";

        Usuario usuarioConEmailInvalido = new Usuario(null, null,
                emailInvalido,null, null, null, null);

        Response response = usuarioResource.crearUsuario(usuarioConEmailInvalido);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeIndicarCuandoUnEmailInstitucionalEsValido(){
        String emailValido = "test@senescyt.gob.ec";

        Usuario usuarioConEmailInvalido = new Usuario(null, null,
                emailValido,null, null, null, null);

        Response response = usuarioResource.crearUsuario(usuarioConEmailInvalido);

        verify(usuarioDAO).guardar(eq(usuarioConEmailInvalido));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void debeGuardarUsuario() {
        long idInstitucion = 1l;

        Usuario usuario = new Usuario(new Identificacion(TipoDocumentoEnum.CEDULA, "1718642174"), new Nombre("Nelson", "Alberto", "Jumbo", "Hidalgo"),
                "njumbo@thoughtworks.com", "123456", new DateTime().withYear(2015).withMonthOfYear(1).withDayOfMonth(12), idInstitucion, "nombreUsuario");

        Response response = usuarioResource.crearUsuario(usuario);

        verify(usuarioDAO).guardar(eq(usuario));
        assertThat(response.getStatus()).isEqualTo(201);
    }
}

package ec.gob.senescyt.usuario.resources;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;

public class UsuarioResourceTest {
    UsuarioResource usuarioResource;

    @Before
    public void init() {
        usuarioResource = new UsuarioResource();
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
        usuarioResource = new UsuarioResource();
        Response response = usuarioResource.verificarCedula(cedulaInvalida);

        assertThat(response.getStatus()).isEqualTo(400);
    }

}

package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.ContraseniaTokenBuilder;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dto.ContraseniaToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static com.google.common.collect.Lists.newArrayList;
import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CredencialResourceIntegracionTest extends AbstractIntegracionTest {

    public static final String NOMBRE_USUARIO = "pepito";
    public static final String CONTRASENIA = "Perez5";

    private Perfil perfilGuardado;
    private Usuario usuarioGuardado;
    private Token tokenGuardado;

    public static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() {
        perfilGuardado = ayudantePerfil.construirConPermisos();
        perfilDAO.guardar(perfilGuardado);

        Usuario usuarioValido = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.perfiles = newArrayList(perfilGuardado.getId()))
                .con(u -> u.nombreUsuario = NOMBRE_USUARIO)
                .generar();
        ClientResponse respuestaUsuario = hacerPost("usuario", usuarioValido);
        Assert.assertThat(respuestaUsuario.getStatus(), is(201));
        usuarioGuardado = respuestaUsuario.getEntity(Usuario.class);

        tokenGuardado = tokenDAO.buscarPorIdUsuario(usuarioGuardado.getId()).get();
    }

    @Test
    public void debeIndicarMalaPeticionSiLaCredencialEsInvalida() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.idToken = "un-token-inventado")
                .con(c -> c.contrasenia = CONTRASENIA)
                .generar();

        ClientResponse respuesta =  hacerPost("credenciales", contraseniaToken);
        assertThat(respuesta.getStatus(), is(400));
        assertErrorMessage(respuesta, "Token inválido");
    }

    @Test
    public void debeAlmacenarUnaNuevaCredencialConElHashDeLaContrasenia() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.idToken = tokenGuardado.getId())
                .con(c -> c.contrasenia = CONTRASENIA)
                .generar();

        ClientResponse respuesta =  hacerPost("credenciales", contraseniaToken);

        assertThat(respuesta.getStatus(), is(201));
    }

    @Test
    public void debePermitirElUsoDelTokenUnaSolaVez() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.idToken = tokenGuardado.getId())
                .con(c -> c.contrasenia = CONTRASENIA)
                .generar();

        ClientResponse primeraRespuesta =  hacerPost("credenciales", contraseniaToken);
        ClientResponse segundaRespuesta =  hacerPost("credenciales", contraseniaToken);

        assertThat(primeraRespuesta.getStatus(), is(201));
        assertThat(segundaRespuesta.getStatus(), is(400));
    }
}

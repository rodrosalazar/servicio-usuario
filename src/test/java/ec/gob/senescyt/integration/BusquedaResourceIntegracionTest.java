package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedMap;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BusquedaResourceIntegracionTest extends AbstractIntegracionTest {

    private static final String ID_TOKEN = "32d88be3-2233-4b58-bf3c-99c35b162805";
private static final String USUARIO_IDENTIFICACION="12345";
    private TokenDAO tokenDAO;
    private Usuario usuarioGuardado;

    @Before
    public void setUp() {
        tokenDAO = new TokenDAO(sessionFactory, RULE.getConfiguration().getDefaultSchema());
        cargarDataParaPruebas();
    }

    private void cargarDataParaPruebas() {
        Perfil perfilGuardado = ayudantePerfil.construirConPermisos();
        perfilDAO.guardar(perfilGuardado);


        Usuario usuario = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.perfiles = newArrayList(perfilGuardado.getId()))
                .con(u -> u.institucion = institucion)
                .generar();

        ClientResponse responseUsuario = hacerPost("usuario", usuario);
        assertThat(responseUsuario.getStatus(), is(201));
        usuarioGuardado = responseUsuario.getEntity(Usuario.class);

        Token token = new Token(ID_TOKEN, usuarioGuardado);
        tokenDAO.guardar(token);
        sessionFactory.getCurrentSession().flush();
    }

    @Test
    public void debeObtenerUnTokenPorSuId() {
        MultivaluedMap<String, String> parametros = new MultivaluedMapImpl();
        parametros.add("token",ID_TOKEN);

        ClientResponse response = hacerGet("busqueda", parametros);

        assertThat(response.getStatus(), is(OK_200));
        Token token = response.getEntity(Token.class);
        assertThat(token.getId(), is(ID_TOKEN));
        assertThat(token.getUsuario().getNombreUsuario(), is("usuarioSenescyt"));
    }

    @Test
    public void debeRetornar400AlBuscarUnUsuarioQueNoExistePorIdentificacion(){
        MultivaluedMap<String,String> parametros = new MultivaluedMapImpl();
        parametros.add("identificacion", USUARIO_IDENTIFICACION);

        ClientResponse response = hacerGet("busqueda",parametros);

        assertThat(response.getStatus(), is(BAD_REQUEST_400));
        assertThat(response.getEntity(String.class), notNullValue());
    }

    @Test
    public void debeRetornarElUsuarioCorrepondienteALaIdentificacion() {
        MultivaluedMap<String, String> parametros = new MultivaluedMapImpl();
        parametros.add("identificacion", usuarioGuardado.getIdentificacion().getNumeroIdentificacion());

        ClientResponse response = hacerGet("busqueda", parametros);

        assertThat(response.getStatus(), is(OK_200));
        Usuario usuario = response.getEntity(Usuario.class);
        assertThat(usuario.getNombre(), is(usuarioGuardado.getNombre()));
    }
}

package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.builders.PerfilBuilder;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ContraseniaResourceIntegracionTest extends BaseIntegracionTest {

    private static final String ID_TOKEN = "32d88be3-2233-4b58-bf3c-99c35b162805";

    private TokenDAO tokenDAO;

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Before
    public void setUp() {
        tokenDAO = new TokenDAO(sessionFactory);
        cargarDataParaPruebas();
    }

    private void cargarDataParaPruebas() {
        Perfil perfil = PerfilBuilder.nuevoPerfil().generar();

        ClientResponse responsePerfil = hacerPost("perfiles", perfil);

        assertThat(responsePerfil.getStatus(), is(201));
        Perfil perfilGuardado = responsePerfil.getEntity(Perfil.class);

        Usuario usuario = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.perfiles = newArrayList(perfilGuardado.getId()))
                .generar();

        ClientResponse responseUsuario = hacerPost("usuario", usuario);
        assertThat(responseUsuario.getStatus(), is(201));
        Usuario usuarioGuardado = responseUsuario.getEntity(Usuario.class);

        Token token = new Token(ID_TOKEN, usuarioGuardado);
        tokenDAO.guardar(token);
        sessionFactory.getCurrentSession().flush();
    }

    @Test
    public void debeObtenerUnTokenPorSuId() throws Exception {
        ClientResponse response = client.resource(
                String.format("http://localhost:%d/contrasenia/" + ID_TOKEN, RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));
        Token token = response.getEntity(Token.class);
        assertThat(token.getId(), is(ID_TOKEN));
        assertThat(token.getUsuario().getNombreUsuario(), is("usuarioSenescyt"));
    }
}

package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.builders.PerfilBuilder;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ContraseniaResourceIntegracionTest {

    private static final String CONFIGURACION = "test-integracion.yml";
    private static final String ID_TOKEN = "32d88be3-2233-4b58-bf3c-99c35b162805";

    private SessionFactory sessionFactory;
    private TokenDAO tokenDAO;
    private UsuarioDAO usuarioDAO;
    private PerfilDAO perfilDAO;
    private long perfilGuardadoId;
    private long idUsuarioGuardado;

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    private static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() {
        sessionFactory = ((UsuarioApplication) RULE.getApplication()).getSessionFactory();
        ManagedSessionContext.bind(sessionFactory.openSession());
        cargarDataParaPruebas();
    }

    private void cargarDataParaPruebas() {
        tokenDAO = new TokenDAO(sessionFactory);
        usuarioDAO = new UsuarioDAO(sessionFactory);
        perfilDAO = new PerfilDAO(sessionFactory);

        Perfil perfil = PerfilBuilder.nuevoPerfil()
                .con(p -> p.nombre = "Perfil")
                .con(p -> p.permisos = null)
                .generar();
        Perfil perfilGuardado = perfilDAO.guardar(perfil);
        perfilGuardadoId = perfilGuardado.getId();
        Usuario usuario = UsuarioBuilder.usuarioValido(perfilGuardado);

        Usuario usuarioGuardado = usuarioDAO.guardar(usuario);
        idUsuarioGuardado = usuarioGuardado.getId();

        Token token = new Token(ID_TOKEN, usuario);
        tokenDAO.guardar(token);
        sessionFactory.getCurrentSession().flush();
    }

    @After
    public void tearDown() {
        eliminarDataParaPruebas();
        ManagedSessionContext.unbind(sessionFactory);
    }

    private void eliminarDataParaPruebas() {
        sessionFactory.getCurrentSession().flush();
        perfilDAO.eliminar(perfilGuardadoId);
        tokenDAO.eliminar(ID_TOKEN);
        usuarioDAO.eliminar(idUsuarioGuardado);
        sessionFactory.getCurrentSession().flush();
    }

    @Test
    public void debeObtenerUnTokenPorSuId() throws Exception {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/contrasenia/" + ID_TOKEN, RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));
        Token token = response.getEntity(Token.class);
        assertThat(token.getId(), is(ID_TOKEN));
        assertThat(token.getUsuario().getNombreUsuario(), is("usuarioSenescyt"));
    }
}

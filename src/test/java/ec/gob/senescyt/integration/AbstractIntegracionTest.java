package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.Constantes;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dao.FuncionDAO;
import ec.gob.senescyt.usuario.dao.ModuloDAO;
import ec.gob.senescyt.usuario.dao.NivelDeAccesoDAO;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.dao.UsuarioPerfilDAO;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.net.URISyntaxException;

public class AbstractIntegracionTest {

    protected static final String CONFIGURACION = "test-integracion.yml";
    protected SessionFactory sessionFactory;

    protected UsuarioDAO usuarioDAO;
    protected PerfilDAO perfilDAO;
    protected TokenDAO tokenDAO;
    protected NivelDeAccesoDAO nivelDeAccesoDAO;
    protected FuncionDAO funcionDAO;
    protected ModuloDAO moduloDAO;
    protected UsuarioPerfilDAO usuarioPerfilDAO;
    private CredencialDAO credencialDAO;
    protected Session session;
    private boolean seInicializaDB = false;
    protected final Client CLIENT = new Client();

    @Rule
    public final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    protected static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Before
    public void setUpDB() {
        if (!seInicializaDB){ inicializaDB(); }
        ManagedSessionContext.bind(session);
        limpiarTablas();
    }

    private void inicializaDB() {
        sessionFactory = ((UsuarioApplication) RULE.getApplication()).getSessionFactory();
        usuarioDAO = new UsuarioDAO(sessionFactory);
        credencialDAO = new CredencialDAO(sessionFactory);
        perfilDAO = new PerfilDAO(sessionFactory);
        tokenDAO = new TokenDAO(sessionFactory);
        nivelDeAccesoDAO = new NivelDeAccesoDAO(sessionFactory);
        funcionDAO = new FuncionDAO(sessionFactory);
        moduloDAO = new ModuloDAO(sessionFactory);
        usuarioPerfilDAO = new UsuarioPerfilDAO(sessionFactory);
        session = sessionFactory.openSession();
        seInicializaDB = true;
    }

    @After
    public void tearDownDB() {
        ManagedSessionContext.unbind(sessionFactory);
    }


    private void limpiarTablas() {
        session.flush();
        credencialDAO.limpiar();
        tokenDAO.limpiar();
        usuarioDAO.limpiar();
        perfilDAO.limpiar();
        nivelDeAccesoDAO.limpiar();
        funcionDAO.limpiar();
        moduloDAO.limpiar();
        usuarioPerfilDAO.limpiar();
        session.disconnect();
    }

    protected ClientResponse hacerPost(final String recurso, Object objectoAEnviar) {
        return CLIENT.resource(String.format("https://localhost:%d/" + recurso, Constantes.HTTPS_PORT))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, objectoAEnviar);
    }

    protected ClientResponse hacerGet(String recurso, MultivaluedMap<String, String> parametros) {
        return CLIENT.resource(
                String.format("https://localhost:%d/" + recurso, Constantes.HTTPS_PORT))
                .queryParams(parametros)
                .get(ClientResponse.class);
    }

    protected ClientResponse hacerGet(String recurso) {
        return CLIENT.resource(
                String.format("https://localhost:%d/" + recurso, Constantes.HTTPS_PORT))
                .get(ClientResponse.class);
    }
}
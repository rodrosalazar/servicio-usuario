package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.Constantes;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.net.URISyntaxException;

public abstract class AbstractIntegracionTest {

    protected static final String CONFIGURACION = "test-integracion.yml";
    protected SessionFactory sessionFactory;
    protected Client client;
    protected UsuarioDAO usuarioDAO;
    protected PerfilDAO perfilDAO;
    protected TokenDAO tokenDAO;
    private CredencialDAO credencialDAO;

    protected static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Before
    public void setUpDB() {
        sessionFactory = ((UsuarioApplication) getRule().getApplication()).getSessionFactory();
        usuarioDAO = new UsuarioDAO(sessionFactory);
        credencialDAO = new CredencialDAO(sessionFactory);
        perfilDAO = new PerfilDAO(sessionFactory);
        tokenDAO = new TokenDAO(sessionFactory);

        ManagedSessionContext.bind(sessionFactory.openSession());
        limpiarTablas();

        client = new Client();
    }

    @After
    public void tearDownDB() {
        limpiarTablas();
        ManagedSessionContext.unbind(sessionFactory);
    }

    private void limpiarTablas() {
        sessionFactory.getCurrentSession().flush();
        credencialDAO.limpiar();
        tokenDAO.limpiar();
        usuarioDAO.limpiar();
        perfilDAO.limpiar();
        sessionFactory.getCurrentSession().flush();
    }

    protected abstract DropwizardAppRule<UsuarioConfiguration> getRule();

    protected ClientResponse hacerPost(final String recurso, Object objectoAEnviar) {
        return client.resource(String.format("https://localhost:%d/" + recurso, Constantes.HTTPS_PORT))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, objectoAEnviar);
    }

    protected ClientResponse hacerGet(String recurso, MultivaluedMap<String, String> parametros) {
        return client.resource(
                String.format("https://localhost:%d/" + recurso, Constantes.HTTPS_PORT))
                .queryParams(parametros)
                .get(ClientResponse.class);
    }

    protected ClientResponse hacerGet(String recurso) {
        return client.resource(
                String.format("https://localhost:%d/" + recurso, Constantes.HTTPS_PORT))
                .get(ClientResponse.class);
    }
}
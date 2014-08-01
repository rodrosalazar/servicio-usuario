package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
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

public abstract class BaseIntegracionTest {

    protected static final String CONFIGURACION = "test-integracion.yml";
    protected SessionFactory sessionFactory;
    protected Client client;
    private UsuarioDAO usuarioDAO;
    private PerfilDAO perfilDAO;
    private TokenDAO tokenDAO;

    protected static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUpDB() {
        sessionFactory = ((UsuarioApplication) getRule().getApplication()).getSessionFactory();
        usuarioDAO = new UsuarioDAO(sessionFactory);
        perfilDAO = new PerfilDAO(sessionFactory);
        tokenDAO = new TokenDAO(sessionFactory);
        client = new Client();
        ManagedSessionContext.bind(sessionFactory.openSession());
        limpiarTablas();
    }

    @After
    public void tearDownDB() {
        limpiarTablas();
        ManagedSessionContext.unbind(sessionFactory);
    }

    private void limpiarTablas() {
        sessionFactory.getCurrentSession().flush();
        tokenDAO.limpiar();
        usuarioDAO.limpiar();
        perfilDAO.limpiar();
        sessionFactory.getCurrentSession().flush();
    }

    protected abstract DropwizardAppRule<UsuarioConfiguration> getRule();

    protected SessionFactory getSessionFactory(DropwizardAppRule<UsuarioConfiguration> rule) {
        return ((UsuarioApplication) rule.getApplication()).getSessionFactory();
    }

    protected ClientResponse hacerPost(final String recurso, Object objectoAEnviar) {
        return client.resource(String.format("http://localhost:%d/" + recurso, UsuarioResourceIntegracionTest.RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, objectoAEnviar);
    }

    protected ClientResponse hacerGet(MultivaluedMap<String, String> parametros) {
        return client.resource(
                String.format("http://localhost:%d/usuario/validacion", UsuarioResourceIntegracionTest.RULE.getLocalPort()))
                .queryParams(parametros)
                .get(ClientResponse.class);
    }
}
package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.ayudantes.AyudantePerfil;
import ec.gob.senescyt.commons.Constantes;
import ec.gob.senescyt.usuario.core.Entidad;
import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.PermisoDAO;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.commons.lang.RandomStringUtils;
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
    protected InstitucionDAO institucionDAO;
    protected CredencialDAO credencialDAO;
    protected PermisoDAO permisoDAO;
    protected Session session;
    protected AyudantePerfil ayudantePerfil = new AyudantePerfil();
    private boolean seInicializaDB = false;
    protected final Client CLIENT = new Client();

    @Rule
    public final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));
    protected Institucion institucion;

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
        String nombre = RandomStringUtils.random(10).toString();
        institucion = new Institucion(1L, nombre, 1L, nombre, 1L, nombre, 1L, nombre);
        institucionDAO.guardar(institucion);
        session.flush();
    }

    private void inicializaDB() {
        sessionFactory = ((UsuarioApplication) RULE.getApplication()).getSessionFactory();
        usuarioDAO = new UsuarioDAO(sessionFactory, RULE.getConfiguration().getDefaultSchema());
        credencialDAO = new CredencialDAO(sessionFactory, RULE.getConfiguration().getDefaultSchema());
        perfilDAO = new PerfilDAO(sessionFactory, RULE.getConfiguration().getDefaultSchema());
        tokenDAO = new TokenDAO(sessionFactory, RULE.getConfiguration().getDefaultSchema());
        permisoDAO = new PermisoDAO(sessionFactory, RULE.getConfiguration().getDefaultSchema());
        institucionDAO = new InstitucionDAO(sessionFactory);
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
        
        if (institucion != null){
            institucionDAO.eliminar(institucion);
        }
        
        permisoDAO.limpiar();
        session.disconnect();
    }

    protected ClientResponse hacerPost(final String recurso, Object objectoAEnviar) {
        return CLIENT.resource(getURL(recurso))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, objectoAEnviar);
    }


    protected ClientResponse hacerPut(final String recurso, Entidad entidad) {
        return CLIENT.resource(getURL(recurso))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, entidad);
    }

    protected ClientResponse hacerDelete(final String recurso) {
        return CLIENT.resource(getURL(recurso))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);
    }

    private String getURL(String recurso) {
        return String.format("https://localhost:%d/" + recurso, Constantes.HTTPS_PORT);
    }
    
    protected ClientResponse hacerGet(String recurso, MultivaluedMap<String, String> parametros) {
        return CLIENT.resource(
                getURL(recurso))
                .queryParams(parametros)
                .get(ClientResponse.class);
    }

    protected ClientResponse hacerGet(String recurso) {
        return CLIENT.resource(
                getURL(recurso))
                .get(ClientResponse.class);
    }
}
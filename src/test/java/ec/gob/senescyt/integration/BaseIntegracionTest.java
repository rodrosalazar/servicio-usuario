package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;

import java.io.File;

public abstract class BaseIntegracionTest {

    private static final String CONFIGURACION = "test-integracion.yml";
    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));
    protected SessionFactory sessionFactory;

    private static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUpDB() {
        sessionFactory = ((UsuarioApplication) RULE.getApplication()).getSessionFactory();
        ManagedSessionContext.bind(sessionFactory.openSession());
//        limpiarTablas();
    }

    @After
    public void tearDownDB() {
//        limpiarTablas();
        ManagedSessionContext.unbind(sessionFactory);
    }

    private void limpiarTablas() {
        String stringQuery = "DELETE FROM " + getTableName();
        Query query = sessionFactory.getCurrentSession().createQuery(stringQuery);
        query.executeUpdate();
    }

    protected abstract String getTableName();
}

package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EtniaResourceIntegracionTest {

    private static final String CONFIGURACION = "test-integracion.yml";

    private SessionFactory sessionFactory;

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
    }

    @After
    public void tearDown() {
        ManagedSessionContext.unbind(sessionFactory);
    }

    @Test
    public void debeObtenerTodasLasEtnias() throws Exception {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/etnias", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));
        assertThat(((List)(response.getEntity(HashMap.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_ETNIAS.getNombre()))).size(), is(not(0)));
    }

}
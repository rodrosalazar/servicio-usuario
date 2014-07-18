package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.*;

import javax.ws.rs.core.MediaType;
import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TituloExtranjeroResourceIntegrationTest {

    private static final String CONFIGURACION = "test-integracion.yml";

    private SessionFactory sessionFactory;

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    public static String resourceFilePath(String resourceClassPathLocation) {
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
    public void debeAlmacenarUnNuevoExpedienteDeTituloExtranjero() {
        Client client = new Client();
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo().generar();

        ClientResponse respuesta = client.resource(
                String.format("http://localhost:%d/titulo/extranjero", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(respuesta.getStatus(), is(201));
        assertThat(respuesta.getEntity(PortadorTitulo.class).getEmail(), is(portadorTitulo.getEmail()));
    }
}

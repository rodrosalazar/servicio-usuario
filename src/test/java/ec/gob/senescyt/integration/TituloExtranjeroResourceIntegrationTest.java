package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.Cedula;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.File;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TituloExtranjeroResourceIntegrationTest {

    private static final String CONFIGURACION = "test-integracion.yml";

    private SessionFactory sessionFactory;

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    private String numeroIdentificacionCedulaValida = "1111111116";

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
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroIdentificacionCedulaValida))
                .generar();

        ClientResponse respuesta = client.resource(
                String.format("http://localhost:%d/titulo/extranjero", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(respuesta.getStatus(), is(201));
        assertThat(respuesta.getEntity(PortadorTitulo.class).getEmail(), is(portadorTitulo.getEmail()));
    }

    @Test
    public void debeHacerAlgoCuandoElCodigoDelPaisNoExiste() {
        Client client = new Client();
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroIdentificacionCedulaValida))
                .con(p -> p.idPaisNacionalidad = "invali")
                .generar();

        ClientResponse respuesta = client.resource(
                String.format("http://localhost:%d/titulo/extranjero", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(respuesta.getStatus(), is(400));
        assertErrorMessage(respuesta, "idpaisnacionalidad no es un valor válido");
    }

    @Test
    public void debeHacerAlgoCuandoElCodigoDeEtniaNoExiste() {
        Client client = new Client();
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroIdentificacionCedulaValida))
                .con(p -> p.idEtnia = "xx")
                .generar();

        ClientResponse respuesta = client.resource(
                String.format("http://localhost:%d/titulo/extranjero", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(respuesta.getStatus(), is(400));
        assertErrorMessage(respuesta, "etnia_id no es un valor válido");
    }
}

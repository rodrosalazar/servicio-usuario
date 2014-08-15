package ec.gob.senescyt.contracts;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.Constantes;
import ec.gob.senescyt.usuario.core.CedulaInfo;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hamcrest.CoreMatchers;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.URISyntaxException;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BusquedaCedulaTest {

    private static final String CONFIGURACION = "test-integracion.yml";
    public static final String CEDULA_VALIDA = "1111111116";
    private static final String CEDULA_INVALIDA = "1234567890";
    private SessionFactory sessionFactory;

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));
    private Client client;

    private static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Before
    public void setUp() {
        sessionFactory = ((UsuarioApplication) RULE.getApplication()).getSessionFactory();
        ManagedSessionContext.bind(sessionFactory.openSession());
        client = new Client();
    }

    @After
    public void tearDown() {
        ManagedSessionContext.unbind(sessionFactory);
    }

    @Test
    public void debeDevolverLosDatosDeUnaCedulaValida() {
        ClientResponse respuesta = client.resource(String.format("https://localhost:%d/busqueda", Constantes.HTTPS_PORT))
                .queryParam("cedula", CEDULA_VALIDA)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        assertThat(respuesta.getStatus(), is(200));
        CedulaInfo cedulaInfo = respuesta.getEntity(CedulaInfo.class);
        assertThatCedulaIsValida(cedulaInfo);
    }


    @Test
    public void debeDevolverMensajeErrorCuandoCedulaEsInvalida() {
        ClientResponse respuesta = client.resource(String.format("https://localhost:%d/busqueda", Constantes.HTTPS_PORT))
                .queryParam("cedula", CEDULA_INVALIDA)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        assertThat(respuesta.getStatus(), is(400));
        assertErrorMessage(respuesta, "CEDULA NO ENCONTRADA");
    }

    private void assertThatCedulaIsValida(CedulaInfo cedulaInfo) {
        assertThat(cedulaInfo.getNombre(), CoreMatchers.is("ASDFASDFS ASDASDF"));
        assertThat(cedulaInfo.getDireccionCompleta(), CoreMatchers.is("WEQWEQW E,  "));
        assertThat(cedulaInfo.getProvincia(), CoreMatchers.is("BOLIVAR"));
        assertThat(cedulaInfo.getIdProvincia(), CoreMatchers.is("02"));
        assertThat(cedulaInfo.getCanton(), CoreMatchers.is("CHILLANES"));
        assertThat(cedulaInfo.getParroquia(), CoreMatchers.is("SAN JOSE DE TAMBO"));
        assertThat(cedulaInfo.getFechaNacimiento(), CoreMatchers.is("25/12/2000"));
        assertThat(cedulaInfo.getGenero(), CoreMatchers.is("MASCULINO"));
        assertThat(cedulaInfo.getNacionalidad(), CoreMatchers.is("SUAZI"));
    }

}

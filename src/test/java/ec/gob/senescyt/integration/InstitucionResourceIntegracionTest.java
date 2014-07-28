package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.titulos.core.UniversidadExtranjera;
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

import static org.apache.commons.lang.Validate.notEmpty;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InstitucionResourceIntegracionTest {

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
    public void debeObtenerTodasLasInstituciones() throws Exception {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/instituciones", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));
        assertThat(response.getEntity(List.class).size(), is(not(0)));
    }

    @Test
    public void debeObtenerUniversidadesDeConvenio() throws Exception {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/instituciones/convenio", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));

        List<UniversidadExtranjera> universidadesConvenioEncontradas =
                (List) response.getEntity(HashMap.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_UNIVERSIDADES_CONVENIO.getNombre());

        assertThat(universidadesConvenioEncontradas.size(), is(not(0)));
    }

    @Test
    public void debeObtenerUniversidadesDeListado() throws Exception {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/instituciones/listado", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));

        List<UniversidadExtranjera> universidadesListadoEncontradas =
                (List) response.getEntity(HashMap.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_UNIVERSIDADES_LISTADO.getNombre());

        assertThat(universidadesListadoEncontradas.size(), is(not(0)));
    }
}

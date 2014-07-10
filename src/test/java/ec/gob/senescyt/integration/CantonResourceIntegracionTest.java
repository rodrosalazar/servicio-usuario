package ec.gob.senescyt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.UsuarioApplication;
import ec.gob.senescyt.usuario.UsuarioConfiguration;
import ec.gob.senescyt.usuario.core.Canton;
import ec.gob.senescyt.usuario.core.Provincia;
import ec.gob.senescyt.usuario.dao.CantonDAO;
import ec.gob.senescyt.usuario.dao.ProvinciaDAO;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CantonResourceIntegracionTest {

    private static final String CONFIGURACION = "test-integracion.yml";

    private SessionFactory sessionFactory;

    private static final String ID_PROVINCIA_TEST = "80";
    private static final String NOMBRE_PROVINCIA_TEST = "PROVINCIA_TEST";
    private static final String NOMBRE_CANTON_TEST = "CANTON_TEST";
    private static final String ID_CANTON_TEST = "8001";
    private CantonDAO cantonDAO;
    private ProvinciaDAO provinciaDAO;

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

        cargarDataParaPruebas();
    }

    private void cargarDataParaPruebas(){
        provinciaDAO = new ProvinciaDAO(sessionFactory);
        cantonDAO = new CantonDAO(sessionFactory);


        Provincia provinciaTest = new Provincia(ID_PROVINCIA_TEST, NOMBRE_PROVINCIA_TEST);
        provinciaDAO.guardar(provinciaTest);


        Canton cantonTest = new Canton(provinciaTest, ID_CANTON_TEST, NOMBRE_CANTON_TEST);
        cantonDAO.guardar(cantonTest);

        sessionFactory.getCurrentSession().flush();
    }

    @After
    public void tearDown() {
        eliminarInformacionCargadaParaPrueba();
        ManagedSessionContext.unbind(sessionFactory);

    }

    private void eliminarInformacionCargadaParaPrueba(){
        cantonDAO.eliminar(ID_CANTON_TEST);
        provinciaDAO.eliminar(ID_PROVINCIA_TEST);
        sessionFactory.getCurrentSession().flush();
    }

    @Test
    public void debeObtenerCantonesParaProvinciaTest() throws Exception {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/cantones/" + ID_PROVINCIA_TEST, RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));

        String idProvinciaDeCantonEncontrado = obtenerIdProvinciaDesdeCanton(response);

        assertThat(idProvinciaDeCantonEncontrado, is(ID_PROVINCIA_TEST));
    }

    private String obtenerIdProvinciaDesdeCanton(final ClientResponse response) throws java.io.IOException {
        ObjectMapper MAPPER = Jackson.newObjectMapper();
        Canton cantonEncontrado = MAPPER.readValue(response.getEntity(String.class).replace("[", "").replace("]", ""), Canton.class);
        return cantonEncontrado.getId().substring(0, ID_PROVINCIA_TEST.length());
    }
}

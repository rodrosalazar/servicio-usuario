package ec.gob.senescyt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.titulos.core.Canton;
import ec.gob.senescyt.titulos.core.Provincia;
import ec.gob.senescyt.titulos.dao.CantonDAO;
import ec.gob.senescyt.titulos.dao.ProvinciaDAO;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProvinciaResourceIntegracionTest extends BaseIntegracionTest {

    private static final String ID_PROVINCIA_TEST = "80";
    private static final String NOMBRE_PROVINCIA_TEST = "PROVINCIA_TEST";
    private static final String NOMBRE_CANTON_TEST = "CANTON_TEST";
    private static final String ID_CANTON_TEST = "8001";
    private CantonDAO cantonDAO;
    private ProvinciaDAO provinciaDAO;

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Before
    public void setUp() {
        provinciaDAO = new ProvinciaDAO(sessionFactory);
        cantonDAO = new CantonDAO(sessionFactory);
        cargarDataParaPruebas();
    }

    private void cargarDataParaPruebas() {
        Provincia provinciaTest = new Provincia(ID_PROVINCIA_TEST, NOMBRE_PROVINCIA_TEST);
        provinciaDAO.guardar(provinciaTest);

        Canton cantonTest = new Canton(provinciaTest, ID_CANTON_TEST, NOMBRE_CANTON_TEST);
        cantonDAO.guardar(cantonTest);

        sessionFactory.getCurrentSession().flush();
    }

    @After
    public void tearDown() {
        eliminarInformacionCargadaParaPrueba();
    }


    private void eliminarInformacionCargadaParaPrueba() {
        cantonDAO.eliminar(ID_CANTON_TEST);
        sessionFactory.getCurrentSession().flush();
        provinciaDAO.eliminar(ID_PROVINCIA_TEST);
        sessionFactory.getCurrentSession().flush();
    }

    @Test
    public void debeObtenerTodosLosPaises() {
        ClientResponse response = hacerGet("provincias");

        assertThat(response.getStatus(), is(200));
        assertThat(response.getEntity(HashMap.class).size(), is(not(0)));
    }

    @Test
    public void debeObtenerCantonesParaProvinciaTest() throws IOException {
        ClientResponse response = hacerGet("provincias/" + ID_PROVINCIA_TEST + "/cantones");

        assertThat(response.getStatus(), is(200));

        String idProvinciaDeCantonEncontrado = obtenerIdProvinciaDesdeCanton(response);

        assertThat(idProvinciaDeCantonEncontrado, is(ID_PROVINCIA_TEST));
    }

    private String obtenerIdProvinciaDesdeCanton(final ClientResponse response) throws java.io.IOException {
        ObjectMapper MAPPER = Jackson.newObjectMapper();
        String cantones = response.getEntity(String.class);

        Canton cantonEncontrado = MAPPER.readValue(cantones.replace("{\"" + ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CANTONES.getNombre() + "\":[", "").replace("]", ""), Canton.class);
        return cantonEncontrado.getId().substring(0, ID_PROVINCIA_TEST.length());
    }
}

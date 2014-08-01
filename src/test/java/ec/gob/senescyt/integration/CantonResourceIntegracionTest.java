package ec.gob.senescyt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.titulos.core.Canton;
import ec.gob.senescyt.titulos.core.Parroquia;
import ec.gob.senescyt.titulos.core.Provincia;
import ec.gob.senescyt.titulos.dao.CantonDAO;
import ec.gob.senescyt.titulos.dao.ParroquiaDAO;
import ec.gob.senescyt.titulos.dao.ProvinciaDAO;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CantonResourceIntegracionTest extends BaseIntegracionTest {

    private static final String ID_PROVINCIA_TEST = "80";
    private static final String NOMBRE_PROVINCIA_TEST = "PROVINCIA_TEST";
    private static final String NOMBRE_CANTON_TEST = "CANTON_TEST";
    private static final String ID_CANTON_TEST = "8001";
    private static final String ID_PARROQUIA_TEST = "800150";
    private static final String NOMBRE_PARROQUIA_TEST = "PARROQUIA_TEST";
    private ProvinciaDAO provinciaDAO;
    private CantonDAO cantonDAO;
    private ParroquiaDAO parroquiaDAO;

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
        parroquiaDAO = new ParroquiaDAO(sessionFactory);
        cargarDataParaPruebas();
    }

    private void cargarDataParaPruebas() {
        Provincia provinciaTest = new Provincia(ID_PROVINCIA_TEST, NOMBRE_PROVINCIA_TEST);
        provinciaDAO.guardar(provinciaTest);

        Canton cantonTest = new Canton(provinciaTest, ID_CANTON_TEST, NOMBRE_CANTON_TEST);
        cantonDAO.guardar(cantonTest);

        Parroquia parroquiaTest = new Parroquia(cantonTest, ID_PARROQUIA_TEST, NOMBRE_PARROQUIA_TEST);
        parroquiaDAO.guardar(parroquiaTest);

        sessionFactory.getCurrentSession().flush();
    }

    @After
    public void tearDown() {
        eliminarInformacionCargadaParaPrueba();
    }

    private void eliminarInformacionCargadaParaPrueba() {
        parroquiaDAO.eliminar(ID_PARROQUIA_TEST);
        cantonDAO.eliminar(ID_CANTON_TEST);
        sessionFactory.getCurrentSession().flush();
    }

    @Test
    public void debeObtenerParroquiasParaCantonTest() throws Exception {
        ClientResponse response = hacerGet("cantones/" + ID_CANTON_TEST + "/parroquias");

        assertThat(response.getStatus(), is(200));

        String idCantonDeParroquiaEncontrada = obtenerIdCantonDesdeParroquia(response);

        assertThat(idCantonDeParroquiaEncontrada, is(ID_CANTON_TEST));
    }

    private String obtenerIdCantonDesdeParroquia(final ClientResponse response) throws java.io.IOException {
        ObjectMapper MAPPER = Jackson.newObjectMapper();
        String parroquias = response.getEntity(String.class);

        Parroquia parroquiaEncontrada = MAPPER.readValue(parroquias.replace("{\"" + ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PARROQUIAS.getNombre() + "\":[", "").replace("]", ""), Parroquia.class);
        return parroquiaEncontrada.getId().substring(0, ID_CANTON_TEST.length());
    }
}
package ec.gob.senescyt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.titulos.core.CategoriaVisa;
import ec.gob.senescyt.titulos.core.TipoVisa;
import ec.gob.senescyt.titulos.dao.CategoriaVisaDAO;
import ec.gob.senescyt.titulos.dao.TipoVisaDAO;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TiposDeVisaResourceIntegracionTest extends BaseIntegracionTest {

    private static final String ID_TIPO_VISA_TEST = "3";
    private static final String NOMBRE_TIPO_VISA_TEST = "TIPO_VISA_TEST";
    private static final String ID_CATEGORIA_VISA_TEST = "90";
    private static final String NOMBRE_CATEGORIA_VISA_TEST = "CATEGORIA_VISA_TEST";
    private TipoVisaDAO tipoVisaDAO;
    private CategoriaVisaDAO categoriaVisaDAO;

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Before
    public void setUp() {
        tipoVisaDAO = new TipoVisaDAO(sessionFactory);
        categoriaVisaDAO = new CategoriaVisaDAO(sessionFactory);
        cargarDataParaPruebas();
    }

    private void cargarDataParaPruebas() {
        TipoVisa tipoVisaTest = new TipoVisa(ID_TIPO_VISA_TEST, NOMBRE_TIPO_VISA_TEST);
        tipoVisaDAO.guardar(tipoVisaTest);

        CategoriaVisa categoriaVisaTest = new CategoriaVisa(tipoVisaTest, ID_CATEGORIA_VISA_TEST, NOMBRE_CATEGORIA_VISA_TEST);
        categoriaVisaDAO.guardar(categoriaVisaTest);

        sessionFactory.getCurrentSession().flush();
    }

    @After
    public void tearDown() {
        eliminarInformacionCargadaParaPrueba();
    }

    private void eliminarInformacionCargadaParaPrueba() {
        categoriaVisaDAO.eliminar(ID_CATEGORIA_VISA_TEST);
        sessionFactory.getCurrentSession().flush();
        tipoVisaDAO.eliminar(ID_TIPO_VISA_TEST);
        sessionFactory.getCurrentSession().flush();
    }

    @Test
    public void debeObtenerTodosLosTipoDeVisa() {
        ClientResponse response = hacerGet("tiposDeVisa");

        assertThat(response.getStatus(), is(200));
        List tiposDeVisa = (List) response.getEntity(HashMap.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_TIPO_VISA.getNombre());
        assertThat(tiposDeVisa.isEmpty(), is(not(true)));
    }

    @Test
    public void debeObtenerCategoriasDeVisaParaTipoDeVisa() throws IOException {
        ClientResponse response = hacerGet("tiposDeVisa/" + ID_TIPO_VISA_TEST + "/categorias");

        assertThat(response.getStatus(), is(200));

        String idTipoVisaDeCategoriaEncontrada = obtenerIdTipoVisaDesdeCategoria(response);
        assertThat(idTipoVisaDeCategoriaEncontrada, is(ID_CATEGORIA_VISA_TEST));
    }

    private String obtenerIdTipoVisaDesdeCategoria(final ClientResponse response) throws java.io.IOException {
        ObjectMapper MAPPER = Jackson.newObjectMapper();
        String categoriasVisa = response.getEntity(String.class);

        CategoriaVisa categoriaEncontrada = MAPPER.readValue(categoriasVisa.replace("{\"" + ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CATEGORIA_VISA.getNombre() + "\":[", "").replace("]", ""), CategoriaVisa.class);
        return categoriaEncontrada.getId();
    }
}

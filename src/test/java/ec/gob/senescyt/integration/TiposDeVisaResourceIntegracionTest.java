package ec.gob.senescyt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.titulos.core.CategoriaVisa;
import ec.gob.senescyt.titulos.core.TipoVisa;
import ec.gob.senescyt.titulos.dao.CategoriaVisaDAO;
import ec.gob.senescyt.titulos.dao.TipoVisaDAO;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TiposDeVisaResourceIntegracionTest {

    private static final String CONFIGURACION = "test-integracion.yml";

    private SessionFactory sessionFactory;

    private static final String ID_TIPO_VISA_TEST = "3";
    private static final String NOMBRE_TIPO_VISA_TEST = "TIPO_VISA_TEST";
    private static final String ID_CATEGORIA_VISA_TEST = "90";
    private static final String NOMBRE_CATEGORIA_VISA_TEST = "CATEGORIA_VISA_TEST";
    private TipoVisaDAO tipoVisaDAO;
    private CategoriaVisaDAO categoriaVisaDAO;

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

    private void cargarDataParaPruebas() {
        tipoVisaDAO = new TipoVisaDAO(sessionFactory);
        categoriaVisaDAO = new CategoriaVisaDAO(sessionFactory);

        TipoVisa tipoVisaTest = new TipoVisa(ID_TIPO_VISA_TEST, NOMBRE_TIPO_VISA_TEST);
        tipoVisaDAO.guardar(tipoVisaTest);

        CategoriaVisa categoriaVisaTest = new CategoriaVisa(tipoVisaTest, ID_CATEGORIA_VISA_TEST, NOMBRE_CATEGORIA_VISA_TEST);
        categoriaVisaDAO.guardar(categoriaVisaTest);

        sessionFactory.getCurrentSession().flush();
    }

    @After
    public void tearDown() {
        eliminarInformacionCargadaParaPrueba();
        ManagedSessionContext.unbind(sessionFactory);
    }


    private void eliminarInformacionCargadaParaPrueba() {
        categoriaVisaDAO.eliminar(ID_CATEGORIA_VISA_TEST);
        tipoVisaDAO.eliminar(ID_TIPO_VISA_TEST);
        sessionFactory.getCurrentSession().flush();
    }


    @Test
    public void debeObtenerCategoriasDeVisaParaTipoDeVisa() throws Exception {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/tiposDeVisa/" + ID_TIPO_VISA_TEST + "/categorias", RULE.getLocalPort()))
                .get(ClientResponse.class);

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

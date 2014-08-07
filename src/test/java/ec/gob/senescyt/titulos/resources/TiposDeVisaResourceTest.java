package ec.gob.senescyt.titulos.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.CategoriaVisa;
import ec.gob.senescyt.titulos.core.TipoVisa;
import ec.gob.senescyt.titulos.dao.CategoriaVisaDAO;
import ec.gob.senescyt.titulos.dao.TipoVisaDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class TiposDeVisaResourceTest {

    public static final String ID_CATEGORIA_VISA = "1";
    public static final String ID_TIPO_VISA = "1";
    public static final String NOMBRE_TIPO_VISA = "Visa 12 - NO INMIGRANTE";
    public static final String NOMBRE_CATEGORIA_VISA = "I-II     FUNCIONARIO DE MISIONES DIPLOMATICAS";

    private static TipoVisaDAO tipoVisaDAO = Mockito.mock(TipoVisaDAO.class);
    private static CategoriaVisaDAO categoriaVisaDAO = Mockito.mock(CategoriaVisaDAO.class);
    private static ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();
    private List<TipoVisa> tiposDeVisa = new ArrayList<>();


    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new TipoDeVisaResource(tipoVisaDAO, categoriaVisaDAO, constructorRespuestas))
            .addProvider(ValidacionExceptionMapper.class)
            .build();

    private Client client;


    @Before
    public void setUp() {
        tiposDeVisa.add(new TipoVisa(ID_TIPO_VISA, NOMBRE_TIPO_VISA));
        client = RESOURCES.client();
    }


    @Test
    public void debeObtenerTiposDeVisa() {
        Mockito.when(tipoVisaDAO.obtenerTodos()).thenReturn(tiposDeVisa);

        ClientResponse response = client.resource("/tiposDeVisa")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Mockito.verify(tipoVisaDAO).obtenerTodos();
        MatcherAssert.assertThat(response.getStatus(), is(200));
        Map tiposVisa = response.getEntity(Map.class);
        MatcherAssert.assertThat(tiposVisa.isEmpty(), is(not(true)));
    }

    @Test
    public void debeObtenerCategoriasVisaParaUnaVisa() {
        Mockito.when(tipoVisaDAO.obtenerPorId(ID_TIPO_VISA)).thenReturn(new TipoVisa(ID_TIPO_VISA, NOMBRE_TIPO_VISA));

        CategoriaVisa categoriaVisa = new CategoriaVisa(tipoVisaDAO.obtenerPorId(ID_TIPO_VISA), ID_CATEGORIA_VISA, NOMBRE_CATEGORIA_VISA);
        Mockito.when(categoriaVisaDAO.obtenerPorTipoVisa(ID_TIPO_VISA)).thenReturn(newArrayList(categoriaVisa));

        ClientResponse response = client.resource("/tiposDeVisa/" + ID_TIPO_VISA + "/categorias")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Mockito.verify(categoriaVisaDAO).obtenerPorTipoVisa(ID_TIPO_VISA);
        MatcherAssert.assertThat(response.getStatus(), is(200));
        Map categorias = response.getEntity(Map.class);
        MatcherAssert.assertThat(categorias.isEmpty(), is(not(true)));
    }
}

package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.CategoriaVisa;
import ec.gob.senescyt.usuario.core.TipoVisa;
import ec.gob.senescyt.usuario.dao.CategoriaVisaDAO;
import ec.gob.senescyt.usuario.dao.TipoVisaDAO;
import ec.gob.senescyt.usuario.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.usuario.resources.builders.ConstructorRespuestas;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TiposDeVisaResourceTest {

    public static final String ID_CATEGORIA_VISA = "1";
    public static final String ID_TIPO_VISA = "1";
    public static final String NOMBRE_TIPO_VISA = "Visa 12 - NO INMIGRANTE";
    public static final String NOMBRE_CATEGORIA_VISA = "I-II     FUNCIONARIO DE MISIONES DIPLOMATICAS";

    private TipoDeVisaResource tiposDeVisaResource;
    private TipoVisaDAO tipoVisaDAO = mock(TipoVisaDAO.class);
    private CategoriaVisaDAO categoriaVisaDAO = mock(CategoriaVisaDAO.class);
    private ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();

    @Before
    public void setUp() throws Exception {
        tiposDeVisaResource = new TipoDeVisaResource(tipoVisaDAO, categoriaVisaDAO, constructorRespuestas);
    }


    @Test
    public void debeObtenerParroquiasParaUnCanton() throws Exception {

        when(tipoVisaDAO.obtenerPorId(ID_TIPO_VISA)).thenReturn(new TipoVisa(ID_TIPO_VISA, NOMBRE_TIPO_VISA));

        CategoriaVisa categoriaVisa = new CategoriaVisa(tipoVisaDAO.obtenerPorId(ID_TIPO_VISA), ID_CATEGORIA_VISA, NOMBRE_CATEGORIA_VISA);
        when(categoriaVisaDAO.obtenerPorTipoVisa(ID_TIPO_VISA)).thenReturn(newArrayList(categoriaVisa));

        Response response = tiposDeVisaResource.obtenerCategoriasParaTipoVisa(ID_TIPO_VISA);

        verify(categoriaVisaDAO).obtenerPorTipoVisa(ID_TIPO_VISA);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((HashMap<String,List<CategoriaVisa>>) response.getEntity()).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CATEGORIA_VISA.getNombre()).get(0).getTipoVisa().getId()).isEqualTo(ID_TIPO_VISA);
    }
}

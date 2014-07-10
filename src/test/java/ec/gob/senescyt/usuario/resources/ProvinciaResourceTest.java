package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Canton;
import ec.gob.senescyt.usuario.core.Provincia;
import ec.gob.senescyt.usuario.dao.CantonDAO;
import ec.gob.senescyt.usuario.dao.ProvinciaDAO;
import ec.gob.senescyt.usuario.enums.ElementosRaicesJSONEnum;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProvinciaResourceTest {

    public static final String ID_PROVINCIA = "1";
    public static final String ID_CANTON = "101";
    public static final String NOMBRE_CANTON = "Cuenca";
    public static final String NOMBRE_PROVINCIA = "Provincia";

    private ProvinciaResource provinciaResource;
    private ProvinciaDAO provinciaDAO = mock(ProvinciaDAO.class);
    private CantonDAO cantonDAO = mock(CantonDAO.class);

    @Before
    public void setUp() throws Exception {
        provinciaResource = new ProvinciaResource(provinciaDAO, cantonDAO);
    }

    @Test
    public void debeObtenerTodasLasProvincias() throws Exception {
        Provincia provincia = new Provincia(ID_PROVINCIA, NOMBRE_PROVINCIA);
        when(provinciaDAO.obtenerTodos()).thenReturn(newArrayList(provincia));

        Response response = provinciaResource.obtenerTodos();

        verify(provinciaDAO).obtenerTodos();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((HashMap<String,List<Provincia>>) response.getEntity()).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PROVINCIAS.getNombre())).isNotEmpty();
    }

    @Test
    public void debeObtenerCantonesParaUnaProvincia() throws Exception {
        when(provinciaDAO.obtenerPorId(ID_PROVINCIA)).thenReturn(new Provincia(ID_PROVINCIA,NOMBRE_PROVINCIA));

        Canton canton = new Canton(provinciaDAO.obtenerPorId(ID_PROVINCIA),ID_CANTON, NOMBRE_CANTON);
        when(cantonDAO.obtenerPorProvincia(ID_PROVINCIA)).thenReturn(newArrayList(canton));

        Response response = provinciaResource.obtenerCantonesPorProvincia(ID_PROVINCIA);

        verify(cantonDAO).obtenerPorProvincia(ID_PROVINCIA);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((HashMap<String,List<Canton>>) response.getEntity()).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CANTONES.getNombre()).get(0).getProvincia().getId()).isEqualTo(ID_PROVINCIA);
    }
}

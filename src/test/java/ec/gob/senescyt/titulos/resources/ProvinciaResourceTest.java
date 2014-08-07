package ec.gob.senescyt.titulos.resources;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.Canton;
import ec.gob.senescyt.titulos.core.Provincia;
import ec.gob.senescyt.titulos.dao.CantonDAO;
import ec.gob.senescyt.titulos.dao.ProvinciaDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProvinciaResourceTest {

    public static final String ID_PROVINCIA = "1";
    public static final String ID_CANTON = "101";
    public static final String NOMBRE_CANTON = "Cuenca";
    public static final String NOMBRE_PROVINCIA = "Provincia";

    private ProvinciaResource provinciaResource;
    private ProvinciaDAO provinciaDAO = Mockito.mock(ProvinciaDAO.class);
    private CantonDAO cantonDAO = Mockito.mock(CantonDAO.class);
    private ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();

    @Before
    public void setUp() {
        provinciaResource = new ProvinciaResource(provinciaDAO, cantonDAO, constructorRespuestas);
    }

    @Test
    public void debeObtenerTodasLasProvincias() {
        Provincia provincia = new Provincia(ID_PROVINCIA, NOMBRE_PROVINCIA);
        when(provinciaDAO.obtenerTodos()).thenReturn(newArrayList(provincia));

        Response response = provinciaResource.obtenerTodos();

        verify(provinciaDAO).obtenerTodos();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((HashMap<String,List<Provincia>>) response.getEntity()).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PROVINCIAS.getNombre())).isNotEmpty();
    }

    @Test
    public void debeObtenerCantonesParaUnaProvincia() {
        when(provinciaDAO.obtenerPorId(ID_PROVINCIA)).thenReturn(new Provincia(ID_PROVINCIA,NOMBRE_PROVINCIA));

        Canton canton = new Canton(provinciaDAO.obtenerPorId(ID_PROVINCIA),ID_CANTON, NOMBRE_CANTON);
        when(cantonDAO.obtenerPorProvincia(ID_PROVINCIA)).thenReturn(newArrayList(canton));

        Response response = provinciaResource.obtenerCantonesPorProvincia(ID_PROVINCIA);

        verify(cantonDAO).obtenerPorProvincia(ID_PROVINCIA);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((HashMap<String,List<Canton>>) response.getEntity()).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CANTONES.getNombre()).get(0).getProvincia().getId()).isEqualTo(ID_PROVINCIA);
    }
}

package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Canton;
import ec.gob.senescyt.usuario.core.Parroquia;
import ec.gob.senescyt.usuario.core.Provincia;
import ec.gob.senescyt.usuario.dao.CantonDAO;
import ec.gob.senescyt.usuario.dao.ParroquiaDAO;
import ec.gob.senescyt.usuario.dao.ProvinciaDAO;
import ec.gob.senescyt.usuario.enums.ElementosRaicesJSONEnum;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CantonesResourceTest {

    public static final String ID_PARROQUIA = "1";
    public static final String ID_CANTON = "101";
    public static final String NOMBRE_CANTON = "Cuenca";
    public static final String NOMBRE_PARROQUIA = "Provincia";

    private CantonResource cantonResource;
    private CantonDAO cantonDAO = mock(CantonDAO.class);
    private ParroquiaDAO parroquiaDAO = mock(ParroquiaDAO.class);

    @Before
    public void setUp() throws Exception {
        cantonResource = new CantonResource(cantonDAO, parroquiaDAO);
    }


    @Test
    public void debeObtenerParroquiasParaUnCanton() throws Exception {

        when(cantonDAO.obtenerPorId(ID_CANTON)).thenReturn(new Canton(null, ID_CANTON,NOMBRE_CANTON));

        Parroquia parroquia = new Parroquia(cantonDAO.obtenerPorId(ID_CANTON),ID_PARROQUIA, NOMBRE_PARROQUIA);
        when(parroquiaDAO.obtenerPorCanton(ID_CANTON)).thenReturn(newArrayList(parroquia));

        Response response = cantonResource.obtenerParroquiasParaCanton(ID_CANTON);

        verify(parroquiaDAO).obtenerPorCanton(ID_CANTON);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((HashMap<String,List<Parroquia>>) response.getEntity()).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PARROQUIAS.getNombre()).get(0).getCanton().getId()).isEqualTo(ID_CANTON);
    }
}

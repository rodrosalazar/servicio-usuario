package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Canton;
import ec.gob.senescyt.usuario.core.Provincia;
import ec.gob.senescyt.usuario.dao.CantonDAO;
import ec.gob.senescyt.usuario.dao.ProvinciaDAO;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CantonResourceTest {

    public static final String ID_PROVINCIA = "1";
    public static final String ID_CANTON = "101";
    public static final String NOMBRE_CANTON = "Cuenca";
    public static final String NOMBRE_PROVINCIA = "Provincia";

    private CantonResource cantonResource;
    private CantonDAO cantonDAO = mock(CantonDAO.class);
    private ProvinciaDAO provinciaDAO = mock(ProvinciaDAO.class);

    @Before
    public void setUp() throws Exception {
        cantonResource = new CantonResource(cantonDAO);
    }

    @Test
    public void debeObtenerCantonesParaUnaProvincia() throws Exception {
        when(provinciaDAO.obtenerPorId(ID_PROVINCIA)).thenReturn(new Provincia(ID_PROVINCIA,NOMBRE_PROVINCIA));

        Canton canton = new Canton(provinciaDAO.obtenerPorId(ID_PROVINCIA),ID_CANTON, NOMBRE_CANTON);
        when(cantonDAO.obtenerPorProvincia(ID_PROVINCIA)).thenReturn(newArrayList(canton));

        Response response = cantonResource.obtenerCantonesPorProvincia(ID_PROVINCIA);

        verify(cantonDAO).obtenerPorProvincia(ID_PROVINCIA);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((List<Canton>) response.getEntity()).get(0).getProvincia().getId()).isEqualTo(ID_PROVINCIA);
    }
}

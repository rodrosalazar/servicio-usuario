package ec.gob.senescyt.biblioteca.resource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.NivelArbol;
import ec.gob.senescyt.biblioteca.dao.ArbolDAO;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.resources.UsuarioResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class ArbolResourceTest {

    public static final String ID_PARROQUIA = "1";
    public static final String ID_CANTON = "101";
    public static final String NOMBRE_CANTON = "Cuenca";
    public static final String NOMBRE_PARROQUIA = "Provincia";

    private ArbolResource arbolResource;
    private static ArbolDAO arbolDAO = mock(ArbolDAO.class);

    private static final Integer ID_ARBOL = 1;
    private static final String NOMBRE_ARBOL = "arbol_test";
    private List<NivelArbol> nivelesArbol;

    Integer idNivelGenerales = 1;
    String nombreNivelGenerales = "Generales";
    Integer idNivelPadreDeGenerales = null;

    Integer idSubnivelLeyes = 2;
    String nombreSubnivelLeyes = "Leyes";
    Integer idNivelPadreDeLeyes = 1;

    Integer idSubnivelReglamentos = 3;
    String nombreSubnivelReglamentos = "Reglamentos";
    Integer idNivelPadreDeReglamentos = 1;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ArbolResource(arbolDAO))
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;

    @Before
    public void setUp() throws Exception {
        client = resources.client();
        reset(arbolDAO);

        nivelesArbol = new ArrayList<>();
        nivelesArbol.add(new NivelArbol(idNivelGenerales, nombreNivelGenerales, idNivelPadreDeGenerales));
        nivelesArbol.add(new NivelArbol(idSubnivelLeyes, nombreSubnivelLeyes, idNivelPadreDeLeyes));
        nivelesArbol.add(new NivelArbol(idSubnivelReglamentos, nombreSubnivelReglamentos, idNivelPadreDeReglamentos));
    }

    @Test
    public void debeObtenerArbolDeNormativas() throws Exception {

        when(arbolDAO.obtenerPorId(ID_ARBOL)).thenReturn(new Arbol(ID_ARBOL,NOMBRE_ARBOL,nivelesArbol));

        ClientResponse response = client.resource("/arboles/" + ID_ARBOL)
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));
//        assertThat(response.getEntity(Arbol.class).getId(), is(ID_ARBOL));
//        assertThat(response.getEntity(Arbol.class).getNivelesArbol().size(), is(nivelesArbol.size()));
    }
}

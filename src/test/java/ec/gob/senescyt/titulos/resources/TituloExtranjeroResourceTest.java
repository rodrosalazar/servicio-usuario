package ec.gob.senescyt.titulos.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.dao.PortadorTituloDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class TituloExtranjeroResourceTest {

    private static PortadorTituloDAO portadorTituloDAO = mock(PortadorTituloDAO.class);;
    private static final String CAMPO_EN_BLANCO = "";

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TituloExtranjeroResource(portadorTituloDAO))
            .addProvider(ValidacionExceptionMapper.class)
            .build();

    private Client client;

    @Before
    public void setUp() throws Exception {
        client = resources.client();
        reset(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloSinMail() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo().conEmail(CAMPO_EN_BLANCO).generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConFechaDeNacimientoAnteriorALaFechaActual() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo().conFechaNacimiento(DateUtils.addDays(DateTime.now().toDate(), 1)).generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    @Ignore
    public void debeCrearPortadorTituloConCedula() throws Exception {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo().generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        verify(portadorTituloDAO).guardar(any(PortadorTitulo.class));
        assertThat(response.getStatus(), is(201));
    }
}


package ec.gob.senescyt.titulos.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.dao.PortadorTituloDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.CoreMatchers.containsString;
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
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conEmail(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConMailDeFormatoInvalido() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conEmail("formatoInvalido")
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "email Formato inválido");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConFechaDeNacimientoAnteriorALaFechaActual() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conFechaNacimiento(DateTime.now().minusDays(-1))
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "No puede ser mayor a la fecha actual");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConTelefonoConvencionalConMasOMenosDe9Digitos() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conTelefonoConvencional("12345678")
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener 9 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConTelefonoConvencionalConOtrosCaracteresQueNoSeanNumeros() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conTelefonoConvencional("1234$6789")
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener 9 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConTelefonoCelularConMasOMenosDe10Digitos() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conTelefonoCelular("12345678")
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener 10 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConTelefonoCelularConOtrosCaracteresQueNoSeanNumeros() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conTelefonoCelular("1234E6S89A")
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener 10 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConExtensionConMasDe5igitos() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conExtension("12345678")
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "No puede contener más de 5 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConExtensionConOtrosCaracteresQueNoSeanNumeros() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conExtension("123SE")
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "No puede contener más de 5 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloSinSexo() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conSexo(null)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoElNombreEstaVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conNombreCompleto(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConnombreVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conNombreCompleto(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearPortadorTituloSinAceptarTerminos() throws Exception {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conAceptaCondiciones(false).generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe aceptar las condiciones");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdEtniaVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conIdEtnia(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConDireccionVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conDireccion(null)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConCallePrincipalVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conCallePrincipal(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConCalleSecundariaVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conCalleSecundaria(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConNumeroDeCasaVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conNumeroDeCasa(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdProvinciaVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conIdProvincia(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdCantonVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conIdCanton(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdParroquiaVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conIdParroquia(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdPaisVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .conIdPais(CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void debeCrearPortadorTituloConCedula() throws Exception {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo().generar();

        ClientResponse response = client.resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        verify(portadorTituloDAO).guardar(any(PortadorTitulo.class));
        assertThat(response.getStatus(), is(201));
    }

    private void assertErrorMessage(ClientResponse response, String expectedErrorMessage) {
        String errorMessage = response.getEntity(String.class);
        assertThat(errorMessage, containsString(expectedErrorMessage));
    }
}


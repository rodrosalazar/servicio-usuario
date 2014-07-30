package ec.gob.senescyt.titulos.resources;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.dao.PortadorTituloDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class TituloExtranjeroResourceTest {

    private static PortadorTituloDAO portadorTituloDAO = mock(PortadorTituloDAO.class);
    private static final String CAMPO_EN_BLANCO = "";

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TituloExtranjeroResource(portadorTituloDAO))
            .addProvider(ValidacionExceptionMapper.class)
            .build();

    @Before
    public void setUp() throws Exception {
        reset(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloSinMail() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.email = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConMailDeFormatoInvalido() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.email = "formatoInvalido")
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "email Formato inválido");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdPaisVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idPaisNacionalidad = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdPaisMayorDe6Caracteres() {
        String paisDe6Caracteres = RandomStringUtils.random(7);
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idPaisNacionalidad = paisDe6Caracteres)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 6 caracter");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConFechaDeNacimientoAnteriorALaFechaActual() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.fechaNacimiento = DateTime.now(DateTimeZone.UTC).minusDays(-1))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "No puede ser mayor a la fecha actual");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConTelefonoConvencionalConMasOMenosDe9Digitos() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.telefonoConvencional = "12345678")
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener 9 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConTelefonoConvencionalConOtrosCaracteresQueNoSeanNumeros() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.telefonoConvencional = "1234$6789")
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener 9 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConTelefonoCelularConMasOMenosDe10Digitos() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.telefonoCelular = "12345678")
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener 10 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConTelefonoCelularConOtrosCaracteresQueNoSeanNumeros() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.telefonoCelular = "1234E6S89A")
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener 10 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConExtensionConMasDe5igitos() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.extension = "12345678")
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "No puede contener más de 5 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConExtensionConOtrosCaracteresQueNoSeanNumeros() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.extension = "123SE")
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "No puede contener más de 5 dígitos");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloSinGenero() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.genero = null)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoElNombreEstaVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.nombresCompletos = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdEtniaVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idEtnia = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdEtniaConMasDe2Caracteres() {
        String etniaConMasDe2Caracteres = "invalido";
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idEtnia = etniaConMasDe2Caracteres)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 2 caracteres");
        verifyZeroInteractions(portadorTituloDAO);
    }

    private ClientResponse hacerPost(PortadorTitulo portadorTitulo) {
        return resources.client().resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);
    }
}
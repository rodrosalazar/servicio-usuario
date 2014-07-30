package ec.gob.senescyt.titulos.resources;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.*;
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
import java.io.IOException;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TituloExtranjeroResourceAnidadosTest {

    private static PortadorTituloDAO portadorTituloDAO = mock(PortadorTituloDAO.class);
    private static final String STRING_CON_255_CARACTERES = RandomStringUtils.random(256);
    private static final String CAMPO_EN_BLANCO = "";
    private String numeroCedulaValido = "1111111116";
    private String numeroPasaporte = "ASE23";
    private DateTime fechaFinVigenciaPasaporteValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);
    private DateTime fechaFinVigenciaVisaValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);
    private String pasaporteEnBlanco = "";
    private String campoConformadoSoloDeEspaciosEnBlanco = "   ";
    private DateTime fechaFinVigenciaVisaEnBlanco = null;
    private DateTime fechaFinVigenciaPasaporteEnBlanco = null;
    private DateTime fechaFinVigenciaVisaMenorFechaActual = new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);


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
    public void noDebeCrearTituloConDireccionCompletaVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.direccionCompleta = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConDireccionCompletaDeMasDe255Caracteres() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.direccionCompleta = STRING_CON_255_CARACTERES)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 255 caracter");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdProvinciaVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idProvincia = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdProvinciaMayorDe2Caracteres() {
        String provinciaDe3Caracteres = RandomStringUtils.random(3);
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idProvincia = provinciaDe3Caracteres)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 2 caracter");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdCantonVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idCanton = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdCantonMayorDe4Caracteres() {
        String cantonDe5Caracteres = RandomStringUtils.random(5);
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idCanton = cantonDe5Caracteres)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 4 caracter");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdParroquiaVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idParroquia = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdParroquiaMayorDe6Caracteres() {
        String parroquiaDe6Caracteres = RandomStringUtils.random(7);
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idParroquia = parroquiaDe6Caracteres)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 6 caracteres");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConDireccionNula() throws IOException {
        String portadorTitulo = fixture("fixtures/portador_titulo_sin_direccion.json");

        ClientResponse response = resources.client().resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void debeVerificarQueIdentificacionNoSeaNula() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = null)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void debeVerificarQueNumeroDeIdentificacionNoEsteVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(""))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void debeCrearPortadorTituloConCedula() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroCedulaValido)).generar();

        ClientResponse response = hacerPost(portadorTitulo);

        verify(portadorTituloDAO).guardar(any(PortadorTitulo.class));
        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void noDebeCrearTituloCuandoElNumeroDePasaporteEstaEnBlanco() {
        String pasaporteEnBlanco = "";
        DateTime fechaFinVigenciaPasaporteValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);
        DateTime fechaFinVigenciaVisaValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);

        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(pasaporteEnBlanco, fechaFinVigenciaPasaporteValida, fechaFinVigenciaVisaValida, "9", false))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoElNumeroDePasaporteEstaConformadoSoloDeEspaciosEnBlanco() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(campoConformadoSoloDeEspaciosEnBlanco, fechaFinVigenciaPasaporteValida, fechaFinVigenciaVisaValida, "9", false))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoFechaDeFinDeVigenciaVisaEstaEnBlanco() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> {
                    p.identificacion = new Pasaporte(numeroPasaporte, fechaFinVigenciaPasaporteValida, fechaFinVigenciaVisaEnBlanco, "9", true);
                })
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoFechaDeFinDeVigenciaPasaporteEstaEnBlanco() {

        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte("AS23", fechaFinVigenciaPasaporteEnBlanco, fechaFinVigenciaVisaValida, "9", true))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }


    @Test
    public void noDebeCrearTituloCuandoFechaDeFinDeVigenciaVisaEsMenorALaActual() {

        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(numeroPasaporte, fechaFinVigenciaPasaporteValida, fechaFinVigenciaVisaMenorFechaActual, "9", true))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "No puede ser menor a la fecha actual");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoFechaDeFinDeVigenciaPasaporteEsMenorALaActual() {
        DateTime fechaFinVigenciaVisaValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);
        DateTime fechaFinVigenciaPasaporteMenorFechaActual = new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(numeroPasaporte, fechaFinVigenciaPasaporteMenorFechaActual, fechaFinVigenciaVisaValida, "9", true))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "No puede ser menor a la fecha actual");
        verifyZeroInteractions(portadorTituloDAO);
    }

    private ClientResponse hacerPost(PortadorTitulo portadorTitulo) {
        return resources.client().resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);
    }
}

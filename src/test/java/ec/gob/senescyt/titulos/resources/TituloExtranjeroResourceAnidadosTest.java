package ec.gob.senescyt.titulos.resources;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.Cedula;
import ec.gob.senescyt.titulos.core.Pasaporte;
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
import java.io.IOException;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class TituloExtranjeroResourceAnidadosTest {

    private static PortadorTituloDAO portadorTituloDAO = mock(PortadorTituloDAO.class);
    private static final String ID_CATEGORIA_VISA = "9";
    private static final String STRING_CON_255_CARACTERES = RandomStringUtils.random(256);
    private static final String CAMPO_EN_BLANCO = "";
    private static final String NUMERO_CEDULA_VALIDO = "1111111116";
    private static final String NUMERO_PASAPORTE = "ASE23";
    private static final DateTime FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);
    private static final DateTime FECHA_FIN_VIGENCIA_VISA_VALIDA = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);
    private static final String CAMPO_SOLO_ESPACIOS = "   ";
    private static final DateTime FECHA_FIN_VIGENCIA_VISA_EN_BLANCO = null;
    private static final DateTime FECHA_FIN_VIGENCIA_PASAPORTE_EN_BLANCO = null;
    private static final DateTime FECHA_FIN_VIGENCIA_VISA_MENOR_FECHA_ACTUAL = new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);
    private static final String NUMERO_PASAPORTE_CON_ESPACIOS = "ASD 123";
    private static final String NUMERO_PASAPORTE_CON_CARACTERES_ESPECIALES = "asd ^%^**";


    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new TituloExtranjeroResource(portadorTituloDAO))
            .addProvider(ValidacionExceptionMapper.class)
            .build();

    @Before
    public void setUp() {
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

        ClientResponse response = RESOURCES.client().resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoElNumeroDePasaporteEstaEnBlanco() {
        String pasaporteEnBlanco = "";
        DateTime fechaFinVigenciaPasaporteValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);
        DateTime fechaFinVigenciaVisaValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);

        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(pasaporteEnBlanco, fechaFinVigenciaPasaporteValida, fechaFinVigenciaVisaValida, ID_CATEGORIA_VISA, false))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoElNumeroDePasaporteEstaConformadoSoloDeEspaciosEnBlanco() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(CAMPO_SOLO_ESPACIOS, FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA, FECHA_FIN_VIGENCIA_VISA_VALIDA, ID_CATEGORIA_VISA, false))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
         public void noDebeCrearTituloCuandoFechaDeFinDeVigenciaVisaEstaEnBlancoYNoSeaVisaIndefinida() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> {
                    p.identificacion = new Pasaporte(NUMERO_PASAPORTE, FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA, FECHA_FIN_VIGENCIA_VISA_EN_BLANCO, ID_CATEGORIA_VISA, false);
                })
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "La fecha de fin de vigencia de la visa es inválida");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoFechaDeFinDeVigenciaVisaTieneUnValorYSeaVisaIndefinida() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> {
                    p.identificacion = new Pasaporte(NUMERO_PASAPORTE, FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA, FECHA_FIN_VIGENCIA_VISA_VALIDA, ID_CATEGORIA_VISA, true);
                })
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "La fecha de fin de vigencia de la visa es inválida");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoFechaDeFinDeVigenciaPasaporteEstaEnBlanco() {

        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte("AS23", FECHA_FIN_VIGENCIA_PASAPORTE_EN_BLANCO, FECHA_FIN_VIGENCIA_VISA_VALIDA, ID_CATEGORIA_VISA, false))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoFechaDeFinDeVigenciaVisaEsMenorALaActual() {

        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(NUMERO_PASAPORTE, FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA, FECHA_FIN_VIGENCIA_VISA_MENOR_FECHA_ACTUAL, ID_CATEGORIA_VISA, false))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "La fecha de fin de vigencia de la visa es inválida");
        verifyZeroInteractions(portadorTituloDAO);
    }


    @Test
    public void noDebeCrearTituloCuandoFechaDeFinDeVigenciaPasaporteEsMenorALaActual() {
        DateTime fechaFinVigenciaVisaValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);
        DateTime fechaFinVigenciaPasaporteMenorFechaActual = new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(NUMERO_PASAPORTE, fechaFinVigenciaPasaporteMenorFechaActual, fechaFinVigenciaVisaValida, ID_CATEGORIA_VISA, false))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "No puede ser menor a la fecha actual");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoNumeroDePasaporteContieneEspacios() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(NUMERO_PASAPORTE_CON_ESPACIOS, FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA, FECHA_FIN_VIGENCIA_VISA_VALIDA, ID_CATEGORIA_VISA, false))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Número Inválido");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloCuandoNumeroDePasaporteContieneCaracteresEspeciales() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(NUMERO_PASAPORTE_CON_CARACTERES_ESPECIALES, FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA, FECHA_FIN_VIGENCIA_VISA_VALIDA, ID_CATEGORIA_VISA, false))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Número Inválido");
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
                .con(p -> p.identificacion = new Cedula(NUMERO_CEDULA_VALIDO)).generar();

        ClientResponse response = hacerPost(portadorTitulo);

        verify(portadorTituloDAO).guardar(any(PortadorTitulo.class));
        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void debeCrearTituloCuandoLaVisaEsIndefinidaYLaFechaDeFinDeVigenciaDeLaVisaEsNula() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Pasaporte(NUMERO_PASAPORTE, FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA, null, ID_CATEGORIA_VISA, true))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        verify(portadorTituloDAO).guardar(any(PortadorTitulo.class));
        assertThat(response.getStatus(), is(201));
    }

    private ClientResponse hacerPost(PortadorTitulo portadorTitulo) {
        return RESOURCES.client().resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);
    }
}

package ec.gob.senescyt.titulos.resources;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.dao.PortadorTituloDAO;
import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.apache.commons.lang.RandomStringUtils;
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
    public void noDebeCrearTituloConCallePrincipalVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.callePrincipal = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConCallePrincipalDeMasDe255Caracteres() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.callePrincipal = STRING_CON_255_CARACTERES)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 255 caracter");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConCalleSecundariaVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.calleSecundaria = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConCalleSecundariaDeMasDe255Caracteres() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.calleSecundaria = STRING_CON_255_CARACTERES)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 255 caracter");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConNumeroDeCasaVacia() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.numeroCasa = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConNumeroDeCasaMayorDe50Caracteres() {
        String masDe50Caracteres = RandomStringUtils.random(51);
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.numeroCasa = masDe50Caracteres)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 50 caracter");
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
        assertErrorMessage(response, "Debe ser un maximo de 6 caracter");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void noDebeCrearTituloConIdPaisVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.idPais = CAMPO_EN_BLANCO)
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
                .con(p -> p.idPais = paisDe6Caracteres)
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe ser un maximo de 6 caracter");
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
    public void debeVerificarQueTipoDeIdentificacionNoSeaNulo() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Identificacion(null, "2222222222"))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void debeVerificarQueNumeroDeIdentificacionNoEsteVacio() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Identificacion(TipoDocumentoEnum.PASAPORTE, ""))
                .generar();

        ClientResponse response = hacerPost(portadorTitulo);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(portadorTituloDAO);
    }

    @Test
    public void debeCrearPortadorTituloConCedula() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo().generar();

        ClientResponse response = hacerPost(portadorTitulo);

        verify(portadorTituloDAO).guardar(any(PortadorTitulo.class));
        assertThat(response.getStatus(), is(201));
    }

    private ClientResponse hacerPost(PortadorTitulo portadorTitulo) {
        return resources.client().resource("/titulo/extranjero")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, portadorTitulo);
    }
}
package ec.gob.senescyt.usuario.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.usuario.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UsuarioResourceTest {
    private UsuarioResource usuarioResource;
    private static UsuarioDAO usuarioDAO = mock(UsuarioDAO.class);
    private static CedulaValidator cedulaValidator = new CedulaValidator();
    private static LectorArchivoDePropiedades lectorArchivoDePropiedades = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UsuarioResource(usuarioDAO, cedulaValidator, lectorArchivoDePropiedades))
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;

    @Before
    public void setUp() {
        usuarioResource = new UsuarioResource(usuarioDAO, cedulaValidator, lectorArchivoDePropiedades);
        client = resources.client();
        reset(usuarioDAO);
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaValidaUsandoResource() {
        String cedulaValida = "1718642174";

        Response response = usuarioResource.validar(Optional.of(cedulaValida), Optional.<String>absent(), Optional.<String>absent());

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaInvalidaUsandoResource() {
        String cedulaInvalida = "1111111111";

        Response response = usuarioResource.validar(Optional.of(cedulaInvalida), Optional.<String>absent(), Optional.<String>absent());

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void debeVerificarQueCedulaDeUsuarioNoEsteEnBlanco() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConCedulaEnBlanco());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeAlertarCuandoUnaCedulaDeUnUsuarioSeaInvalida() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConCedulaInvalida());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeAlertarCuandoUnUsuarioTengaUnDocumentoDistintoACedulaOPasaporte() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConDocumentoInvalido());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeGuardarUsuarioConPasaporte() throws Exception {
        Usuario usuarioConPasaporte = UsuarioBuilder.usuarioConPasaporte();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConPasaporte);

        verify(usuarioDAO).guardar(any(Usuario.class));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void debeIndicarCuandoUnEmailInstitucionalEsInvalido() {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConEmailInvalido());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueEmailNoEsteEnBlanco() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConEmailEnBlanco());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeIndicarCuandoUnEmailInstitucionalEsValido() throws Exception {
        Usuario usuarioConEmailValido = UsuarioBuilder.usuarioValido();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConEmailValido);

        verify(usuarioDAO).guardar(any(Usuario.class));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void debeVerificarQueLaFechaDeFinDeVigenciaNoPuedeSerMenorALaFechaActual() throws JsonProcessingException {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConFechaDeVigenciaInvalida());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity(String.class)).contains("El campo es obligatorio");
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueNumeroQuipuxNoEsteEnBlanco() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConNumeroQuipuxEnBlanco());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeIndicarQueFormatoDeNumeroAutorizacionQuipuxEsInvalido() throws JsonProcessingException {

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConNumeroQuipuxInvalido());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQuePrimerNombreNoEsteEnBlanco() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConPrimerNombreEnBlanco());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);

    }

    @Test
    public void debeVerificarQuePrimerApellidoNoEsteEnBlanco() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConPrimerApellidoEnBlanco());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);

    }

    @Test
    public void debeVerificarQueFechaFinDeVigenciaNoEsteEnBlanco() throws Exception{
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConFechaDeVigenciaEnBlanco());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueCodigoInstitucionNoSeaNulo() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConIdInstitucionNulo());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueCodigoInstitucionNoEsteEnBlanco() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConIdInstitucionEnBlanco());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueNombreDeUsuarioNoEsteEnBlanco() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConNombreUsuarioEnBlanco());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueLosPerfilesNoEstenVacios() {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioSinPerfiles());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeGuardarUsuario() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioValido());

        verify(usuarioDAO).guardar(any(Usuario.class));
        assertThat(response.getStatus()).isEqualTo(201);
    }
}

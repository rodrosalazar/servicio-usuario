package ec.gob.senescyt.usuario.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class UsuarioResourceTest {
    private UsuarioResource usuarioResource;
    private static UsuarioDAO usuarioDAO = mock(UsuarioDAO.class);
    private static CedulaValidator cedulaValidator = new CedulaValidator();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UsuarioResource(usuarioDAO, cedulaValidator))
            .addProvider(ValidacionExceptionMapper.class)
            .build();

    @Before
    public void init() {
        usuarioResource = new UsuarioResource(usuarioDAO, cedulaValidator);
        reset(usuarioDAO);
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaValidaUsandoResource() {
        String cedulaValida = "1718642174";

        Response response = usuarioResource.validarUsuario(cedulaValida, null, null);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaInvalidaUsandoResource() {
        String cedulaInvalida = "1111111111";

        Response response = usuarioResource.validarUsuario(cedulaInvalida,null,null);

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void debeVerificarQueCedulaDeUsuarioNoEsteEnBlanco() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConCedulaEnBlanco().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeAlertarCuandoUnaCedulaDeUnUsuarioSeaInvalida() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConCedulaInvalida().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }


    @Test
    public void debeAlertarCuandoUnUsuarioTengaUnDocumentoDistintoACedulaOPasaporte() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConDocumentoInvalido().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeGuardarUsuarioConPasaporte() throws Exception {

        Usuario usuarioConPasaporte = UsuarioBuilder.usuarioConPasaporte();
        Response response = usuarioResource.crearUsuario(usuarioConPasaporte);
        verify(usuarioDAO).guardar(eq(usuarioConPasaporte));

        assertThat(response.getStatus()).isEqualTo(201);

    }

    @Test
    public void debeIndicarCuandoUnEmailInstitucionalEsInvalido() {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConEmailInvalido());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueEmailNoEsteEnBlanco() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConEmailEnBlanco());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }


    @Test
    public void debeIndicarCuandoUnUsuarioEsValido() throws Exception {
        Usuario usuarioConEmailValido = UsuarioBuilder.usuarioValido();
        Response response = usuarioResource.crearUsuario( usuarioConEmailValido);

        verify(usuarioDAO).guardar(eq(usuarioConEmailValido));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void debeVerificarQueLaFechaDeFinDeVigenciaNoPuedeSerMenorALaFechaActual() throws JsonProcessingException {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConFechaDeVigenciaInvalida().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueNumeroQuipuxNoEsteEnBlanco() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConNumeroQuipuxEnBlanco().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeIndicarQueFormatoDeNumeroAutorizacionQuipuxEsInvalido() throws JsonProcessingException {

        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConNumeroQuipuxInvalido().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQuePrimerNombreNoEsteEnBlanco() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConPrimerNombreEnBlanco().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);

    }

    @Test
    public void debeVerificarQuePrimerApellidoNoEsteEnBlanco() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConPrimerApellidoEnBlanco().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);

    }

    @Test
    public void debeVerificarQueFechaFinDeVigenciaNoEsteEnBlanco() throws Exception{
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConFechaDeVigenciaEnBlanco().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    //TODO:Verificar si cuando no se seleccione una opcion se va a mandar un null o un cero
    public void debeVerificarQueCodigoInstitucionNoEsteEnBlanco() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConIdInstitucionEnBlanco().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueNombreDeUsuarioNoEsteEnBlanco() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.usuarioConNombreUsuarioEnBlanco().toJson());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeGuardarUsuario() throws Exception {
        Usuario usuario = UsuarioBuilder.usuarioValido();

        Response response = usuarioResource.crearUsuario(usuario);

        verify(usuarioDAO).guardar(eq(usuario));
        assertThat(response.getStatus()).isEqualTo(201);
    }
}

package ec.gob.senescyt.usuario.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.core.Nombre;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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
    ;
    private static CedulaValidator cedulaValidator = new CedulaValidator();

    private static final TipoDocumentoEnum tipoDocumentoCedula = TipoDocumentoEnum.CEDULA;
    private static final String cedula = "1718642174";
    private static final String primerNombre = "Nelson";
    private static final String segundoNombre = "Alberto";
    private static final String primerApellido = "Jumbo";
    private static final String segundoApellido = "Hidalgo";
    private static final String emailValido = "test@senescyt.gob.ec";
    private static final String emailInvalido = "emailInvalido";
    private static final String numeroQuipuxInvalido = "123456";
    private static final String numeroQuipuxValido = "SENESCYT-DFAPO-2014-65946-MI";
    private static final DateTime now = new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay();
    private static final long idInstitucion = 1l;
    private static final String nombreUsuario = "nombreUsuario";

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

        Response response = usuarioResource.verificarCedula(cedulaValida);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaInvalidaUsandoResource() {
        String cedulaInvalida = "1111111111";

        Response response = usuarioResource.verificarCedula(cedulaInvalida);

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
    public void debeGuardarUsuarioConPasaporte() {

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
    public void debeIndicarCuandoUnUsuarioEsValido() {
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
    public void debeGuardarUsuario() {
        long idInstitucion = 1l;
        DateTime fechaDentroDeUnMes = new DateTime().plusMonths(1)
                .withZone(DateTimeZone.UTC)
                .withTimeAtStartOfDay();

        Usuario usuario = new Usuario(new Identificacion(tipoDocumentoCedula, cedula),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailValido, numeroQuipuxValido,
                fechaDentroDeUnMes,
                idInstitucion, nombreUsuario);

        Response response = usuarioResource.crearUsuario(usuario);

        verify(usuarioDAO).guardar(eq(usuario));
        assertThat(response.getStatus()).isEqualTo(201);
    }
}

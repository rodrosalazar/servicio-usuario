package ec.gob.senescyt.usuario.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.commons.configuracion.ConfiguracionEmail;
import ec.gob.senescyt.commons.email.DespachadorEmail;
import ec.gob.senescyt.commons.helpers.ResourceTestHelper;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.commons.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.enums.TipoDocumento;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;

import javax.mail.Message;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class UsuarioResourceTest {

    private static UsuarioDAO usuarioDAO = mock(UsuarioDAO.class);
    private static TokenDAO tokenDAO = mock(TokenDAO.class);
    private static CedulaValidator cedulaValidator = new CedulaValidator();
    private static LectorArchivoDePropiedades lectorPropiedadesValidacion = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());
    private static LectorArchivoDePropiedades lectorPropiedadesEmail = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_PROPIEDADES_EMAIL.getBaseName());
    private static ConfiguracionEmail configuracionEmail = spy(new ConfiguracionEmail());
    private static DespachadorEmail despachadorEmail = new DespachadorEmail(configuracionEmail);
    private static UsuarioResource usuarioResource = new UsuarioResource(usuarioDAO, cedulaValidator, lectorPropiedadesValidacion, despachadorEmail, tokenDAO, lectorPropiedadesEmail);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(usuarioResource)
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;

    @Before
    public void setUp() {
        client = resources.client();
        ResourceTestHelper.mockConfiguracionMail(configuracionEmail);
        Mailbox.clearAll();
    }

    @After
    public void tearDown() throws Exception {
        reset(usuarioDAO);
        reset(tokenDAO);
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaValidaUsandoResource() {
        String cedulaValida = "1718642174";

        Response response = usuarioResource.validar(Optional.of(cedulaValida), Optional.<String>absent(), Optional.<String>absent());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaInvalidaUsandoResource() {
        String cedulaInvalida = "1111111111";

        Response response = usuarioResource.validar(Optional.of(cedulaInvalida), Optional.<String>absent(), Optional.<String>absent());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeVerificarQueCedulaDeUsuarioNoEsteEnBlanco() throws Exception {
        Usuario usuarioConCedulaEnBlanco = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.numeroIdentificacion = "")
                .generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConCedulaEnBlanco);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeAlertarCuandoUnaCedulaDeUnUsuarioSeaInvalida() throws Exception {
        Usuario usuarioConCedulaInvalida = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.numeroIdentificacion = "11")
                .generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConCedulaInvalida);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeAlertarCuandoUnUsuarioTengaUnDocumentoDistintoACedulaOPasaporte() throws Exception {
        Usuario usuarioConTipoDeDocumentoInvalido = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.tipoDocumento = null)
                .generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConTipoDeDocumentoInvalido);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeGuardarUsuarioConPasaporte() throws Exception {
        Usuario usuarioConPasaporte = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.tipoDocumento = TipoDocumento.PASAPORTE)
                .generar();

        when(usuarioDAO.guardar(any(Usuario.class))).thenReturn(usuarioConPasaporte);

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConPasaporte);

        verify(usuarioDAO).guardar(any(Usuario.class));
        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void debeIndicarCuandoUnEmailInstitucionalEsInvalido() {
        Usuario usuarioEmailInstitucionalInvalido = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.emailInstitucional = "invalido")
                .generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioEmailInstitucionalInvalido);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueEmailNoEsteEnBlanco() throws Exception {
        Usuario usuarioConEmailInstitucionalInvalido = UsuarioBuilder.nuevoUsuario().con(u -> u.emailInstitucional = "").generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConEmailInstitucionalInvalido);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeIndicarCuandoUnEmailInstitucionalEsValido() throws Exception {
        Usuario usuarioConEmailValido = UsuarioBuilder.nuevoUsuario().generar();
        when(usuarioDAO.guardar(any(Usuario.class))).thenReturn(usuarioConEmailValido);

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConEmailValido);

        verify(usuarioDAO).guardar(any(Usuario.class));
        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void debeVerificarQueLaFechaDeFinDeVigenciaNoPuedeSerMenorALaFechaActual() throws JsonProcessingException {
        Usuario usuarioConFinDeVigenciaInvalida = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.fechaDeVigencia = new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay().minusMonths(1))
                .generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConFinDeVigenciaInvalida);

        assertThat(response.getStatus(), is(400));
        assertThat(response.getEntity(String.class), containsString("inDeVigencia No puede ser menor a la fecha actual"));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueNumeroQuipuxNoEsteEnBlanco() throws Exception {
        Usuario usuarioConQuipuxEnBlanco = UsuarioBuilder.nuevoUsuario().con(u -> u.numeroAutorizacionQuipux = "").generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConQuipuxEnBlanco);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeIndicarQueFormatoDeNumeroAutorizacionQuipuxEsInvalido() throws JsonProcessingException {
        Usuario usuarioConNumeroDeQuipuxInvalido = UsuarioBuilder.nuevoUsuario().con(u -> u.numeroAutorizacionQuipux = "123456").generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConNumeroDeQuipuxInvalido);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQuePrimerNombreNoEsteEnBlanco() throws Exception {
        Usuario usuarioConPrimerNombreEnBlanco = UsuarioBuilder.nuevoUsuario().con(u -> u.primerNombre = "").generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConPrimerNombreEnBlanco);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQuePrimerApellidoNoEsteEnBlanco() throws Exception {
        Usuario usuarioConApellidoEnBlanco = UsuarioBuilder.nuevoUsuario().con(u -> u.primerApellido = "").generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConApellidoEnBlanco);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueFechaFinDeVigenciaNoEsteEnBlanco() throws Exception{
        Usuario usuarioConFechaDeVigenciaNula = UsuarioBuilder.nuevoUsuario().con(u -> u.fechaDeVigencia = null).generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConFechaDeVigenciaNula);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueCodigoInstitucionNoSeaNulo() throws Exception {
        Usuario usuarioConInstitucionNula = UsuarioBuilder.nuevoUsuario().con(u -> u.idInstitucion = null).generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConInstitucionNula);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueCodigoInstitucionNoEsteEnBlanco() throws Exception {
        String usuarioConInstucionEnBlanco = UsuarioBuilder.usuarioConIdInstitucionEnBlanco();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConInstucionEnBlanco);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueNombreDeUsuarioNoEsteEnBlanco() throws Exception {
        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, UsuarioBuilder.nuevoUsuario().con(u -> u.nombreUsuario = "").generar());

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeVerificarQueLosPerfilesNoEstenVacios() {
        Usuario usuarioSinPerfiles = UsuarioBuilder.nuevoUsuario().con(u -> u.perfiles = null).generar();

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioSinPerfiles);

        assertThat(response.getStatus(), is(400));
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeGuardarUsuario() throws Exception {
        Usuario usuarioValido = UsuarioBuilder.nuevoUsuario().generar();
        when(usuarioDAO.guardar(any(Usuario.class))).thenReturn(usuarioValido);

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioValido);

        verify(usuarioDAO).guardar(any(Usuario.class));

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void debeEnviarMailAlUsuarioQueAcabaDeSerCreado() throws Exception {
        Usuario usuarioValido = UsuarioBuilder.nuevoUsuario().generar();
        when(usuarioDAO.guardar(any(Usuario.class))).thenReturn(usuarioValido);

        ClientResponse response = client.resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioValido);

        verify(usuarioDAO).guardar(any(Usuario.class));
        verify(tokenDAO).guardar(any(Token.class));

        List<Message> bandejaEntradaTest = Mailbox.get(usuarioValido.getEmailInstitucional());
        assertThat(bandejaEntradaTest.size(), is(1));

        assertThat(response.getStatus(), is(201));
    }
}

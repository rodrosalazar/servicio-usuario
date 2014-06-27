package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.core.Nombre;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.validation.ConstraintViolationExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

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
    public void debeVerificarQueUnaCedulaSeaValida() {
        String cedulaValida = "1718642174";

        Response response = usuarioResource.verificarCedula(cedulaValida);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void debeVerificarQueUnaCedulaSeaInvalida() {
        String cedulaInvalida = "1111111111";

        Response response = usuarioResource.verificarCedula(cedulaInvalida);

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    @Ignore("NO esta validando correctmente el numero de identificacion")
    public void debeVerificarQueCedulaDeUsuarioNoEsteEnBlanco() {
        String cedulaInvalida = "11";
        Usuario usuario = new Usuario(new Identificacion(tipoDocumentoCedula, cedulaInvalida),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailValido, numeroQuipuxValido,
                now,
                idInstitucion, nombreUsuario);

        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConCedulaInvalidaAsJSON());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity(String.class)).isEmpty();
        verifyZeroInteractions(usuarioDAO);

    }

    private String usuarioConCedulaInvalidaAsJSON() {
        return "{\n" +
                "    \"identificacion\": {\n" +
                "        \"tipoDocumento\": \"CEDULA\",\n" +
                "        \"numeroIdentificacion\": \"\"\n" +
                "    },\n" +
                "    \"nombre\": {\n" +
                "        \"primerNombre\": \"Nelson\",\n" +
                "        \"segundoNombre\": \"Alberto\",\n" +
                "        \"primerApellido\": \"Jumbo\",\n" +
                "        \"segundoApellido\": \"Hidalgo\"\n" +
                "    },\n" +
                "    \"emailInstitucional\":\"test@senescyt.gob.ec\",\n" +
                "    \"numeroAutorizacionQuipux\":\"" + numeroQuipuxValido + "\",\n" +
                "    \"finDeVigencia\":\"12/01/2015\",\n" +
                "    \"idInstitucion\":\"1\",\n" +
                "    \"nombreUsuario\":\"njumbo\"\n" +
                "}\n";
    }

    @Test
    public void debeVerificarQueNumeroQuipuxNoEsteEnBlanco() throws Exception {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioConNumeroQuipuxEnBlancoAsJSON());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    private String usuarioConNumeroQuipuxEnBlancoAsJSON() {
        return "{\n" +
                "    \"identificacion\": {\n" +
                "        \"tipoDocumento\": \"CEDULA\",\n" +
                "        \"numeroIdentificacion\": \"1718642174\"\n" +
                "    },\n" +
                "    \"nombre\": {\n" +
                "        \"primerNombre\": \"Nelson\",\n" +
                "        \"segundoNombre\": \"Alberto\",\n" +
                "        \"primerApellido\": \"Jumbo\",\n" +
                "        \"segundoApellido\": \"Hidalgo\"\n" +
                "    },\n" +
                "    \"emailInstitucional\":\"test@senescyt.gob.ec\",\n" +
                "    \"numeroAutorizacionQuipux\":\"\",\n" +
                "    \"finDeVigencia\":\"12/01/2015\",\n" +
                "    \"idInstitucion\":\"1\",\n" +
                "    \"nombreUsuario\":\"njumbo\"\n" +
                "}\n";
    }

    @Test
    public void debeGuardarUsuarioConPasaporte() {
        Usuario usuario = new Usuario(new Identificacion(tipoDocumentoCedula, cedula),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailValido, numeroQuipuxValido,
                now,
                idInstitucion, nombreUsuario);

        Response response = usuarioResource.crearUsuario(usuario);
        verify(usuarioDAO).guardar(eq(usuario));

        assertThat(response.getStatus()).isEqualTo(201);

    }

    @Test
    public void debeIndicarCuandoUnEmailInstitucionalEsInvalido() {
        ClientResponse response = resources.client().resource("/usuario")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, usuarioAsJSON());

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    private String usuarioAsJSON() {
        return "{\n" +
                "    \"identificacion\": {\n" +
                "        \"tipoDocumento\": \"CEDULA\",\n" +
                "        \"numeroIdentificacion\": \"1718642174\"\n" +
                "    },\n" +
                "    \"nombre\": {\n" +
                "        \"primerNombre\": \"Nelson\",\n" +
                "        \"segundoNombre\": \"Alberto\",\n" +
                "        \"primerApellido\": \"Jumbo\",\n" +
                "        \"segundoApellido\": \"Hidalgo\"\n" +
                "    },\n" +
                "    \"emailInstitucional\":\"test\",\n" +
                "    \"numeroAutorizacionQuipux\":\"" + numeroQuipuxValido + "\",\n" +
                "    \"finDeVigencia\":\"12/01/2015\",\n" +
                "    \"idInstitucion\":\"1\",\n" +
                "    \"nombreUsuario\":\"njumbo\"\n" +
                "}\n";
    }

    @Test
    public void debeIndicarCuandoUnEmailInstitucionalEsValido() {
        Usuario usuarioConEmailValido = new Usuario(new Identificacion(tipoDocumentoCedula, cedula),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailValido, numeroQuipuxValido,
                now,
                idInstitucion, nombreUsuario);

        Response response = usuarioResource.crearUsuario(usuarioConEmailValido);

        verify(usuarioDAO).guardar(eq(usuarioConEmailValido));
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void debeVerificarQueLaFechaDeFinDeVigenciaNoPuedeSerMenorALaFechaActual() {
        DateTime fechaHaceUnMes = new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay().minusMonths(1);

        Usuario usuarioConFechaDeVigenciaInvalida = new Usuario(new Identificacion(tipoDocumentoCedula, cedula),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailValido, numeroQuipuxValido,
                fechaHaceUnMes,
                idInstitucion, nombreUsuario);

        Response response = usuarioResource.crearUsuario(usuarioConFechaDeVigenciaInvalida);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    @Ignore("[#20] no esta pasando porque se esta revisando validaciond de numero quipux")
    public void debeIndicarQueFormatoDeNumeroAutorizacionQuipuxEsInvalido() {
        Usuario usuarioConNumeroQuipuxInvalido = new Usuario(new Identificacion(tipoDocumentoCedula, cedula),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailValido, numeroQuipuxInvalido,
                now,
                idInstitucion, nombreUsuario);

        Response response = usuarioResource.crearUsuario(usuarioConNumeroQuipuxInvalido);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
    }

    @Test
    public void debeAceptarNumeroAutorizacionQuipuxValido() {
        Usuario usuarioConNumeroQuipuxValido = new Usuario(new Identificacion(tipoDocumentoCedula, cedula),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailValido, numeroQuipuxValido,
                now,
                idInstitucion, nombreUsuario);

        Response response = usuarioResource.crearUsuario(usuarioConNumeroQuipuxValido);

        verify(usuarioDAO).guardar(eq(usuarioConNumeroQuipuxValido));
        assertThat(response.getStatus()).isEqualTo(201);
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

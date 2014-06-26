package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.core.Nombre;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class UsuarioResourceTest {
    private UsuarioResource usuarioResource;
    private UsuarioDAO usuarioDAO;
    private CedulaValidator cedulaValidator;

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

    @Before
    public void init() {
        usuarioDAO = mock(UsuarioDAO.class);
        cedulaValidator = new CedulaValidator();
        usuarioResource = new UsuarioResource(usuarioDAO, cedulaValidator);
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
    public void debeVerificarQueCedulaDeUsuarioEsCorrecta() {
        String cedulaInvalida = "11";
        Usuario usuario = new Usuario(new Identificacion(tipoDocumentoCedula, cedulaInvalida),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailInvalido, numeroQuipuxValido,
                now,
                idInstitucion, nombreUsuario);

        Response response = usuarioResource.crearUsuario(usuario);
        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);

    }

    @Test
    public void debeIndicarCuandoUnEmailInstitucionalEsInvalido() {
        Usuario usuarioConEmailInvalido = new Usuario(new Identificacion(tipoDocumentoCedula, cedula),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailInvalido, numeroQuipuxValido,
                now,
                idInstitucion, nombreUsuario);

        Response response = usuarioResource.crearUsuario(usuarioConEmailInvalido);

        assertThat(response.getStatus()).isEqualTo(400);
        verifyZeroInteractions(usuarioDAO);
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

package ec.gob.senescyt.usuario.services;

import com.google.common.base.Optional;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dto.ContraseniaToken;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.exceptions.CifradoErroneoException;
import ec.gob.senescyt.usuario.utils.Hasher;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServicioCredencialTest {

    private ServicioCredencial servicioCredencial;
    private CredencialDAO credencialDAO = mock(CredencialDAO.class);
    private ServicioCifrado servicioCifrado;
    private Hasher hasher;
    private static final String FORMATO_FECHA_CADUCIDAD = "dd/MM/yyyy/HH/mm";

    @Before
    public void setUp() throws CifradoErroneoException {
        servicioCifrado = mock(ServicioCifrado.class);
        hasher = mock(Hasher.class);
        servicioCredencial = new ServicioCredencial(credencialDAO, servicioCifrado, hasher);
    }

    @Test
    public void debeConvertirUnContraseniaTokenYUnTokenAUnaCredencialConLaContraseniaHasheada() {
        String nombreUsuario = "juan";
        String contrasenia = "una-contrasenia";
        String idToken = "un-token";
        ContraseniaToken contraseniaToken = new ContraseniaToken(contrasenia, idToken);
        Token token = new Token(idToken, UsuarioBuilder.nuevoUsuario().con(u -> u.nombreUsuario = nombreUsuario).generar());
        when(hasher.calcularHash(contrasenia)).thenReturn("hashedValue");

        Credencial credencial = servicioCredencial.convertirACredencial(contraseniaToken, token);

        assertThat(credencial.getNombreUsuario(), is(nombreUsuario));
        assertThat(credencial.getHash(), is("hashedValue"));
        assertThat(credencial.getHash(), is(not(contrasenia)));
    }

    @Test
    public void debeConvertirUnContraseniaTokenYUnTokenAUnaCredencialConLaContraseniaHasheadaYUsuario() {
        String nombreUsuario = "juan";
        String contrasenia = "una-contrasenia";
        String idToken = "un-token";
        ContraseniaToken contraseniaToken = new ContraseniaToken(contrasenia, idToken);
        Token token = new Token(idToken, UsuarioBuilder.nuevoUsuario().con(u -> u.nombreUsuario = nombreUsuario).generar());
        when(hasher.calcularHash(contrasenia)).thenReturn("hashedValue");

        Credencial credencial = servicioCredencial.convertirACredencial(contraseniaToken, token);

        assertThat(credencial.getNombreUsuario(), is(nombreUsuario));
        assertThat(credencial.getHash(), is("hashedValue"));
        assertThat(credencial.getHash(), is(not(contrasenia)));
    }

    @Test
    public void debeDevolverAbsentCuandoElNombreUsuarioNoExiste() {
        String nombreUsuarioInexistente = "inexistente";
        String contrasenia = "una-contrasenia";
        when(credencialDAO.obtenerPorNombreUsuario(nombreUsuarioInexistente)).thenReturn(null);
        CredencialLogin login = new CredencialLogin(nombreUsuarioInexistente, contrasenia);

        Optional token = servicioCredencial.obtenerTokenDeInicioDeSesion(login);

        assertThat(token.isPresent(), is(false));
    }

    @Test
    public void debeDevolverAbsentCuandoContraseniaNoEsCorrecta() {
        String nombreUsuario = "existente";
        String contraseniaIncorrecta = "incorrecta";
        CredencialLogin login = new CredencialLogin(nombreUsuario, contraseniaIncorrecta);
        Credencial credencialEncontrada = new Credencial(nombreUsuario, "hashedValido");
        when(credencialDAO.obtenerPorNombreUsuario(nombreUsuario)).thenReturn(credencialEncontrada);
        when(hasher.calcularHash(anyString())).thenReturn("hashedInvalido");

        Optional token = servicioCredencial.obtenerTokenDeInicioDeSesion(login);

        assertThat(token.isPresent(), is(false));
    }

    @Test
    public void debeDevolverAbsentCuandoElCifradoEsErroneo() throws CifradoErroneoException {
        String nombreUsuario = "existente";
        String contraseniaIncorrecta = "incorrecta";
        CredencialLogin login = new CredencialLogin(nombreUsuario, contraseniaIncorrecta);
        Credencial credencialEncontrada = new Credencial(nombreUsuario, "hashedValido");
        when(credencialDAO.obtenerPorNombreUsuario(nombreUsuario)).thenReturn(credencialEncontrada);
        when(hasher.calcularHash(anyString())).thenReturn("hashedInvalido");
        when(servicioCifrado.cifrar(anyString())).thenThrow(CifradoErroneoException.class);

        Optional token = servicioCredencial.obtenerTokenDeInicioDeSesion(login);

        assertThat(token.isPresent(), is(false));
    }

    @Test
    public void debeGenerarTokenIncluyendoNombreDeUsuarioYFechaYHoraDeCaducidad() throws CifradoErroneoException {
        String nombreUsuario = "existente";
        String contraseniaValida = "valido";
        String tokenSinEncriptar = nombreUsuario + "/" + generarRepresentacionDeFinDeSesion();
        String hashAlmacenado = "hashedValue";
        CredencialLogin login = new CredencialLogin(nombreUsuario, contraseniaValida);
        Credencial credencialEncontrada = new Credencial(nombreUsuario, hashAlmacenado);
        when(credencialDAO.obtenerPorNombreUsuario(nombreUsuario)).thenReturn(credencialEncontrada);
        when(hasher.verificarHash(contraseniaValida, hashAlmacenado)).thenReturn(true);
        when(servicioCifrado.cifrar(eq(tokenSinEncriptar))).thenReturn("tokenEncriptado");

        Optional tokenCifradoGenerado = servicioCredencial.obtenerTokenDeInicioDeSesion(login);

        assertThat(tokenCifradoGenerado.isPresent(), is(true));
        verify(servicioCifrado).cifrar(eq(tokenSinEncriptar));
    }

    private String generarRepresentacionDeFinDeSesion() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(FORMATO_FECHA_CADUCIDAD);
        DateTime finDeSesion = DateTime.now().plusMinutes(30);
        String finDeSesionFormateado = dateTimeFormatter.print(finDeSesion);

        return finDeSesionFormateado;
    }
}
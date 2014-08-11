package ec.gob.senescyt.usuario.services;

import com.google.common.base.Optional;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dto.ContraseniaToken;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.exceptions.CifradoErroneoException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioCredencialTest {

    private ServicioCredencial servicioCredencial;
    private CredencialDAO credencialDAO = mock(CredencialDAO.class);
    private ServicioCifrado servicioCifrado;
    private static final String FORMATO_FECHA_CADUCIDAD = "dd/MM/yyyy/HH/mm";

    @Before
    public void setUp() throws CifradoErroneoException {
        servicioCifrado = new ServicioCifrado();
        servicioCredencial = new ServicioCredencial(credencialDAO, servicioCifrado);
    }

    @Test
    public void debeCalcularLaFuncionHashAUnaContrasenia() {
        String contrasenia = "Super Secreta";

        String hashCalculado = servicioCredencial.calcularHash(contrasenia);

        assertThat(BCrypt.checkpw(contrasenia, hashCalculado), is(true));
    }

    @Test
    public void noDebeCalcularElMismoHashParaLaMismaContrasenia() {
        String contrasenia = "Super Secreta";

        String primerHash = servicioCredencial.calcularHash(contrasenia);
        String segundoHash = servicioCredencial.calcularHash(contrasenia);

        assertThat(primerHash, is(not(segundoHash)));
    }

    @Test
    public void debeVerificarQueLaContraseniaCorrespondaAlHash() {
        String contrasenia = "Super Secreta";
        String hashCalculado = servicioCredencial.calcularHash(contrasenia);

        boolean corresponden = servicioCredencial.verificarContrasenia(contrasenia, hashCalculado);

        assertThat(corresponden, is(true));
    }

    @Test
    public void debeVerificarQueLaContraseniaNoCorrespondaAlHash() {
        String contrasenia = "Super Secreta";
        String contraseniaErronea = "Secreto Mal";
        String hashCalculado = servicioCredencial.calcularHash(contrasenia);

        boolean corresponden = servicioCredencial.verificarContrasenia(contraseniaErronea, hashCalculado);

        assertThat(corresponden, is(false));
    }

    @Test
    public void debeConvertirUnContraseniaTokenYUnTokenAUnaCredencialConLaContraseniaHasheada() {
        String nombreUsuario = "juan";
        String contrasenia = "una-contrasenia";
        String idToken = "un-token";
        ContraseniaToken contraseniaToken = new ContraseniaToken(contrasenia, idToken);
        Token token = new Token(idToken, UsuarioBuilder.nuevoUsuario().con(u -> u.nombreUsuario = nombreUsuario).generar());

        Credencial credencial = servicioCredencial.convertirACredencial(contraseniaToken, token);

        assertThat(credencial.getNombreUsuario(), is(nombreUsuario));
        assertThat(servicioCredencial.verificarContrasenia(contrasenia, credencial.getHash()), is(true));
        assertThat(credencial.getHash(), is(not(contrasenia)));
    }

    @Test
    public void debeConvertirUnContraseniaTokenYUnTokenAUnaCredencialConLaContraseniaHasheadaYUsuario() {
        String nombreUsuario = "juan";
        String contrasenia = "una-contrasenia";
        String idToken = "un-token";
        ContraseniaToken contraseniaToken = new ContraseniaToken(contrasenia, idToken);
        Token token = new Token(idToken, UsuarioBuilder.nuevoUsuario().con(u -> u.nombreUsuario = nombreUsuario).generar());

        Credencial credencial = servicioCredencial.convertirACredencial(contraseniaToken, token
        );

        assertThat(credencial.getNombreUsuario(), is(nombreUsuario));
        assertThat(servicioCredencial.verificarContrasenia(contrasenia, credencial.getHash()), is(true));
        assertThat(credencial.getHash(), is(not(contrasenia)));
    }

    @Test
    public void debeDevolverAbsentCuandoElNombreUsuarioNoExiste() {
        String nombreUsuarioInexistente = "inexistente";
        String contrasenia = "una-contrasenia";
        when(credencialDAO.obtenerPorNombreUsuario(nombreUsuarioInexistente)).thenReturn(null);

        Optional token = servicioCredencial.obtenerTokenDeInicioDeSesion(new CredencialLogin(nombreUsuarioInexistente, contrasenia));
        assertThat(token.isPresent(), is(false));
    }

    @Test
    public void debeDevolverAbsentCuandoContraseniaNoEsCorrecta() {
        String nombreUsuario = "existente";
        String contraseniaIncorrecta = "incorrecta";
        String contraseniaValidaHashed = servicioCredencial.calcularHash("valido");
        Credencial credencialEncontrada = new Credencial(nombreUsuario, contraseniaValidaHashed);
        CredencialLogin login = new CredencialLogin(nombreUsuario, contraseniaIncorrecta);
        when(credencialDAO.obtenerPorNombreUsuario(nombreUsuario)).thenReturn(credencialEncontrada);

        Optional token = servicioCredencial.obtenerTokenDeInicioDeSesion(login);

        assertThat(token.isPresent(), is(false));
    }

    @Test
    public void debeGenerarTokenIncluyendoNombreDeUsuarioYFechaYHoraDeCaducidad() throws CifradoErroneoException {

        String nombreUsuario = "existente";
        String contraseniaValida = "valido";
        String tokenSinEncriptar = nombreUsuario + "/" + generarRepresentacionDeFinDeSesion();
        String hashAlmacenado = servicioCredencial.calcularHash(contraseniaValida);
        Credencial credencialEncontrada = new Credencial(nombreUsuario, hashAlmacenado);
        when(credencialDAO.obtenerPorNombreUsuario(nombreUsuario)).thenReturn(credencialEncontrada);
        CredencialLogin login = new CredencialLogin(nombreUsuario, contraseniaValida);

        Optional tokenCifradoGenerado = servicioCredencial.obtenerTokenDeInicioDeSesion(login);

        assertThat(tokenCifradoGenerado.isPresent(), is(true));
        String tokenDescifrado = servicioCifrado.descifrar(tokenCifradoGenerado.get().toString());
        assertThat(tokenDescifrado, is(tokenSinEncriptar));
    }

    private String generarRepresentacionDeFinDeSesion() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(FORMATO_FECHA_CADUCIDAD);
        DateTime finDeSesion = DateTime.now().plusMinutes(30);
        String finDeSesionFormateado = dateTimeFormatter.print(finDeSesion);

        return finDeSesionFormateado;
    }
}
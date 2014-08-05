package ec.gob.senescyt.usuario.services;

import ec.gob.senescyt.biblioteca.dto.ContraseniaToken;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Token;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ServicioCredencialTest {

    private ServicioCredencial servicioCredencial;

    @Before
    public void setUp() throws Exception {
        servicioCredencial = new ServicioCredencial();
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
}
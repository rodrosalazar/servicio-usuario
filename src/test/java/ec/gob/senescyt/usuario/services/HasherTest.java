package ec.gob.senescyt.usuario.services;

import ec.gob.senescyt.usuario.utils.Hasher;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HasherTest {

    private Hasher hasher;
    public static final String CONTRASENIA = "Super Secreta";

    @Before
    public void setUp() throws Exception {
        hasher = new Hasher();
    }

    @Test
    public void debeCalcularLaFuncionHashAUnaContrasenia() {

        String hashCalculado = hasher.calcularHash(CONTRASENIA);

        assertThat(BCrypt.checkpw(CONTRASENIA, hashCalculado), is(true));
    }

    @Test
    public void noDebeCalcularElMismoHashParaLaMismaContrasenia() {
        String primerHash = hasher.calcularHash(CONTRASENIA);
        String segundoHash = hasher.calcularHash(CONTRASENIA);

        assertThat(primerHash, is(not(segundoHash)));
    }

    @Test
    public void debeVerificarQueLaContraseniaCorrespondaAlHash() {
        String hashCalculado = hasher.calcularHash(CONTRASENIA);

        boolean corresponden = hasher.verificarHash(CONTRASENIA, hashCalculado);

        assertThat(corresponden, is(true));
    }

    @Test
    public void debeVerificarQueLaContraseniaNoCorrespondaAlHash() {
        String contraseniaErronea = "Secreto Mal";
        String hashCalculado = hasher.calcularHash(CONTRASENIA);

        boolean corresponden = hasher.verificarHash(contraseniaErronea, hashCalculado);

        assertThat(corresponden, is(false));
    }

}
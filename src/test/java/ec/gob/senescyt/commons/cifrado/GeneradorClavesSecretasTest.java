package ec.gob.senescyt.commons.cifrado;

import org.junit.Test;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GeneradorClavesSecretasTest {


    @Test
    public void debeGenerarClaveSecreta() throws IOException {
        GeneradorClavesSecretas generadorClavesSecretas = new GeneradorClavesSecretas();
        SecretKeySpec secretKeySpec = new SecretKeySpec("RAW".getBytes(),"AES");
        SecretKeySpec clave = generadorClavesSecretas.generarClaveSecretaSpec();

        assertThat(clave.getFormat(),is(secretKeySpec.getFormat()));
    }
}

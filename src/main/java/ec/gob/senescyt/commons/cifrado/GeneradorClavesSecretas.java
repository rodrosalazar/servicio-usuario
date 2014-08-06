package ec.gob.senescyt.commons.cifrado;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GeneradorClavesSecretas {

    private static final String METODO_CIFRADO = "AES";
    private String archivoClaveSecreta = "aesKey.key";

    public SecretKeySpec generarClaveSecretaSpec() throws IOException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(obtenerBytesDeArchivo(), METODO_CIFRADO);
        return secretKeySpec;
    }

    private byte[] obtenerBytesDeArchivo() throws IOException {
        return Files.readAllBytes(Paths.get(archivoClaveSecreta));
    }
}

package ec.gob.senescyt.usuario.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Hasher {

    public String calcularHash(String textoPlano) {
        return BCrypt.hashpw(textoPlano, BCrypt.gensalt());
    }

    public boolean verificarHash(String textoPlano, String hashCalculado) {
        return BCrypt.checkpw(textoPlano, hashCalculado);
    }
}

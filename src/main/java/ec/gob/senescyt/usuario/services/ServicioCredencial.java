package ec.gob.senescyt.usuario.services;

import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.dto.ContraseniaToken;
import org.mindrot.jbcrypt.BCrypt;

public class ServicioCredencial {

    public String calcularHash(String contrasenia) {
        return BCrypt.hashpw(contrasenia, BCrypt.gensalt());
    }

    public boolean verificarContrasenia(String contrasenia, String hashCalculado) {
        return BCrypt.checkpw(contrasenia, hashCalculado);
    }

    public Credencial convertirACredencial(ContraseniaToken contraseniaToken, Token token) {
        String nombreUsuario = token.getUsuario().getNombreUsuario();
        String contraseniaHasheada = calcularHash(contraseniaToken.getContrasenia());

        return new Credencial(nombreUsuario, contraseniaHasheada);
    }
}

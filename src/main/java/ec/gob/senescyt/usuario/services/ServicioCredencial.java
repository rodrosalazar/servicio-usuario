package ec.gob.senescyt.usuario.services;

import ec.gob.senescyt.usuario.dto.ContraseniaToken;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
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

    public Credencial convertirACredencialDesdeLogin(CredencialLogin credencialLogin) {
        // credencial = SELECT * FROM CREDENCIALES WHERE NOMBREUSUARIO = credencialLogin.getNombreUSuario()
        // verificarContrasenia(credencialLogin.getContrasenia(), credencial.getHash();
        String contraseniaHasheada = calcularHash(credencialLogin.getContrasenia());

        return new Credencial(credencialLogin.getNombreUsuario(), contraseniaHasheada);
    }
}

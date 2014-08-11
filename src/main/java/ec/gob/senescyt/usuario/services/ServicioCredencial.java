package ec.gob.senescyt.usuario.services;

import com.google.common.base.Optional;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dto.ContraseniaToken;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.exceptions.CifradoErroneoException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServicioCredencial {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioCredencial.class);
    private CredencialDAO credencialDAO;
    private ServicioCifrado servicioCifrado;

    public ServicioCredencial(CredencialDAO credencialDAO, ServicioCifrado servicioCifrado) {
        this.credencialDAO = credencialDAO;
        this.servicioCifrado = servicioCifrado;
    }

    public Optional<String> obtenerTokenDeInicioDeSesion(CredencialLogin credencialLogin) {
        Credencial credencialEncontrada = credencialDAO.obtenerPorNombreUsuario(credencialLogin.getNombreUsuario());

        if (credencialEncontrada != null &&
                verificarContrasenia(credencialLogin.getContrasenia(), credencialEncontrada.getHash())) {

            return Optional.fromNullable(generarToken(credencialEncontrada));
        }

        return Optional.absent();
    }

    public String generarToken(Credencial credencialEncontrada) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy/HH/mm");
        DateTime finDeSesion = new DateTime().plusMinutes(30);
        String finDeSesionFormateado = dateTimeFormatter.print(finDeSesion);
        String tokenSinEncriptar = credencialEncontrada.getNombreUsuario() + "/" + finDeSesionFormateado;

        try {
            return servicioCifrado.cifrar(tokenSinEncriptar);
        } catch (CifradoErroneoException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

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

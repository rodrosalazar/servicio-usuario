package ec.gob.senescyt.usuario.services;

import com.google.common.base.Optional;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServicioCredencial {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioCredencial.class);
    private CredencialDAO credencialDAO;
    private ServicioCifrado servicioCifrado;
    private Hasher hasher;

    public ServicioCredencial(CredencialDAO credencialDAO, ServicioCifrado servicioCifrado, Hasher hasher) {
        this.credencialDAO = credencialDAO;
        this.servicioCifrado = servicioCifrado;
        this.hasher = hasher;
    }

    public Optional<String> obtenerTokenDeInicioDeSesion(CredencialLogin credencialLogin) {
        Credencial credencialEncontrada = credencialDAO.obtenerPorNombreUsuario(credencialLogin.getNombreUsuario());

        if (credencialEncontrada != null &&
                hasher.verificarHash(credencialLogin.getContrasenia(), credencialEncontrada.getHash())) {

            return Optional.fromNullable(generarToken(credencialEncontrada));
        }

        return Optional.absent();
    }

    private String generarToken(Credencial credencialEncontrada) {
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

    public Credencial convertirACredencial(ContraseniaToken contraseniaToken, Token token) {
        String nombreUsuario = token.getUsuario().getNombreUsuario();
        String contraseniaHasheada = hasher.calcularHash(contraseniaToken.getContrasenia());

        return new Credencial(nombreUsuario, contraseniaHasheada);
    }
}


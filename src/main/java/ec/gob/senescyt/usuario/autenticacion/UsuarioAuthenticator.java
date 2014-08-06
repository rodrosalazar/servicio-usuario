package ec.gob.senescyt.usuario.autenticacion;

import com.google.common.base.Optional;
import ec.gob.senescyt.usuario.core.Credencial;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class UsuarioAuthenticator implements Authenticator<String, Credencial> {

    @Override
    public Optional<Credencial> authenticate(String credentials) throws AuthenticationException {
        return Optional.of(new Credencial("contrasenia....", ""));
    }
}

package ec.gob.senescyt.usuario.dao;

import com.sun.jersey.api.NotFoundException;
import ec.gob.senescyt.usuario.core.TokenUsuario;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class TokenUsuarioDAO extends AbstractDAO<TokenUsuario> {

    public TokenUsuarioDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public TokenUsuario buscar(String token) throws NotFoundException{
        return null;
    }
}

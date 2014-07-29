package ec.gob.senescyt.usuario.dao;

import com.sun.jersey.api.NotFoundException;
import ec.gob.senescyt.usuario.core.Token;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class TokenDAO extends AbstractDAO<Token> {

    public TokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Token buscar(String token) throws NotFoundException{
        return null;
    }
}

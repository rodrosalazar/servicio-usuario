package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Token;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class TokenDAO extends AbstractDAO<Token> {

    public TokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Token buscar(String token) {
        return null;
    }
}

package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Token;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class TokenDAO extends AbstractDAO<Token> {

    public TokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Token buscar(String idToken) {
        return get(idToken);
    }

    public void guardar(final Token token) {
        persist(token);
    }

    public void eliminar(String id) {

    }
}

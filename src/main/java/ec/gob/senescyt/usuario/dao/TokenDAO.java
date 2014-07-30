package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Token;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

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
        Token tokenAEliminar = (Token) currentSession()
                .createCriteria(Token.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

        currentSession().delete(tokenAEliminar);
    }
}

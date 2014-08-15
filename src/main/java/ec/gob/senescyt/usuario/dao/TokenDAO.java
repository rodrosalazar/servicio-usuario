package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Token;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Table;
import java.util.Optional;

public class TokenDAO extends AbstractDAO<Token> {

    private String defaultSchema;

    public TokenDAO(SessionFactory sessionFactory, String defaultSchema) {
        super(sessionFactory);
        this.defaultSchema = defaultSchema;
    }

    public Optional<Token> buscar(String idToken) {
        Token token = get(idToken);
        return Optional.ofNullable(token);
    }

    public Optional<Token> buscarPorIdUsuario(long idUsuario) {
        Token token = (Token) currentSession()
                .createCriteria(Token.class)
                .add(Restrictions.eq("usuario.id", idUsuario))
                .uniqueResult();

        return Optional.ofNullable(token);
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

    public void eliminarPorIdUsuario(long id) {
        Token tokenAEliminar = (Token) currentSession()
                .createCriteria(Token.class)
                .add(Restrictions.eq("usuario.id", id))
                .uniqueResult();

        currentSession().delete(tokenAEliminar);
    }

    public void limpiar() {
        String nombreTabla = getEntityClass().getAnnotation(Table.class).name();
        String sqlQuery = String.format("TRUNCATE %s.%s CASCADE", defaultSchema, nombreTabla);
        Query query = currentSession().createSQLQuery(sqlQuery);
        query.executeUpdate();
    }
}

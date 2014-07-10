package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Pais;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class PaisDAO extends AbstractDAO<Pais> {
    public PaisDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Pais> obtenerTodos() {
        Query query = currentSession().createQuery("SELECT p FROM Pais p");
        return query.list();
    }
}

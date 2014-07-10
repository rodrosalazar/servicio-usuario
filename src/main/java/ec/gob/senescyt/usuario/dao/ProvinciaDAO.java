package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Provincia;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class ProvinciaDAO extends AbstractDAO<Provincia> {

    public ProvinciaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Provincia> obtenerTodos() {
        Query query = currentSession().createQuery("SELECT p FROM Provincia p");

        return query.list();
    }
}

package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Etnia;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class EtniaDAO extends AbstractDAO<Etnia>{
    public EtniaDAO(SessionFactory sessionFactory) {
         super(sessionFactory);
    }

    public List<Etnia> obtenerTodos() {
        Query query = currentSession().createQuery("SELECT e FROM Etnia e");

        return query.list();
    }
}

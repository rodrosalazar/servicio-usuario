package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Institucion;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class InstitucionDAO extends AbstractDAO<Institucion>{
    public InstitucionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Institucion> obtenerTodas() {
        Query todas = currentSession().createQuery("SELECT i from Institucion i");

        return todas.list();
    }
}

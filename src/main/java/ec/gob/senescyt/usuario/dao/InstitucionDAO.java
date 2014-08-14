package ec.gob.senescyt.usuario.dao;

import com.google.common.annotations.VisibleForTesting;
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
        Query todos = currentSession().createQuery("SELECT i from Institucion i");

        return todos.list();
    }

    @VisibleForTesting
    public Institucion guardar(Institucion institucion){
        return persist(institucion);
    }

    @VisibleForTesting
    public void limpiar() {
        currentSession().createSQLQuery("TRUNCATE instituciones CASCADE ").executeUpdate();
    }
}

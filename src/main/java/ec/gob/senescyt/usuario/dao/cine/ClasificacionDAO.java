package ec.gob.senescyt.usuario.dao.cine;

import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class ClasificacionDAO extends AbstractDAO<Clasificacion> {
    public ClasificacionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Clasificacion obtenerClasificacion(String id) {
        Query consulta = currentSession().createQuery("SELECT c FROM Clasificacion AS c WHERE c.id = :id");
        consulta.setParameter("id", id);

        return (Clasificacion) consulta.uniqueResult();
    }
}

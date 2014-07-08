package ec.gob.senescyt.usuario.dao.cine;

import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class ClasificacionDAO extends AbstractDAO<Clasificacion> {
    public ClasificacionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Clasificacion obtenerClasificacion() {
        String consulta = "SELECT c FROM Clasificacion AS c WHERE c.id = '001'";
        Query cine1997 = currentSession().createQuery(consulta);

        return (Clasificacion) cine1997.uniqueResult();
    }

    public Clasificacion obtenerClasificacion2013() {
        String consulta = "SELECT c FROM Clasificacion AS c WHERE c.id = '002'";
        Query cine2013 = currentSession().createQuery(consulta);

        return (Clasificacion) cine2013.uniqueResult();
    }
}

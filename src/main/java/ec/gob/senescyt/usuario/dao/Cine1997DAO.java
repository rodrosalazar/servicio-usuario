package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class Cine1997DAO extends AbstractDAO<Clasificacion> {
    public Cine1997DAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Clasificacion obtenerClasificacion() {
        String consulta = "SELECT c FROM Clasificacion AS c WHERE c.id = '001'";
        Query cine1997 = currentSession().createQuery(consulta);

        return (Clasificacion) cine1997.uniqueResult();
    }
}

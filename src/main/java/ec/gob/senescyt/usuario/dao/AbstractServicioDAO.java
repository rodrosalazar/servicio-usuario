package ec.gob.senescyt.usuario.dao;

import com.google.common.annotations.VisibleForTesting;
import ec.gob.senescyt.usuario.core.Entidad;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.persistence.Table;

public abstract class AbstractServicioDAO<T extends Entidad> extends AbstractDAO<T> {

    public AbstractServicioDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void guardar(T entidad) {
        persist(entidad);
    }

    public T encontrarPorId(int id) {
        return get(id);
    }

    @VisibleForTesting
    public void limpiar(){
        String nombreTabla = getEntityClass().getAnnotation(Table.class).name();
        String sqlQuery = String.format("TRUNCATE %s CASCADE", nombreTabla);
        Query query = currentSession().createSQLQuery(sqlQuery);
        query.executeUpdate();
    }
}

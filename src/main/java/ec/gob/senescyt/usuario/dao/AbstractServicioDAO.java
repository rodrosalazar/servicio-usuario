package ec.gob.senescyt.usuario.dao;

import com.google.common.annotations.VisibleForTesting;
import ec.gob.senescyt.usuario.core.Entidad;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.persistence.Table;
import java.util.List;

public abstract class AbstractServicioDAO<T extends Entidad> extends AbstractDAO<T> {
    private String nombreTabla = getEntityClass().getAnnotation(Table.class).name();

    public AbstractServicioDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void guardar(T entidad) {
        persist(entidad);
    }

    public T encontrarPorId(Long id) {
        return get(id);
    }

    @VisibleForTesting
    public void limpiar(){
        String sqlQuery = String.format("TRUNCATE %s CASCADE", nombreTabla);
        Query query = currentSession().createSQLQuery(sqlQuery);
        query.executeUpdate();
    }

    public List<T> obtenerTodos() {
        return currentSession().createCriteria(getEntityClass()).list();
    }

    public void eliminar(Long id) {
        T registro = (T) currentSession().load(getEntityClass(), id);
        currentSession().delete(registro);
    }

    public String nombreDeLaColeccion() {
        return nombreTabla;
    }
}

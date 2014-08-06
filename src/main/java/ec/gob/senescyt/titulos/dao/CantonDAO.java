package ec.gob.senescyt.titulos.dao;

import ec.gob.senescyt.titulos.core.Canton;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CantonDAO extends AbstractDAO<Canton> {

    public CantonDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Canton> obtenerPorProvincia(String idProvincia) {
        return currentSession().createCriteria(Canton.class, "c")
                .add(Restrictions.eq("c.provincia.id",idProvincia)).list();
    }

    public void guardar(final Canton canton) {
        persist(canton);
    }

    public void eliminar(final String idCanton) {
        Canton canton = (Canton) currentSession().createCriteria(Canton.class, "c")
                .add(Restrictions.eq("id", idCanton)).uniqueResult();

        currentSession().delete(canton);
    }

    public Canton obtenerPorId(String idCanton) {
        return get(idCanton);
    }
}

package ec.gob.senescyt.titulos.dao;

import ec.gob.senescyt.titulos.core.TipoVisa;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class TipoVisaDAO extends AbstractDAO<TipoVisa> {
    public TipoVisaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public TipoVisa obtenerPorId(String idTipoVisa) {
        return null;
    }

    public void guardar(TipoVisa tipoVisa) {
        persist(tipoVisa);
    }

    public void eliminar(String idTipoVisa) {
        TipoVisa tipoVisa = (TipoVisa) currentSession().createCriteria(TipoVisa.class, "tv")
                .add(Restrictions.eq("tv.id", idTipoVisa)).uniqueResult();
        currentSession().delete(tipoVisa);
    }

    public List<TipoVisa> obtenerTodos() {
        return currentSession().createCriteria(TipoVisa.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
}

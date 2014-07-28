package ec.gob.senescyt.titulos.dao;

import ec.gob.senescyt.titulos.core.Canton;
import ec.gob.senescyt.titulos.core.Provincia;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class CantonDAO extends AbstractDAO<Canton>{

    public CantonDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Canton> obtenerPorProvincia(String idProvincia) {
        Query query = currentSession().createQuery("SELECT c FROM Canton c WHERE c.provincia.id = :idProvincia");
        query.setParameter("idProvincia",idProvincia);

        return query.list();
    }

    public void guardar(final Canton canton){
        persist(canton);
    }

    public void eliminar(final String idCanton) {
        Query query = currentSession().createQuery("DELETE FROM Canton c WHERE c.id =:idCanton");
        query.setParameter("idCanton", idCanton);
        query.executeUpdate();
    }

    public Canton obtenerPorId(String idCanton) {
        return get(idCanton);
    }
}

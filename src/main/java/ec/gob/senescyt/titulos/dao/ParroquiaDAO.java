package ec.gob.senescyt.titulos.dao;

import ec.gob.senescyt.titulos.core.Canton;
import ec.gob.senescyt.titulos.core.Parroquia;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class ParroquiaDAO extends AbstractDAO<Parroquia>{
    public ParroquiaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Parroquia> obtenerPorCanton(String idCanton) {
        Query query = currentSession().createQuery("SELECT p FROM Parroquia p WHERE p.canton.id = :idCanton");
        query.setParameter("idCanton",idCanton);

        return query.list();
    }

    public void guardar(Parroquia parroquia) {
        persist(parroquia);
    }

    public void eliminar(final String idParroquia) {
        Query query = currentSession().createQuery("DELETE FROM Parroquia p WHERE p.id =:idParroquia");
        query.setParameter("idParroquia", idParroquia);
        query.executeUpdate();
    }
}

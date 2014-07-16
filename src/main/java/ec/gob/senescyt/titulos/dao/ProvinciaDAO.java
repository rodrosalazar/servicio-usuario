package ec.gob.senescyt.titulos.dao;

import ec.gob.senescyt.titulos.core.Provincia;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class ProvinciaDAO extends AbstractDAO<Provincia> {

    public ProvinciaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Provincia> obtenerTodos() {
        Query query = currentSession().createQuery("SELECT p FROM Provincia p");

        return query.list();
    }

    public Provincia obtenerPorId(final String idProvincia) {
        return get(idProvincia);
    }

    public void guardar(final Provincia provincia) {
        persist(provincia);
    }

    public void eliminar(final String idProvincia) {
        Query query = currentSession().createQuery("DELETE FROM Provincia p WHERE p.id =:idProvincia");
        query.setParameter("idProvincia", idProvincia);
        query.executeUpdate();
    }
}

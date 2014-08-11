package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Perfil;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PerfilDAO extends AbstractDAO<Perfil> {

    public PerfilDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Perfil guardar(Perfil perfil) {
        return persist(perfil);
    }

    public List<Perfil> obtenerTodos() {
        Query todos = currentSession().createQuery("SELECT i from Perfil i");

        return todos.list();
    }

    public void limpiar() {
        Query query = currentSession().createSQLQuery("TRUNCATE perfiles CASCADE");
        query.executeUpdate();
    }

    public void eliminar(long id) {
        Perfil perfil = (Perfil) currentSession().createCriteria(Perfil.class, "p")
                .add(Restrictions.eq("id", id)).uniqueResult();

        currentSession().delete(perfil);
    }
}

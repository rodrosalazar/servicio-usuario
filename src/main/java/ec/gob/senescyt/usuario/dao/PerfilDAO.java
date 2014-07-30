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

    public void eliminar(long id) {
        Perfil perfilAEliminar = (Perfil) currentSession()
                .createCriteria(Perfil.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

        currentSession().delete(perfilAEliminar);
    }
}

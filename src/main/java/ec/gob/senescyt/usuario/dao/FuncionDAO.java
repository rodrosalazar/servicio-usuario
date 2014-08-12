package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Funcion;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class FuncionDAO extends AbstractDAO<Funcion>{

    public FuncionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void limpiar() {
        Query query = currentSession().createSQLQuery("TRUNCATE funciones CASCADE");
        query.executeUpdate();
    }

    public void guardar(Funcion funcion) {
        persist(funcion);
    }

    public Funcion encontrarPorId(int id) {
        return get(id);
    }
}

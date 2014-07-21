package ec.gob.senescyt.biblioteca.dao;

import ec.gob.senescyt.biblioteca.Arbol;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class ArbolDAO extends AbstractDAO<Arbol> {
    public ArbolDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Arbol obtenerPorId(Integer idArbol) {
        return get(idArbol);
    }
}

package ec.gob.senescyt.biblioteca.dao;

import ec.gob.senescyt.biblioteca.Arbol;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class ArbolDAO extends AbstractDAO<Arbol> {
    public ArbolDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Arbol obtenerPorId(Integer idArbol) {
        return get(idArbol);
    }

    public void guardar(Arbol arbolTest) {
        persist(arbolTest);
    }

    public void eliminar(Integer idArbol) {
        Query query = currentSession().createQuery("DELETE FROM Arbol c WHERE c.id =:idArbol");
        query.setParameter("idArbol", idArbol);
        query.executeUpdate();
    }
}

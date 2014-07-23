package ec.gob.senescyt.biblioteca.dao;

import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.NivelArbol;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Arbol> obtenerTodos() {
        StringBuilder hql = new StringBuilder();
        hql.append("SELECT a from Arbol a ");

        Query query = currentSession().createQuery(hql.toString());

        List<Arbol> arboles = query.list();

        for (Arbol arbol : arboles) {

            List<NivelArbol> nivelesConHijos = arbol.getNivelesArbol().stream()
                    .filter(nivelArbol -> nivelArbol.getNivelesHijos().size() > 0)
                    .collect(Collectors.toList());

            arbol.getNivelesArbol().clear();
            arbol.getNivelesArbol().addAll(nivelesConHijos);
        }

        return arboles;
    }
}

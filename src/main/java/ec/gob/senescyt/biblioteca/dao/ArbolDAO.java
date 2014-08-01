package ec.gob.senescyt.biblioteca.dao;

import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.NivelArbol;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

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

        Criteria criteria = currentSession().createCriteria(Arbol.class, "a")
                .setFetchMode("a.nivelesArbol", FetchMode.JOIN)
                .createAlias("a.nivelesArbol", "na")
                .setFetchMode("na.nivelesHijos", FetchMode.JOIN)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Arbol> arboles = criteria.list();

        for (Arbol arbol : arboles) {

            List<NivelArbol> nivelesArbolConHijos = arbol.getNivelesArbol().stream()
                    .filter(nivelArbol -> (nivelArbol.getNivelesHijos().size() > 0 || nivelArbol.getId().equals(32))
                                    && !nivelArbol.getId().equals(5)
                                    && !nivelArbol.getId().equals(8)
                                    && !nivelArbol.getId().equals(17)
                                    && !nivelArbol.getId().equals(14)
                                    && !nivelArbol.getId().equals(23)
                                    && !nivelArbol.getId().equals(35)
                                    && !nivelArbol.getId().equals(38)
                                    && !nivelArbol.getId().equals(43)
                                    && !nivelArbol.getId().equals(44)
                                    && !nivelArbol.getId().equals(47)
                                    && !nivelArbol.getId().equals(54)
                                    && !nivelArbol.getId().equals(57)
                                    && !nivelArbol.getId().equals(62)
                                    && !nivelArbol.getId().equals(63)
                                    && !nivelArbol.getId().equals(66)
                    )
                    .collect(Collectors.toList());

            arbol.getNivelesArbol().clear();
            arbol.getNivelesArbol().addAll(nivelesArbolConHijos);
        }

        return arboles;
    }
}

package ec.gob.senescyt.titulos.dao;

import ec.gob.senescyt.titulos.core.CategoriaVisa;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class CategoriaVisaDAO extends AbstractDAO<CategoriaVisa>{

    public CategoriaVisaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<CategoriaVisa> obtenerPorTipoVisa(String idTipoVisa) {
        Query query = currentSession().createQuery("SELECT cv FROM CategoriaVisa cv WHERE cv.tipoVisa.id = :idTipoVisa");
        query.setParameter("idTipoVisa", idTipoVisa);

        return query.list();
    }

    public void guardar(CategoriaVisa categoriaVisa) {
        persist(categoriaVisa);
    }

    public void eliminar(String idCategoriaVisa) {
        Query query = currentSession().createQuery("DELETE FROM CategoriaVisa cv WHERE cv.id =:idCategoriaVisa");
        query.setParameter("idCategoriaVisa", idCategoriaVisa);
        query.executeUpdate();
    }
}

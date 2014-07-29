package ec.gob.senescyt.titulos.dao;

import ec.gob.senescyt.titulos.core.CategoriaVisa;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CategoriaVisaDAO extends AbstractDAO<CategoriaVisa>{

    public CategoriaVisaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<CategoriaVisa> obtenerPorTipoVisa(String idTipoVisa) {
        return currentSession().createCriteria(CategoriaVisa.class, "cv")
                .add(Restrictions.eq("cv.tipoVisa.id", idTipoVisa)).list();
    }

    public void guardar(CategoriaVisa categoriaVisa) {
        persist(categoriaVisa);
    }

    public void eliminar(String idCategoriaVisa) {
        CategoriaVisa categoriaVisa = (CategoriaVisa) currentSession().createCriteria(CategoriaVisa.class, "cv")
                .add(Restrictions.eq("cv.id", idCategoriaVisa)).uniqueResult();
        currentSession().delete(categoriaVisa);
    }
}

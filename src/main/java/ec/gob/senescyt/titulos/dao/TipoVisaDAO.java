package ec.gob.senescyt.titulos.dao;

import ec.gob.senescyt.titulos.core.TipoVisa;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class TipoVisaDAO extends AbstractDAO<TipoVisa>{
    public TipoVisaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public TipoVisa obtenerPorId(String idTipoVisa) {
        return null;
    }

    public void guardar(TipoVisa tipoVisa) {
        persist(tipoVisa);
    }

    public void eliminar(String idTipoVisa) {
        Query query = currentSession().createQuery("DELETE FROM CategoriaVisa cv WHERE cv.id =:idTipoVisa");
        query.setParameter("idTipoVisa", idTipoVisa);
        query.executeUpdate();
    }
}

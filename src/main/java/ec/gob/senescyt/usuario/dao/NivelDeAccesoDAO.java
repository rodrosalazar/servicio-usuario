package ec.gob.senescyt.usuario.dao;

import com.google.common.annotations.VisibleForTesting;
import ec.gob.senescyt.usuario.core.NivelDeAcceso;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class NivelDeAccesoDAO extends AbstractDAO<NivelDeAcceso> {
    public NivelDeAccesoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @VisibleForTesting
    public void limpiar() {
        Query query = currentSession().createSQLQuery("TRUNCATE niveles_de_acceso CASCADE");
        query.executeUpdate();
    }

    public void guardar(NivelDeAcceso nivelDeAcceso) {
        persist(nivelDeAcceso);
    }

    public NivelDeAcceso encontrarPorId(int id) {
        return get(id);
    }
}

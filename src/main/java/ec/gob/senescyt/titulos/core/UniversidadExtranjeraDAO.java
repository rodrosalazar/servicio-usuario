package ec.gob.senescyt.titulos.core;

import ec.gob.senescyt.commons.Constantes;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class UniversidadExtranjeraDAO extends AbstractDAO<UniversidadExtranjera>{
    public UniversidadExtranjeraDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<UniversidadExtranjera> obtenerUniversidadesConvenio() {

        Query query = currentSession().createQuery("SELECT u FROM UniversidadExtranjera u WHERE u.codigoTipo =:tipoConvenio");
        query.setParameter("tipoConvenio", Constantes.CODIGO_UNIVERSIDAD_TIPO_CONVENIO);

        return query.list();
    }

    public List<UniversidadExtranjera> obtenerUniversidadesListado() {
        Query query = currentSession().createQuery("SELECT u FROM UniversidadExtranjera u WHERE u.codigoTipo =:tipoListado");
        query.setParameter("tipoListado", Constantes.CODIGO_UNIVERSIDAD_TIPO_LISTADO);

        return query.list();
    }
}

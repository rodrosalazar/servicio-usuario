package ec.gob.senescyt.titulos.dao;

import ec.gob.senescyt.titulos.core.PortadorTitulo;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class PortadorTituloDAO extends AbstractDAO<PortadorTitulo>{
    public PortadorTituloDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public PortadorTitulo guardar(final PortadorTitulo portadorTitulo){
        return persist(portadorTitulo);
    }
}

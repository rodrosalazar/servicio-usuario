package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Funcion;
import org.hibernate.SessionFactory;

public class FuncionDAO extends AbstractServicioDAO<Funcion> {

    public FuncionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}

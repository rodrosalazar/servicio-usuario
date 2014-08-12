package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Modulo;
import org.hibernate.SessionFactory;

public class ModuloDAO extends AbstractServicioDAO<Modulo>{
    public ModuloDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

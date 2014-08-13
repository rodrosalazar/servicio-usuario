package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Permiso;
import org.hibernate.SessionFactory;

public class PermisoDAO extends AbstractServicioDAO<Permiso> {
    public PermisoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

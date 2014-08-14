package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Perfil;
import org.hibernate.SessionFactory;

public class PerfilDAO extends AbstractServicioDAO<Perfil> {

    public PerfilDAO(SessionFactory sessionFactory, String defaultSchema) {
        super(sessionFactory, defaultSchema);
    }
}

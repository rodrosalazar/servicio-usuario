package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Perfil;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class PerfilDAO extends AbstractDAO<Perfil> {

    public PerfilDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Perfil guardar(Perfil perfil) {
        return persist(perfil);
    }

}

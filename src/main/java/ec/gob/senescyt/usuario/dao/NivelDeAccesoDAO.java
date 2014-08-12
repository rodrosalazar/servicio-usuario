package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.NivelDeAcceso;
import org.hibernate.SessionFactory;

public class NivelDeAccesoDAO extends AbstractRolDAO<NivelDeAcceso> {
    public NivelDeAccesoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}

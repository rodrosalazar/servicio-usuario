package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.NivelDeAcceso;
import org.hibernate.SessionFactory;

public class NivelDeAccesoDAO extends AbstractRolDAO<NivelDeAcceso> {
    public NivelDeAccesoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void limpiar() {
        truncar("niveles_de_acceso");
    }
}

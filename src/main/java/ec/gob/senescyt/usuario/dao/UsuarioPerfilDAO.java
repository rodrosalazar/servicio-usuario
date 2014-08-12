package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.UsuarioPerfil;
import org.hibernate.SessionFactory;

public class UsuarioPerfilDAO extends AbstractServicioDAO<UsuarioPerfil> {
    public UsuarioPerfilDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

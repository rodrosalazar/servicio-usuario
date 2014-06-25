package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Usuario;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class UsuarioDAO extends AbstractDAO<Usuario> {

    public UsuarioDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Usuario guardar(final Usuario usuario) {
        return persist(usuario);
    }
}

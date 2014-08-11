package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Usuario;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class UsuarioDAO extends AbstractDAO<Usuario> {

    public UsuarioDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Usuario guardar(final Usuario usuario) {
        return persist(usuario);
    }

    public boolean isRegistradoNombreUsuario(String nombreUsuario) {
        Query query = currentSession().createQuery(
                "SELECT COUNT(*) FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);

        Long usuariosRegistrados = (Long) query.uniqueResult();

        return usuariosRegistrados > 0;

    }

    public boolean isRegistradoNumeroIdentificacion(String numeroIdentificacion) {
        Query query = currentSession().createQuery(
                "SELECT COUNT(*) FROM Usuario u WHERE u.identificacion.numeroIdentificacion = :numeroIdentificacion");
        query.setParameter("numeroIdentificacion", numeroIdentificacion);

        Long usuariosRegistrados = (Long) query.uniqueResult();

        return usuariosRegistrados > 0;
    }

    public List<Usuario> obtenerTodos() {
        Criteria criteria = currentSession().createCriteria(Usuario.class);
        return criteria.list();
    }

    public void limpiar() {
        Query query = currentSession().createSQLQuery("TRUNCATE usuarios CASCADE");
        query.executeUpdate();
    }
}

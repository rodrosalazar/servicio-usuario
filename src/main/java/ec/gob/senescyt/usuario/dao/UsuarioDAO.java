package ec.gob.senescyt.usuario.dao;

import com.google.common.base.Optional;
import ec.gob.senescyt.usuario.core.Usuario;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Table;
import java.util.List;

public class UsuarioDAO extends AbstractDAO<Usuario> {

    private String defaultSchema;

    public UsuarioDAO(SessionFactory sessionFactory, String defaultSchema) {
        super(sessionFactory);
        this.defaultSchema = defaultSchema;
    }

    public Usuario guardar(final Usuario usuario) {
        return persist(usuario);
    }

    public boolean isRegistradoNombreUsuario(String nombreUsuario) {
        List usuarios = currentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("nombreUsuario", nombreUsuario))
                .list();

        return !usuarios.isEmpty();
    }

    public boolean isRegistradoNumeroIdentificacion(String numeroIdentificacion) {
        Query query = currentSession().createQuery(
                "SELECT COUNT(*) FROM Usuario u WHERE u.identificacion.numeroIdentificacion = :numeroIdentificacion");
        query.setParameter("numeroIdentificacion", numeroIdentificacion);

        Long usuariosRegistrados = (Long) query.uniqueResult();

        return usuariosRegistrados > 0;
    }

    public List<Usuario> obtenerTodos() {
        return currentSession().createCriteria(Usuario.class).list();
    }

    public void limpiar() {
        String nombreTabla = getEntityClass().getAnnotation(Table.class).name();
        String sqlQuery = String.format("TRUNCATE %s.%s CASCADE", defaultSchema, nombreTabla);
        Query query = currentSession().createSQLQuery(sqlQuery);
        query.executeUpdate();
    }

    public Usuario obtenerPorId(long id) {
        Criteria criteria = currentSession().createCriteria(Usuario.class);
        return (Usuario) criteria.add(Restrictions.eq("id", id)).uniqueResult();
    }

    public Optional<Usuario> buscarPorIdentificacion(String identificacion) {
        Criteria criteria = currentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("identificacion.numeroIdentificacion", identificacion));

        return Optional.fromNullable((Usuario) criteria.uniqueResult());
    }
}

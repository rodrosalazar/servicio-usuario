package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Credencial;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class CredencialDAO extends AbstractDAO<Credencial> {

    public CredencialDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Credencial guardar(Credencial credencial) {
        return persist(credencial);
    }

    public void limpiar() {
        Query query = currentSession().createSQLQuery("TRUNCATE credenciales CASCADE");
        query.executeUpdate();
    }

    public void eliminar(final String nombreUsuario) {
        Credencial credencial = (Credencial) currentSession().createCriteria(Credencial.class, "c")
                .add(Restrictions.eq("nombreUsuario", nombreUsuario)).uniqueResult();

        currentSession().delete(credencial);
    }

    public Credencial obtenerPorNombreUsuario(String nombreUsuario) {
        Criteria criteria = currentSession().createCriteria(Credencial.class);
        return (Credencial) criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario)).uniqueResult();
    }
}

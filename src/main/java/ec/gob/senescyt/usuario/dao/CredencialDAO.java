package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Credencial;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Table;

public class CredencialDAO extends AbstractDAO<Credencial> {

    private String defaultSchema;

    public CredencialDAO(SessionFactory sessionFactory, String defaultSchema) {
        super(sessionFactory);
        this.defaultSchema = defaultSchema;
    }

    public Credencial guardar(Credencial credencial) {
        return persist(credencial);
    }

    public void limpiar() {
        String nombreTabla = getEntityClass().getAnnotation(Table.class).name();
        String sqlQuery = String.format("TRUNCATE %s.%s CASCADE", defaultSchema, nombreTabla);
        Query query = currentSession().createSQLQuery(sqlQuery);
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

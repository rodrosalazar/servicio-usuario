package ec.gob.senescyt.usuario.dao;

import com.google.common.base.Optional;
import ec.gob.senescyt.usuario.core.Credencial;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class CredencialDAO extends AbstractDAO<Credencial>{

    public CredencialDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional validar(Credencial credencialesUsuario) {
        return Optional.absent();
    }

    public Credencial guardar(Credencial credencial) {
        return persist(credencial);
    }
}
